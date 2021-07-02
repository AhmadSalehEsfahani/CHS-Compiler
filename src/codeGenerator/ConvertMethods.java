package codeGenerator;

public class ConvertMethods {

    /*
    keep in mind that I don't pass zero before numbers that are entered by the user
    to the code generator. in some cases like scientific numbers, lack of zero before number
    cause an error but in lexer not in the code generator.
    for example, if the user enters .12E3 it causes an error in the lexer but if he or she
    enters 0.12E3 it is correct but I still pass .12E3 to the code generator.
     */


    //input string example : .123  or  12.345  ---> just put zero before this .12
    public static String realStringCorrection(String numberStr) {
        String result = numberStr;
        if (result.charAt(0) == '.') {
            result = '0' + result;
        }
        return result;
    }

    //input string example : 12.2E+2 or 12.E2 or 1.2E-1 or .12E3 ---> just put zero before this .12
    //and change E to lower case
    public static String sciStringCorrection(String numberStr) {
        String result = numberStr;
        if (result.charAt(0) == '.') {
            result = '0' + result;
        }
        return result.replace('E', 'e');
    }

    //input string example : x12Af4  or  X43  or  xa22b --->
    //just put zero before x and change x to lower case
    public static String hexStringCorrection(String numberStr) {
        return "0x" + numberStr.substring(1);
    }
}
