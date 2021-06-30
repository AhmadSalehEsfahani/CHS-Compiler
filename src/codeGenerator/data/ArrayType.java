package codeGenerator.data;

public class ArrayType extends Data {
    public int size = 0;
    boolean isInput;

    public ArrayType(String name, String type, String address, boolean isInput) {
        super(name, type, address);
        this.isInput = isInput;
    }
}
