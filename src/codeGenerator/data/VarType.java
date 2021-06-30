package codeGenerator.data;

public class VarType extends Data {
    public String address;

    public VarType(String type, String address) {
        super(type);
        this.address = address;
    }
}
