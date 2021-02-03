package compiler.phase2;

public class Value {
    Class clas;
    Method method;
    Loop loop;
    Field field;
    Variable variable;
    String type;

    public Value(Class clas){
        type = "class";
        this.clas = clas;
        this.method = null;
        this.loop = null;
        this.field = null;
        this.variable = null;
    }
    public Value(Method method) {
        type = "method";
        this.method = method;
        this.clas = null;
        this.loop = null;
        this.field = null;
        this.variable = null;
    }
    public Value(Loop loop){
        type = "loop";
        this.loop = loop;
        this.clas = null;
        this.method = null;
        this.field = null;
        this.variable = null;
    }
    public Value(Field field){
        type = "field";
        this.loop = null;
        this.clas = null;
        this.method = null;
        this.variable = null;
        this.field = field;
    }
    public Value(Variable variable){
        type = "variable";
        this.clas = null;
        this.method = null;
        this.loop = null;
        this.field = null;
        this.variable = variable;
    }

    public Class getClas() {
        return clas;
    }

    public void setClas(Class clas) {
        this.clas = clas;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Loop getLoop() {
        return loop;
    }

    public void setLoop(Loop loop) {
        this.loop = loop;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        if(clas != null){
            return clas.toString();
        }
        else if(method != null){
            return method.toString();
        }
        else if(field != null){
            return field.toString();
        }
        else if (variable != null){
            return variable.toString();
        }
        else{
            return "";
        }
    }
}
