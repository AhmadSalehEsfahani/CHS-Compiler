package codeGenerator;

import codeGenerator.data.*;
import lexer.Lexer;
import parser.CodeGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Formatter;
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
    private final static String stringMaxSize = "20";

    private boolean inMethodInputDCL = false;
    private boolean inArrayDCL = false;
    private boolean newArrayInRight = false;
    private ArrayType array;
    private Data globalData;
    private int literalCounter = 1;

    private Stack<String> semanticStack = new Stack<>();


    public CodeGen(Lexer lexer) {
        this.scanner = lexer;
        code.append(".text\n");
        code.append(".globl main\n");
        codeForGlobalRoutines();
        data.append(".data\n");
        data.append(".align 2\n");
    }

    @Override
    public void doSemantic(String sem) {
        try {
            switch (sem) {

                case "backScope":
                    currentScope = currentScope.previousScope;
                    break;

                case "checkAndPushVarId":
                    checkAndPushVarId();
                    break;

                case "varDCL":
                    varDCL();
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
                    methodDCL();
                    break;

                case "setMethodReturn":
                    setMethodReturn();
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
                    checkAndPushUsedID();
                    break;

                case "check&pushAddrForAssign":
                    checkAndPushAddrForAssign();
                    break;

                case "arrayInLeftAssign":
                    arrayInLeftAssign();
                    break;

                case "fetchArrayValue":
                    fetchArrayValue();
                    break;

                case "arrayNew":
                    arrayNew();
                    break;

                case "setLiteralInData":
                    setLiteralInData();
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

                case "out_int":
                    print_int();
                    break;

                case "out_string":
                    print_string();
                    break;

                case "out_real":
                    print_float();
                    break;

                case "in_int":
                    read_int();
                    break;

                case "in_string":
                    read_string();
                    break;

                case "in_real":
                    read_float();
                    break;

                case "finalize":
                    finalActions();
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read_float(){
        code.append("jal ").append(readFloat).append("\n");
        String reg = RegisterPool.getFloat();
        code.append("mov.s ").append(reg).append(", ").append("$f0").append("\n");
        semanticStack.push(reg);
    }

    private void read_string(){
        String address = "literalAND" + literalCounter;
        literalCounter ++;
        data.append(address).append(": .space ").append(stringMaxSize).append("\n");
        code.append("la $a0, ").append(address).append("\n");
        code.append("li $a1, ").append(stringMaxSize).append("\n");
        code.append("jal ").append(readString).append("\n");
        String regAddr = RegisterPool.getSavedTemp();
        code.append("move ").append(regAddr).append(", ").append("$a0").append("\n");
        semanticStack.push(regAddr);
    }

    private void read_int(){
        code.append("jal ").append(readInt).append("\n");
        String reg = RegisterPool.getTemp();
        code.append("move ").append(reg).append(", ").append("$v0 ").append("\n");
        semanticStack.push(reg);
    }

    private void print_float() throws CoolCompileError {
        String expr = semanticStack.pop();
        if(!expr.contains("$f")){
            throw new CoolCompileError("it is not float in out_float");
        }
        code.append("mov.s $f12, ").append(expr).append("\n");
        code.append("jal ").append(printFloat).append("\n");
        RegisterPool.backFloat(expr);
    }

    private void print_string() throws CoolCompileError {
        String strReg = semanticStack.pop();
        if(!strReg.contains("$s")){
            throw new CoolCompileError("it is not string in print_string");
        }
        code.append("move $a0, ").append(strReg).append("\n");
        code.append("jal ").append(printString).append("\n");
        RegisterPool.backSavedTemp(strReg);
    }

    private void print_int() throws CoolCompileError {
        String expr = semanticStack.pop();
        if(!expr.contains("$t")){
            throw new CoolCompileError("it is not int in print_int");
        }
        code.append("move $a0, ").append(expr).append("\n");
        code.append("jal ").append(printInt).append("\n");
        RegisterPool.backTemp(expr);
    }

    private void setLiteralInData() {
        String register;
        String literal = Lexer.STP;
        String addr = Integer.toString(literalCounter);
        literalCounter++;
        data.append("literalAND").append(addr).append(" : ").append(".ascii ")
                .append("\"").append(literal).append("\"").append("\n");
        register = RegisterPool.getSavedTemp();
        code.append("la ").append(register).append(", ")
                .append("literalAND").append(addr).append("\n");
        semanticStack.push(register);
    }

    private void arrayNew() throws CoolCompileError {
        String varType;
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
        int size = (array.address + ": .space 0").length();
        data.replace(begin, begin + size, array.address + ": .space " + Integer.parseInt(arraySize)*4);

        begin = data.indexOf(array.address + "ANDsize: .word 0");
        size = (array.address + "ANDsize: .word 0").length();
        data.replace(begin, begin + size, array.address + "ANDsize: .word " + arraySize);


        newArrayInRight = true;
        inArrayDCL = false;
        array = null;
    }

    private void arrayInLeftAssign() {
        String indexReg;
        String arrayAddrReg;
        indexReg = semanticStack.pop();
        arrayAddrReg = semanticStack.pop();

        codeForArrayCheckBound(indexReg, array.address);

        code.append("mul ").append(indexReg).append(", ").append(indexReg).append(", ").append("4").append("\n");
        code.append("add ").append(arrayAddrReg).append(", ").append(arrayAddrReg).append(", ").append(indexReg).append("\n");

        semanticStack.push(arrayAddrReg);
        RegisterPool.backTemp(indexReg);

    }

    private void checkAndPushAddrForAssign() throws CoolCompileError {
        String id;
        String register;
        id = Lexer.STP;
        if (currentScope.symbolTable.containsKey(id)) {
            globalData = currentScope.symbolTable.get(id);
        } else if (currentScope.previousScope.symbolTable.containsKey(id)) {
            globalData = currentScope.previousScope.symbolTable.get(id);
        } else {
            throw new CoolCompileError("id not defined");
        }
        if (globalData.type.equals("array"))
            array = (ArrayType) globalData;

        register = RegisterPool.getTemp();
        code.append("la ").append(register).append(", ").append(globalData.address).append("\n");
        semanticStack.push(register);
    }

    private void checkAndPushUsedID() throws CoolCompileError {
        String id;
        id = Lexer.STP;
        if (currentScope.symbolTable.containsKey(id)) {
            assignVarToReg(currentScope.symbolTable.get(id));
        } else if (currentScope.previousScope.symbolTable.containsKey(id)) {
            assignVarToReg(currentScope.previousScope.symbolTable.get(id));
        } else {
            throw new CoolCompileError("id not defined");
        }
    }

    private void setMethodReturn() {
        String returnType = semanticStack.pop();
        if (inArrayDCL) {
            returnType = returnType + "[]";
            inArrayDCL = false;
        }
        Method method = (Method) currentScope;
        method.returnType = returnType;
    }

    private void methodDCL() {
        String varID;
        varID = semanticStack.pop();
        Scope newMethod;
        if (varID.equals("main"))
            newMethod = new Method(varID, "method", varID, currentScope);
        else
            newMethod = new Method(varID, "method", currentScope.address + "AND" + varID, currentScope);
        currentScope.symbolTable.put(varID, newMethod);
        currentScope = newMethod;
        inMethodInputDCL = true;

        code.append(newMethod.address).append(":").append("\n");
    }

    private void varDCL() {
        String varType;
        String varID;
        VarType variable;
        varType = semanticStack.pop();
        varID = semanticStack.pop();
        if (inArrayDCL) {
            variable = new ArrayType(varID, "array", varType, currentScope.address + "AND" + varID, inMethodInputDCL);
        } else if (varType.equals("string")) {
            variable = new StringCool(varID, varType, currentScope.address + "AND" + varID, inMethodInputDCL, "\"\"");
        } else {
            variable = new VarType(varID, varType, currentScope.address + "AND" + varID, inMethodInputDCL);
        }

        addVarToData(variable);
        currentScope.symbolTable.put(varID, variable);
        inArrayDCL = false;
    }

    private void checkAndPushVarId() throws CoolCompileError {
        String varID;
        varID = Lexer.STP;
        if (Arrays.asList(reservedKeyWords).contains(varID)) {
            throw new CoolCompileError("reserved keywords must not be used");
        }
        if (currentScope.symbolTable.containsKey(varID)) {
            throw new CoolCompileError("ID has used");
        } else {
            semanticStack.push(varID);
        }
        return;
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
        code.append("lw ").append(newRegisterValue).append(",  ").append(registerAddress.address).append("\n");

        code.append("addi ").append(newRegisterValue).append(",  ").append(newRegisterValue).append(",  ").append(value).append("\n");

        code.append("sw ").append(newRegisterValue).append(",  ").append(registerAddress.address).append("\n");
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
        code.append("lw ").append(newRegisterValue).append(",  ").append(registerAddress.address).append("\n");

        semanticStack.push(newRegisterValue);

        code.append("addi ").append(newRegisterValue).append(",  ").append(newRegisterValue).append(",  ").append("1").append("\n");

        code.append("sw ").append(newRegisterValue).append(",  ").append(registerAddress.address).append("\n");
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
        code.append("lw ").append(newRegisterValue).append(",  ").append(registerAddress.address).append("\n");

        semanticStack.push(newRegisterValue);

        code.append("addi ").append(newRegisterValue).append(",  ").append(newRegisterValue).append(",  ").append("-1").append("\n");

        code.append("sw ").append(newRegisterValue).append(",  ").append(registerAddress.address).append("\n");
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
                code.append(floatOperator).append(" ").append(topRegister1).append(",  ")
                        .append(topRegister1).append(",  ").append(topRegister2).append("\n");


            } else {
                throw new CoolCompileError("otherwise f type are illegal");
            }

        }
    }

    private void notFunction() throws CoolCompileError {
        String topRegister = semanticStack.pop();
        if (topRegister.charAt(1) == 't') {
            code.append("neg").append(topRegister).append(",  ").append(topRegister).append("\n");
        } else if (topRegister.charAt(1) == 'f') {
            code.append("neg.s").append(topRegister).append(",  ").append(topRegister).append("\n");
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
            code.append(floatOperator).append(" ").append(topRegister1).append(",  ")
                    .append(topRegister1).append(",  ").append(topRegister2).append("\n");

        } else {
            if (topRegister1.charAt(1) == 't') {
                code.append(integerOperator).append(" ").append(topRegister1).append(",  ")
                        .append(topRegister1).append(",  ").append(topRegister2).append("\n");
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
                code.append("li ").append(reg).append(", ").append(number).append("\n");
                break;
            case "real":
                reg = RegisterPool.getFloat();
                code.append("li.s ").append(reg).append(", ").append(ConvertMethods.realStringCorrection(number)).append("\n");
                break;
            case "hex":
                reg = RegisterPool.getTemp();
                code.append("li ").append(reg).append(", ").append(ConvertMethods.hexStringCorrection(number)).append("\n");
                break;
            case "sci":
                reg = RegisterPool.getFloat();
                code.append("li.s ").append(reg).append(", ").append(ConvertMethods.sciStringCorrection(number)).append("\n");
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
                data.append(variable.address).append("ANDsize").append(": ").append(".word ").append("0").append("\n");
                break;
        }
    }

    private void assignVarToReg(Data variable) throws CoolCompileError {
        String register;
        switch (variable.type) {
            case "int":
                register = RegisterPool.getTemp();
                code.append("lw ").append(register).append(", ").append(variable.address).append("\n");
                break;
            case "real":
                register = RegisterPool.getFloat();
                code.append("l.s ").append(register).append(", ").append(variable.address).append("\n");
                break;

            case "array":
                array = (ArrayType) variable;
            case "method":
            case "string":
                register = RegisterPool.getSavedTemp();
                code.append("la ").append(register).append(", ").append(variable.address).append("\n");
                break;

            default:
                throw new CoolCompileError("this variable in expresion is invalid");
        }

        semanticStack.push(register);
    }

    private void codeForArrayCheckBound(String indexReg, String arrayAddr) {
        String sizeAddrReg = RegisterPool.getTemp();
        String compResReg = RegisterPool.getTemp();

        code.append("lw ").append(sizeAddrReg).append(", ").append(arrayAddr + "AND" + "size").append("\n");
        code.append("sge ").append(compResReg).append(", ").append(indexReg).append(", ").append(sizeAddrReg).append("\n");
        code.append("slt ").append(compResReg).append(", ").append(indexReg).append(", ").append("0").append("\n");
        code.append("beq ").append(compResReg).append(", ").append("1").append(", ").append(exceptionRoutineAddr).append("\n");

        RegisterPool.backTemp(sizeAddrReg);
        RegisterPool.backTemp(compResReg);
    }

    private void assignment() throws CoolCompileError {
        if (newArrayInRight){
            String left = semanticStack.pop();
            RegisterPool.backSavedTemp(left);
            newArrayInRight = false;
            return;
        }
        String rightReg = semanticStack.pop();
        String leftReg = semanticStack.pop();

        switch (globalData.type) {
            case "int":
            case "bool":
                if (!rightReg.contains("$t")) {
                    throw new CoolCompileError("not compatible in assignment");
                }
                code.append("sw ").append(rightReg).append(", ").append("(").append(leftReg).append(")").append("\n");
                RegisterPool.backTemp(rightReg);
                RegisterPool.backTemp(leftReg);
                break;
            case "real":
                if (!rightReg.contains("$f")) {
                    throw new CoolCompileError("not compatible in assignment");
                }
                code.append("s.s ").append(rightReg).append(", ").append("(").append(leftReg).append(")").append("\n");
                RegisterPool.backFloat(rightReg);
                RegisterPool.backFloat(leftReg);
                break;
            case "string":
            case "array":
                code.append("sw ").append(rightReg).append(", ").append("(").append(leftReg).append(")").append("\n");
                RegisterPool.backTemp(rightReg);
                RegisterPool.backSavedTemp(leftReg);
                break;
            default:
                throw new CoolCompileError("error in assignment");
        }
    }

    private void loadTrue() {
        String register = RegisterPool.getTemp();
        code.append("li ").append(register).append(", ").append("1").append("\n");
        semanticStack.push(register);
    }

    private void loadFalse() {
        String register = RegisterPool.getTemp();
        code.append("li ").append(register).append(", ").append("0").append("\n");
        semanticStack.push(register);
    }

    private void fetchArrayValue() throws CoolCompileError {
        if (array == null) {
            throw new CoolCompileError("problem in array fetch");
        }
        String indexReg = semanticStack.pop();
        String arrayAddrReg = semanticStack.pop();

        String finalValueReg;

        codeForArrayCheckBound(indexReg, array.address);
        code.append("mul ").append(indexReg).append(", ").append(indexReg).append(", ").append("4").append("\n");
        code.append("add ").append(arrayAddrReg).append(", ").append(arrayAddrReg).append(", ").append(indexReg).append("\n");

        if (array.type.equals("real")) {
            finalValueReg = RegisterPool.getFloat();
            code.append("l.s ").append(finalValueReg).append(", ").append("(").append(arrayAddrReg).append(")").append("\n");
        } else {
            finalValueReg = RegisterPool.getTemp();
            code.append("lw ").append(finalValueReg).append(", ").append("(").append(arrayAddrReg).append(")").append("\n");
        }

        semanticStack.push(finalValueReg);
        RegisterPool.backTemp(indexReg);
        RegisterPool.backSavedTemp(arrayAddrReg);
        array = null;
    }

    private void codeForPrintString() {
        code.append(printString).append(": ").append("\n");
        code.append("li $v0, 4").append("\n");
        code.append("syscall").append("\n");
        code.append("jr $ra").append("\n");
    }

    private void codeForPrintInt() {
        code.append(printInt).append(": ").append("\n");
        code.append("li $v0, 1").append("\n");
        code.append("syscall").append("\n");
        code.append("jr $ra").append("\n");
    }

    private void codeForPrintFloat() {
        code.append(printFloat).append(": ").append("\n");
        code.append("li $v0, 2").append("\n");
        code.append("syscall").append("\n");
        code.append("jr $ra").append("\n");
    }

    private void codeForReadInt() {
        code.append(readInt).append(": ").append("\n");
        code.append("li $v0, 5").append("\n");
        code.append("syscall").append("\n");
        code.append("jr $ra").append("\n");
    }

    private void codeForReadString() {
        code.append(readString).append(": ").append("\n");
        code.append("li $v0, 8").append("\n");
        code.append("syscall").append("\n");
        code.append("jr $ra").append("\n");

    }

    private void codeForReadFloat() {
        code.append(readFloat).append(": ").append("\n");
        code.append("li $v0, 6").append("\n");
        code.append("syscall").append("\n");
        code.append("jr $ra").append("\n");
    }

    private void codeForExceptionHandling() {
        code.append(exceptionRoutineAddr).append(": ").append("\n");
    }

    private void codeForTermination() {
        code.append("termination: ").append("\n");
        code.append("li $v0, 10").append("\n");
        code.append("li $t0, 0").append("\n");
        code.append("move $a0, $t0").append("\n");
        code.append("syscall").append("\n");
    }

    private void finalActions() throws FileNotFoundException {
        codeForTermination();

        Formatter formatter = new Formatter(new FileOutputStream("src/out.s"));
        formatter.format(data.toString());
        formatter.flush();
        formatter.format("\n");
        formatter.flush();
        formatter.format(code.toString());
        formatter.flush();
    }

    private void codeForGlobalRoutines() {
        codeForPrintString();
        codeForPrintInt();
        codeForPrintFloat();
        codeForReadInt();
        codeForReadString();
        codeForReadFloat();
        codeForExceptionHandling();
    }
}
