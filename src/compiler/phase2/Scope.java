package compiler.phase2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scope {
    Scope parent;
    String name;
    int start;
    HashMap<String , Value> symboltable;
    ArrayList<Scope> children;

    public Scope(Scope parent,String name, int start) {
        this.parent = parent;
        this.name = name;
        this.start = start;
        symboltable = new HashMap<>();
        children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public HashMap<String, Value> getSymboltable() {
        return symboltable;
    }

    public void setSymboltable(HashMap<String, Value> symboltable) {
        this.symboltable = symboltable;
    }

    public ArrayList<Scope> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Scope> children) {
        this.children = children;
    }

    public Scope getParent() {
        return parent;
    }

    public void setParent(Scope parent) {
        this.parent = parent;
    }

    //i'm not sure
    public void insert(String idefName, Value attrubutes){
        symboltable.put(idefName, attrubutes);
    }

    public void addChild(Scope child){
        children.add(child);
    }

    public String printItems(){
        String itemsStr = "";
        for (Map.Entry<String,Value> entry : symboltable.entrySet()) {
            itemsStr += "Key = " + entry.getKey() + "  | Value = " + entry.getValue().toString() + "\n";
        }
        return itemsStr;
    }

    public String toString() {
        return "-------------  " + name + " : " + start + "  -------------\n" +
                printItems() +
                "-----------------------------------------\n";
    }

}
