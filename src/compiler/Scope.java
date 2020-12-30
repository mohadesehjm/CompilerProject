package compiler;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope {

    SymbolTable symbolTable;
    Scope parent;
    String name;
    int value;
    ArrayList<Scope> children;

    public Scope(Scope parent, String name, int value) {
        this.parent = parent;
        this.name = name;
        this.value = value;
        HashMap<String , SymbolTableItem > st = new HashMap<>();
        this.symbolTable = new SymbolTable(this.name, this.value, st);
        children = new ArrayList<>();
    }
    public Scope(SymbolTable symbolTable, Scope parent, String name){
        this.symbolTable = symbolTable;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public void setChildren(ArrayList<Scope> children) {
        this.children = children;
    }

    public ArrayList<Scope> getChildren() {
        return children;
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public Scope getParent() {
        return parent;
    }

    public void setParent(Scope parent) {
        this.parent = parent;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void insert(String name, SymbolTableItem atr){
        this.symbolTable.insert(name, atr);
    }

    public void addChild(Scope child){
        children.add(child);
    }


}
