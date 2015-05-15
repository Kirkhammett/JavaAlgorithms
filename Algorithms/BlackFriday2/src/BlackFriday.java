import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.StringTokenizer;

class Heap<E extends Comparable<E>> {
    
    private int N;
    private E elements[];
    
    private Comparator<? super E> comparator;
    
    private int compare(E k1, E k2) {
	return (comparator==null ? k1.compareTo(k2) : comparator.compare(k1, k2));
    }
    
    int getParent(int i) {
        return (i+1)/2-1;
    }
    
    public E getAt(int i) {
        return elements[i];
    }
    
    int getLeft(int i) {
        return (i+1)*2-1;
    }
    
    int getRight(int i) {
        return (i+1)*2;
    }
    
    void setElement(int index, E elem) {
        elements[index] = elem;
    }
    
    void swap(int i, int j) {
        E tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;
    }
    
    void adjust(int i, int n){
        
        while (i < n) {
            
            int left = getLeft(i);
            int right = getRight(i);
            int largest = i;
            
            if ((left < n)&&(elements[left].compareTo(elements[largest]) > 0))
                largest = left;
            if ((right < n)&&(elements[right].compareTo(elements[largest]) > 0))
                largest = right;
            
            if (largest == i)
                break;
            
            swap(i, largest);
            i = largest;
            
        }
	
    }
    
    void buildHeap() {
        int i;
        for (i=elements.length/2-1;i>=0;i--)
            adjust(i, elements.length);
    }
    
    public void heapSort() {
        int i;
        buildHeap();
        for (i=elements.length;i>1;i--) {
            swap(0, i-1);
            adjust(0, i-1);
        }
    }
    
    @SuppressWarnings("unchecked")
    public Heap(int size) {
        elements = (E[])new Comparable[size];
        N = 0;
    }
    
    public boolean insert(E elem) {
        if (N == elements.length)
            return false;   // there is not enough space in the array
        elements[N] = elem;
        N++;
        adjustUp(N-1, N);
        return true;
    }
    
    public E getMax() {
        if (N == 0)
            return null;
        return elements[0];
    }
    
    public E removeMax() {
        if (N == 0)
            return null;
        E tmp = elements[0];
        elements[0] = elements[N-1];
        N--;
        adjust(0, N);
        return tmp;
    }
    
    void adjustUp(int i, int n){
        while (i > 0) {
            int parent = getParent(i);
            if (elements[i].compareTo(elements[parent]) <= 0) {
                break;
            } else {
                swap(i, parent);
                i = parent;
            }
        }	
    }
    
    public boolean isEmpty() {
        if (N == 0)
            return true;
        return false;
    }
    
    public int getSize() {
        return N;
    }
    
}

class Visit implements Comparable<Visit> {
    String timeFrom;
    int duration;   // in minutes
    
    int minutesStart;
    int minutesEnd;
        
    Visit(String timeFrom, int duration) {
        this.timeFrom = timeFrom;
        this.duration = duration;
        int hours = Integer.parseInt(timeFrom.substring(0, 2));
        int minutes = Integer.parseInt(timeFrom.substring(3, 5));
        minutesStart = hours*60+minutes;
        minutesEnd = minutesStart+duration;
        
    }
    
    @Override
    public int compareTo(Visit o) {
        if (minutesEnd < o.minutesEnd)
            return 1;
        if (minutesEnd > o.minutesEnd)
            return -1;
        return 0;
    }
    
}

public class BlackFriday {
    
    public static void main(String[] args) throws Exception {
        int i,j,k;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        Visit visits[] = new Visit[N];
        
        for (i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            visits[i] = new Visit(st.nextToken(), Integer.parseInt(st.nextToken()));
        }
        
        Heap<Visit> ab = new Heap<Visit>(N);
        for (i = 0; i < N; i++) {
            ab.insert(visits[i]);
        }
        ab.heapSort();
        int max = 0;
        int counter = 0;
        for (i = N - 1; i >= 0; i--) {
            counter = 0;
            for (j = i; j >= 0; j--) {
                if (ab.getAt(i).minutesEnd >= ab.getAt(j).minutesStart) {
                    counter++;
                }
            }
            if (counter > max) {
                max = counter;
            }
        }
        System.out.println(max);
        
    }
    
}