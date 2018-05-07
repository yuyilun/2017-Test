package cn.test.graph;
/**
 * 邻接矩阵有向图
 * @author Administrator
 *
 */
public class MatrixDG {
	int size;
	char[] vertexs;
	int[][] matrix;

	public MatrixDG(char[] vertexs, char[][] edges) {
		size = vertexs.length;
		matrix = new int[size][size];
		this.vertexs = vertexs;

		// 和邻接矩阵无向图差别仅仅在这里
		for (char[] c : edges) {
			int p1 = getPosition(c[0]);
			int p2 = getPosition(c[1]);

			matrix[p1][p2] = 1;
		}

	}

	public void print() {
		for (int[] i : matrix) {
			for (int j : i) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
	}

	private int getPosition(char ch) {
		for (int i = 0; i < vertexs.length; i++)
			if (vertexs[i] == ch)
				return i;
		return -1;
	}

	public static void main(String[] args) {
		char[] vexs = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K' };
		char[][] edges = new char[][] { { 'A', 'C' }, { 'A', 'D' }, { 'A', 'F' }, { 'B', 'C' }, { 'C', 'D' },
				{ 'E', 'G' }, { 'D', 'G' }, { 'I', 'J' }, { 'J', 'G' }, };
		MatrixDG pG;
		// 自定义"图"(输入矩阵队列)
		// pG = new MatrixUDG();
		// 采用已有的"图"
		pG = new MatrixDG(vexs, edges);

		pG.print();
	}
}
