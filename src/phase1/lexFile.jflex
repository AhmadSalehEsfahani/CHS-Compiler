package phase1;
import phase1.HtmlHighlighter.HtmlHighlighter;




%%

%class Lexer
%unicode
%type String
%function next_token
%line
%column


%{
    HtmlHighlighter htmlHighlighter = new HtmlHighlighter();
    StringBuilder number = new StringBuilder(""); 
%}

 LineTerminator = \r|\n|\r\n
 InputCharacter = [^\r\n]
 WhiteSpace     = {LineTerminator} | [ \t\f]

 TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
    // Comment can be the last line of the file, without line terminator.
 EndOfLineComment     = "//" {InputCharacter}*


 ReservedWord = [void|int|real|bool|string|for|while|if|else|return|break|rof|let|fi|array|void|in_string|out_string|new|continue|loop|pull|in_int|out_int|then|len]


 Identifier = [a-zA-Z][a-zA-Z0-9_]{0,30}

 StringPattern = ([^\"\\\n]|\\.|\\\n)*



 Operators = [\+|\*|\-|\/|\+=|\-=|\*=|\/=|\+\+|\-\-|<|<=|>|>=|==|\!=|<\-|%|\&\&|\|\||&|\||\^|\!|\.|,|;|\[|\]|\{|\}|\(|\)]

%state STRING
%state ZERO
%state INTEGER
%state REAL
%state HEX                        


%%

    <YYINITIAL> {
        {ReservedWord} {
            htmlHighlighter.reservedKeyWords(yytext());
        }

        {Operators} {
            htmlHighlighter.operatorsAndPunctuations(yytext());
        }

        {Identifier} {
            htmlHighlighter.Identifiers(yytext());
        }

        "\"" {
            htmlHighlighter.operatorsAndPunctuations("\"");
            yybegin(STRING);
        }

        "0" {
            number.append(yytext());
            yybegin(ZERO);
        }

        [1-9] {
            number.append(yytext());
            yybegin(INTEGER);
        }
    }

    <STRING> {
        {StringPattern} {
            htmlHighlighter.stringsAndCharacters(yytext());
        }
        "\"" {
            yybegin(YYINITIAL);
        }
    }

    <ZERO> {
        [0-9] {
            number.append(yytext());
            yybegin(INTEGER);
        }
        x|X {
            number.append(yytext());
            yybegin(HEX);
        }
        "." {
            number.append(yytext());
            yybegin(REAL);
        }
    }

    <INTEGER> {
        [0-9]* {
            number.append(yytext());
            htmlHighlighter.integerNumbers(number.toString());
            number.setLength(0);
            yybegin(YYINITIAL);
        }
        "." {
            number.append(yytext());
            yybegin(REAL);
        }
    }

    <REAL> {

        [0-9]*E[-|+]?[(0-9)]+ {
            number.append(yytext());
            htmlHighlighter.realNumbers(number.toString());
            number.setLength(0);
            yybegin(YYINITIAL);
        }
        [0-9]* {
            number.append(yytext());
            htmlHighlighter.realNumbers(number.toString());
            number.setLength(0);
            yybegin(YYINITIAL);
        }
    }

    <HEX> {
        [0-9a-fA-F]* {
            number.append(yytext());
            htmlHighlighter.integerNumbers(number.toString());
            number.setLength(0);
            yybegin(YYINITIAL);
        }
    }

    [^] {
        htmlHighlighter.undefinedToken(yytext());
    }