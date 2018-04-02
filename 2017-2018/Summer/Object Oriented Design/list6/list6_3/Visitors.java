package list6.list6_3;

abstract class TreeVisitor1 {
    int visit(Tree tree) {
        if (tree instanceof TreeNode)
            return this.visitNode((TreeNode) tree);
        if (tree instanceof TreeLeaf)
            return this.visitLeaf((TreeLeaf) tree);
        return 0;
    }

    int visitNode(TreeNode node) {
        if (node != null) {
            return this.visit(node.getLeft()) + this.visit(node.getRight());
        }
        return 0;
    }

    int visitLeaf(TreeLeaf leaf) {
        return 0;
    }
}

class DepthTreeVisitor1 extends TreeVisitor1 {

    @Override
    int visitNode(TreeNode node) {
        if(node != null) {
            int d1 = this.visit(node.getLeft());
            int d2 = this.visit(node.getRight());
            return Math.max(d1,d2) + 1;
        }
        return 0;
    }
}

abstract class TreeVisitor2 {
    public abstract int visitNode( TreeNode node );
    public abstract int visitLeaf( TreeLeaf leaf );
}

class DepthTreeVisitor2 extends TreeVisitor2 {

    @Override
    public int visitNode(TreeNode node) {
        return 1;
    }

    @Override
    public int visitLeaf(TreeLeaf leaf) {
        return 0;
    }
}
