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
     * @param treeNode 节点
     * @param key      值
     * @return {@link AVLTreeNode}<{@link T}> 根节点
     */
    private AVLTreeNode<T> insert(AVLTreeNode<T> treeNode, T key) {
        if (treeNode == null) {
            treeNode = new AVLTreeNode<>(key, null, null);
        } else {
            int p = treeNode.key.compareTo(key);
            if (p < 0) {
                //插入左子树
                treeNode.left = insert(treeNode.left, key);
                if (height(treeNode.left) - height(treeNode.right) == 2) {
                    //这里左子树肯定高于又子树
                    if (key.compareTo(treeNode.key) < 0) {
                        //插入的是左节点，直接用左左旋
                        treeNode = leftLeftRotating(treeNode);
                    } else {
                        //插入的右节点，直接用左右旋
                        treeNode = LeftRigthRotating(treeNode);
                    }
                }
            } else if (p > 0) {
                treeNode.right = insert(treeNode.right, key);
                if (key.compareTo(treeNode.key) < 0) {
                    //插入的是左节点，直接用右左旋
                    treeNode = rigthLeftRotating(treeNode);
                } else {
                    treeNode = rightRightRotating(treeNode);
                }
            } else {    // cmp==0
                System.out.println("添加失败: 不允许添加相同的节点！");
            }
        }
        mRoot.height = max(height(mRoot.left), height(mRoot.right))+1;
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
