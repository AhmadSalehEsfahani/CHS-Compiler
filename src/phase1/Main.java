package phase1;

import java.io.FileReader;
import java.io.IOException; 

public class Main{

    public static void main(String[] args) throws IOException {
        
        final String fileAddress = "src/phase1/code.txt";
        Lexer lexer = new Lexer(new FileReader(fileAddress));

        while(true){
            String token = lexer.next_token();

            if(lexer.yyatEOF()){
                break;
            }
        }
    }
}