package list6.list6_3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepthTreeVisitorTest {

    private Tree tree = new TreeNode(new TreeNode(new TreeLeaf(3), new TreeNode(new TreeLeaf(4), null)), new TreeLeaf(5));

    @Test
    void testDepth1() {
        DepthTreeVisitor1 visitor1 = new DepthTreeVisitor1();
        assertEquals(3, visitor1.visit(tree));
    }

    @Test
    void testDepth2() {
        DepthTreeVisitor2 visitor2 = new DepthTreeVisitor2();
        assertEquals(3, tree.accept(visitor2));
    }
}