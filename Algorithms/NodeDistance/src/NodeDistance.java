import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

class BNode<E> {

	public E info;
	public BNode<E> left;
	public BNode<E> right;

	static int LEFT = 1;
	static int RIGHT = 2;

	public BNode(E info) {
		this.info = info;
		left = null;
		right = null;
	}

	public BNode() {
		this.info = null;
		left = null;
		right = null;
	}

	public BNode(E info, BNode<E> left, BNode<E> right) {
		this.info = info;
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return "" + info;
	}
}

class BTree<E> {

	public BNode<E> root;

	public BTree() {
		root = null;
	}

	public BTree(E info) {
		root = new BNode<E>(info);
	}

	public void makeRoot(E elem) {
		root = new BNode(elem);
	}

	public void makeRootNode(BNode<E> node) {
		root = node;
	}

	public BNode<E> addChild(BNode<E> node, int where, E elem) {

		BNode<E> tmp = new BNode<E>(elem);

		if (where == BNode.LEFT) {
			if (node.left != null) // veke postoi element
				return null;
			node.left = tmp;
		} else {
			if (node.right != null) // veke postoi element
				return null;
			node.right = tmp;
		}

		return tmp;
	}

	public BNode<E> addChildNode(BNode<E> node, int where, BNode<E> tmp) {

		if (where == BNode.LEFT) {
			if (node.left != null) // veke postoi element
				return null;
			node.left = tmp;
		} else {
			if (node.right != null) // veke postoi element
				return null;
			node.right = tmp;
		}

		return tmp;
	}

}

interface Stack<E> {

	// Elementi na stekot se objekti od proizvolen tip.

	// Metodi za pristap:

	public boolean isEmpty();

	// Vrakja true ako i samo ako stekot e prazen.

	public E peek();

	// Go vrakja elementot na vrvot od stekot.

	// Metodi za transformacija:

	public void clear();

	// Go prazni stekot.

	public void push(E x);

	// Go dodava x na vrvot na stekot.

	public E pop();
	// Go otstranuva i vrakja elementot shto e na vrvot na stekot.
}

class ArrayStack<E> implements Stack<E> {
	private E[] elems;
	private int depth;

	@SuppressWarnings("unchecked")
	public ArrayStack(int maxDepth) {
		// Konstrukcija na nov, prazen stek.
		elems = (E[]) new Object[maxDepth];
		depth = 0;
	}

	public boolean isEmpty() {
		// Vrakja true ako i samo ako stekot e prazen.
		return (depth == 0);
	}

	public E peek() {
		// Go vrakja elementot na vrvot od stekot.
		if (depth == 0)
			throw new NoSuchElementException();
		return elems[depth - 1];
	}

	public void clear() {
		// Go prazni stekot.
		for (int i = 0; i < depth; i++)
			elems[i] = null;
		depth = 0;
	}

	public void push(E x) {
		// Go dodava x na vrvot na stekot.
		elems[depth++] = x;
	}

	public E pop() {
		// Go otstranuva i vrakja elementot shto e na vrvot na stekot.
		if (depth == 0)
			throw new NoSuchElementException();
		E topmost = elems[--depth];
		elems[depth] = null;
		return topmost;
	}
}

public class NodeDistance {

	public static void main(String[] args) throws Exception {
		int i, j, k;
		int index;
		String action;

		String line;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		BNode<String> nodes[] = new BNode[N];
		BTree<String> tree = new BTree<String>();

		for (i = 0; i < N; i++)
			nodes[i] = new BNode<String>();

		for (i = 0; i < N; i++) {
			line = br.readLine();
			st = new StringTokenizer(line);
			index = Integer.parseInt(st.nextToken());
			nodes[index].info = st.nextToken();
			action = st.nextToken();
			if (action.equals("LEFT")) {
				tree.addChildNode(nodes[Integer.parseInt(st.nextToken())],
						BNode.LEFT, nodes[index]);
			} else if (action.equals("RIGHT")) {
				tree.addChildNode(nodes[Integer.parseInt(st.nextToken())],
						BNode.RIGHT, nodes[index]);
			} else {
				// this node is the root
				tree.makeRootNode(nodes[index]);
			}
		}

		int cases = Integer.parseInt(br.readLine());
		for (int l = 0; l < cases; l++) {
			String split[] = br.readLine().split(" +");
			String from = split[0];
			String to = split[1];

			// Vasiot kod ovde

			int distance = findDistance(tree.root, from, to);
			if (distance != -1) {
				System.out.println(distance);
			} else {
				System.out.println("ERROR");
			}

		}
		br.close();

	}

	private static int findDistance(BNode<String> node, String first,
			String second) {
		BNode<String> pathF = new BNode<String>("");
		BNode<String> pathS = new BNode<String>("");
		findPath(node, first, pathF);
		findPath(node, second, pathS);
		String path1 = pathF.info;
		String path2 = pathS.info;

		path1 = path1.replaceAll("\\s+", " ");
		path2 = path2.replaceAll("\\s+", " ");

		String[] pathFirst = path1.split("\\s");
		String[] pathSecond = path2.split("\\s");

		if ((pathFirst.length == 0) || (pathSecond.length == 0)) {
			return -1;
		}

		int i = 0;
		int border;
		if (pathFirst.length > pathSecond.length) {
			border = pathSecond.length;
		} else {
			border = pathFirst.length;
		}
		for (; i < border; i++) {
			if (!pathFirst[i].equals(pathSecond[i])) {
				break;
			}
		}
		return pathFirst.length * 2 + pathSecond.length * 2 - 4 * i;
	}

	private static boolean findPath(BNode<String> node, String value,
			BNode<String> path) {
		if (node == null) {
			return false;
		}
		path.info = path + node.info + " ";
		if (node.info.equals(value)) {
			return true;
		}
		if (findPath(node.left, value, path)
				|| findPath(node.right, value, path)) {
			return true;
		}
		path.info = path.info.replace(node.info, "");
		return false;
	}

}
