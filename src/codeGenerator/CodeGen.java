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
                         , "then", "len"};

    private Scope topMostScope = new Scope("program","scope", null, null);
    private Scope currentScope = topMostScope;

    private boolean inMethodInputDCL = false;
    private boolean inArrayDCL = false;

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

        try {

            switch (sem) {
                case "classDCL":
                    className = Lexer.STP;
                    if(Arrays.asList(reservedKeyWords).contains(className)){
                        throw new CoolCompileError("reserved keywords must not be used");
                    }
                    if (currentScope.symbolTable.containsKey(className)) {
                        throw new CoolCompileError("repetitive class name");
                    }
                    else {
                        newScope = new Scope(className, "class", className, currentScope);
                        currentScope.symbolTable.put(className, newScope);
                        currentScope = newScope;
                    }
                    break;

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
                        //TODO code generate for .data
                    }
                    else {
                        variable = new VarType(varID, varType, currentScope.address + "&" + varID, inMethodInputDCL);
                        //TODO code generate for .data
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

                case "checkTypeIdAndPush":
                    varID = Lexer.STP;
                    if(!topMostScope.symbolTable.containsKey(varID)){
                        throw new CoolCompileError("Undefined Type");
                    }
                    else {
                        semanticStack.push(varID);
                    }
                    break;

                case "switchInputDCL":
                    inMethodInputDCL = !inMethodInputDCL;
                    break;

                case "methodDCL":
                    varID = semanticStack.pop();

                    Scope newMethod = new Method(varID, "method", currentScope.address + "&" + varID, currentScope);
                    currentScope.symbolTable.put(varID, newMethod);
                    currentScope = newMethod;
                    inMethodInputDCL = !inMethodInputDCL;

                    //TODO add assembly code for method label

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
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void createImmAssign(String numType){
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
}
