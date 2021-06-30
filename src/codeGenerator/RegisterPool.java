package codeGenerator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class RegisterPool {
    private static Queue<String> temporaryRegisters = new LinkedList<>();
    private static Queue<String> savedTemporaryRegisters = new LinkedList<>();
    private static Queue<String> argumentRegisters = new LinkedList<>();
    private static Queue<String> funcResultRegisters = new LinkedList<>();

    static {
        temporaryRegisters.addAll(Arrays.asList("$t0", "$t1", "$t2", "$t3",
                                                "$t4", "$t5", "$t6", "$t7", "$t8", "$t9"));
        savedTemporaryRegisters.addAll(Arrays.asList("$s0", "$s1", "$s2", "$s3", "$s4", "$s5"
                                                        , "$s6", "$s7"));
        argumentRegisters.addAll(Arrays.asList("$a0", "$a1", "$a2", "$a3"));
        funcResultRegisters.addAll(Arrays.asList("$v0", "$v1"));
    }

    public static String getSP(){
        return "$sp";
    }

    public static String getGP(){
        return "$gp";
    }

    public static String getRA(){
        return "$ra";
    }

    public static String getTemp(){
        return temporaryRegisters.remove();
    }

    public static String getSavedTemp(){
        return savedTemporaryRegisters.remove();
    }

    public static String getArg(){
        return argumentRegisters.remove();
    }

    public static String getFuncRes(){
        return funcResultRegisters.remove();
    }

    public static void backTemp(String reg){
        temporaryRegisters.add(reg);
    }

    public static void backSavedTemp(String reg){
        savedTemporaryRegisters.add(reg);
    }

    public static void backArg(String reg){
        argumentRegisters.add(reg);
    }

    public static void backFuncRes(String reg){
        funcResultRegisters.add(reg);
    }

}
