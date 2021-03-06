package lexer;
import lexer.HtmlHighlighter.HtmlHighlighter;



%%

%class Lexer
%unicode
%type String
%function next_token
%line
%column


%{
    public static HtmlHighlighter htmlHighlighter = new HtmlHighlighter();
    StringBuilder number = new StringBuilder(""); 
    public static String STP = "";
%}

 LineTerminator = \r|\n|\r\n
 InputCharacter = [^\r\n]
 WhiteSpace     = {LineTerminator} | [ \t\f]

 TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
    // Comment can be the last line of the file, without line terminator.
 EndOfLineComment     = "//" {InputCharacter}*
 Comment = {TraditionalComment} | {EndOfLineComment}


 ReservedWord = "pool"|"class"|"void"|"int"|"real"|"bool"|"string"|"for"|"while"|
                "if"|"else"|"return"|"break"|"rof"|"let"|"fi"|"Array"|"in_string"|"out_string"|
                "new"|"continue"|"loop"|"pull"|"in_int"|"out_int"|"then"|"len"|"out_real"|
                "in_real"|"true"|"false"


 Identifier = [a-zA-Z][a-zA-Z0-9_]{0,30}

 StringPattern = ([^\"\\\n]|\\.|\\\n)*



 Operators = "+"|"*"|"-"|"/"|"+="|"-="|"*="|"/="|"++"|"--"|"<"|"<="|">"|">="|"=="|"!="|"<-"|"%"|"&&"|"||"|"&"|"|"|"^"|"!"|"."|","|";"|"["|"]"|"{"|"}"|"("|")"|":"

%state STRING
%state ZERO
%state INTEGER
%state REAL
%state HEX                        


%%

    <YYINITIAL> {
        {ReservedWord} {
            String str = yytext();
            htmlHighlighter.reservedKeyWords(str);
            STP = str;
            return str;
        }

        {Operators} {
            String str = yytext();
            htmlHighlighter.operatorsAndPunctuations(yytext());
            STP = str;
            return str;
        }

        {Identifier} {
            STP = yytext();
            htmlHighlighter.Identifiers(STP);
            return "id";
        }

        {Comment} {
            htmlHighlighter.comments(yytext());
        }

        "\"" {
            htmlHighlighter.operatorsAndPunctuations("\"");
            yybegin(STRING);
        }
        

        [0-9]+ {
            String str = yytext();
            number.append(str);
            STP = number.toString();
            htmlHighlighter.integerNumbers(STP);
            number.setLength(0);
            return "decNum";
        }

        [0-9]+[.] {
            number.append(yytext());
            yybegin(REAL);
        }

        0[x|X] {
            String str = yytext();
            htmlHighlighter.specialCharacters(str);
            number.append(str);
            yybegin(HEX);
        }


        [ ] {
            htmlHighlighter.space();
        }

        [\n] {
            htmlHighlighter.newLine();
        }
    }

    <STRING> {
        {StringPattern} {
            STP = yytext();
            htmlHighlighter.stringsAndCharacters(STP);
        }
        "\"" {
            htmlHighlighter.operatorsAndPunctuations("\"");
            yybegin(YYINITIAL);
            return "str_constant";
        }
    }


    <REAL> {

        [0-9]*[E|e][\-|+]?[(0-9)]+ {
            number.append(yytext());
            STP = number.toString();
            htmlHighlighter.realNumbers(STP);
            number.setLength(0);
            yybegin(YYINITIAL);
            return "sciNum";
        }
        [0-9]* {
            number.append(yytext());
            STP = number.toString();
            htmlHighlighter.realNumbers(STP);
            number.setLength(0);
            yybegin(YYINITIAL);
            return "realNum";
        }
    }

    <HEX> {
        [0-9a-fA-F]+ {
            number.append(yytext());
            STP = number.toString();
            htmlHighlighter.integerNumbers(STP);
            number.setLength(0);
            yybegin(YYINITIAL);
            return "hexNum";
        }
    }

    [^] {
        htmlHighlighter.undefinedToken(yytext());
    }