package compiler;

import java.util.ArrayList;

public class Type {
    String name;
    boolean isDefined;


    public Type(String name){
        this.name = name;
        ArrayList<String > defineds = new ArrayList<>();
        defineds.add("String");
        defineds.add("int");
        defineds.add("boolean");
        if(defineds.contains(this.name)){
            this.isDefined = true;
        }
        else{
            isDefined = false;
        }
    }

    public String toString(){
        String st = "";
        String n = this.name;
        if (n.endsWith("[]")){
            st += "array of ";
            n = this.name.substring(0, this.name.length() - 2);
        }
        if(n.startsWith("int")){
            st += "int";
        }
        else if(n.startsWith("boolean")){
            st += "bool";
        }
        else if(n.startsWith("void")){
            st += "void";
        }
        else{
            st += "[classType = " + n + ", isDefined = " + isDefined + "]";
        }
        return st;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isDefined() {
        return isDefined;
    }

    public void setDefined(boolean defined) {
        isDefined = defined;
    }
}
