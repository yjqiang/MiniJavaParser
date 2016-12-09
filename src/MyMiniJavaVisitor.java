import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.antlr.v4.runtime.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class MyMiniJavaVisitor extends MiniJavaBaseVisitor<Tree> {
    private Parser parser;

    public MyMiniJavaVisitor(Parser parser) {
        this.parser = parser;
    }

    @Override
    public Tree visit(ParseTree tree) {
        return visit(tree, null);
    }

    public Tree visit(ParseTree tree, Tree parent) {
        Parser parser = this.parser;
        List<Tree> children = new ArrayList<>();

        Tree res = new Tree() {
            @Override
            public Tree getParent() {
                return parent;
            }

            @Override
            public Object getPayload() {
                return tree.toStringTree(parser).split(" ")[0].substring(1);
            }

            @Override
            public Tree getChild(int i) {
                return children.get(i);
            }

            @Override
            public int getChildCount() {
                return children.size();
            }

            @Override
            public String toStringTree() {
                return null;
            }
        };

        for (int i = 0; i < tree.getChildCount(); i++) {
            ParseTree child = tree.getChild(i);
            if (!(child instanceof TerminalNodeImpl)) {
                children.add(visit(child, res));
            }
        }

        return res;
    }
}