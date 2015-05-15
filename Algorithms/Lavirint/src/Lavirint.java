import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

class GraphNode<E> {
	private int index;// index (reden broj) na temeto vo grafot
	private E info;
	private LinkedList<GraphNode<E>> neighbors;

	public GraphNode(int index, E info) {
		this.index = index;
		this.info = info;
		neighbors = new LinkedList<GraphNode<E>>();
	}

	boolean containsNeighbor(GraphNode<E> o) {
		return neighbors.contains(o);
	}

	void addNeighbor(GraphNode<E> o) {
		neighbors.add(o);
	}

	void removeNeighbor(GraphNode<E> o) {
		if (neighbors.contains(o))
			neighbors.remove(o);
	}

	@Override
	public String toString() {
		String ret = "INFO:" + info + " SOSEDI:";
		for (int i = 0; i < neighbors.size(); i++)
			ret += neighbors.get(i).info + " ";
		return ret;

	}

	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		GraphNode<E> pom = (GraphNode<E>) obj;
		return (pom.info.equals(this.info));
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public E getInfo() {
		return info;
	}

	public void setInfo(E info) {
		this.info = info;
	}

	public LinkedList<GraphNode<E>> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(LinkedList<GraphNode<E>> neighbors) {
		this.neighbors = neighbors;
	}
}

class Graph<E> {

	int num_nodes;
	GraphNode<E> adjList[];

	@SuppressWarnings("unchecked")
	public Graph(int num_nodes, E[] list) {
		this.num_nodes = num_nodes;
		adjList = (GraphNode<E>[]) new GraphNode[num_nodes];
		for (int i = 0; i < num_nodes; i++)
			adjList[i] = new GraphNode<E>(i, list[i]);
	}

	@SuppressWarnings("unchecked")
	public Graph(int num_nodes) {
		this.num_nodes = num_nodes;
		adjList = (GraphNode<E>[]) new GraphNode[num_nodes];
		for (int i = 0; i < num_nodes; i++)
			adjList[i] = new GraphNode<E>(i, (E) new Object());
	}

	int adjacent(int x, int y) {
		// proveruva dali ima vrska od jazelot so
		// indeks x do jazelot so indeks y
		return (adjList[x].containsNeighbor(adjList[y])) ? 1 : 0;
	}

	void addEdge(int x, int y) {
		// dodava vrska od jazelot so indeks x do jazelot so indeks y
		if (!adjList[x].containsNeighbor(adjList[y])) {
			adjList[x].addNeighbor(adjList[y]);
		}
	}

	void deleteEdge(int x, int y) {
		adjList[x].removeNeighbor(adjList[y]);
	}

	void dfsNonrecursive(int node) {
		boolean visited[] = new boolean[num_nodes];
		for (int i = 0; i < num_nodes; i++)
			visited[i] = false;
		visited[node] = true;
		System.out.println(node + ": " + adjList[0].getInfo());
		Stack<Integer> s = new Stack<Integer>();
		s.push(node);

		GraphNode<E> pom;

		while (!s.isEmpty()) {
			pom = adjList[s.peek()];
			GraphNode<E> tmp = null;
			for (int i = 0; i < pom.getNeighbors().size(); i++) {
				tmp = pom.getNeighbors().get(i);
				if (!visited[tmp.getIndex()])
					break;
			}
			if (tmp != null && !visited[tmp.getIndex()]) {
				visited[tmp.getIndex()] = true;
				System.out.println(tmp.getIndex() + ": " + tmp.getInfo());
				s.push(tmp.getIndex());
			} else
				s.pop();
		}
	}

	@Override
	public String toString() {
		String ret = new String();
		for (int i = 0; i < this.num_nodes; i++)
			ret += i + ": " + adjList[i] + "\n";
		return ret;
	}
}

public class Lavirint {
	Graph<String> g;
	int start_node; // indeks temeto koe e vlez
	int end_node; // indeks na temeto koe e izlez

	Hashtable<String, Integer> h;
	Hashtable<Integer, String> tmp;

	public Lavirint() {
		h = new Hashtable<String, Integer>();
		tmp = new Hashtable<Integer, String>();
	}

	void generateGraph(int rows, int columns, String[] in) {
		// Vashiot kod tuka...
		int num_nodes = 0;
		String key;

		for (int i = 1; i < rows - 1; i++) {
			for (int j = 1; j < columns - 1; j++) {
				if (in[i].charAt(j) != '#') {
					key = i + "," + j;
					h.put(key, num_nodes);
					tmp.put(num_nodes, key);
					if (in[i].charAt(j) == 'S')
						start_node = num_nodes;
					if (in[i].charAt(j) == 'E')
						end_node = num_nodes;
					num_nodes++;
				}
			}
		}
		g = new Graph(num_nodes);

		int x, y;

		for (int i = 1; i < rows - 1; i++) { // generiranje na matrica na
												// sosednost
			for (int j = 1; j < columns - 1; j++) {
				if (in[i].charAt(j) != '#') {
					if (in[i].charAt(j - 1) != '#') { // dali ima teme pred nego
						x = h.get(i + "," + j);
						y = h.get(i + "," + (j - 1));
						g.addEdge(x, y);
					}
					if (in[i].charAt(j + 1) != '#') { // dali ima teme posle
														// nego
						x = h.get(i + "," + j);
						y = h.get(i + "," + (j + 1));
						g.addEdge(x, y);
					}
					if (in[i - 1].charAt(j) != '#') { // dali ima teme nad nego
						x = h.get(i + "," + j);
						y = h.get((i - 1) + "," + j);
						g.addEdge(x, y);
					}
					if (in[i + 1].charAt(j) != '#') { // dali ima teme pod nego
						x = h.get(i + "," + j);
						y = h.get((i + 1) + "," + j);
						g.addEdge(x, y);
					}
				}
			}
		}

	}

	void findPath() {
		// Vashiot kod tuka...
		boolean visited[] = new boolean[g.num_nodes];
		for (int i = 0; i < g.num_nodes; i++)
			visited[i] = false;
		visited[start_node] = true;
		Stack<Integer> s = new Stack<Integer>();
		s.push(start_node);

		int pom, pom1;
		while (!s.isEmpty() && s.peek() != end_node) {
			pom = s.peek();
			pom1 = pom;
			for (int i = 0; i < g.num_nodes; i++) {
				if (g.adjacent(pom, i) == 1) {
					pom1 = i;
					if (!visited[i])
						break;
				}
			}
			if (!visited[pom1]) {
				visited[pom1] = true;
				s.push(pom1);
			} else
				s.pop();
		}
		Stack<String> os = new Stack<String>();
		while (!s.isEmpty())
			os.push(tmp.get(s.pop()));
		while (!os.isEmpty())
			System.out.println(os.pop());

	}

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Lavirint l = new Lavirint();
		String pom = br.readLine();
		String[] ppom = pom.split(",");
		int rows = Integer.parseInt(ppom[0]);
		int columns = Integer.parseInt(ppom[1]);
		String[] in = new String[rows];

		for (int i = 0; i < rows; i++)
			in[i] = br.readLine();

		l.generateGraph(rows, columns, in);
		l.findPath();

	}

}
