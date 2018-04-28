package cn.test.tree;

import java.util.Stack;

/**
 * 二叉查找树
 * @author yuyilun
 * 四种主要的遍历思想为：
 * 前序遍历：根结点 ---> 左子树 ---> 右子树	:前序遍历首先访问根结点然后遍历左子树，最后遍历右子树。Preorder Traversal (DLR)
 * 中序遍历：左子树---> 根结点 ---> 右子树	，根节点在中间
 * 后序遍历：左子树 ---> 右子树 ---> 根结点	，根节点在最后
 * 层次遍历：只需按层次遍历即可
 */
public class BinaryTree {
	//根节点，其实就是第一个节点
	private Node root = null;
	
	BinaryTree(int value){
		root=new Node(value);
		root.leftChild	=	null;
		root.rightChild	=	null;
	}
	
	/**
	 * 查找  
	 * @param value
	 * @return
	 */
	public Node findKey(int value) {
		Node current=root;
		while(true) {
			if(value == current.value) {
				return current;
			}
			else if (value < current.value ) {
				current=current.leftChild;
			}
			else if	(value > current.value) {
				current=current.rightChild;
			}
			if (current == null) {  
		            return null;  
		    }  
		}
	}
	
	/**
	 * 插入 ,不能插入重复的数据（实际当中，数据不仅仅是int，可能是一个对象，需要重新比较）
	 * 其实在于构建二叉树的逻辑规则
	 * @param value
	 * @return
	 */
    public String insert(int value) { 
    	String error=null;
    	Node node=new Node(value);
    	
    	if(root == null) {
    		//如果根节点为空，第一个添加的节点就是根节点
    		root=node;
    		root.leftChild=null;
    		root.rightChild=null;
    	}else {
    		Node current	=	root;
    		Node parent		=	null;
    		while(true) {
    			if(value	<	current.value) {
    				parent	=	current;
    				current	=	current.leftChild;
    				if(current == null) {
    					parent.leftChild	=	node;
    					break;
    				}
    			}
    			else if(value	>	current.value) {
    				parent	=	current;
    				current	=	current.rightChild;
    				if(current	==	null) {
    					parent.rightChild	=	node;
    					break;
    				}
    			}
    			else {
    				error="having same value in binary tree";
    			}
    		}
    	}
    	return error;
    } 
    
    
    /**
     * 前序遍历  :前序遍历首先访问根结点然后遍历左子树，最后遍历右子树。Preorder Traversal (DLR)
     * 递归实现
     */
    public void preorderTraversal() {
    	System.out.println("前序遍历:");
    	preorderTraversal(root);
    	System.out.println();
    }
    private void preorderTraversal(Node node) {
    	if(node == null) {
    		return;
    	}
		node.disPlay();//先根节点
		preorderTraversal(node.leftChild);//左子节点
		preorderTraversal(node.rightChild);//右子节点
    	
    }
    /**
     * 前序遍历  :前序遍历首先访问根结点然后遍历左子树，最后遍历右子树。Preorder Traversal (DLR)
     * 前序遍历非递归操作  
     */
    public void preorderTraversalByStack() {
    	System.out.println("前序非递归遍历:"); 
    	Stack<Node> stack	=	new Stack<Node>();//设置栈中单元为节点
    	Node current	=	root;
    	while(current	!=	null	||	!stack.isEmpty()) {
    		while(current != null){
    			stack.push(current);//循环将当前node压入栈中
    			current.disPlay();
    			current=current.leftChild;
    		}
    		if(!stack.isEmpty()) {
    			current	=	stack.pop();//从栈顶取值
    			current = current.rightChild;
    		}
    	}
    	System.out.println();  
    } 
    
    
    /**
     * 中序遍历递归操作  :在二叉树中，先左后根再右
     * Inorder Traversal (LDR)
     */
    public void inorderTraversal() {
    	System.out.println("中序遍历:");  
    	inorderTraversal(root);  
    	System.out.println();
    }
    /**
     *
     * @param node
     */
    private void inorderTraversal(Node node) {
    	if(node == null ) {
    		return;//如果当前node节点为空，则立即返回
    	}
    	inorderTraversal(node.leftChild);
    	node.disPlay();//打印最左节点
    	//取最左节点的兄弟节点
    	inorderTraversal(node.rightChild);
    }
    
    /**
     * 中序遍历非递归操作    
     */
    public void inorderTraversalByStack() {
    	System.out.println("中序非递归遍历:"); 
    	Stack<Node> stack	=	new Stack<Node>();//设置栈中单元为节点
    	Node current	=	root;
    	while(current	!=	null	||	!stack.isEmpty()) {
    		while(current != null)
    		{
    			stack.push(current);//循环最左子节点，将当前node压入栈顶中
    			current=current.leftChild;
    		}
    		if(!stack.isEmpty()) {
    			current	=	stack.pop();//从栈顶取值
    			current.disPlay();
    			current = current.rightChild;
    		}
    	}
    	System.out.println();  
    }
    
  
    
    /**
     * 后序遍历 ,Postorder Traversal (LRD)
     * 在二叉树中，先左后右再根
     */
    public void postorderTraversal() {
    	
    	System.out.println("后序遍历:");
    	postorderTraversal(root);
    	System.out.println();
    } 
    
    private void postorderTraversal(Node node) {
    	if(node == null) {
    		return;
    	}
    	postorderTraversal(node.leftChild);//左子节点
    	postorderTraversal(node.rightChild);//右子节点
    	node.disPlay();//当前节点
    }
   
    /**
     * 后序遍历 ,Postorder Traversal (LRD)
     * 在二叉树中，先左后右再根
     * 后序遍历非递归操作 
     *  
     * 首先要搞清楚先序、中序、后序的非递归算法共同之处：用栈来保存先前走过的路径，
     * 以便可以在访问完子树后,可以利用栈中的信息,回退到当前节点的双亲节点,进行下一步操作。
     * 
     * 后序遍历的非递归算法是三种顺序中最复杂的：
     * 原因在于，后序遍历是先访问左、右子树,再访问根节点，而在非递归算法中，利用栈回退到时，
     * 并不知道是从左子树回退到根节点，还是从右子树回退到根节点，如果从左子树回退到根节点，
     * 此时就应该去访问右子树，而如果从右子树回退到根节点，此时就应该访问根节点。
     * 所以相比前序和后序，必须得在压栈时添加信息，以便在退栈时可以知道是从左子树返回，
     * 还是从右子树返回进而决定下一步的操作。
     */
    public void postorderTraversalByStack() {
    	System.out.println("后序非递归遍历:"); 
    	Stack<Node> stack	=	new Stack<Node>();//设置栈中单元为节点
    	Node current	=	root;
    	Node preNode	=	null;
    	while(current	!=	null	||	!stack.isEmpty()) {
    		while(current != null){
    			stack.push(current);//循环将当前node压入栈顶中
    			current=current.leftChild;
    		}
    		if(!stack.isEmpty()) {
    			current=stack.peek().rightChild;//取出最左节点的右子节点
    			if(current == null || current == preNode) {
    				current	= stack.pop();//从栈顶取值,并删除栈顶的值
    				current.disPlay();
    				preNode = current;
    				current = null;
    			}
    		}
    	}
    	System.out.println();  
    }  
    /**
     * 得到最小(大)值  
     * @return
     */
    public int getMinValue() {
    	Node current =	root;
    	while(true) {
    		if(current.leftChild == null) {
    			return current.value;
    		}
    		current	=	current.leftChild;//取得最左子节点
    	}
    } 
    /**
     * 得到最小(大)值  
     * @return
     */
    public int getMaxValue() {
    	Node current =	root;
    	while(true) {
    		if(current.rightChild == null) {
    			return current.value;
    		}
    		current	=	current.rightChild;//取得最右子节点
    	}
    } 
    /**
     * 删除  
     * @param value
     * @return
     */
    public boolean delete(int value) {
    	Node current = root;    //需要删除的节点  
	    Node parent = null;     //需要删除的节点的父节点  
	    boolean isLeftChild = true;   //需要删除的节点是否父节点的左子树  
	      
	    while (true) {  
	        if (value == current.value) {  
	            break;  
	        } else if (value < current.value) {  
	            isLeftChild = true;  
	            parent = current;  
	            current = current.leftChild;  
	        } else {  
	            isLeftChild = false;  
	            parent = current;  
	            current = current.rightChild;  
	        }  
	          
	        //找不到需要删除的节点，直接返回  
	        if (current == null) {
	        	return false;  
	        }  
	    }  
	      
	    //分情况考虑  
	    //1、需要删除的节点为叶子节点  
	    if (current.leftChild == null && current.rightChild == null) {  
	        //如果该叶节点为根节点，将根节点置为null  
	        if (current == root) {  
	            root = null;  
	        } else {  
	            //如果该叶节点是父节点的左子节点，将父节点的左子节点置为null  
	            if (isLeftChild) {  
	                parent.leftChild  = null;  
	            } else { //如果该叶节点是父节点的右子节点，将父节点的右子节点置为null  
	                parent.rightChild = null;  
	            }  
	        }  
	    }   
	    //2、需要删除的节点有一个子节点，且该子节点为左子节点  
	    else if (current.rightChild == null) {  
	        //如果该节点为根节点，将根节点的左子节点变为根节点  
	        if (current == root) {  
	            root = current.leftChild;  
	        } else {  
	            //如果该节点是父节点的左子节点，将该节点的左子节点变为父节点的左子节点  
	            if (isLeftChild) {  
	                parent.leftChild = current.leftChild;  
	            } else {  //如果该节点是父节点的右子节点，将该节点的左子节点变为父节点的右子节点  
	                parent.rightChild = current.leftChild;  
	            }  
	        }  
	    }  
	    //2、需要删除的节点有一个子节点，且该子节点为右子节点  
	    else if (current.leftChild == null) {  
	        //如果该节点为根节点，将根节点的右子节点变为根节点  
	        if (current == root) {  
	            root = current.rightChild;  
	        } else {  
	            //如果该节点是父节点的左子节点，将该节点的右子节点变为父节点的左子节点  
	            if (isLeftChild) {  
	                parent.leftChild = current.rightChild;  
	            } else {  //如果该节点是父节点的右子节点，将该节点的右子节点变为父节点的右子节点  
	                parent.rightChild = current.rightChild;  
	            }  
	        }  
	    }  
	    //3、需要删除的节点有两个子节点，需要寻找该节点的后续节点替代删除节点  
	    else {  
	        Node successor = getSuccessor(current);  
	        //如果该节点为根节点，将后继节点变为根节点，并将根节点的左子节点变为后继节点的左子节点  
	        if (current == root) {  
	            root = successor;  
	        } else {  
	            //如果该节点是父节点的左子节点，将该节点的后继节点变为父节点的左子节点  
	            if (isLeftChild) {  
	                parent.leftChild = successor;  
	            } else {  //如果该节点是父节点的右子节点，将该节点的后继节点变为父节点的右子节点  
	                parent.rightChild = successor;  
	            }  
	        }  
	    }  
	    current = null;  
	    return true;
    } 
	
    private Node getSuccessor(Node delNode) {  
        Node successor = delNode;  
        Node successorParent = null;  
        Node current = delNode.rightChild;  
          
        while (current != null) {  
            successorParent = successor;  
            successor = current;  
            current = current.leftChild;  
        }  
          
        //如果后继节点不是删除节点的右子节点时，  
        if (successor != delNode.rightChild) {  
            //要将后继节点的右子节点指向后继结点父节点的左子节点，  
            successorParent.leftChild = successor.rightChild;  
            //并将删除节点的右子节点指向后继结点的右子节点  
            successor.rightChild = delNode.rightChild;  
        }  
        //任何情况下，都需要将删除节点的左子节点指向后继节点的左子节点  
        successor.leftChild = delNode.leftChild;  
          
        return successor;  
    }  
}