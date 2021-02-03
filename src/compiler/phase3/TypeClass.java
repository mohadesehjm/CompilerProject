package compiler.phase3;

public class TypeClass {
    Type type;
    Boolean isArray;

    public TypeClass(Type type, Boolean isArray) {
        this.type = type;
        this.isArray = isArray;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getArray() {
        return isArray;
    }

    public void setArray(Boolean array) {
        isArray = array;
    }
}
