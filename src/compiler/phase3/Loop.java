package compiler.phase3;

import java.util.ArrayList;

public class Loop {
    ArrayList<Variable> variables;
    public Loop(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }
}
