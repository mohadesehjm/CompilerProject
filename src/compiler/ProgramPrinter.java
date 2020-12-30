//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package compiler;

import gen.MoolaListener;
import gen.MoolaParser.Access_modifierContext;
import gen.MoolaParser.ClassDeclarationContext;
import gen.MoolaParser.ClosedConditionalContext;
import gen.MoolaParser.ClosedStatementContext;
import gen.MoolaParser.EntryClassDeclarationContext;
import gen.MoolaParser.ExpressionAddContext;
import gen.MoolaParser.ExpressionAddTempContext;
import gen.MoolaParser.ExpressionAndContext;
import gen.MoolaParser.ExpressionAndTempContext;
import gen.MoolaParser.ExpressionCmpContext;
import gen.MoolaParser.ExpressionCmpTempContext;
import gen.MoolaParser.ExpressionContext;
import gen.MoolaParser.ExpressionEqContext;
import gen.MoolaParser.ExpressionEqTempContext;
import gen.MoolaParser.ExpressionMethodsContext;
import gen.MoolaParser.ExpressionMethodsTempContext;
import gen.MoolaParser.ExpressionMultModContext;
import gen.MoolaParser.ExpressionMultModTempContext;
import gen.MoolaParser.ExpressionOrContext;
import gen.MoolaParser.ExpressionOrTempContext;
import gen.MoolaParser.ExpressionOtherContext;
import gen.MoolaParser.ExpressionUnaryContext;
import gen.MoolaParser.FieldDeclarationContext;
import gen.MoolaParser.MethodDeclarationContext;
import gen.MoolaParser.MoolaTypeContext;
import gen.MoolaParser.OpenConditionalContext;
import gen.MoolaParser.OpenStatementContext;
import gen.MoolaParser.ProgramContext;
import gen.MoolaParser.SingleTypeContext;
import gen.MoolaParser.StatementAssignmentContext;
import gen.MoolaParser.StatementBlockContext;
import gen.MoolaParser.StatementBreakContext;
import gen.MoolaParser.StatementClosedLoopContext;
import gen.MoolaParser.StatementContext;
import gen.MoolaParser.StatementContinueContext;
import gen.MoolaParser.StatementDecContext;
import gen.MoolaParser.StatementIncContext;
import gen.MoolaParser.StatementOpenLoopContext;
import gen.MoolaParser.StatementReturnContext;
import gen.MoolaParser.StatementVarDefContext;
import gen.MoolaParser.StatementWriteContext;
import java.io.FileWriter;
import java.io.IOException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ProgramPrinter implements MoolaListener {
    String program = "";
    int tab = 0;
    int entry = 0;

    public ProgramPrinter() {
    }

    public void enterProgram(ProgramContext ctx) {
        this.program = this.program + "start program{\n";
        ++this.tab;
    }

    public void exitProgram(ProgramContext ctx) {
        this.program = this.program + "}\n";

        try {
            FileWriter myWriter = new FileWriter("src/outputs/output3.txt");
            myWriter.write(this.program);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException var3) {
            System.out.println("An error occurred.");
            var3.printStackTrace();
        }

    }

    public void enterClassDeclaration(ClassDeclarationContext ctx) {
        if (this.entry == 0) {
            for(int i = 0; i < this.tab; ++i) {
                this.program = this.program + "\t";
            }

            String var10001 = this.program;
            this.program = var10001 + "class: " + ctx.ID(0);
            if (ctx.classParent != null) {
                var10001 = this.program;
                this.program = var10001 + "/ class parent: (" + ctx.classParent.toString().split("='")[1].split("'")[0] + ")";
            }

            this.program = this.program + " {\n";
            ++this.tab;
        }

    }

    public void exitClassDeclaration(ClassDeclarationContext ctx) {
        if (this.entry == 0) {
            for(int i = 0; i < this.tab - 1; ++i) {
                this.program = this.program + "\t";
            }

            this.program = this.program + "}\n";
            --this.tab;
        }

    }

    public void enterEntryClassDeclaration(EntryClassDeclarationContext ctx) {
        this.entry = 1;

        for(int i = 0; i < this.tab; ++i) {
            this.program = this.program + "\t";
        }

        String var10001 = this.program;
        this.program = var10001 + "main class: " + ctx.classDeclaration().className.toString().split("='")[1].split("',")[0] + "{\n";
        ++this.tab;
    }

    public void exitEntryClassDeclaration(EntryClassDeclarationContext ctx) {
        this.entry = 0;

        for(int i = 0; i < this.tab - 1; ++i) {
            this.program = this.program + "\t";
        }

        this.program = this.program + "}\n";
        --this.tab;
    }

    public void enterFieldDeclaration(FieldDeclarationContext ctx) {
        for(int i = 0; i < this.tab; ++i) {
            this.program = this.program + "\t";
        }

        String var10001 = this.program;
        this.program = var10001 + "field: " + ctx.fieldName.getText() + "/ type=" + ctx.fieldType.getText() + "/ access modifier=";
        if (ctx.fieldAccessModifier != null) {
            var10001 = this.program;
            this.program = var10001 + ctx.fieldAccessModifier.getText();
        } else {
            this.program = this.program + "public";
        }

        this.program = this.program + "\n";
    }

    public void exitFieldDeclaration(FieldDeclarationContext ctx) {
    }

    public void enterAccess_modifier(Access_modifierContext ctx) {
    }

    public void exitAccess_modifier(Access_modifierContext ctx) {
    }

    public void enterMethodDeclaration(MethodDeclarationContext ctx) {
        String AccMod = "";

        for(int i = 0; i < this.tab; ++i) {
            this.program = this.program + "\t";
        }

        if (ctx.getText().split("function")[0].contains("private")) {
            AccMod = "private";
        } else {
            AccMod = "public";
        }

        if (this.entry == 1) {
            AccMod = "public";
        }

        String var10001 = this.program;
        this.program = var10001 + "class method: " + ctx.methodName.getText() + "/ return type=" + ctx.getText().split("returns")[1].split(":")[0] + "/ access modifier=" + AccMod + "{\n";
        ++this.tab;
    }

    public void exitMethodDeclaration(MethodDeclarationContext ctx) {
        for(int i = 0; i < this.tab - 1; ++i) {
            this.program = this.program + "\t";
        }

        this.program = this.program + "}\n";
        --this.tab;
    }

    public void enterClosedStatement(ClosedStatementContext ctx) {
    }

    public void exitClosedStatement(ClosedStatementContext ctx) {
    }

    public void enterClosedConditional(ClosedConditionalContext ctx) {
    }

    public void exitClosedConditional(ClosedConditionalContext ctx) {
    }

    public void enterOpenConditional(OpenConditionalContext ctx) {
    }

    public void exitOpenConditional(OpenConditionalContext ctx) {
    }

    public void enterOpenStatement(OpenStatementContext ctx) {
    }

    public void exitOpenStatement(OpenStatementContext ctx) {
    }

    public void enterStatement(StatementContext ctx) {
    }

    public void exitStatement(StatementContext ctx) {
    }

    public void enterStatementVarDef(StatementVarDefContext ctx) {
        for(int i = 0; i < this.tab; ++i) {
            this.program = this.program + "\t";
        }

        String var10001 = this.program;
        this.program = var10001 + ctx.children.toArray()[0].toString() + ": " + ctx.children.toArray()[1].toString() + "\n";
    }

    public void exitStatementVarDef(StatementVarDefContext ctx) {
    }

    public void enterStatementBlock(StatementBlockContext ctx) {
    }

    public void exitStatementBlock(StatementBlockContext ctx) {
    }

    public void enterStatementContinue(StatementContinueContext ctx) {
    }

    public void exitStatementContinue(StatementContinueContext ctx) {
    }

    public void enterStatementBreak(StatementBreakContext ctx) {
    }

    public void exitStatementBreak(StatementBreakContext ctx) {
    }

    public void enterStatementReturn(StatementReturnContext ctx) {
    }

    public void exitStatementReturn(StatementReturnContext ctx) {
    }

    public void enterStatementClosedLoop(StatementClosedLoopContext ctx) {
    }

    public void exitStatementClosedLoop(StatementClosedLoopContext ctx) {
    }

    public void enterStatementOpenLoop(StatementOpenLoopContext ctx) {
    }

    public void exitStatementOpenLoop(StatementOpenLoopContext ctx) {
    }

    public void enterStatementWrite(StatementWriteContext ctx) {
    }

    public void exitStatementWrite(StatementWriteContext ctx) {
    }

    public void enterStatementAssignment(StatementAssignmentContext ctx) {
    }

    public void exitStatementAssignment(StatementAssignmentContext ctx) {
    }

    public void enterStatementInc(StatementIncContext ctx) {
    }

    public void exitStatementInc(StatementIncContext ctx) {
    }

    public void enterStatementDec(StatementDecContext ctx) {
    }

    public void exitStatementDec(StatementDecContext ctx) {
    }

    public void enterExpression(ExpressionContext ctx) {
    }

    public void exitExpression(ExpressionContext ctx) {
    }

    public void enterExpressionOr(ExpressionOrContext ctx) {
    }

    public void exitExpressionOr(ExpressionOrContext ctx) {
    }

    public void enterExpressionOrTemp(ExpressionOrTempContext ctx) {
    }

    public void exitExpressionOrTemp(ExpressionOrTempContext ctx) {
    }

    public void enterExpressionAnd(ExpressionAndContext ctx) {
    }

    public void exitExpressionAnd(ExpressionAndContext ctx) {
    }

    public void enterExpressionAndTemp(ExpressionAndTempContext ctx) {
    }

    public void exitExpressionAndTemp(ExpressionAndTempContext ctx) {
    }

    public void enterExpressionEq(ExpressionEqContext ctx) {
    }

    public void exitExpressionEq(ExpressionEqContext ctx) {
    }

    public void enterExpressionEqTemp(ExpressionEqTempContext ctx) {
    }

    public void exitExpressionEqTemp(ExpressionEqTempContext ctx) {
    }

    public void enterExpressionCmp(ExpressionCmpContext ctx) {
    }

    public void exitExpressionCmp(ExpressionCmpContext ctx) {
    }

    public void enterExpressionCmpTemp(ExpressionCmpTempContext ctx) {
    }

    public void exitExpressionCmpTemp(ExpressionCmpTempContext ctx) {
    }

    public void enterExpressionAdd(ExpressionAddContext ctx) {
    }

    public void exitExpressionAdd(ExpressionAddContext ctx) {
    }

    public void enterExpressionAddTemp(ExpressionAddTempContext ctx) {
    }

    public void exitExpressionAddTemp(ExpressionAddTempContext ctx) {
    }

    public void enterExpressionMultMod(ExpressionMultModContext ctx) {
    }

    public void exitExpressionMultMod(ExpressionMultModContext ctx) {
    }

    public void enterExpressionMultModTemp(ExpressionMultModTempContext ctx) {
    }

    public void exitExpressionMultModTemp(ExpressionMultModTempContext ctx) {
    }

    public void enterExpressionUnary(ExpressionUnaryContext ctx) {
    }

    public void exitExpressionUnary(ExpressionUnaryContext ctx) {
    }

    public void enterExpressionMethods(ExpressionMethodsContext ctx) {
    }

    public void exitExpressionMethods(ExpressionMethodsContext ctx) {
    }

    public void enterExpressionMethodsTemp(ExpressionMethodsTempContext ctx) {
    }

    public void exitExpressionMethodsTemp(ExpressionMethodsTempContext ctx) {
    }

    public void enterExpressionOther(ExpressionOtherContext ctx) {
    }

    public void exitExpressionOther(ExpressionOtherContext ctx) {
    }

    public void enterMoolaType(MoolaTypeContext ctx) {
    }

    public void exitMoolaType(MoolaTypeContext ctx) {
    }

    public void enterSingleType(SingleTypeContext ctx) {
    }

    public void exitSingleType(SingleTypeContext ctx) {
    }

    public void visitTerminal(TerminalNode terminalNode) {
    }

    public void visitErrorNode(ErrorNode errorNode) {
    }

    public void enterEveryRule(ParserRuleContext parserRuleContext) {
    }

    public void exitEveryRule(ParserRuleContext parserRuleContext) {
    }
}
