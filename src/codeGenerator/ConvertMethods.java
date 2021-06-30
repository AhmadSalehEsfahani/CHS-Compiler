package codeGenerator;

public class ConvertMethods {

    /*
    keep in mind that I don't pass zero before numbers that are entered by the user
    to the code generator. in some cases like scientific numbers, lack of zero before number
    cause an error but in lexer not in the code generator.
    for example, if the user enters .12E3 it causes an error in the lexer but if he or she
    enters 0.12E3 it is correct but I still pass .12E3 to the code generator.
     */


    //input string example :  1234
    public static int decStringToInt(String numberStr){
        //TODO
    }

    //input string example : .123  or  12.345
    public static float realStringToFloat(String numberStr){
        //TODO
    }

    //input string example : 12.2E+2 or 12.E2 or 1.2E-1 or .12E3
    public static float sciStringToFloat(String numberStr){
        //TODO
    }

    //input string example : x12Af4  or  X43  or  xa22b
    public static int hexStringToInt(String numberStr){
        //TODO
    }
}
