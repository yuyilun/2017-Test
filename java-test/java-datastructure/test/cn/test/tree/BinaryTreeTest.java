package cn.test.tree;

import java.util.Random;
import org.junit.Test;

import cn.test.tree.AVLTree.AVLTreeNode;

public class BinaryTreeTest {
	
	@Test
	public void test() {
		 //≤Â»Î  
		BinaryTree bTree=new BinaryTree(56);
		for(int i = 0 ; i < 10 ; i++ ){	
			bTree.insert(new Random().nextInt(200));
		}
		
		bTree.preorderTraversal();
		bTree.preorderTraversalByStack();
		
		bTree.inorderTraversal();
		bTree.inorderTraversalByStack();
		
		bTree.postorderTraversal();
		bTree.postorderTraversalByStack();
		
		bTree.delete(56);
		
		bTree.inorderTraversal();
	} 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void avlTest() {
		int[] arr=new int[]{82,44,15,31,50,49,64,51,72,132,113,91,83,129,158,154,157,185,183,196};
		AVLTree<Integer> avl=new AVLTree<>();
		
		AVLTreeNode tree = null;
		for(int i = 0 ; i < 20 ; i++ ){	
			tree=avl.insert(tree, arr[i]);
		}
		avl.preorderTraversalPub(tree);
		avl.inorderTraversalPub(tree);
		avl.postorderTraversalPub(tree);
		
		avl.remove(tree, 91);
		
		avl.preorderTraversalPub(tree);
		avl.inorderTraversalPub(tree);
		avl.postorderTraversalPub(tree);
	}
	
}
