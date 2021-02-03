package compiler.phase2;

import gen.MoolaListener;
import gen.MoolaParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Program implements MoolaListener {
    Scope currentScope;
    Class currentClass;
    ArrayList<Class> classes;
    Boolean isEntry = false;

    public String PrintScope(Scope scope){
        StringBuilder result = new StringBuilder();
        result.append(scope.toString()).append("\n");
        for (int i =0; i<scope.getChildren().size(); i++){
            Scope child = scope.getChildren().get(i);
            result.append(PrintScope(child));
        }
        return result.toString();
    }


    @Override
    public void enterProgram(MoolaParser.ProgramContext ctx) {
        classes = new ArrayList<>();
        currentScope = new Scope(null,"program",ctx.start.getLine());
    }

    @Override
    public void exitProgram(MoolaParser.ProgramContext ctx) {
        try {
            FileWriter myWriter = new FileWriter("src/outputs/output1.txt");
            myWriter.write(PrintScope(currentScope));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    @Override
    public void enterClassDeclaration(MoolaParser.ClassDeclarationContext ctx) {
            Class parent = null;
            if (ctx.classParent != null) {
                String parentName = ctx.classParent.toString().split("='")[1].split("'")[0];
                for (Class temp : classes) {
                    if (temp.name.contains(parentName)) {
                        parent = temp;
                    }
                }
                if (parent == null) {
                    parent = new Class(parentName, null);
                }
            }

            Class classA = new Class(ctx.ID(0).toString(), parent);
            classes.add(classA);
            currentClass = classA;
            Value value = new Value(classA);
            if(isEntry == true){
                currentScope.insert("mainClass_"+ctx.ID(0) , value);
            }
            else {
                currentScope.insert("class_" + ctx.ID(0), value);
            }
            Scope newScope = new Scope(currentScope, ctx.ID(0).getText(), ctx.start.getLine());
            currentScope.addChild(newScope);
            currentScope = newScope;

    }

    @Override
    public void exitClassDeclaration(MoolaParser.ClassDeclarationContext ctx) {
        currentScope = currentScope.getParent();
    }

    @Override
    public void enterEntryClassDeclaration(MoolaParser.EntryClassDeclarationContext ctx) {
        isEntry = true;
//        Class parent = null;
//        String temps[] = ctx.getText().split("inherits");
//        if(temps.length > 1){
//            String parentName = temps[1].split(":")[0];
//            for (Class temp : classes){
//                if(temp.name.contains(parentName)){
//                    parent = temp;
//                }
//            }
//            if(parent == null){
//                parent = new Class(parentName,null);
//            }
//        }
//
//        Class mainClass = new Class(ctx.classDeclaration().className.toString().split("='")[1].split("',")[0],parent );
//        currentClass = mainClass;
//        Value value = new Value(mainClass);
//        currentScope.insert("mainClass_"+ctx.classDeclaration().className.toString().split("='")[1].split("',")[0] , value);
//        Scope newScope = new Scope(currentScope, ctx.classDeclaration().className.toString().split("='")[1].split("',")[0] , ctx.start.getLine());
//        currentScope.addChild(newScope);
//        currentScope = newScope;
    }

    @Override
    public void exitEntryClassDeclaration(MoolaParser.EntryClassDeclarationContext ctx) {
        isEntry = false;
//        currentScope = currentScope.getParent();

    }

    @Override
    public void enterFieldDeclaration(MoolaParser.FieldDeclarationContext ctx) {
        AccessModifier AccMod;

        if (ctx.getText().contains("private")) {
            AccMod = AccessModifier.PRIVATE;
        } else {
            AccMod = AccessModifier.PUBLIC;
        }
        Type returnType = null;
        if (ctx.getText().contains("int")) {
            returnType = Type.INT;
        } else if(ctx.getText().contains("string")){
            returnType = Type.STRING;
        }else if(ctx.getText().contains("bool")){
            returnType = Type.BOOL;
        }
        Field field = new Field(AccMod, ctx.fieldName.getText(),returnType,true);
        Value value = new Value(field);
        currentScope.insert("field_"+field.getName(), value);
    }

    @Override
    public void exitFieldDeclaration(MoolaParser.FieldDeclarationContext ctx) {

    }

    @Override
    public void enterAccess_modifier(MoolaParser.Access_modifierContext ctx) {

    }

    @Override
    public void exitAccess_modifier(MoolaParser.Access_modifierContext ctx) {

    }

    @Override
    public void enterMethodDeclaration(MoolaParser.MethodDeclarationContext ctx) {
        AccessModifier AccMod;
        ArrayList<Type> parametersType = new ArrayList<>();

        if (ctx.getText().split("function")[0].contains("private")) {
            AccMod = AccessModifier.PRIVATE;
        } else {
            AccMod = AccessModifier.PUBLIC;
        }

        Type returnType = null;
        if (ctx.getText().split("returns")[1].split(":")[0].contains("int")) {
            returnType = Type.INT;
        } else if(ctx.getText().split("returns")[1].split(":")[0].contains("string")){
            returnType = Type.STRING;
        }else if(ctx.getText().split("returns")[1].split(":")[0].contains("bool")){
            returnType = Type.BOOL;
        }
        else {
            returnType = Type.CLASS;
        }
        Method method = new Method(ctx.methodName.getText(),AccMod,returnType);
        String tempstr[] =ctx.getText().split("\\(")[1].split("\\)")[0].split(":");
        String nextarg = "";
        String thisarg = "";
        String thistype;
        TypeClass tp = new TypeClass(Type.INT,false);
        for(int i = 0; i < tempstr.length ; i ++){
            if(tempstr[i].trim().isEmpty() == true){
                break;
            }
            if(i == 0){
                nextarg = tempstr[i].trim();
            }
            else if(i != tempstr.length - 1){
                thisarg = nextarg;
                String[] test = tempstr[i].split(",");
                thistype = test[0].trim();
                nextarg = test[1].trim();
                if(thistype.contains("[]")){
                    tp.setArray(true);
                }
                if(thistype.contains("bool")){
                    tp.setType(Type.BOOL);
                }
                else if(thistype.contains("string")){
                    tp.setType(Type.STRING);
                }
                else if(thistype.contains("int")){
                    tp.setType(Type.INT);
                }
                else{
                    tp.setType(Type.CLASS);
                }
                method.addArg(thisarg,new TypeClass(tp.getType(),tp.getArray()));
            }
            else{
                thistype = tempstr[i].trim();
                if(thistype.contains("[]")){
                    tp.setArray(true);
                }
                if(thistype.contains("bool")){
                    tp.setType(Type.BOOL);
                }
                else if(thistype.contains("string")){
                    tp.setType(Type.STRING);
                }
                else{
                    tp.setType(Type.CLASS);
                }
                method.addArg(nextarg,new TypeClass(tp.getType(),tp.getArray()));
            }
        }
        Value value = new Value(method);
        currentScope.insert("method_"+method.getName(),value);
        Scope newScope = new Scope(currentScope, ctx.methodName.getText(), ctx.start.getLine());
        currentScope.addChild(newScope);
        currentScope = newScope;

    }

    @Override
    public void exitMethodDeclaration(MoolaParser.MethodDeclarationContext ctx) {
        currentScope = currentScope.getParent();
    }

    @Override
    public void enterClosedStatement(MoolaParser.ClosedStatementContext ctx) {

    }

    @Override
    public void exitClosedStatement(MoolaParser.ClosedStatementContext ctx) {

    }

    @Override
    public void enterClosedConditional(MoolaParser.ClosedConditionalContext ctx) {
        if(ctx.elseStmt != null){
            Scope newScope = new Scope(currentScope, "else", ctx.start.getLine());
            currentScope.addChild(newScope);
            currentScope = newScope;
        }
        else if(ctx.elifExp != null){
            Scope newScope = new Scope(currentScope, "elif", ctx.start.getLine());
            currentScope.addChild(newScope);
            currentScope = newScope;
        }

    }

    @Override
    public void exitClosedConditional(MoolaParser.ClosedConditionalContext ctx) {
        currentScope = currentScope.getParent();
    }

    @Override
    public void enterOpenConditional(MoolaParser.OpenConditionalContext ctx) {
        if(ctx.elseStmt != null) {
            System.out.println(ctx.elifExp.getText());
        }
        Scope newScope = new Scope(currentScope, "if", ctx.start.getLine());
        currentScope.addChild(newScope);
        currentScope = newScope;
    }

    @Override
    public void exitOpenConditional(MoolaParser.OpenConditionalContext ctx) {
        currentScope = currentScope.getParent();
    }

    @Override
    public void enterOpenStatement(MoolaParser.OpenStatementContext ctx) {

    }

    @Override
    public void exitOpenStatement(MoolaParser.OpenStatementContext ctx) {

    }

    @Override
    public void enterStatement(MoolaParser.StatementContext ctx) {

    }

    @Override
    public void exitStatement(MoolaParser.StatementContext ctx) {

    }

    @Override
    public void enterStatementVarDef(MoolaParser.StatementVarDefContext ctx) {
        Variable var = new Variable(ctx.children.get(1).getText(),ctx.getText().split("=")[1].split(";")[0]);
        currentScope.insert("var_" + ctx.children.get(1).getText(),new Value(var));
    }

    @Override
    public void exitStatementVarDef(MoolaParser.StatementVarDefContext ctx) {

    }

    @Override
    public void enterStatementBlock(MoolaParser.StatementBlockContext ctx) {

    }

    @Override
    public void exitStatementBlock(MoolaParser.StatementBlockContext ctx) {

    }

    @Override
    public void enterStatementContinue(MoolaParser.StatementContinueContext ctx) {

    }

    @Override
    public void exitStatementContinue(MoolaParser.StatementContinueContext ctx) {

    }

    @Override
    public void enterStatementBreak(MoolaParser.StatementBreakContext ctx) {

    }

    @Override
    public void exitStatementBreak(MoolaParser.StatementBreakContext ctx) {

    }

    @Override
    public void enterStatementReturn(MoolaParser.StatementReturnContext ctx) {

    }

    @Override
    public void exitStatementReturn(MoolaParser.StatementReturnContext ctx) {

    }

    @Override
    public void enterStatementClosedLoop(MoolaParser.StatementClosedLoopContext ctx) {
        Scope newScope = new Scope(currentScope, "while", ctx.start.getLine());
        currentScope.addChild(newScope);
        currentScope = newScope;
    }

    @Override
    public void exitStatementClosedLoop(MoolaParser.StatementClosedLoopContext ctx) {
        currentScope = currentScope.getParent();
    }

    @Override
    public void enterStatementOpenLoop(MoolaParser.StatementOpenLoopContext ctx) {

    }

    @Override
    public void exitStatementOpenLoop(MoolaParser.StatementOpenLoopContext ctx) {

    }

    @Override
    public void enterStatementWrite(MoolaParser.StatementWriteContext ctx) {

    }

    @Override
    public void exitStatementWrite(MoolaParser.StatementWriteContext ctx) {

    }

    @Override
    public void enterStatementAssignment(MoolaParser.StatementAssignmentContext ctx) {

    }

    @Override
    public void exitStatementAssignment(MoolaParser.StatementAssignmentContext ctx) {

    }

    @Override
    public void enterStatementInc(MoolaParser.StatementIncContext ctx) {

    }

    @Override
    public void exitStatementInc(MoolaParser.StatementIncContext ctx) {

    }

    @Override
    public void enterStatementDec(MoolaParser.StatementDecContext ctx) {

    }

    @Override
    public void exitStatementDec(MoolaParser.StatementDecContext ctx) {

    }

    @Override
    public void enterExpression(MoolaParser.ExpressionContext ctx) {

    }

    @Override
    public void exitExpression(MoolaParser.ExpressionContext ctx) {

    }

    @Override
    public void enterExpressionOr(MoolaParser.ExpressionOrContext ctx) {

    }

    @Override
    public void exitExpressionOr(MoolaParser.ExpressionOrContext ctx) {

    }

    @Override
    public void enterExpressionOrTemp(MoolaParser.ExpressionOrTempContext ctx) {

    }

    @Override
    public void exitExpressionOrTemp(MoolaParser.ExpressionOrTempContext ctx) {

    }

    @Override
    public void enterExpressionAnd(MoolaParser.ExpressionAndContext ctx) {

    }

    @Override
    public void exitExpressionAnd(MoolaParser.ExpressionAndContext ctx) {

    }

    @Override
    public void enterExpressionAndTemp(MoolaParser.ExpressionAndTempContext ctx) {

    }

    @Override
    public void exitExpressionAndTemp(MoolaParser.ExpressionAndTempContext ctx) {

    }

    @Override
    public void enterExpressionEq(MoolaParser.ExpressionEqContext ctx) {

    }

    @Override
    public void exitExpressionEq(MoolaParser.ExpressionEqContext ctx) {

    }

    @Override
    public void enterExpressionEqTemp(MoolaParser.ExpressionEqTempContext ctx) {

    }

    @Override
    public void exitExpressionEqTemp(MoolaParser.ExpressionEqTempContext ctx) {

    }

    @Override
    public void enterExpressionCmp(MoolaParser.ExpressionCmpContext ctx) {

    }

    @Override
    public void exitExpressionCmp(MoolaParser.ExpressionCmpContext ctx) {

    }

    @Override
    public void enterExpressionCmpTemp(MoolaParser.ExpressionCmpTempContext ctx) {

    }

    @Override
    public void exitExpressionCmpTemp(MoolaParser.ExpressionCmpTempContext ctx) {

    }

    @Override
    public void enterExpressionAdd(MoolaParser.ExpressionAddContext ctx) {

    }

    @Override
    public void exitExpressionAdd(MoolaParser.ExpressionAddContext ctx) {

    }

    @Override
    public void enterExpressionAddTemp(MoolaParser.ExpressionAddTempContext ctx) {

    }

    @Override
    public void exitExpressionAddTemp(MoolaParser.ExpressionAddTempContext ctx) {

    }

    @Override
    public void enterExpressionMultMod(MoolaParser.ExpressionMultModContext ctx) {

    }

    @Override
    public void exitExpressionMultMod(MoolaParser.ExpressionMultModContext ctx) {

    }

    @Override
    public void enterExpressionMultModTemp(MoolaParser.ExpressionMultModTempContext ctx) {

    }

    @Override
    public void exitExpressionMultModTemp(MoolaParser.ExpressionMultModTempContext ctx) {

    }

    @Override
    public void enterExpressionUnary(MoolaParser.ExpressionUnaryContext ctx) {

    }

    @Override
    public void exitExpressionUnary(MoolaParser.ExpressionUnaryContext ctx) {

    }

    @Override
    public void enterExpressionMethods(MoolaParser.ExpressionMethodsContext ctx) {

    }

    @Override
    public void exitExpressionMethods(MoolaParser.ExpressionMethodsContext ctx) {

    }

    @Override
    public void enterExpressionMethodsTemp(MoolaParser.ExpressionMethodsTempContext ctx) {

    }

    @Override
    public void exitExpressionMethodsTemp(MoolaParser.ExpressionMethodsTempContext ctx) {

    }

    @Override
    public void enterExpressionOther(MoolaParser.ExpressionOtherContext ctx) {

    }

    @Override
    public void exitExpressionOther(MoolaParser.ExpressionOtherContext ctx) {

    }

    @Override
    public void enterMoolaType(MoolaParser.MoolaTypeContext ctx) {

    }

    @Override
    public void exitMoolaType(MoolaParser.MoolaTypeContext ctx) {

    }

    @Override
    public void enterSingleType(MoolaParser.SingleTypeContext ctx) {

    }

    @Override
    public void exitSingleType(MoolaParser.SingleTypeContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
