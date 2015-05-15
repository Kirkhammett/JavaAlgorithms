import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

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

public class DecimalToBinary {

	static void convert(int n) {
		// Vasiot kod tuka
		Stack<Integer> s = new ArrayStack<>(100);

		while (n > 0) {
			s.push(n % 2);
			n = n / 2;
		}
		
		while(!s.isEmpty()) {
			System.out.print(s.pop());
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s = br.readLine();
		int n = Integer.parseInt(s);

		convert(n);

		br.close();

	}

}