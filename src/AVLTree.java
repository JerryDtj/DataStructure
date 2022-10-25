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

    public static void main(String[] args) {

    }

    /**
     * LL: 左左对应的情况(左单旋转)。
     *
     * @param k2
     * @return 旋转后的根节点
     */
    private AVLTreeNode<T> leftLeftRotating(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1;
        k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k1.height = this.max(this.height(k1.left), this.height(k2.right)) + 1;
        k2.height = this.max(this.height(k2.left), this.height(k2.right) + 1);
        return k1;
    }

    /**
     * RR: 右右对应的情况（右单旋转）
     *
     * @param k2 k2
     * @return 旋转后的根节点
     */
    private AVLTreeNode<T> rightRightRotating(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1;
        k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;

        k1.height = this.max(height(k1.left), height(k1.right)) + 1;
        k2.height = this.max(height(k2.left), height(k2.right)) + 1;
        return k1;
    }


    /**
     * LR: 左右对应的情况(左双旋转)。
     *
     * @param k2 k2
     * @return {@link AVLTreeNode}<{@link T}> 旋转后的根节点
     */
    private AVLTreeNode<T> LeftRigthRotating(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1 = this.leftLeftRotating(k2.left);
        return this.rightRightRotating(k1);
    }

    /**
     * 右左对应的情况(右双旋转)。
     *
     * @param k2 k2
     * @return {@link AVLTreeNode}<{@link T}> 旋转后的根节点
     */
    private AVLTreeNode<T> rigthLeftRotating(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1 = this.rightRightRotating(k2.right);
        return this.leftLeftRotating(k1);
    }


    /**
     * 插入
     *
     * @param mRoot 根
     * @param key   值
     * @return {@link AVLTreeNode}<{@link T}> 根节点
     */
    private AVLTreeNode<T> insert(AVLTreeNode<T> mRoot, T key) {
        if (mRoot == null) {
            mRoot = new AVLTreeNode<>(key, null, null);
        } else {
            int p = mRoot.key.compareTo(key);
            if (p < 0) {
                //插入左边
                AVLTreeNode left = this.insert(mRoot.left, key);
                int height = height(left.left) - height(left.right);
                if (height >= 2) {
                    //左边比右边大，插入的是左边
                    mRoot = leftLeftRotating(left);
                } else if (height <= 2) {
                    //左边比右边小，插入的是右边
                    mRoot = leftLeftRotating(left);
                }
            } else {
                //插入右边
                AVLTreeNode right = this.insert(mRoot.right, key);
                int height = height(right.left) - height(right.right);
                if (height >= 2) {
                    //左边比右边大，插入的是左边
                    mRoot = rigthLeftRotating(right);
                } else if (height <= 2) {
                    //左边比右边小，插入的是右边
                    mRoot = rightRightRotating(right);
                }
            }
        }
        mRoot.height = max(height(mRoot.left), height(mRoot.right)) + 1;

        return mRoot;
    }

    public void insert(T key) {
        mRoot = insert(mRoot, key);
    }

    private class AVLTreeNode<T> {
        private T key;
        private AVLTreeNode<T> left;
        private AVLTreeNode<T> right;
        private int height;

        public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }
}
