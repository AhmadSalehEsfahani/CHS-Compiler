package codeGenerator.data;

public class ArrayType extends Data {
    public String address;
    public int lowerBound;
    public int upperBound;

    public ArrayType(String type, String address, int lowerBound, int upperBound) {
        super(type);
        this.address = address;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
}
