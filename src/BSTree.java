import jdk.nashorn.internal.ir.IfNode;

import javax.swing.tree.TreeNode;

/**
 * bstree 二叉搜索树
 *
 * @author Jerry
 * @Date 2022/10/15 17:31
 */
public class BSTree<T extends Comparable<T>> {

    private BSTreeNode mRoot;

    /**
     * bstree节点
     *
     * @author jerry
     * @date 2022/10/15
     */
    public class BSTreeNode <T extends Comparable<T>>{
        private T key;
        private BSTreeNode parert;
        private BSTreeNode left;
        private BSTreeNode right;

        public BSTreeNode(T key, BSTreeNode parert, BSTreeNode left, BSTreeNode right) {
            this.key = key;
            this.parert = parert;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 前序遍历
     *
     * @param treeNode 树节点
     */
    private void preOrder(BSTreeNode<T> treeNode){
        if (treeNode!=null){
            System.out.println(treeNode.key);
            preOrder(treeNode.left);
            preOrder(treeNode.right);
        }
    }

    public void preOrder(){
        this.preOrder(mRoot);
    }

    /**
     * 中序遍历
     *
     * @param treeNode 树节点
     */
    public void inOrder(BSTreeNode<T> treeNode){
        if (treeNode!=null){
            inOrder(treeNode.left);
            System.out.println(treeNode.key);
            inOrder(treeNode.right);
        }
    }

    public void inOrder(){
        this.inOrder(mRoot);
    }

    /**
     * 后序遍历
     * @param treeNode
     */
    public void postOrder(BSTreeNode<T> treeNode){
        if (treeNode!=null){
            inOrder(treeNode.left);
            inOrder(treeNode.right);
            System.out.println(treeNode.key);
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
        }else if (treeNode.key.compareTo(key)==1){
            return this.search(key,treeNode.right);
        }else {
            return this.search(key,treeNode.left);
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
    private BSTreeNode<T> max(BSTreeNode<T> treeNode){
        if (treeNode==null){
            return null;
        }
        while (treeNode.right!=null){
            treeNode = treeNode.right;
        }
        return treeNode;
    }

    private T max(){
        BSTreeNode<T> treeNode = this.max(mRoot);
        if (treeNode!=null){
            return treeNode.key;
        }else {
            return null;
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
     * 前驱:当前节点左子树下最大的节点
     *
     *
     * @param treeNode 树节点
     * @return {@link BSTreeNode}<{@link T}>
     */
    private BSTreeNode<T> precursor(BSTreeNode<T> treeNode){
//        如果有左孩子，那么直接找右孩子即可
        while (treeNode.left!=null){
            return this.max(treeNode.left);
        }
//        如果没有，那么判断当前节点
//        如果当前节点为右孩子，那么前驱就是就是他的父节点
//        如果当前节点是父节点的左孩子则 向上寻找一个节点Q 直到Q节点是其父节点P的右孩子 p节点即为前驱节点
        BSTreeNode<T> p = treeNode.parert;
        while (p!=null&&treeNode == p.left){
            treeNode = p;
            p = p.parert;
        }

        return treeNode;
    }

    public BSTreeNode<T> precursor(){
        return this.precursor(mRoot);
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
        BSTreeNode<T> p = treeNode.parert;
        while (p!=null&&treeNode==p.right){
            treeNode = p;
            p = p.parert;
        }

        return treeNode;
    }

    public BSTreeNode<T> afterFlooding(){
        return this.precursor(mRoot);
    }

    /**
     * 插入
     *
     * @param bsTree     b树
     * @param insertNode 插入节点
     */
    public void insert(BSTree<T> bsTree,BSTreeNode<T> insertNode){
        int c = 0;
        BSTreeNode<T> treeNode = bsTree.mRoot;
        BSTreeNode<T> tempNode = null;

        //找位置
        while (treeNode!=null){
            tempNode = treeNode;
            c = treeNode.key.compareTo(insertNode.key);
            if (c > 0) {
                treeNode = treeNode.right;
            } else {
                treeNode = treeNode.left;
            }
        }

        insertNode.parert = tempNode;
        //插入
        if (treeNode==null){
            bsTree.mRoot = insertNode;
        }else{
            if (c>0){
                tempNode.right = insertNode;
            }else {
                tempNode.left = insertNode;
            }
        }
    }

    public void insert(T key){
        BSTreeNode<T> insertNode = new BSTreeNode(key,null,null,null);
        this.insert(this,insertNode);
    }



    private BSTreeNode<T> remove(BSTree<T> tree,BSTreeNode<T> treeNode){
        BSTreeNode<T> replaceNode = null;

        if (treeNode.right!=null&&treeNode.left!=null){
            replaceNode = precursor(treeNode);
        }
        if (treeNode.left!=null){
            replaceNode = treeNode.left;
        }
        if (treeNode.right!=null){
            replaceNode = treeNode.right;
        }

        if (treeNode.parert!=null){
            if (treeNode.parert.left == treeNode){
                treeNode.parert.left = treeNode;
            }else {
                treeNode.parert.right = treeNode;
            }
        }else {
            tree.mRoot = replaceNode;
        }




        return null;
    }

}
