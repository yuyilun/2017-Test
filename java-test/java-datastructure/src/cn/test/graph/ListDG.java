package cn.test.graph;

public class ListDG {
	Vertex[] vertexLists;
	int size;

	class Vertex {
		char ch;
		Vertex next;

		Vertex(char ch) {
			this.ch = ch;
		}

		void add(char ch) {
			Vertex node = this;
			while (node.next != null) {
				node = node.next;
			}
			node.next = new Vertex(ch);
		}

	}

	public ListDG(char[] vertexs, char[][] edges) {

		size = vertexs.length;
		this.vertexLists = new Vertex[size];
		for (int i = 0; i < size; i++) {
			this.vertexLists[i] = new Vertex(vertexs[i]);
		}

		for (char[] c : edges) {
			int p = getPosition(c[0]);
			vertexLists[p].add(c[1]);
		}

	}

	private int getPosition(char ch) {
		for (int i = 0; i < size; i++)
			if (vertexLists[i].ch == ch)
				return i;
		return -1;
	}

	public void print() {
		for (int i = 0; i < size; i++) {
			Vertex temp = vertexLists[i];
			while (temp != null) {
				System.out.print(temp.ch + " ");
				temp = temp.next;
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		char[] vexs = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K' };
		char[][] edges = new char[][] { { 'A', 'C' }, { 'A', 'D' }, { 'A', 'F' }, { 'B', 'C' }, { 'C', 'D' },
				{ 'E', 'G' }, { 'D', 'G' }, { 'I', 'J' }, { 'J', 'G' }, };

		ListDG pG;

		long start = System.nanoTime();

		for (int i = 0; i < 1; i++) {
			pG = new ListDG(vexs, edges);
			pG.print(); // 打印图
		}

		long end = System.nanoTime();

		System.out.println(end - start);

	}
}
