package codeGenerator.data;

public class VarType extends Data {
    boolean isInput;

    public VarType(String name, String type, String address, boolean isInput) {
        super(name, type, address);
        this.isInput = isInput;
    }
}
