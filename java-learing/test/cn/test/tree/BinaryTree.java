package cn.test.tree;

import java.util.Stack;

/**
 * ������
 * @author xyd-yuyilun
 * ������Ҫ�ı���˼��Ϊ��
 * ǰ������������ ---> ������ ---> ������	:ǰ��������ȷ��ʸ����Ȼ���������������������������Preorder Traversal (DLR)
 * ���������������---> ����� ---> ������	�����ڵ����м�
 * ��������������� ---> ������ ---> �����	�����ڵ������
 * ��α�����ֻ�谴��α�������
 */
public class BinaryTree {
	//���ڵ㣬��ʵ���ǵ�һ���ڵ�
	private Node root = null;
	
	BinaryTree(int value){
		root=new Node(value);
		root.leftChild	=	null;
		root.rightChild	=	null;
	}
	
	/**
	 * ����  
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
	 * ���� ,���ܲ����ظ������ݣ�ʵ�ʵ��У����ݲ�������int��������һ��������Ҫ���±Ƚϣ�
	 * ��ʵ���ڹ������������߼�����
	 * @param value
	 * @return
	 */
    public String insert(int value) { 
    	String error=null;
    	Node node=new Node(value);
    	
    	if(root == null) {
    		//������ڵ�Ϊ�գ���һ����ӵĽڵ���Ǹ��ڵ�
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
     * ǰ�����  :ǰ��������ȷ��ʸ����Ȼ���������������������������Preorder Traversal (DLR)
     * �ݹ�ʵ��
     */
    public void preorderTraversal() {
    	System.out.println("ǰ�����:");
    	preorderTraversal(root);
    	System.out.println();
    }
    private void preorderTraversal(Node node) {
    	if(node == null) {
    		return;
    	}
		node.disPlay();//�ȸ��ڵ�
		preorderTraversal(node.leftChild);//���ӽڵ�
		preorderTraversal(node.rightChild);//���ӽڵ�
    	
    }
    /**
     * ǰ�����  :ǰ��������ȷ��ʸ����Ȼ���������������������������Preorder Traversal (DLR)
     * ǰ������ǵݹ����  
     */
    public void preorderTraversalByStack() {
    	System.out.println("ǰ��ǵݹ����:"); 
    	Stack<Node> stack	=	new Stack<Node>();//����ջ�е�ԪΪ�ڵ�
    	Node current	=	root;
    	while(current	!=	null	||	!stack.isEmpty()) {
    		while(current != null){
    			stack.push(current);//ѭ������ǰnodeѹ��ջ��
    			current.disPlay();
    			current=current.leftChild;
    		}
    		if(!stack.isEmpty()) {
    			current	=	stack.pop();//��ջ��ȡֵ
    			current = current.rightChild;
    		}
    	}
    	System.out.println();  
    } 
    
    
    /**
     * ��������ݹ����  :�ڶ������У�����������
     * Inorder Traversal (LDR)
     */
    public void inorderTraversal() {
    	System.out.println("�������:");  
    	inorderTraversal(root);  
    	System.out.println();
    }
    /**
     *
     * @param node
     */
    private void inorderTraversal(Node node) {
    	if(node == null ) {
    		return;//�����ǰnode�ڵ�Ϊ�գ�����������
    	}
    	inorderTraversal(node.leftChild);
    	node.disPlay();//��ӡ����ڵ�
    	//ȡ����ڵ���ֵܽڵ�
    	inorderTraversal(node.rightChild);
    }
    
    /**
     * ��������ǵݹ����    
     */
    public void inorderTraversalByStack() {
    	System.out.println("����ǵݹ����:"); 
    	Stack<Node> stack	=	new Stack<Node>();//����ջ�е�ԪΪ�ڵ�
    	Node current	=	root;
    	while(current	!=	null	||	!stack.isEmpty()) {
    		while(current != null)
    		{
    			stack.push(current);//ѭ�������ӽڵ㣬����ǰnodeѹ��ջ����
    			current=current.leftChild;
    		}
    		if(!stack.isEmpty()) {
    			current	=	stack.pop();//��ջ��ȡֵ
    			current.disPlay();
    			current = current.rightChild;
    		}
    	}
    	System.out.println();  
    }
    
  
    
    /**
     * ������� ,Postorder Traversal (LRD)
     * �ڶ������У���������ٸ�
     */
    public void postorderTraversal() {
    	
    	System.out.println("�������:");
    	postorderTraversal(root);
    	System.out.println();
    } 
    
    private void postorderTraversal(Node node) {
    	if(node == null) {
    		return;
    	}
    	postorderTraversal(node.leftChild);//���ӽڵ�
    	postorderTraversal(node.rightChild);//���ӽڵ�
    	node.disPlay();//��ǰ�ڵ�
    }
   
    /**
     * ������� ,Postorder Traversal (LRD)
     * �ڶ������У���������ٸ�
     * ��������ǵݹ���� 
     *  
     * ����Ҫ������������򡢺���ķǵݹ��㷨��֮ͬ������ջ��������ǰ�߹���·����
     * �Ա�����ڷ�����������,��������ջ�е���Ϣ,���˵���ǰ�ڵ��˫�׽ڵ�,������һ��������
     * 
     * ��������ķǵݹ��㷨������˳������ӵģ�
     * ԭ�����ڣ�����������ȷ�����������,�ٷ��ʸ��ڵ㣬���ڷǵݹ��㷨�У�����ջ���˵�ʱ��
     * ����֪���Ǵ����������˵����ڵ㣬���Ǵ����������˵����ڵ㣬��������������˵����ڵ㣬
     * ��ʱ��Ӧ��ȥ����������������������������˵����ڵ㣬��ʱ��Ӧ�÷��ʸ��ڵ㡣
     * �������ǰ��ͺ��򣬱������ѹջʱ�����Ϣ���Ա�����ջʱ����֪���Ǵ����������أ�
     * ���Ǵ����������ؽ���������һ���Ĳ�����
     */
    public void postorderTraversalByStack() {
    	System.out.println("����ǵݹ����:"); 
    	Stack<Node> stack	=	new Stack<Node>();//����ջ�е�ԪΪ�ڵ�
    	Node current	=	root;
    	Node preNode	=	null;
    	while(current	!=	null	||	!stack.isEmpty()) {
    		while(current != null){
    			stack.push(current);//ѭ������ǰnodeѹ��ջ����
    			current=current.leftChild;
    		}
    		if(!stack.isEmpty()) {
    			current=stack.peek().rightChild;//ȡ������ڵ�����ӽڵ�
    			if(current == null || current == preNode) {
    				current	= stack.pop();//��ջ��ȡֵ,��ɾ��ջ����ֵ
    				current.disPlay();
    				preNode = current;
    				current = null;
    			}
    		}
    	}
    	System.out.println();  
    }  
    /**
     * �õ���С(��)ֵ  
     * @return
     */
    public int getMinValue() {
    	Node current =	root;
    	while(true) {
    		if(current.leftChild == null) {
    			return current.value;
    		}
    		current	=	current.leftChild;//ȡ�������ӽڵ�
    	}
    } 
    /**
     * �õ���С(��)ֵ  
     * @return
     */
    public int getMaxValue() {
    	Node current =	root;
    	while(true) {
    		if(current.rightChild == null) {
    			return current.value;
    		}
    		current	=	current.rightChild;//ȡ�������ӽڵ�
    	}
    } 
    /**
     * ɾ��  
     * @param value
     * @return
     */
    public boolean delete(int value) {
    	Node current = root;    //��Ҫɾ���Ľڵ�  
	    Node parent = null;     //��Ҫɾ���Ľڵ�ĸ��ڵ�  
	    boolean isLeftChild = true;   //��Ҫɾ���Ľڵ��Ƿ񸸽ڵ��������  
	      
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
	          
	        //�Ҳ�����Ҫɾ���Ľڵ㣬ֱ�ӷ���  
	        if (current == null) {
	        	return false;  
	        }  
	    }  
	      
	    //���������  
	    //1����Ҫɾ���Ľڵ�ΪҶ�ӽڵ�  
	    if (current.leftChild == null && current.rightChild == null) {  
	        //�����Ҷ�ڵ�Ϊ���ڵ㣬�����ڵ���Ϊnull  
	        if (current == root) {  
	            root = null;  
	        } else {  
	            //�����Ҷ�ڵ��Ǹ��ڵ�����ӽڵ㣬�����ڵ�����ӽڵ���Ϊnull  
	            if (isLeftChild) {  
	                parent.leftChild  = null;  
	            } else { //�����Ҷ�ڵ��Ǹ��ڵ�����ӽڵ㣬�����ڵ�����ӽڵ���Ϊnull  
	                parent.rightChild = null;  
	            }  
	        }  
	    }   
	    //2����Ҫɾ���Ľڵ���һ���ӽڵ㣬�Ҹ��ӽڵ�Ϊ���ӽڵ�  
	    else if (current.rightChild == null) {  
	        //����ýڵ�Ϊ���ڵ㣬�����ڵ�����ӽڵ��Ϊ���ڵ�  
	        if (current == root) {  
	            root = current.leftChild;  
	        } else {  
	            //����ýڵ��Ǹ��ڵ�����ӽڵ㣬���ýڵ�����ӽڵ��Ϊ���ڵ�����ӽڵ�  
	            if (isLeftChild) {  
	                parent.leftChild = current.leftChild;  
	            } else {  //����ýڵ��Ǹ��ڵ�����ӽڵ㣬���ýڵ�����ӽڵ��Ϊ���ڵ�����ӽڵ�  
	                parent.rightChild = current.leftChild;  
	            }  
	        }  
	    }  
	    //2����Ҫɾ���Ľڵ���һ���ӽڵ㣬�Ҹ��ӽڵ�Ϊ���ӽڵ�  
	    else if (current.leftChild == null) {  
	        //����ýڵ�Ϊ���ڵ㣬�����ڵ�����ӽڵ��Ϊ���ڵ�  
	        if (current == root) {  
	            root = current.rightChild;  
	        } else {  
	            //����ýڵ��Ǹ��ڵ�����ӽڵ㣬���ýڵ�����ӽڵ��Ϊ���ڵ�����ӽڵ�  
	            if (isLeftChild) {  
	                parent.leftChild = current.rightChild;  
	            } else {  //����ýڵ��Ǹ��ڵ�����ӽڵ㣬���ýڵ�����ӽڵ��Ϊ���ڵ�����ӽڵ�  
	                parent.rightChild = current.rightChild;  
	            }  
	        }  
	    }  
	    //3����Ҫɾ���Ľڵ��������ӽڵ㣬��ҪѰ�Ҹýڵ�ĺ����ڵ����ɾ���ڵ�  
	    else {  
	        Node successor = getSuccessor(current);  
	        //����ýڵ�Ϊ���ڵ㣬����̽ڵ��Ϊ���ڵ㣬�������ڵ�����ӽڵ��Ϊ��̽ڵ�����ӽڵ�  
	        if (current == root) {  
	            root = successor;  
	        } else {  
	            //����ýڵ��Ǹ��ڵ�����ӽڵ㣬���ýڵ�ĺ�̽ڵ��Ϊ���ڵ�����ӽڵ�  
	            if (isLeftChild) {  
	                parent.leftChild = successor;  
	            } else {  //����ýڵ��Ǹ��ڵ�����ӽڵ㣬���ýڵ�ĺ�̽ڵ��Ϊ���ڵ�����ӽڵ�  
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
          
        //�����̽ڵ㲻��ɾ���ڵ�����ӽڵ�ʱ��  
        if (successor != delNode.rightChild) {  
            //Ҫ����̽ڵ�����ӽڵ�ָ���̽�㸸�ڵ�����ӽڵ㣬  
            successorParent.leftChild = successor.rightChild;  
            //����ɾ���ڵ�����ӽڵ�ָ���̽������ӽڵ�  
            successor.rightChild = delNode.rightChild;  
        }  
        //�κ�����£�����Ҫ��ɾ���ڵ�����ӽڵ�ָ���̽ڵ�����ӽڵ�  
        successor.leftChild = delNode.leftChild;  
          
        return successor;  
    }  
}
