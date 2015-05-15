import java.util.Iterator;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

class Edge {
	private int fromVertex, toVertex;
	private int weight;

	public Edge(int from, int to, int minweight) {
		this.fromVertex = from;
		this.toVertex = to;
		this.weight = minweight;
	}

	public int getFrom() {
		return this.fromVertex;
	}

	public int getTo() {
		return this.toVertex;
	}

	public int getWeight() {
		return this.weight;
	}
}

class GraphNodeNeighbor<E> {
	GraphNode<E> node;
	int weight;

	public GraphNodeNeighbor(GraphNode<E> node, int weight) {
		this.node = node;
		this.weight = weight;
	}

	public GraphNodeNeighbor(GraphNode<E> node) {
		this.node = node;
		this.weight = 0;
	}

	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		GraphNodeNeighbor<E> pom = (GraphNodeNeighbor<E>) obj;
		return pom.node.equals(this.node);
	}
}

class GraphNode<E> {
	private int index; // index (reden broj) na temeto vo grafot
	private E info;
	private LinkedList<GraphNodeNeighbor<E>> neighbors;

	public GraphNode(int index, E info) {
		this.index = index;
		this.info = info;
		neighbors = new LinkedList<GraphNodeNeighbor<E>>();
	}

	public boolean containsNeighbor(GraphNode<E> o) {
		GraphNodeNeighbor<E> pom = new GraphNodeNeighbor<E>(o, 0);
		return neighbors.contains(pom);
	}

	public void addNeighbor(GraphNode<E> o, int weight) {
		GraphNodeNeighbor<E> pom = new GraphNodeNeighbor<E>(o, weight);
		neighbors.add(pom);
	}

	public void removeNeighbor(GraphNode<E> o) {
		GraphNodeNeighbor<E> pom = new GraphNodeNeighbor<E>(o, 0);
		if (neighbors.contains(pom))
			neighbors.remove(pom);
	}

	@Override
	public String toString() {
		String ret = "INFO:" + info + " SOSEDI:";
		for (int i = 0; i < neighbors.size(); i++)
			ret += "(" + neighbors.get(i).node.info + ","
					+ neighbors.get(i).weight + ") ";
		return ret;

	}

	public void updateNeighborWeight(GraphNode<E> o, int weight) {
		Iterator<GraphNodeNeighbor<E>> i = neighbors.iterator();
		while (i.hasNext()) {
			GraphNodeNeighbor<E> pom = i.next();
			if (pom.node.equals(o))
				pom.weight = weight;
		}

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

	public LinkedList<GraphNodeNeighbor<E>> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(LinkedList<GraphNodeNeighbor<E>> neighbors) {
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

	void addEdge(int x, int y, int tezina) {
		// dodava vrska od jazelot so indeks x do jazelot so indeks y so tezina
		if (adjList[x].containsNeighbor(adjList[y])) {
			adjList[x].updateNeighborWeight(adjList[y], tezina);
		} else
			adjList[x].addNeighbor(adjList[y], tezina);
	}

	void deleteEdge(int x, int y) {
		adjList[x].removeNeighbor(adjList[y]);
	}

	// Funkcija za prebaruvanje na index na jazel so dadeno info vo listata na
	// sosednost vo grafot
	int searchIndex(String key) {
		for (int i = 0; i < num_nodes; i++) {
			Iterator<GraphNodeNeighbor<E>> it = adjList[i].getNeighbors()
					.iterator();
			while (it.hasNext()) {
				GraphNodeNeighbor<E> pom = it.next();
				if (pom.node.getInfo().equals(key))
					return pom.node.getIndex();

			}
		}
		return -1;
	}

	Edge getMinimalEdge(boolean[] included) {
		int index1 = Integer.MAX_VALUE;
		int index2 = Integer.MAX_VALUE;
		int minweight = Integer.MAX_VALUE;

		for (int i = 0; i < this.num_nodes; i++) {
			if (included[i]) {
				Iterator<GraphNodeNeighbor<E>> it = adjList[i].getNeighbors()
						.iterator();
				while (it.hasNext()) {
					GraphNodeNeighbor<E> pom = it.next();
					if (!included[pom.node.getIndex()]
							&& pom.weight < minweight) {
						index1 = i;
						index2 = pom.node.getIndex();
						minweight = pom.weight;
					}
				}
			}
		}
		if (minweight < Integer.MAX_VALUE) {
			Edge ret = new Edge(index1, index2, minweight);
			return ret;
		}
		return null;
	}

	List<Edge> prim(int start) {
		List<Edge> mstEdges = new ArrayList<Edge>();
		boolean[] included = new boolean[this.num_nodes];
		for (int i = 0; i < this.num_nodes; i++) {
			included[i] = false;
		}

		included[start] = true;

		for (int i = 0; i < this.num_nodes; i++) {
			Edge e = this.getMinimalEdge(included);
			if(e != null) {
				included[e.getFrom()] = included[e.getTo()] = true;
				mstEdges.add(e);
			}
		}
		return mstEdges;
	}

	int hierarchy(int sef) {
		// Vasiot kod ovde
		// Moze da koristite dopolnitelni funkcii ako vi se potrebni
		List<Edge> list = this.prim(sef);
		int sum = 0;
		for (Edge e : list) {
			sum += e.getWeight();
		}
		return sum;
	}

	@Override
	public String toString() {
		String ret = new String();
		for (int i = 0; i < this.num_nodes; i++)
			ret += adjList[i] + "\n";
		return ret;
	}

}

public class Hierarchy {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n_nodes = Integer.parseInt(br.readLine());

		String clen[] = new String[n_nodes];
		Graph<String> g = new Graph<String>(n_nodes, clen);

		int n_edges = Integer.parseInt(br.readLine());

		int x, y, tezina;
		String xInfo, yInfo, pom[];
		for (int i = 0; i < n_edges; i++) {
			pom = br.readLine().split(" ");
			x = Integer.parseInt(pom[0]);
			xInfo = pom[1];
			y = Integer.parseInt(pom[2]);
			yInfo = pom[3];
			g.adjList[x].setInfo(xInfo);
			g.adjList[y].setInfo(yInfo);
			tezina = Integer.parseInt(pom[4]);
			g.addEdge(x, y, tezina);
			g.addEdge(y, x, tezina);
		}

		String sef = br.readLine();
		br.close();

		System.out.println(g.hierarchy(g.searchIndex(sef)));
	}

}