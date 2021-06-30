package codeGenerator.data;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable extends Data {
    public Map<String, Data> symbolTable = new HashMap<>();

    public SymbolTable(String type) {
        super(type);
    }
}
