package cn.test.tree;

import java.util.Random;
import org.junit.Test;

public class BinaryTreeTest {
	
	@Test
	public void test() {
		 //≤Â»Î  
		BinaryTree bTree=new BinaryTree(56);
		for(int i=0;i<10;i++){	
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

	
}
