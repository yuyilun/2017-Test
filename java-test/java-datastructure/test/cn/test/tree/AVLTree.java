package cn.test.tree;

/**
 * avl自平衡二叉树
 * 
 * 1.本身首先是一棵二叉搜索树。
 * 2.带有平衡条件：每个结点的左右子树的高度之差的绝对值（平衡因子）最多为1。
 *	也就是说，AVL树，本质上是带了平衡功能的二叉查找树（二叉排序树，二叉搜索树）。
 * 
 * （1）对A的左二子的左子树进行一次插入
 * （2）对A的左二子的右子树进行一次插入
 * （3）对A的右儿子的左子树进行一次插入
 * （4）对A的右儿子的右子树进行一次插入
 * 
 * 
 * 
 * @author xyd-yuyilun
 *
 */
public class AVLTree<T extends Comparable<T>> {
	
	//private AVLTree<T> root;	// 根结点
	
    /**
     * AVL树的节点(内部类)
     * @author xyd-yuyilun
     * @param <T>
     */
	 @SuppressWarnings("hiding")
	class AVLTreeNode<T extends Comparable<T>> {
        T key;                // 关键字(键值)
        int height;         // 高度
        AVLTreeNode<T> left;    // 左孩子
        AVLTreeNode<T> right;    // 右孩子

        public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
        
        public void disPlay() {
    		System.out.print("value:"+key+","+ "height:"+height+";");
    	}
    }
	 
	 
	 /*
	  * 获取树的高度，如果为空书，则为 -1
	  */
	 private int height(AVLTreeNode<T> tree) {
		 return tree == null ? -1 : tree.height;
	 }		
	 
		
	 
	 /**
	  * 平衡因子
	  */
	 private static final int ALLOWED_IMBALANCE = 1;
	 
	 
	 /**
	  * 将不平衡的avl树，通过自旋转生成新的平衡的avl树
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> balance(AVLTreeNode<T> tree){
		 
		 if(tree == null) {
			 return tree;
		 }
		 
		 if(height(tree.left) - height(tree.right) > ALLOWED_IMBALANCE) {	//左子树失衡，太长了
			 if(height(tree.left.left) >= height(tree.left.right)) {
				 tree = leftLeftRotation(tree);
			 }else {
				 tree = leftRightRotation(tree);
			 }
			 
		 }else if(height(tree.right) - height(tree.left) > ALLOWED_IMBALANCE) {		//右子树失衡
			 if(height(tree.right.right) >= height(tree.right.left)) {
				 tree = rightRightRotation(tree);
			 }else { 
				 tree = rightLeftRotation(tree);
			 }
			 
		 }
		 
		 tree.height = Math.max(height(tree.left), height(tree.right)) + 1;//重新平衡高度
		 return tree;
	 }
	
	 /**
	  * 当前节点的左子树的左子节点失衡，需要平衡
	  * 单旋转
	  * 当前节点的左子树上移，重新平衡
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> tree) {
		 //取到左子树
		 AVLTreeNode<T> leftChild = tree.left;
		 //当前节点的的左子树重新取值，取左子树的右节点。
		 tree.left = leftChild.right;
		 //将当前节点下沉，作为左子树的右节点；左子树作为当前节点
		 leftChild.right = tree;
		 //重新计算高度
		 tree.height = Math.max(height(tree.left), height(tree.right)) + 1;
		 leftChild.height = Math.max(height(leftChild.left), height(leftChild.right)) + 1;
		 return leftChild;//返回当前节点
	 }
	 
	 /**
	  * 与leftLeftRotation单旋转是一样的
	  * 当前节点的右子树的右节点失衡
	  * 其实是当前节点的右子树上移重新平衡
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> tree) {
		 //取当前节点的右子树
		 AVLTreeNode<T> rightChild = tree.right;
		 //将当前节点的右子树重新复制为右子树的左节点
		 tree.right = rightChild.left;
		 //将当前节点下沉，作为右子树的左节点，右子树作为当前节点
		 rightChild.left = tree;
		 //重新计算高度
		 tree.height = Math.max(height(tree.left), height(tree.right)) + 1;
		 rightChild.height = Math.max(height(rightChild.right), height(rightChild.left)) + 1;
		 return rightChild;//返回右子树
	 }
	 
	 /**
	  * 由于一次旋转达不到自平衡，需要多次
	  * 左子树的右节点失衡：
	  * 1、先将当前节点的左子树的右子节点上移,变成当前节点的左子树
	  * 2、再将当前节点的左节点上移
	  * 3、返回当前节点
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> tree) {
		 tree.left = rightRightRotation(tree.left);
		 return leftLeftRotation(tree);
	 }
	 
	 /**
	  * 当前节点的右子树的左子树失衡：
	  * 1、先将当前节点的右子树的左子树上移，变成当前节点的右子树
	  * 2、再将当前节点的右子树上移
	  * 3、返回当前节点
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> tree) { 
		 tree.right = leftLeftRotation(tree.right);
		 return rightRightRotation(tree);
	 }
	 
	 /**
	  * 新增一个节点
	  * 1、循环查询需要插入的节点位置
	  * 2、将新的节点设置为当前节点的左节点或者右节点
	  * 3、重新平衡当前节点
	  * @param tree
	  * @param key
	  * @return
	  */
	 public AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key) {
		 if(tree ==	null) {
			 //最后一次将T，生成一个节点
			 return new AVLTreeNode<T>(key,null,null);
		 }
		 
		 int compareTo = key.compareTo(tree.key);
		 
		 if( compareTo < 0 ) {
			 tree.left = insert(tree.left, key);
		 }else if( compareTo > 0 ) {
			 tree.right = insert(tree.right, key);
		 }else {
			 //表示与当前节点值相同
		 }
		 return balance(tree);
	 }
	 
	 /**
	  * 移除一个节点
	  * 1、循环查询到需要移除的当前节点
	  * 2、然后移除
	  * @param tree
	  * @param key
	  * @return
	  */
	 public AVLTreeNode<T> remove(AVLTreeNode<T> tree, T key) {
		 
		 if(tree == null) {
			 return tree;
		 }
		 
		 int compareTo = key.compareTo(tree.key);
		 
		 if(compareTo < 0) {
			 tree.left = remove(tree.left, key);
		 }else if(compareTo > 0) {
			 tree.right = remove(tree.right, key);
		 }else if ( tree.left != null && tree.right != null){
			 tree.key = findMin(tree.right).key;
			 tree.right = remove(tree.right , tree.key);
		 }else {
			 tree = tree.left != null ? tree.left : tree.right;
		 }
		 return balance(tree);
	 }
	 
	 /**
	  * 找到当前节点的最小节点
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> findMin(AVLTreeNode<T> tree){
		 if(tree == null) {
			 return tree;
		 }
		 
		 if(tree.left == null) {
			return tree;
		 }
		 return findMin(tree.left);
	 }
	 
	 
	 
	 /**
     * 前序遍历  :前序遍历首先访问根结点然后遍历左子树，最后遍历右子树。Preorder Traversal (DLR)
     * 递归实现
     */
    public void preorderTraversalPub(AVLTreeNode<T> tree) {
    	System.out.println("前序遍历:");
    	preorderTraversal(tree);
    	System.out.println();
    }
    private void preorderTraversal(AVLTreeNode<T> tree) {
    	if(tree == null) {
    		return;
    	}
		tree.disPlay();//先根节点
		preorderTraversal(tree.left);//左子节点
		preorderTraversal(tree.right);//右子节点
    	
    }
    
    
    /**
     * 中序遍历递归操作  :在二叉树中，先左后根再右
     * Inorder Traversal (LDR)
     */
    public void inorderTraversalPub(AVLTreeNode<T> tree) {
    	System.out.println("中序遍历:");  
    	inorderTraversal(tree);  
    	System.out.println();
    }
    /**
     *
     * @param node
     */
    private void inorderTraversal(AVLTreeNode<T> tree) {
    	if(tree == null ) {
    		return;//如果当前node节点为空，则立即返回
    	}
    	inorderTraversal(tree.left);
    	tree.disPlay();//打印最左节点
    	//取最左节点的兄弟节点
    	inorderTraversal(tree.right);
    }
    
    /**
     * 后序遍历 ,Postorder Traversal (LRD)
     * 在二叉树中，先左后右再根
     */
    public void postorderTraversalPub(AVLTreeNode<T> tree) {
    	
    	System.out.println("后序遍历:");
    	postorderTraversal(tree);
    	System.out.println();
    } 
    
    private void postorderTraversal(AVLTreeNode<T> tree) {
    	if(tree == null) {
    		return;
    	}
    	postorderTraversal(tree.left);//左子节点
    	postorderTraversal(tree.right);//右子节点
    	tree.disPlay();//当前节点
    }
}
