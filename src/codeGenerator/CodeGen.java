package codeGenerator;

import codeGenerator.data.*;
import lexer.Lexer;
import parser.CodeGenerator;

import java.util.Arrays;
import java.util.Stack;

public class CodeGen implements CodeGenerator {

    private Lexer scanner;
    private StringBuilder code = new StringBuilder();
    private StringBuilder data = new StringBuilder();
    private String[] reservedKeyWords = new String[]{"void", "int", "real", "bool", "string", "class", "for", "while", "if"
                         ,"else", "return", "break", "rof", "let", "fi", "Array", "void", "in_string"
                         ,"out_string", "new", "break", "continue", "loop", "pool", "in_int", "out_int"
                         , "then", "len", "true", "false"};

    private Scope topMostScope = new Scope("Main","class", "Main", null);
    private Scope currentScope = topMostScope;
    private final static String exceptionRoutineAddr = "exception";

    private boolean inMethodInputDCL = false;
    private boolean inArrayDCL = false;
    private ArrayType array;

    private Stack<String> semanticStack = new Stack<>();


    public CodeGen(Lexer lexer) {
        this.scanner = lexer;
        code.append(".text\n");
        code.append(".globl main\n");
        data.append(".data\n");
    }

    @Override
    public void doSemantic(String sem) {
        Scope newScope;
        String className;
        String varID;
        String varType;
        Data variable;
        String indexReg;
        String arrayAddrReg;
        String id;
        String register;

        try {

            switch (sem) {

                case "backScope":
                    currentScope = currentScope.previousScope;
                    break;

                case "checkAndPushVarId":
                    varID = Lexer.STP;
                    if(Arrays.asList(reservedKeyWords).contains(varID)){
                        throw new CoolCompileError("reserved keywords must not be used");
                    }
                    if(currentScope.symbolTable.containsKey(varID)){
                        throw new CoolCompileError("ID has used");
                    }
                    else {
                        semanticStack.push(varID);
                    }
                    break;

                case "varDCL":
                    varType = Lexer.STP;
                    varID = semanticStack.pop();
                    if(inArrayDCL){
                        variable = new ArrayType(varID, varType, currentScope.address+"&"+varID, inMethodInputDCL);
                    }
                    else {
                        if(varType.equals("string")){
                            variable = new StringCool(varID, varType, currentScope.address + "&" + varID, inMethodInputDCL, "\"\"");
                        }
                        else {
                            variable = new VarType(varID, varType, currentScope.address + "&" + varID, inMethodInputDCL);
                        }
                        addVarToData((VarType) variable);
                    }
                    currentScope.symbolTable.put(varID, variable);
                    inArrayDCL = false;
                    break;

                case "switchArrayDCL":
                    inArrayDCL = !inArrayDCL;
                    break;

                case "pushStr":
                    semanticStack.push(Lexer.STP);
                    break;


                case "switchInputDCL":
                    inMethodInputDCL = false;
                    break;

                case "methodDCL":
                    varID = semanticStack.pop();

                    Scope newMethod = new Method(varID, "method", currentScope.address+"&"+varID, currentScope);
                    currentScope.symbolTable.put(varID, newMethod);
                    currentScope = newMethod;
                    inMethodInputDCL = true;

                    code.append(newMethod.address).append(":").append("\n");

                    break;

                case "setMethodReturn":
                    String returnType = semanticStack.pop();
                    if(inArrayDCL) {
                        returnType = returnType + "[]";
                        inArrayDCL = false;
                    }
                    Method method = (Method)currentScope;
                    method.returnType = returnType;
                    break;

                case "pushDecNum" :
                    createImmAssign("dec");
                    break;

                case "pushRealNum":
                    createImmAssign("real");
                    break;

                case "pushHexNum":
                    createImmAssign("hex");
                    break;

                case "pushSciNum":
                    createImmAssign("sci");
                    break;

                case "check&pushUsedID":
                    id = Lexer.STP;
                    if(currentScope.symbolTable.containsKey(id)){
                        assignVarToReg(currentScope.symbolTable.get(id));
                    }
                    else if (currentScope.previousScope.symbolTable.containsKey(id)){
                        assignVarToReg(currentScope.previousScope.symbolTable.get(id));
                    }
                    else {
                        throw new CoolCompileError("id not defined");
                    }
                    break;

                case "check&pushAddr":
                    id = Lexer.STP;
                    if(currentScope.symbolTable.containsKey(id)){
                        variable = currentScope.symbolTable.get(id);
                    }
                    else if (currentScope.previousScope.symbolTable.containsKey(id)){
                        variable = currentScope.previousScope.symbolTable.get(id);
                    }
                    else {
                        throw new CoolCompileError("id not defined");
                    }
                    if(variable.type.equals("array"))
                        array = (ArrayType) variable;

                    register = RegisterPool.getSavedTemp();
                    code.append("la ").append(register).append(", ").append(variable.address);
                    semanticStack.push(register);
                    break;

                case "arrayInLeftAssign":
                    indexReg = semanticStack.pop();
                    arrayAddrReg = semanticStack.pop();

                    codeForArrayCheckBound(indexReg);

                    code.append("add ").append(arrayAddrReg).append(", ").append(arrayAddrReg).append(indexReg);

                    semanticStack.push(arrayAddrReg);
                    RegisterPool.backTemp(indexReg);
                    break;

                case "fetchArrayValue":
                    indexReg = semanticStack.pop();
                    arrayAddrReg = semanticStack.pop();
                    break;

                case "arrayNew":
                    varType = semanticStack.pop();
                    if (inArrayDCL){
                        varType = varType + "[]";
                    }
                    String arraySize = Lexer.STP;

                    inArrayDCL = false;
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void createImmAssign(String numType){
        String number = Lexer.STP;
        String reg = "";

        switch (numType) {
            case "dec":
                reg = RegisterPool.getTemp();
                code.append("li ").append(reg).append(" ,").append(number);
                break;
            case "real":
                reg = RegisterPool.getFloat();
                code.append("li.s ").append(reg).append(" ,").append(ConvertMethods.realStringCorrection(number));
                break;
            case "hex":
                reg = RegisterPool.getTemp();
                code.append("li ").append(reg).append(" ,").append(ConvertMethods.hexStringCorrection(number));
                break;
            case "sci":
                reg = RegisterPool.getFloat();
                code.append("li.s ").append(reg).append(" ,").append(ConvertMethods.sciStringCorrection(number));
                break;
        }

        semanticStack.push(reg);
    }

    private void addVarToData(VarType variable){
        switch (variable.type) {
            case "int":
            case "bool":
            case "void":
                data.append(variable.address).append(": ").append(".word ").append("0");
                break;
            case "real":
                data.append(variable.address).append(": ").append(".float ").append("0.0");
                break;

            case "string":
                StringCool var = (StringCool)variable;
                data.append(variable.address).append(": ").append(".ascii ").append(var.value);
                break;
        }
    }

    private void assignVarToReg(Data variable) throws CoolCompileError {
        String register;
        switch (variable.type) {
            case "int":
                register = RegisterPool.getTemp();
                code.append("lw ").append(register).append(" ,").append(variable.address).append("\n");
                break;
            case "float":
                register = RegisterPool.getFloat();
                code.append("l.s ").append(register).append(" ,").append(variable.address).append("\n");
                break;

            case "array":
            case "method":
            case "string":
                register = RegisterPool.getSavedTemp();
                code.append("la ").append(register).append(" ,").append(variable.address).append("\n");
                break;

            default:
                throw new CoolCompileError("this variable in expresion is invalid");
        }

        semanticStack.push(register);
    }

    private void codeForArrayCheckBound(String indexReg){
        String sizeAddrReg = RegisterPool.getTemp();
        String compResReg = RegisterPool.getTemp();

        code.append("lw ").append(sizeAddrReg).append(", ").append(array.address+"&"+"size");
        code.append("sge ").append(compResReg).append(", ").append(indexReg).append(", ").append(sizeAddrReg);
        code.append("slt ").append(compResReg).append(", ").append(indexReg).append(", ").append("0");
        code.append("beq ").append(compResReg).append(", ").append(exceptionRoutineAddr);

        RegisterPool.backTemp(sizeAddrReg);
        RegisterPool.backTemp(compResReg);
    }

}
