import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

	// Each MapEntry object is a pair consisting of a key (a Comparable
	// object) and a value (an arbitrary object).
	K key;
	E value;

	public MapEntry (K key, E val) {
		this.key = key;
		this.value = val;
	}

	public int compareTo (K that) {
		// Compare this map entry to that map entry.
		@SuppressWarnings("unchecked")
		MapEntry<K,E> other = (MapEntry<K,E>) that;
		return this.key.compareTo(other.key);
	}

	public String toString () {
		return "<" + key + "," + value + ">";
	}
}

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

class CBHT<K extends Comparable<K>, E> {

	// An object of class CBHT is a closed-bucket hash table, containing
	// entries of class MapEntry.
	private SLLNode<MapEntry<K,E>>[] buckets;

	@SuppressWarnings("unchecked")
	public CBHT(int m) {
		// Construct an empty CBHT with m buckets.
		buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
	}

	private int hash(K key)
	{
		// Napishete ja vie HASH FUNKCIJATA
		return Math.abs(key.hashCode()) % buckets.length;
	}

	public SLLNode<MapEntry<K,E>> search(K targetKey) {
		// Find which if any node of this CBHT contains an entry whose key is
		// equal
		// to targetKey. Return a link to that node (or null if there is none).
		int b = hash(targetKey);
		for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
			if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
				return curr;
		}
		return null;
	}

	public void insert(K key, E val) {              // Insert the entry <key, val> into this CBHT.
		MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
		int b = hash(key);
		for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
			if (key.equals(((MapEntry<K, E>) curr.element).key)) {
				// Make newEntry replace the existing entry ...
				curr.element = newEntry;
				return;
			}
		}
		// Insert newEntry at the front of the 1WLL in bucket b ...
		buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
	}

	public void delete(K key) {
		// Delete the entry (if any) whose key is equal to key from this CBHT.
		int b = hash(key);
		for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
			if (key.equals(((MapEntry<K,E>) curr.element).key)) {
				if (pred == null)
					buckets[b] = curr.succ;
				else
					pred.succ = curr.succ;
				return;
			}
		}
	}

	public String toString() {
		String temp = "";
		for (int i = 0; i < buckets.length; i++) {
			temp += i + ":";
			for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
				temp += curr.element.toString() + " ";
			}
			temp += "\n";
		}
		return temp;
	}
}

public class DedoMrazPomoshnici {
	public static void main (String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		CBHT<String, String> tabela = new CBHT<String, String>(N+1);// Vie ja odreduvate goleminata na tabelata

		// vo imeDobriDeca se zachuvuvaat iminjata na dobrite deca
		String[] imeDobriDeca = new String[N];
		// tuka se zachuvuvaat soodvetnite adresi na decata
		String[] poklonDobriDeca = new String[N];
		String pom;
		for (int i = 0; i < N; i++)
		{
			pom = br.readLine();
			String[] del = pom.split(" ");
			//imeDobriDeca[i] = del[0];
			//poklonDobriDeca[i] = del[1];
			tabela.insert(del[0],del[1]);
		}

		//tuka se zapishuva imeto na deteto shto treba da se proveri
		String deteZaProverka = br.readLine();

		String pomosen = "";
		for(int i = 0; i<deteZaProverka.length()-1;i++)
		{
			char bukva = deteZaProverka.charAt(i);
			char h= deteZaProverka.charAt(i+1);
			if((bukva == 'c' || bukva == 'C') && h=='h' && (i+1)<deteZaProverka.length())
			{
				pomosen+=bukva;
				i+=1;
			}

			else if((bukva == 's'|| bukva == 'S') && h=='h' && (i+1)<deteZaProverka.length())
			{
				pomosen+=bukva;
				i+=1;
			}

			else if((bukva == 'z' || bukva == 'Z') && h=='h' && (i+1)<deteZaProverka.length())
			{
				pomosen+=bukva;
				i+=1;
			}
			else
				pomosen+=bukva;

			System.out.println(pomosen);

		}
		char bukva2 = (char) (deteZaProverka.charAt(deteZaProverka.length()-1));
		pomosen += bukva2;
		System.out.println(pomosen);
		if(tabela.search(pomosen)!=null)
		{
			SLLNode<MapEntry<String, String>> a= tabela.search(pomosen);
			System.out.println(a.element.value);

		}
		else
			System.out.println("nema poklon");

	}
}

