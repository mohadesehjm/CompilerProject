package compiler.phase3;

import java.util.ArrayList;

public class Class {
    String name;
    Class inherit = null;
    ArrayList<Method> methods;
    ArrayList<Field> fields;

    public Class(String name, Class inherit) {
        this.name = name;
        this.inherit = inherit;
        methods = new ArrayList<>();
        fields = new ArrayList<>();
    }

    public Class(String name) {
        this.name = name;
        methods = new ArrayList<>();
        fields = new ArrayList<>();
    }
    public Class getInherit() {
        return inherit;
    }

    public void setInherit(Class inherit) {
        this.inherit = inherit;
    }

    public ArrayList<Method> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<Method> methods) {
        this.methods = methods;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        String result = "";
        result += "class = ( name : " + this.name + " )";
        if(this.inherit != null){
            result += " ( inherits : " + this.inherit.name + " )";
        }
        return result;
    }
}
