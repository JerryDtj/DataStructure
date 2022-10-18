/**
 * bstree 二叉搜索树
 *
 * @author Jerry
 * @Date 2022/10/15 17:31
 */
public class BSTree<T extends Comparable<T>> {

    private BSTreeNode<T> mRoot;

    /**
     * 前驱:当前节点左子树下最大的节点
     *
     * @param treeNode 树节点
     * @return {@link BSTreeNode}<{@link T}>
     */
    private BSTreeNode<T> precursor(BSTreeNode<T> treeNode) {
//        如果有左孩子，那么直接找右孩子即可
        while (treeNode.left != null) {
            return this.max(treeNode.left);
        }
//        如果没有，那么判断当前节点
//        如果当前节点为右孩子，那么前驱就是就是他的父节点
//        如果当前节点是父节点的左孩子则 向上寻找一个节点Q 直到Q节点是其父节点P的右孩子 p节点即为前驱节点
        BSTreeNode<T> p = treeNode.parent;
        while (p != null && treeNode == p.left) {
            treeNode = p;
            p = p.parent;
        }

        return treeNode;
    }

    public static void main(String[] args) {
        System.out.println();
    }

    public void preOrder() {
        this.preOrder(mRoot);
    }

    /**
     * 前序遍历
     *
     * @param treeNode 树节点
     */
    private void preOrder(BSTreeNode<T> treeNode) {
        if (treeNode != null) {
            System.out.print(treeNode.key + " ");
            preOrder(treeNode.left);
            preOrder(treeNode.right);
        }
    }

    public void inOrder() {
        this.inOrder(mRoot);
    }

    /**
     * 中序遍历
     *
     * @param treeNode 树节点
     */
    public void inOrder(BSTreeNode<T> treeNode) {
        if (treeNode != null) {
            inOrder(treeNode.left);
            System.out.print(treeNode.key + " ");
            inOrder(treeNode.right);
        }
    }

    public void postOrder(){
        this.postOrder(mRoot);
    }

    /**
     * 搜索树节点
     *
     * @param key      关键
     * @param treeNode 树节点
     * @return {@link BSTreeNode}<{@link T}>
     */
    private BSTreeNode<T> search(T key,BSTreeNode<T> treeNode){
        if (treeNode==null){
            return null;
        }
        if (treeNode.key.compareTo(key)==0){
            return treeNode;
        }else if (treeNode.key.compareTo(key)>0){
            return this.search(key,treeNode.left);
        }else {
            return this.search(key,treeNode.right);
        }
    }

    public BSTreeNode<T> search(T key){
        return this.search(key,mRoot);
    }

    /**
     * 最大值
     *
     * @param treeNode 树节点
     * @return {@link BSTreeNode}<{@link T}>
     */
    private BSTreeNode<T> max(BSTreeNode<T> treeNode) {
        if (treeNode == null) {
            return null;
        }
        while (treeNode.right != null) {
            treeNode = treeNode.right;
        }
        return treeNode;
    }

    /**
     * 后序遍历
     *
     * @param treeNode
     */
    public void postOrder(BSTreeNode<T> treeNode) {
        if (treeNode != null) {
            inOrder(treeNode.left);
            inOrder(treeNode.right);
            System.out.print(treeNode.key + " ");
        }
    }

    /**
     * 最小值
     *
     * @param treeNode 树节点
     * @return {@link BSTreeNode}<{@link T}>
     */
    private BSTreeNode<T> min(BSTreeNode<T> treeNode){
        if (treeNode==null){
            return null;
        }
        while (treeNode.left!=null){
            treeNode = treeNode.left;
        }
        return treeNode;
    }

    public T min(){
        BSTreeNode<T> treeNode = min(mRoot);
        if (treeNode==null){
            return null;
        }else {
            return treeNode.key;
        }
    }

    /**
     * 后驱：当前节点右子树下最小的节点
     *
     * @param treeNode 树节点
     * @return {@link BSTreeNode}<{@link T}>
     */
    private BSTreeNode<T> afterFlooding(BSTreeNode<T> treeNode){
        while (treeNode.right!=null){
            this.min(treeNode);
        }
        //和前驱类似，如果该节点没有左子树，那么判断其父节点
        //如果当前节点为左孩子，那么父节点就是后驱节点
//        如果当前节点为父节点的右孩子，那么遍历其父节点，找到一个离当前节点最近的，且父节点具有左孩子的节点。那么该父节点即为当前节点的后驱
        BSTreeNode<T> p = treeNode.parent;
        while (p!=null&&treeNode==p.right){
            treeNode = p;
            p = p.parent;
        }

        return treeNode;
    }

    public T precursor(T key) {
        return this.precursor(search(key)).key;
    }

    /**
     * 删除
     * 如果有左右子树，那么删除实际上是个替换的过程，需要把前驱/后继节点替换到要删除的节点上。然后删除前驱或者后继节点
     * 如果只有左/右子树，那么需要把左/右子树的孩子节点，连接到删除节点的父节点，然后删除该节点
     * 如果没有子节点，那么直接删除就好
     *
     * @param tree     树
     * @param treeNode 树节点
     * @return {@link BSTreeNode}<{@link T}>
     */
    private BSTreeNode<T> remove(BSTree<T> tree, BSTreeNode<T> treeNode) {
        BSTreeNode<T> childNode = null;
        BSTreeNode<T> deleteNode = null;
        if (treeNode.left == null || treeNode.right == null) {
            deleteNode = treeNode;
        } else {
            deleteNode = precursor(treeNode);
        }

        if (deleteNode.left != null) {
            childNode = deleteNode.left;
        } else {
            childNode = deleteNode.right;
        }

        //开始替换树节点
        if (deleteNode != null) {
            childNode.parent = deleteNode.parent;
        }

        if (deleteNode.parent == null) {
            tree.mRoot = childNode;
        } else if (deleteNode == deleteNode.parent.left) {
            deleteNode.parent.left = childNode;
        } else {
            deleteNode.parent.right = childNode;
        }

        if (treeNode != deleteNode) {
            treeNode.key = deleteNode.key;
        }
        return deleteNode;
    }

    public BSTreeNode<T> afterFlooding() {
        return this.precursor(mRoot);
    }

    public T max() {
        BSTreeNode<T> treeNode = this.max(mRoot);
        if (treeNode != null) {
            return treeNode.key;
        } else {
            return null;
        }
    }

    public void insert(T key) {
        BSTreeNode<T> insertNode = new BSTreeNode(key, null, null, null);
        this.insert(this, insertNode);
    }

    /**
     * 插入
     *
     * @param bsTree     b树
     * @param insertNode 插入节点
     */
    public void insert(BSTree<T> bsTree, BSTreeNode<T> insertNode) {
        int c = 0;
        BSTreeNode<T> treeNode = bsTree.mRoot;
        BSTreeNode<T> tempNode = null;

        //找位置
        while (treeNode != null) {
            tempNode = treeNode;
            c = treeNode.key.compareTo(insertNode.key);
            if (c < 0) {
                treeNode = treeNode.right;
            } else {
                treeNode = treeNode.left;
            }
        }

        insertNode.parent = tempNode;
        //插入
        if (tempNode == null) {
            bsTree.mRoot = insertNode;
        } else {
            if (c < 0) {
                tempNode.right = insertNode;
            } else {
                tempNode.left = insertNode;
            }
        }
    }

    /**
     * 打印
     *
     * @param treeNode  当前节点的父节点
     * @param direction 方向 -1 左子节点 0 根节点 1 右子节点
     */
    private void print(BSTreeNode<T> treeNode, int direction) {
        if (treeNode != null) {
            if (direction == 0) {
                System.out.println(String.format("%s is root", treeNode.key));
            } else {
                System.out.println(String.format("%s is %s's %s child", treeNode.key, treeNode.parent.key, direction == 1 ? "right" : "left"));
            }
            print(treeNode.left, -1);
            print(treeNode.right, 1);
        }
    }

    /**
     * bstree节点
     *
     * @author jerry
     * @date 2022/10/15
     */
    public class BSTreeNode<T extends Comparable<T>> {
        private T key;
        private BSTreeNode parent;
        private BSTreeNode left;
        private BSTreeNode right;

        public BSTreeNode(T key, BSTreeNode parent, BSTreeNode left, BSTreeNode right) {
            this.key = key;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    public void print() {
        if (mRoot != null) {
            print(mRoot, 0);
        }
    }

    /**
     * 摧毁
     *
     * @param treeNode 树节点
     */
    private void destroy(BSTreeNode<T> treeNode) {
        if (treeNode == null) {
            return;
        }
        if (treeNode.left != null) {
            destroy(treeNode.left);
        }
        if (treeNode.right != null) {
            destroy(treeNode.right);
        }
        treeNode = null;
    }

    public void remove(T key) {
        BSTreeNode<T> removeNode = this.search(key);
        if (removeNode != null) {
            BSTreeNode<T> treeNode = this.remove(this, removeNode);
            if (treeNode != null) {
                treeNode = null;
            }
        }
    }

    public void clearn() {
        this.destroy(mRoot);
        mRoot = null;
    }
}
