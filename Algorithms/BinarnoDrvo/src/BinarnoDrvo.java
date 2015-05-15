import java.io.BufferedReader;
import java.io.InputStreamReader;

class BNode<E extends Comparable<E>> {

	public E info;
	public BNode<E> left;
	public BNode<E> right;

	public BNode(E info) {
		this.info = info;
		left = null;
		right = null;
	}

	public BNode(E info, BNode<E> left, BNode<E> right) {
		this.info = info;
		this.left = left;
		this.right = right;
	}

}

class BinarySearchTree<E extends Comparable<E>> {

	/** The tree root. */
	private BNode<E> root;

	/**
	 * Construct the tree.
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Insert into the tree; duplicates are ignored.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(E x) {
		root = insert(x, root);
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * 
	 * @param x
	 *            the item to remove.
	 */
	public void remove(E x) {
		root = remove(x, root);
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item or null if empty.
	 */
	public E findMin() {
		return elementAt(findMin(root));
	}

	/**
	 * Find the largest item in the tree.
	 * 
	 * @return the largest item of null if empty.
	 */
	public E findMax() {
		return elementAt(findMax(root));
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return the matching item or null if not found.
	 */
	public BNode<E> find(E x) {
		return find(x, root);
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (isEmpty()) {
			System.out.println("Empty tree");
		} else {
			printTree(root);
		}
	}

	/**
	 * Internal method to get element field.
	 * 
	 * @param t
	 *            the node.
	 * @return the element field or null if t is null.
	 */
	private E elementAt(BNode<E> t) {
		if (t == null)
			return null;
		return t.info;
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x
	 *            the item to insert.
	 * @param t
	 *            the node that roots the tree.
	 * @return the new root.
	 */
	private BNode<E> insert(E x, BNode<E> t) {
		if (t == null) {
			t = new BNode<E>(x, null, null);
		} else if (x.compareTo(t.info) < 0) {
			t.left = insert(x, t.left);
		} else if (x.compareTo(t.info) > 0) {
			t.right = insert(x, t.right);
		} else
			; // Duplicate; do nothing
		return t;
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x
	 *            the item to remove.
	 * @param t
	 *            the node that roots the tree.
	 * @return the new root.
	 */
	private BNode<E> remove(Comparable x, BNode<E> t) {
		if (t == null)
			return t; // Item not found; do nothing

		if (x.compareTo(t.info) < 0) {
			t.left = remove(x, t.left);
		} else if (x.compareTo(t.info) > 0) {
			t.right = remove(x, t.right);
		} else if (t.left != null && t.right != null) { // Two children
			t.info = findMin(t.right).info;
			t.right = remove(t.info, t.right);
		} else {
			if (t.left != null)
				return t.left;
			else
				return t.right;
		}
		return t;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 * @return node containing the smallest item.
	 */
	private BNode<E> findMin(BNode<E> t) {
		if (t == null) {
			return null;
		} else if (t.left == null) {
			return t;
		}
		return findMin(t.left);
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 * @return node containing the largest item.
	 */
	private BNode<E> findMax(BNode<E> t) {
		if (t == null) {
			return null;
		} else if (t.right == null) {
			return t;
		}
		return findMax(t.right);
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x
	 *            is item to search for.
	 * @param t
	 *            the node that roots the tree.
	 * @return node containing the matched item.
	 */
	private BNode<E> find(E x, BNode<E> t) {
		if (t == null)
			return null;

		if (x.compareTo(t.info) < 0) {
			return find(x, t.left);
		} else if (x.compareTo(t.info) > 0) {
			return find(x, t.right);
		} else {
			return t; // Match
		}
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 */
	private void printTree(BNode<E> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.info);
			printTree(t.right);
		}
	}

	public E getSuccessor(BNode<E> t, E x) {
		if (x.compareTo(t.info) < 0) {
			E tmp = findMax(t.left).info;
			if (tmp.equals(x))
				return t.info;
			else
				return getSuccessor(t.left, x);
		} else if (x.compareTo(t.info) > 0) {
			return getSuccessor(t.right, x);
		} else {
			return findMin(t.right).info;
		}
	}

	public E getSuccessor(E x) {
		return getSuccessor(root, x);
	}
	
	public int depth2(BNode<E> node){
	    if(node == null)
	        return 0;
	    int left = depth2(node.left);
	    int right = depth2(node.right);
	     
	    int x = left > right ? left+1 : right+1;
	    return x;
	}
	
	public int broj_na_teminja_na_dlabocina_n_pom(BNode<E> root, int k) {
		// vasiot kod tuka
		if(root == null) {
			return 0;
		}
		if(k == 0) {
			return 1;
		}
		return broj_na_teminja_na_dlabocina_n_pom(root.left, k - 1) + broj_na_teminja_na_dlabocina_n_pom(root.right, k - 1);
	}

	public int broj_na_teminja_na_dlabocina_n(int k) {
		return broj_na_teminja_na_dlabocina_n_pom(this.root, k);
	}

	public int calculateHeight(BNode<E> t) {
		// vasiot kod tuka
		if (t == null) {
			return 0;
		} else {
			return 1 + Math.max(calculateHeight(t.left),
					calculateHeight(t.right));
		}
	}

}

public class BinarnoDrvo {

	public static void main(String[] args) throws Exception {
		int i, j, k;

		BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		for (i = 0; i < N; i++) {
			int num = Integer.parseInt(br.readLine());
			tree.insert(new Integer(num));
		}

		int x = Integer.parseInt(br.readLine());

		BNode<Integer> t = tree.find(x);
		int rez = tree.calculateHeight(t);
		System.out.println(rez);

		int kraj = tree.broj_na_teminja_na_dlabocina_n(rez);
		System.out.println(kraj);
		br.close();

	}

}