import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	private Node first, last;
	private int n;
	
	private class Node {	// inner node class
		Item data;
		Node prev;
		Node next;
		
		Node(Item item) {
			data = item;
			prev = null;
			next = null;
		}
	}
	
	private class ListIterator implements Iterator<Item> { // inner class implementing the Iterator
		
		private Node current = first;
		
		public boolean hasNext() {
			return (current != null);
		}
		
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
		public Item next() {
			if (current == null) {
				throw new java.util.NoSuchElementException();
			}
			Item toReturn = current.data;
			current = current.next;
			return toReturn;
		}
	}
	
	public Deque() {	// construct an empty deque
		n = 0;
	}	
	
	public boolean isEmpty() { 	// is the deque empty?
		return (n == 0);
	}
	
	public int size() {		// return the number of items on the deque
		return n;
	}
	
	public void addFirst(Item item) {	// add the item to the front
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (n == 0) {
			first = new Node(item);
			last = first;
		} else {
			Node newNode = new Node(item);
			newNode.next = first;
			first.prev = newNode;
			first = newNode;
		}
		n++;
	}
	
	public void addLast(Item item) {	// add the item to the end
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (n == 0) {
			last = new Node(item);
			first = last;
		} else {
			Node newNode = new Node(item);
			last.next = newNode;
			newNode.prev = last;
			last = newNode;
		}
		n++;
	}
	
	public Item removeFirst() {		// remove and return the item from the front
		if (n == 0) {
			throw new java.util.NoSuchElementException();
		}
		Item toReturn = first.data;
		if (n > 1) {
			first = first.next;
			first.prev = null;
		} else {
			first = null;
			last = null;
		}
		n--;
		return toReturn;
	}
	
	public Item removeLast() {		// remove and return the item from the end
		if (n == 0) {
			throw new java.util.NoSuchElementException();
		}
		Item toReturn = last.data;
		if (n > 1) {
			last = last.prev;
			last.next = null;
		} else {
			first = null;
			last = null;
		}
		n--;
		return toReturn;
	}
	
	public Iterator<Item> iterator() {		// return an iterator over items in order from front to end
		return new ListIterator();
	}
	
}