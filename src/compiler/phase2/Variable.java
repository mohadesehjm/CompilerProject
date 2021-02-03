package compiler.phase2;

public class Variable {

    String name;
    String assign;

    public void setAssign(String assign) {
        this.assign = assign;
    }

    public String getAssign() {
        return assign;
    }

    public Variable(String name, String assign) {
        this.name = name;
        this.assign = assign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String toString(){
        return "( name : " + name + " )" + " ( assign: " + assign + " )";
    }
}
