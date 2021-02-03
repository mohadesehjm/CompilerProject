package compiler.phase2;

public class Field {
    AccessModifier acc;
    String name;
    TypeClass typeClass;

    public Field(AccessModifier acc, String name,Type type, boolean isArray) {
        this.acc = acc;
        this.name = name;
        this.typeClass = new TypeClass(type,isArray);
    }

    public AccessModifier getAcc() {
        return acc;
    }

    public void setAcc(AccessModifier acc) {
        this.acc = acc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeClass getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(TypeClass typeClass) {
        this.typeClass = typeClass;
    }

    public String toString(){
        String result = "";
        result += "Field: " + "(name: " + name + ") (type: " + typeClass.type;
        if(this.typeClass.isArray == true){
            result += "[]";
        }
        result += ") (accessModifier: " + acc + ")";
        return result;
    }
}
