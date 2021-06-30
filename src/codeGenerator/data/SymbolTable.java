package codeGenerator.data;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable extends Data {
    public Map<String, Data> symbolTable = new HashMap<>();
    public SymbolTable previousScopeTable;

    public SymbolTable(String type, SymbolTable previousScopeTable) {
        super(type);
        this.previousScopeTable = previousScopeTable;
    }
}
