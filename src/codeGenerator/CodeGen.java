package codeGenerator;

import codeGenerator.data.Data;
import codeGenerator.data.SymbolTable;
import lexer.Lexer;
import parser.CodeGenerator;

public class CodeGen implements CodeGenerator {

    private Lexer scanner;
    private StringBuilder code = new StringBuilder();
    private StringBuilder data = new StringBuilder();
    private String[] reservedKeyWords = new String[]{"void", "int", "real", "bool", "string", "class", "for", "while", "if"
                         ,"else", "return", "break", "rof", "let", "fi", "Array", "void", "in_string"
                         ,"out_string", "new", "break", "continue", "loop", "pool", "in_int", "out_int"
                         , "then", "len"};

    private Data topMostTable = new SymbolTable("top_most", null);
    private Data currentScopeTable = topMostTable;


    public CodeGen(Lexer lexer) {
        this.scanner = lexer;
        code.append(".text\n");
        code.append(".globl main\n");
        data.append(".data\n");
    }

    @Override
    public void doSemantic(String sem) {

    }

}
