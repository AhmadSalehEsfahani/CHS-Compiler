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
            , "else", "return", "break", "rof", "let", "fi", "Array", "void", "in_string"
            , "out_string", "new", "break", "continue", "loop", "pool", "in_int", "out_int"
            , "then", "len", "true", "false"};

    private Scope topMostScope = new Scope("Main", "class", "Main", null);
    private Scope currentScope = topMostScope;

    //common routines name
    private final static String exceptionRoutineAddr = "exception";
    private final static String printInt = "print_int";
    private final static String printFloat = "print_float";
    private final static String printString = "print_string";
    private final static String readInt = "read_int";
    private final static String readFloat = "read_float";
    private final static String readString = "read_string";


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
                    if (Arrays.asList(reservedKeyWords).contains(varID)) {
                        throw new CoolCompileError("reserved keywords must not be used");
                    }
                    if (currentScope.symbolTable.containsKey(varID)) {
                        throw new CoolCompileError("ID has used");
                    } else {
                        semanticStack.push(varID);
                    }
                    break;

                case "varDCL":
                    varType = Lexer.STP;
                    varID = semanticStack.pop();
                    if (inArrayDCL) {
                        variable = new ArrayType(varID, "array", varType, currentScope.address + "&" + varID, inMethodInputDCL);
                    } else if (varType.equals("string")) {
                        variable = new StringCool(varID, varType, currentScope.address + "&" + varID, inMethodInputDCL, "\"\"");
                    } else {
                        variable = new VarType(varID, varType, currentScope.address + "&" + varID, inMethodInputDCL);
                    }

                    addVarToData((VarType) variable);
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

                    Scope newMethod = new Method(varID, "method", currentScope.address + "&" + varID, currentScope);
                    currentScope.symbolTable.put(varID, newMethod);
                    currentScope = newMethod;
                    inMethodInputDCL = true;

                    code.append(newMethod.address).append(":").append("\n");

                    break;

                case "setMethodReturn":
                    String returnType = semanticStack.pop();
                    if (inArrayDCL) {
                        returnType = returnType + "[]";
                        inArrayDCL = false;
                    }
                    Method method = (Method) currentScope;
                    method.returnType = returnType;
                    break;

                case "pushDecNum":
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
                    if (currentScope.symbolTable.containsKey(id)) {
                        assignVarToReg(currentScope.symbolTable.get(id));
                    } else if (currentScope.previousScope.symbolTable.containsKey(id)) {
                        assignVarToReg(currentScope.previousScope.symbolTable.get(id));
                    } else {
                        throw new CoolCompileError("id not defined");
                    }
                    break;

                case "check&pushAddr":
                    id = Lexer.STP;
                    if (currentScope.symbolTable.containsKey(id)) {
                        variable = currentScope.symbolTable.get(id);
                    } else if (currentScope.previousScope.symbolTable.containsKey(id)) {
                        variable = currentScope.previousScope.symbolTable.get(id);
                    } else {
                        throw new CoolCompileError("id not defined");
                    }
                    if (variable.type.equals("array"))
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
                    if (array == null) {
                        throw new CoolCompileError("problem in create array");
                    }
                    if (inArrayDCL) {
                        throw new CoolCompileError("array type can not be array");
                    }
                    if (!varType.equals(array.arrayType)) {
                        throw new CoolCompileError("array type not compatible with assign");
                    }
                    String arraySize = Lexer.STP;
                    array.size = Integer.parseInt(arraySize);

                    int begin = data.indexOf(array.address + ": .space 0");
                    int end = data.lastIndexOf(array.address + ": .space 0");
                    data.replace(begin, end + 1, array.address + ": .space " + arraySize);

                    begin = data.indexOf(array.address + "&size: .word 0");
                    end = data.lastIndexOf(array.address + "&size: .word 0");
                    data.replace(begin, end + 1, array.address + "&size: .word " + arraySize);

                    inArrayDCL = false;
                    array = null;
                    break;

                case "setLiteralInData":
                    String literal = Lexer.STP;
                    data.append("literal&").append(literal).append(" : ").append(".ascii ")
                            .append("\"").append(literal).append("\"").append("\n");
                    register = RegisterPool.getSavedTemp();
                    code.append("la ").append(register).append(", ")
                            .append("literal&").append(literal).append("\n");
                    semanticStack.push(register);
                    break;

                case "assign":
                    assignment();
                    break;

                case "loadTrue":
                    loadTrue();
                    break;

                case "loadFalse":
                    loadFalse();
                    break;


                case "logicalORR":
                    expressionFunctions("or", null);
                    break;

                case "logicalAND":
                    expressionFunctions("and", null);
                    break;

                case "bitwiseOR":
                    expressionFunctions("or", null);
                    break;

                case "bitwiseXOR":
                    expressionFunctions("xor", null);
                    break;

                case "bitwiseAND":
                    expressionFunctions("and", null);
                    break;

                case "equality":
                    compressionFunction("seq", "");
                    break;

                case "notEquality":
                    compressionFunction("sne", "");
                    break;

                case "greaterThan":
                    compressionFunction("sgt", "");
                    break;

                case "greaterThanEqual":
                    compressionFunction("sge", "");
                    break;

                case "lessThan":
                    compressionFunction("slt", "");
                    break;

                case "lessThanEqual":
                    compressionFunction("sle", "");
                    break;

                case "addExpr":
                    expressionFunctions("add", "add.s");
                    break;

                case "subExpr":
                    expressionFunctions("sub", "sub.s");
                    break;

                case "mulExpr":
                    expressionFunctions("mulo", "mul.s");
                    break;

                case "divExpr":
                    expressionFunctions("div", "div.s");
                    break;

                case "remExpr":
                    expressionFunctions("rem", null);
                    break;

                case "notNum":
                    notFunction();
                    break;

                case "add1Before":
                    unaryValueBefore(1);
                    break;

                case "sub1Before":
                    unaryValueBefore(-1);
                    break;

                case "add1After":
                    //TODO handle this in graph
                    add1After();
                    break;

                case "sub1After":
                    //TODO handle this in graph
                    sub1After();
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void unaryValueBefore(int value) throws CoolCompileError {
        String id = Lexer.STP;
        Data registerAddress;
        if (currentScope.symbolTable.containsKey(id)) {
            registerAddress = currentScope.symbolTable.get(id);
        } else if (currentScope.previousScope.symbolTable.containsKey(id)) {
            registerAddress = currentScope.previousScope.symbolTable.get(id);
        } else {
            throw new CoolCompileError("id not defined");
        }
        String newRegisterValue = RegisterPool.getTemp();
        code.append("lw ").append(newRegisterValue).append(" , ").append(registerAddress.address).append("\n");

        code.append("addi ").append(newRegisterValue).append(" , ").append(newRegisterValue).append(" , ").append(value).append("\n");

        code.append("sw ").append(newRegisterValue).append(" , ").append(registerAddress.address).append("\n");
        semanticStack.push(newRegisterValue);
    }

    private void add1After() throws CoolCompileError {
        String id = Lexer.STP;
        Data registerAddress;
        if (currentScope.symbolTable.containsKey(id)) {
            registerAddress = currentScope.symbolTable.get(id);
        } else if (currentScope.previousScope.symbolTable.containsKey(id)) {
            registerAddress = currentScope.previousScope.symbolTable.get(id);
        } else {
            throw new CoolCompileError("id not defined");
        }
        String newRegisterValue = RegisterPool.getTemp();
        code.append("lw ").append(newRegisterValue).append(" , ").append(registerAddress.address).append("\n");

        semanticStack.push(newRegisterValue);

        code.append("addi ").append(newRegisterValue).append(" , ").append(newRegisterValue).append(" , ").append("1").append("\n");

        code.append("sw ").append(newRegisterValue).append(" , ").append(registerAddress.address).append("\n");
    }

    private void sub1After() throws CoolCompileError {
        String id = Lexer.STP;
        Data registerAddress;
        if (currentScope.symbolTable.containsKey(id)) {
            registerAddress = currentScope.symbolTable.get(id);
        } else if (currentScope.previousScope.symbolTable.containsKey(id)) {
            registerAddress = currentScope.previousScope.symbolTable.get(id);
        } else {
            throw new CoolCompileError("id not defined");
        }
        String newRegisterValue = RegisterPool.getTemp();
        code.append("lw ").append(newRegisterValue).append(" , ").append(registerAddress.address).append("\n");

        semanticStack.push(newRegisterValue);

        code.append("addi ").append(newRegisterValue).append(" , ").append(newRegisterValue).append(" , ").append("-1").append("\n");

        code.append("sw ").append(newRegisterValue).append(" , ").append(registerAddress.address).append("\n");
    }

    private void compressionFunction(String integerOperator, String floatOperator) throws CoolCompileError {
        if (semanticStack.peek().charAt(1) == 't') {
            expressionFunctions(integerOperator, null);
        } else {
            String topRegister1 = semanticStack.pop();
            String topRegister2 = semanticStack.pop();

            if (topRegister1.charAt(1) != topRegister2.charAt(1)) {
                throw new CoolCompileError("type of two operand is different");
            }
            if (topRegister1.charAt(1) == 'f') {
                //TODO add branch compare for flout numbers
                code.append(floatOperator).append(" ").append(topRegister1).append(" , ")
                        .append(topRegister1).append(" , ").append(topRegister2).append("\n");


            } else {
                throw new CoolCompileError("otherwise f type are illegal");
            }

        }
    }

    private void notFunction() throws CoolCompileError {
        String topRegister = semanticStack.pop();
        if (topRegister.charAt(1) == 't') {
            code.append("neg").append(topRegister).append(" , ").append(topRegister);
        } else if (topRegister.charAt(1) == 'f') {
            code.append("neg.s").append(topRegister).append(" , ").append(topRegister);
        } else {
            throw new CoolCompileError("not operand reject for string numbers");
        }

        semanticStack.push(topRegister);
    }

    private void expressionFunctions(String integerOperator, String floatOperator) throws CoolCompileError {
        //if in boolean operands send numbers bigger than 1 its do ISMP behavior
        String topRegister1 = semanticStack.pop();
        String topRegister2 = semanticStack.pop();

        if (topRegister1.charAt(1) != topRegister2.charAt(1)) {
            throw new CoolCompileError("type of two operand is different");
        }

        if (floatOperator != null && topRegister1.charAt(1) == 'f') {
            code.append(floatOperator).append(" ").append(topRegister1).append(" , ")
                    .append(topRegister1).append(" , ").append(topRegister2).append("\n");

        } else {
            if (topRegister1.charAt(1) == 't') {
                code.append(integerOperator).append(" ").append(topRegister1).append(" , ")
                        .append(topRegister1).append(" , ").append(topRegister2).append("\n");
            } else {
                throw new CoolCompileError("otherwise f,t type are illegal");
            }
        }
        RegisterPool.backTemp(topRegister2);
        semanticStack.push(topRegister1);
    }

    private void createImmAssign(String numType) {
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

    private void addVarToData(VarType variable) {
        switch (variable.type) {
            case "int":
            case "bool":
            case "void":
            case "string":
                data.append(variable.address).append(": ").append(".word ").append("0").append("\n");
                break;
            case "real":
                data.append(variable.address).append(": ").append(".float ").append("0.0").append("\n");
                break;

            case "array":
                data.append(variable.address).append(": ").append(".space ").append("0").append("\n");
                data.append(variable.address).append("&size ").append(": ").append(".word ").append("0").append("\n");
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

    private void codeForArrayCheckBound(String indexReg) {
        String sizeAddrReg = RegisterPool.getTemp();
        String compResReg = RegisterPool.getTemp();

        code.append("lw ").append(sizeAddrReg).append(", ").append(array.address + "&" + "size").append("\n");
        code.append("sge ").append(compResReg).append(", ").append(indexReg).append(", ").append(sizeAddrReg).append("\n");
        code.append("slt ").append(compResReg).append(", ").append(indexReg).append(", ").append("0").append("\n");
        code.append("beq ").append(compResReg).append(", ").append(exceptionRoutineAddr);

        RegisterPool.backTemp(sizeAddrReg);
        RegisterPool.backTemp(compResReg);
    }

    private void assignment() throws CoolCompileError {
        String rightReg = semanticStack.pop();
        String leftReg = semanticStack.pop();

        if (leftReg.contains("$t")) {
            if (!rightReg.contains("$t")) {
                throw new CoolCompileError("not compatible in assignment");
            }
            code.append("sw ").append(rightReg).append(", ").append("(").append(leftReg).append(")").append("\n");
            RegisterPool.backTemp(rightReg);
            RegisterPool.backTemp(leftReg);
        } else if (leftReg.contains("$f")) {
            if (!rightReg.contains("$f")) {
                throw new CoolCompileError("not compatible in assignment");
            }
            code.append("s.s ").append(rightReg).append(", ").append("(").append(leftReg).append(")").append("\n");
            RegisterPool.backFloat(rightReg);
            RegisterPool.backFloat(leftReg);
        } else if (leftReg.contains("$s")) {
            if (!rightReg.contains("$s")) {
                throw new CoolCompileError("not compatible in assignment");
            }
            code.append("sw ").append(rightReg).append(", ").append("(").append(leftReg).append(")").append("\n");
            RegisterPool.backSavedTemp(rightReg);
            RegisterPool.backSavedTemp(leftReg);
        } else {
            throw new CoolCompileError("error in assignment");
        }
    }

    private void loadTrue() {
        String register = RegisterPool.getTemp();
        code.append("li ").append(register).append(" ,").append("1").append("\n");
        semanticStack.push(register);
    }

    private void loadFalse() {
        String register = RegisterPool.getTemp();
        code.append("li ").append(register).append(" ,").append("0").append("\n");
        semanticStack.push(register);
    }
}
