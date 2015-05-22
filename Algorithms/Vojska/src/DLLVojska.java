import java.io.IOException;
import java.io.InputStreamReader;

class DLLNode<E> {
	protected E element;
	protected DLLNode<E> pred, succ;

	public DLLNode(E elem, DLLNode<E> pred, DLLNode<E> succ) {
		this.element = elem;
		this.pred = pred;
		this.succ = succ;
	}

	@Override
	public String toString() {
		return "<-" + element.toString() + "->";
	}
}

class DLL<E> {
	private DLLNode<E> first, last;

	public DLL() {
		// Construct an empty SLL
		this.first = null;
		this.last = null;
	}

	public void deleteList() {
		first = null;
		last = null;
	}

	public int length() {
		int ret;
		if (first != null) {
			DLLNode<E> tmp = first;
			ret = 1;
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret++;
			}
			return ret;
		} else
			return 0;

	}

	public void insertFirst(E o) {
		DLLNode<E> ins = new DLLNode<E>(o, null, first);
		if (first == null)
			last = ins;
		else
			first.pred = ins;
		first = ins;
	}

	public void insertLast(E o) {
		if (first == null)
			insertFirst(o);
		else {
			DLLNode<E> ins = new DLLNode<E>(o, last, null);
			last.succ = ins;
			last = ins;
		}
	}

	public void insertAfter(E o, DLLNode<E> after) {
		if (after == last) {
			insertLast(o);
			return;
		}
		DLLNode<E> ins = new DLLNode<E>(o, after, after.succ);
		after.succ.pred = ins;
		after.succ = ins;
	}

	public void insertBefore(E o, DLLNode<E> before) {
		if (before == first) {
			insertFirst(o);
			return;
		}
		DLLNode<E> ins = new DLLNode<E>(o, before.pred, before);
		before.pred.succ = ins;
		before.pred = ins;
	}

	public E deleteFirst() {
		if (first != null) {
			DLLNode<E> tmp = first;
			first = first.succ;
			if (first != null)
				first.pred = null;
			if (first == null)
				last = null;
			return tmp.element;
		} else
			return null;
	}

	public E deleteLast() {
		if (first != null) {
			if (first.succ == null)
				return deleteFirst();
			else {
				DLLNode<E> tmp = last;
				last = last.pred;
				last.succ = null;
				return tmp.element;
			}
		}
		// else throw Exception
		return null;
	}

	@Override
	public String toString() {
		String ret = new String();
		if (first != null) {
			DLLNode<E> tmp = first;
			ret += tmp + "<->";
			while (tmp.succ != null) {
				tmp = tmp.succ;
				ret += tmp + "<->";
			}
		} else
			ret = "Prazna lista!!!";
		return ret;
	}

	public DLLNode<E> getFirst() {
		return first;
	}

	public DLLNode<E> getLast() {

		return last;
	}

	public DLLNode<E> find(E o) {
		if (first != null) {
			DLLNode<E> tmp = first;
			while (tmp.element != o && tmp.succ != null)
				tmp = tmp.succ;
			if (tmp.element == o) {
				return tmp;
			} else {
				System.out.println("Elementot ne postoi vo listata");
			}
		} else {
			System.out.println("Listata e prazna");
		}
		return first;
	}
}

public class DLLVojska {

	public static void main(String[] args) throws IOException {
		DLL<Integer> lista = new DLL<Integer>();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String s = stdin.readLine();
		int N = Integer.parseInt(s);
		s = stdin.readLine();
		String[] ids = s.split(" ");
		for (int i = 0; i < N; i++) {
			lista.insertLast(Integer.parseInt(ids[i]));
		}

		s = stdin.readLine();
		String interval[] = s.split(" ");
		int a = Integer.parseInt(interval[0]);
		int b = Integer.parseInt(interval[1]);

		s = stdin.readLine();
		interval = s.split(" ");
		int c = Integer.parseInt(interval[0]);
		int d = Integer.parseInt(interval[1]);

		DLL<Integer> result = vojska(lista, a, b, c, d);

		DLLNode<Integer> node = result.getFirst();
		System.out.print(node.element);
		node = node.succ;
		while (node != null) {
			System.out.print(" " + node.element);
			node = node.succ;
		}

	}

	private static DLL<Integer> vojska(DLL<Integer> lista, int a, int b, int c,
			int d) {

		DLL<Integer> lista1 = new DLL<Integer>();
		DLL<Integer> lista2 = new DLL<Integer>();
		DLLNode<Integer> p = lista.find(a);
		DLLNode<Integer> q = lista.find(b);
		DLLNode<Integer> r = lista.find(c);
		DLLNode<Integer> s = lista.find(d);
		// 10
		// 1 2 3 4 5 6 7 8 9 10
		// 1 5
		// 6 10
		while (p != q) {
			lista1.insertLast(p.element);
			p = p.succ;
		}
		lista1.insertLast(p.element);
		String l1 = lista1.toString();

		while (r != s) {
			lista2.insertLast(r.element);
			r = r.succ;
		}

		lista2.insertLast(r.element);

		String l2 = lista2.toString();

		System.out.print(l1 + "\n");
		System.out.print(l2 + "\n");

		DLLNode<Integer> pom1 = lista2.getFirst();
		DLLNode<Integer> pom2 = lista1.getFirst();

		while (pom1.succ != null) 
		{
			pom1 = pom1.succ;
		}
		
		pom1.succ = pom2;
		System.out.print(l2 + "\n");
		return lista2;
	}

}