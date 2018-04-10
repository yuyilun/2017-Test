package cn.test.tree;

/**
 * avl��ƽ�������
 * 
 * 1.����������һ�ö�����������
 * 2.����ƽ��������ÿ���������������ĸ߶�֮��ľ���ֵ��ƽ�����ӣ����Ϊ1��
 *	Ҳ����˵��AVL�����������Ǵ���ƽ�⹦�ܵĶ������������������������������������
 * 
 * ��1����A������ӵ�����������һ�β���
 * ��2����A������ӵ�����������һ�β���
 * ��3����A���Ҷ��ӵ�����������һ�β���
 * ��4����A���Ҷ��ӵ�����������һ�β���
 * 
 * 
 * 
 * @author xyd-yuyilun
 *
 */
public class AVLTree<T extends Comparable<T>> {
	
	//private AVLTree<T> root;	// �����
	
    /**
     * AVL���Ľڵ�(�ڲ���)
     * @author xyd-yuyilun
     * @param <T>
     */
	 @SuppressWarnings("hiding")
	class AVLTreeNode<T extends Comparable<T>> {
        T key;                // �ؼ���(��ֵ)
        int height;         // �߶�
        AVLTreeNode<T> left;    // ����
        AVLTreeNode<T> right;    // �Һ���

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
	  * ��ȡ���ĸ߶ȣ����Ϊ���飬��Ϊ -1
	  */
	 private int height(AVLTreeNode<T> tree) {
		 return tree == null ? -1 : tree.height;
	 }		
	 
		
	 
	 /**
	  * ƽ������
	  */
	 private static final int ALLOWED_IMBALANCE = 1;
	 
	 
	 /**
	  * ����ƽ���avl����ͨ������ת�����µ�ƽ���avl��
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> balance(AVLTreeNode<T> tree){
		 
		 if(tree == null) {
			 return tree;
		 }
		 
		 if(height(tree.left) - height(tree.right) > ALLOWED_IMBALANCE) {	//������ʧ�⣬̫����
			 if(height(tree.left.left) >= height(tree.left.right)) {
				 tree = leftLeftRotation(tree);
			 }else {
				 tree = leftRightRotation(tree);
			 }
			 
		 }else if(height(tree.right) - height(tree.left) > ALLOWED_IMBALANCE) {		//������ʧ��
			 if(height(tree.right.right) >= height(tree.right.left)) {
				 tree = rightRightRotation(tree);
			 }else { 
				 tree = rightLeftRotation(tree);
			 }
			 
		 }
		 
		 tree.height = Math.max(height(tree.left), height(tree.right)) + 1;//����ƽ��߶�
		 return tree;
	 }
	
	 /**
	  * ��ǰ�ڵ�������������ӽڵ�ʧ�⣬��Ҫƽ��
	  * ����ת
	  * ��ǰ�ڵ�����������ƣ�����ƽ��
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> tree) {
		 //ȡ��������
		 AVLTreeNode<T> leftChild = tree.left;
		 //��ǰ�ڵ�ĵ�����������ȡֵ��ȡ���������ҽڵ㡣
		 tree.left = leftChild.right;
		 //����ǰ�ڵ��³�����Ϊ���������ҽڵ㣻��������Ϊ��ǰ�ڵ�
		 leftChild.right = tree;
		 //���¼���߶�
		 tree.height = Math.max(height(tree.left), height(tree.right)) + 1;
		 leftChild.height = Math.max(height(leftChild.left), height(leftChild.right)) + 1;
		 return leftChild;//���ص�ǰ�ڵ�
	 }
	 
	 /**
	  * ��leftLeftRotation����ת��һ����
	  * ��ǰ�ڵ�����������ҽڵ�ʧ��
	  * ��ʵ�ǵ�ǰ�ڵ����������������ƽ��
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> tree) {
		 //ȡ��ǰ�ڵ��������
		 AVLTreeNode<T> rightChild = tree.right;
		 //����ǰ�ڵ�����������¸���Ϊ����������ڵ�
		 tree.right = rightChild.left;
		 //����ǰ�ڵ��³�����Ϊ����������ڵ㣬��������Ϊ��ǰ�ڵ�
		 rightChild.left = tree;
		 //���¼���߶�
		 tree.height = Math.max(height(tree.left), height(tree.right)) + 1;
		 rightChild.height = Math.max(height(rightChild.right), height(rightChild.left)) + 1;
		 return rightChild;//����������
	 }
	 
	 /**
	  * ����һ����ת�ﲻ����ƽ�⣬��Ҫ���
	  * ���������ҽڵ�ʧ�⣺
	  * 1���Ƚ���ǰ�ڵ�������������ӽڵ�����,��ɵ�ǰ�ڵ��������
	  * 2���ٽ���ǰ�ڵ����ڵ�����
	  * 3�����ص�ǰ�ڵ�
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> tree) {
		 tree.left = rightRightRotation(tree.left);
		 return leftLeftRotation(tree);
	 }
	 
	 /**
	  * ��ǰ�ڵ����������������ʧ�⣺
	  * 1���Ƚ���ǰ�ڵ�������������������ƣ���ɵ�ǰ�ڵ��������
	  * 2���ٽ���ǰ�ڵ������������
	  * 3�����ص�ǰ�ڵ�
	  * @param tree
	  * @return
	  */
	 private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> tree) { 
		 tree.right = leftLeftRotation(tree.right);
		 return rightRightRotation(tree);
	 }
	 
	 /**
	  * ����һ���ڵ�
	  * 1��ѭ����ѯ��Ҫ����Ľڵ�λ��
	  * 2�����µĽڵ�����Ϊ��ǰ�ڵ����ڵ�����ҽڵ�
	  * 3������ƽ�⵱ǰ�ڵ�
	  * @param tree
	  * @param key
	  * @return
	  */
	 public AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key) {
		 if(tree ==	null) {
			 //���һ�ν�T������һ���ڵ�
			 return new AVLTreeNode<T>(key,null,null);
		 }
		 
		 int compareTo = key.compareTo(tree.key);
		 
		 if( compareTo < 0 ) {
			 tree.left = insert(tree.left, key);
		 }else if( compareTo > 0 ) {
			 tree.right = insert(tree.right, key);
		 }else {
			 //��ʾ�뵱ǰ�ڵ�ֵ��ͬ
		 }
		 return balance(tree);
	 }
	 
	 /**
	  * �Ƴ�һ���ڵ�
	  * 1��ѭ����ѯ����Ҫ�Ƴ��ĵ�ǰ�ڵ�
	  * 2��Ȼ���Ƴ�
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
	  * �ҵ���ǰ�ڵ����С�ڵ�
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
     * ǰ�����  :ǰ��������ȷ��ʸ����Ȼ���������������������������Preorder Traversal (DLR)
     * �ݹ�ʵ��
     */
    public void preorderTraversalPub(AVLTreeNode<T> tree) {
    	System.out.println("ǰ�����:");
    	preorderTraversal(tree);
    	System.out.println();
    }
    private void preorderTraversal(AVLTreeNode<T> tree) {
    	if(tree == null) {
    		return;
    	}
		tree.disPlay();//�ȸ��ڵ�
		preorderTraversal(tree.left);//���ӽڵ�
		preorderTraversal(tree.right);//���ӽڵ�
    	
    }
    
    
    /**
     * ��������ݹ����  :�ڶ������У�����������
     * Inorder Traversal (LDR)
     */
    public void inorderTraversalPub(AVLTreeNode<T> tree) {
    	System.out.println("�������:");  
    	inorderTraversal(tree);  
    	System.out.println();
    }
    /**
     *
     * @param node
     */
    private void inorderTraversal(AVLTreeNode<T> tree) {
    	if(tree == null ) {
    		return;//�����ǰnode�ڵ�Ϊ�գ�����������
    	}
    	inorderTraversal(tree.left);
    	tree.disPlay();//��ӡ����ڵ�
    	//ȡ����ڵ���ֵܽڵ�
    	inorderTraversal(tree.right);
    }
    
    /**
     * ������� ,Postorder Traversal (LRD)
     * �ڶ������У���������ٸ�
     */
    public void postorderTraversalPub(AVLTreeNode<T> tree) {
    	
    	System.out.println("�������:");
    	postorderTraversal(tree);
    	System.out.println();
    } 
    
    private void postorderTraversal(AVLTreeNode<T> tree) {
    	if(tree == null) {
    		return;
    	}
    	postorderTraversal(tree.left);//���ӽڵ�
    	postorderTraversal(tree.right);//���ӽڵ�
    	tree.disPlay();//��ǰ�ڵ�
    }
}
