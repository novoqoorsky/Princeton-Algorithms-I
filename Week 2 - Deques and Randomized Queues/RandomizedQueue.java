import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private int n;
	private Item[] items;
	
	private class ArrayIterator implements Iterator<Item> {
		
		private final Item[] array;
		private int it;
		
		ArrayIterator() {
			array = (Item[]) new Object[n];
			for (int i = 0; i < n; i++) {
				array[i] = items[i];
			}
			StdRandom.shuffle(array);
		}
		
		public boolean hasNext() {
			return it < n;
		}
		
		public void remove() {
            throw new UnsupportedOperationException();
        }
		
		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			return array[it++];
		}
		
	}
	
	public RandomizedQueue() {		// construct an empty randomized queue
		items = (Item[]) new Object[2];		// starting size of the array is 2
		n = 0;
	}
	
	private Item[] resizeArray(int size) {		// create a larger array
		Item[] newArray =  (Item[]) new Object[size];
		for (int i = 0; i < items.length; i++) {
			newArray[i] = items[i];		
		}
		return newArray;
	}
	
	public boolean isEmpty() {		// is the queue empty?
		return (n == 0);
	}
	
	public int size() {		// return the number of items on the queue
		return n;
	}
	
	public void enqueue(Item item) {	// add the item
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (n == items.length) {	// double size of the array if it gets full
			items = resizeArray(items.length*2);
		}
		items[n] = item;
		n++;
	}
	
	public Item dequeue() {		// remove and return a random item
		if (n == 0) {
			throw new java.util.NoSuchElementException();
		}
		int rand = StdRandom.uniform(0, n);
		Item toReturn = items[rand];
		if (rand != n-1) {		
			items[rand] = items[n-1];	// cut the last value and paste it at rand index
		}
		items[n-1] = null;	// so that no value but last remains null
		n--;
		if (n == items.length/4 && n > 0) {		// if array gets one-quarter full 
			Item[] newArray = (Item[]) new Object[items.length/2];	// resize it to half full
			int j = 0;
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					newArray[j++] = items[i];
				}
			}
			items = newArray;
		}
		return toReturn;
	}
	
	public Item sample() {		// return (but do not remove) a random item
		if (n == 0) {
			throw new java.util.NoSuchElementException();
		}
		return items[StdRandom.uniform(0, n)];
	}
	
	public Iterator<Item> iterator() {	// return an independent iterator over items in random order
		return new ArrayIterator();
	}
	
}
