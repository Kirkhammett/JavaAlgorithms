class SLLNode<E> {

	protected E element;
	protected SLLNode<E> succ;

	public SLLNode(E elem, SLLNode<E> succ) {

		this.element = elem;
		this.succ = succ;

	}

	@Override
	public String toString() {
		return element.toString();
	}
}

class MapEntry<K extends Comparable<K>, E> implements Comparable<K> {

	protected K key;
	protected E value;

	public MapEntry(K key, E val) {

		this.key = key;
		this.value = val;

	}

	@Override
	public int compareTo(K that) {

		MapEntry<K, E> other = (MapEntry<K, E>) that;
		return this.key.compareTo(other.key);

	}

	@Override
	public String toString() {

		return "<" + key + "," + value + ">";

	}

}

public class CBHT<K extends Comparable<K>, E> {

	private SLLNode<MapEntry<K, E>>[] buckets;

	public CBHT(int m) {

		buckets = (SLLNode<MapEntry<K, E>>[]) new SLLNode[m];

	}

	private int hash(K key) {

		return Math.abs(key.hashCode()) % buckets.length;

	}

	public SLLNode<MapEntry<K, E>> search(K targetKey) {

		int b = hash(targetKey);

		for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {

			if (targetKey.equals(((MapEntry<K, E>) curr.element).key)) {

				return curr;

			}

		}

		return null;

	}

	public void insert(K key, E val) {

		MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);

		int b = hash(key);

		for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {

			if (key.equals(((MapEntry<K, E>) curr.element).key)) {

				curr.element = newEntry;
				return;

			}

		}

		buckets[b] = new SLLNode<MapEntry<K, E>>(newEntry, buckets[b]);

	}

	public void delete(K key) {

		int b = hash(key);

		for (SLLNode<MapEntry<K, E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {

			if (key.equals(((MapEntry<K, E>) curr.element).key)) {

				if (pred == null) {

					buckets[b] = curr.succ;

				} else {

					pred.succ = curr.succ;

				}

				return;

			}

		}

	}

	public String toString() {
		String temp = "";
		for (int i = 0; i < buckets.length; i++) {
			temp += i + ":";
			for (SLLNode<MapEntry<K, E>> curr = buckets[i]; curr != null; curr = curr.succ) {
				temp += curr.element.toString() + " ";
			}
			temp += "\n";
		}
		return temp;
	}
	
	
	
	public static void main(String[] args) {
		
		CBHT<ChemicalElement,Integer> table1 = new CBHT<ChemicalElement,Integer>(26);
        table1.insert(new ChemicalElement("F"),  new Integer(9));
        table1.insert(new ChemicalElement("Ne"), new Integer(10));
        table1.insert(new ChemicalElement("Cl"), new Integer(17));
        table1.insert(new ChemicalElement("Ar"), new Integer(18));
        table1.insert(new ChemicalElement("Br"), new Integer(35));
        table1.insert(new ChemicalElement("Kr"), new Integer(36));
        table1.insert(new ChemicalElement("I"),  new Integer(53));
        table1.insert(new ChemicalElement("Xe"), new Integer(54));

        System.out.println ("Tabelata od slajd 5");
        System.out.println(table1);

        CBHT<ChemicalElement,Integer> table2 = new CBHT<ChemicalElement,Integer>(26);
        table2.insert(new ChemicalElement("H"),  new Integer(1));
        table2.insert(new ChemicalElement("He"), new Integer(2));
        table2.insert(new ChemicalElement("Li"), new Integer(3));
        table2.insert(new ChemicalElement("Be"), new Integer(4));
        table2.insert(new ChemicalElement("Na"), new Integer(11));
        table2.insert(new ChemicalElement("Mg"), new Integer(12));
        table2.insert(new ChemicalElement("K"),  new Integer(19));
        table2.insert(new ChemicalElement("Ca"), new Integer(20));
        table2.insert(new ChemicalElement("Rb"), new Integer(37));
        table2.insert(new ChemicalElement("Sr"), new Integer(38));
        table2.insert(new ChemicalElement("Cs"), new Integer(55));
        table2.insert(new ChemicalElement("Ba"), new Integer(56));

        System.out.println ("Tabelata od slajd 6");
        System.out.println(table2);
		
		
	}
	

}

class ChemicalElement implements Comparable<ChemicalElement> {

	private char sym1, sym2;

	public ChemicalElement(String symbol) {

		if (symbol.length() >= 1) {

			sym1 = Character.toUpperCase(symbol.charAt(0));

		} else {

			sym1 = ' ';

		}

		if (symbol.length() >= 2) {

			sym2 = Character.toLowerCase(symbol.charAt(1));

		} else {

			sym2 = ' ';

		}

	}

	public int hashCode() {

		return sym1 - 'A';

	}

	public int stepCode() {
		return (sym2 == ' ') ? 1 : sym2 - 'a' + 2;
	}

	public String toString() {
		return "" + sym1 + sym2;
	}

	public int compareTo(ChemicalElement that) {
		return (this.sym1 < that.sym1) ? -1 : (this.sym1 > that.sym1) ? 1
				: (this.sym2 < that.sym2) ? -1 : (this.sym2 > that.sym2) ? 1
						: 0;
	}

	@Override
	public boolean equals(Object that) {
		ChemicalElement other = (ChemicalElement) that;
		if (other == null || !(other instanceof ChemicalElement))
			return false;
		else {
			return this.compareTo(other) == 0;
		}
	}

}
