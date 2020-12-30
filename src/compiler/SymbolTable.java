package compiler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    String name;
    int value;
   HashMap<String , SymbolTableItem > members;
    ArrayList<Type> types;

    public SymbolTable(String name, int value, HashMap<String, SymbolTableItem> members) {
        this.name = name;
        this.value = value;
        this.members = members;
        this.types = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public HashMap<String, SymbolTableItem> getMembers() {
        return members;
    }

    public void setMembers(HashMap<String, SymbolTableItem> members) {
        this.members = members;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Type> types) { this.types = types; }

    public boolean has(String name){
        return members.containsKey(name);
    }

    public int size(){
        return members.size();
    }

    public void insert(String Name, SymbolTableItem attrubutes){
        members.put(Name, attrubutes);
    }

    public SymbolTableItem lookup(String idefName){
        return members.get(idefName);
    }

//    public String printItems(){
//        StringBuilder itemsStr = new StringBuilder();
//        for (Map.Entry<String,SymbolTableItem > entry : members.entrySet()) {
//            itemsStr.append("Key = ").append(entry.getKey()).append("  | Value = ").append(entry.getValue().toString()).append("\n");
//        }
//        return itemsStr.toString();
//
//    }

    public String printItems(){
        String itemsStr = "";
        for (Map.Entry<String,SymbolTableItem> entry : members.entrySet()) {
            itemsStr += "Key = " + entry.getKey() + "  | Value = " + entry.getValue() + "\n";
        }
        return itemsStr;
    }

    public String toString() {
        return "-------------  " + name + " : " + value + "  -------------\n" +
                printItems() +
                "-----------------------------------------\n";
    }



}
