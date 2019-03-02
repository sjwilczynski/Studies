package list6.list6_3;

abstract class Tree {
    abstract int accept(TreeVisitor2 visitor);
}

class TreeNode extends Tree {
    private Tree left;
    private Tree right;

    TreeNode(Tree left, Tree right) {
        this.left = left;
        this.right = right;
    }

    public Tree getLeft() {
        return left;
    }

    public Tree getRight() {
        return right;
    }

    @Override
    int accept(TreeVisitor2 visitor) {
        int value = visitor.visitNode(this);

        Tree left = getLeft();
        Tree right = getRight();
        int leftValue = 0;
        int rightValue = 0;
        if (left != null)
            leftValue = left.accept(visitor);
        if (right != null)
            rightValue = right.accept(visitor);
        return Math.max(leftValue, rightValue) + value;
    }
}

class TreeLeaf extends Tree {
    private int value;

    TreeLeaf(int value) {
        this.value = value;
    }

    @Override
    int accept(TreeVisitor2 visitor) {
        return visitor.visitLeaf(this);
    }
}
