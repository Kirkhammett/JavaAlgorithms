import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Iterator;
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
			adjList[i] = new GraphNode<E>(i, null);
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

	void dfsVisit(Stack<Integer> s, int i, boolean[] visited) {
		if (!visited[i]) {
			visited[i] = true;
			Iterator<GraphNode<E>> it = adjList[i].getNeighbors().iterator();
			while (it.hasNext()) {
				dfsVisit(s, it.next().getIndex(), visited);
			}
			s.push(i);
		}
	}

	ArrayList<GraphNode<E>> topological_sort_dfs() {
		// vrakja niza od teminjata topoloshi sortirani vo nizata
		ArrayList<GraphNode<E>> niza = new ArrayList<GraphNode<E>>();
		boolean visited[] = new boolean[num_nodes];
		for (int i = 0; i < num_nodes; i++) {
			visited[i] = false;
		}

		Stack<Integer> s = new Stack<Integer>();

		for (int i = 0; i < num_nodes; i++) {
			dfsVisit(s, i, visited);
		}
		while (!s.isEmpty()) {
			niza.add(adjList[s.pop()]);
		}

		return niza;
	}

	@Override
	public String toString() {
		String ret = new String();
		for (int i = 0; i < this.num_nodes; i++)
			ret += i + ": " + adjList[i] + "\n";
		return ret;
	}

}

public class IzborPredmet {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		Graph<String> g = new Graph<String>(n);
		Hashtable<String, Integer> h = new Hashtable<String, Integer>();

		for (int i = 0; i < n; i++) {
			String key = br.readLine();
			g.adjList[i].setInfo(key);
			h.put(key, i);
		}

		int m = Integer.parseInt(br.readLine());

		for (int i = 0; i < m; i++) {
			String[] pom = br.readLine().split(" ");
			for (int j = 1; j < pom.length; j++) {
				g.addEdge(h.get(pom[j]), h.get(pom[0]));
			}
		}

		/*
		 * 
		 * Vashiot kod tuka...
		 */
		String s = br.readLine();
		boolean flag = false;
		ArrayList<GraphNode<String>> a =  g.topological_sort_dfs();
		for(GraphNode<String> node : a) {
			if(flag) {
				System.out.print(node.getInfo());
				break;
			}
			if(node.getInfo().equals(s)) {
				flag = true;
			}
		}
	}

}
