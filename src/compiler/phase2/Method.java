package compiler.phase2;

import java.util.ArrayList;
import java.util.HashMap;

public class Method {
    AccessModifier acc;
    Type returnt;
    String name;
    HashMap<String,TypeClass> arg;
    ArrayList<Variable> variables;
    ArrayList<Loop> loops;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Method(String name,AccessModifier acc, Type returnt ) {
        this.name = name;
        this.acc = acc;
        this.returnt = returnt;
        arg = new HashMap<>();
        variables = new ArrayList<>();
        loops = new ArrayList<>();
    }

    public AccessModifier getAcc() {
        return acc;
    }

    public void setAcc(AccessModifier acc) {
        this.acc = acc;
    }

    public Type getReturnt() {
        return returnt;
    }

    public void setReturnt(Type returnt) {
        this.returnt = returnt;
    }

    public HashMap<String, TypeClass> getArg() {
        return arg;
    }

    public void setArg(HashMap<String, TypeClass> arg) {
        this.arg = arg;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public ArrayList<Loop> getLoops() {
        return loops;
    }

    public void setLoops(ArrayList<Loop> loops) {
        this.loops = loops;
    }

    public void addArg(String argname,TypeClass typec){
        arg.put(argname,typec);
    }
    public String toString(){
        String result = "";
        result += "method = ( name : " + this.name + " )";
        result += " ( accessmodifier : " + this.acc.toString() + " )";
        result += " ( returntype : " + this.returnt.toString() + " )";
        if(arg.size() != 0){
            result += " ( parameterstype : ";
            for (String temp : this.arg.keySet()){
                result += "[" + this.arg.get(temp).getType().toString();
                if(this.arg.get(temp).isArray == true){
                    result += "[]";
                }
                result += "," + temp + "]";
            }
            result += " )";
        }
        return result;
    }
}
