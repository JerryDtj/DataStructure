public class AVLTree<T extends Comparable<T>> {
    private AVLTreeNode<T> mRoot;

    public int height(AVLTreeNode<T> treeNode) {
        if (treeNode != null) {
            return treeNode.height;
        }
        return 0;
    }

    public int height() {
        return this.height(mRoot);
    }

    public int max(int a, int b) {
        return a > b ? a : b;
    }

    private class AVLTreeNode<T> {
        private T key;
        private AVLTreeNode<T> left;
        private AVLTreeNode<T> right;
        private int height;

        public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right, int height) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = height;
        }
    }


}
