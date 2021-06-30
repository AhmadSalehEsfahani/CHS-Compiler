import codeGenerator.CodeGen;
import lexer.Lexer;
import parser.Parser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        String inputAddress = "src/in.cool";
        String tableAddress = "src/parser/table.npt";
        Lexer scanner = new Lexer(new FileReader(inputAddress));
        CodeGen codeGenerator = new CodeGen(scanner);
        Parser parser = new Parser(scanner, codeGenerator, tableAddress);
        // For debugging parser, use bellow
        // Parser parser = new Parser(scanner, codeGenerator, tableAddress, true);
        parser.parse();
    }
}
