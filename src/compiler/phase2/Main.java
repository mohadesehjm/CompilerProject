package compiler.phase2;

import gen.MoolaLexer;
import gen.MoolaListener;
import gen.MoolaParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        CharStream stream = CharStreams.fromFileName("src/inputs/test.mla");
        MoolaLexer lexer = new MoolaLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        MoolaParser parser = new MoolaParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        MoolaListener listener = new Program();
        walker.walk(listener, tree);
    }
}
