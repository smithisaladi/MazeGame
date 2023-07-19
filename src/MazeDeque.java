import java.util.ArrayList;

// To represent a double ended queue 
class Deque<T> {
  Sentinel<T> header;

  // Main constructor 
  Deque() {
    this.header = new Sentinel<T>();
  }

  // Convenience constructor
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // Counts the number of nodes in DQ
  int size() {
    return this.header.sizeHelp();
  }

  // Adds the item at the front of a DQ
  void addAtHead(T given) {
    this.header.addAtHeadHelp(given);
  }

  // Adds the item at the end of a DQ
  void addAtTail(T given) {
    this.header.addAtTailHelp(given);
  }

  // Removes the first node from a DQ
  T removeFromHead() {
    return this.header.removeFromHeadHelp();
  }

  // Removes the last node from the DQ
  T removeFromTail() {
    return this.header.removeFromTailHelp();
  }

  // Makes the first node of the DQ when the predicate returns true
  ANode<T> find(IPred<T> pred) {
    return this.header.findHelp(pred);
  }

  // Removes the given node from the DQ
  void removeNode(ANode<T> given) {
    this.header.removeNodeHelp(given);
  }

  // Returns true if this DQ contains the given data
  boolean contains(T given) {
    return this.header.next.containsHelp(given);
  }
}

// To represent the abstract class ANode
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;   

  // Counts the number of nodes
  int count() {
    return 0;
  }

  // Removes the node 
  abstract T remove();

  // Finds a node that returns true for the predicate 
  abstract ANode<T> find(IPred<T> pred);

  // Removes the given Node
  abstract void removeGiven(ANode<T> given);

  // Returns true if the node contains the given data 
  abstract boolean containsHelp(T given);
}

// To represent a Sentinel 
class Sentinel<T> extends ANode<T> {
  Sentinel() {
    this.next = this;
    this.prev = this;
  }

  // Counts the number of nodes in the next sentinel 
  public int sizeHelp() {
    return this.next.count();
  }

  // Helper for addAtHead
  public void addAtHeadHelp(T given) {
    ANode<T> oldNext = this.next;
    this.next = new Node<T>(given, oldNext, this);
  }

  // Helper for addatTail
  public void addAtTailHelp(T given) {
    ANode<T> oldPrev = this.prev;
    this.prev = new Node<T>(given, this, oldPrev);
  }

  // Helper for removeFromHead
  public T removeFromHeadHelp() {
    return this.next.remove();
  }

  // Helper for removeFromTail
  public T removeFromTailHelp() {
    return this.prev.remove();
  }

  // Removes this node 
  public T remove() {
    throw new RuntimeException();
  }

  // Helper for Find
  public ANode<T> findHelp(IPred<T> pred) {
    return this.next.find(pred);
  }

  // Returns this node if it satisfies the given predicate
  public ANode<T> find(IPred<T> pred) {
    return this;
  }

  // Helper for removeNode
  public void removeNodeHelp(ANode<T> given) {
    this.next.removeGiven(given);
  }

  // Removes the given node in this sentinel
  public void removeGiven(ANode<T> given) {
    return;
  }

  // Returns true if the node contains the given item 
  public boolean containsHelp(T given) {
    return false;
  }
}

// To represent a node
class Node<T> extends ANode<T> {
  T data;

  // initializes next and prev to null
  Node(T data) {
    this.next = null;
    this.prev = null;
  }

  // Main Constructor 
  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;

    if (next == null || prev == null) {
      throw new IllegalArgumentException();
    }
    else {
      this.prev = prev;
      this.next = next;
      prev.next = this;
      next.prev = this;
    }
  }

  // counts the number of nodes
  public int count() {
    return 1 + this.next.count();
  }

  // Removes the node 
  public T remove() {
    this.prev.next = this.next;
    this.next.prev = this.prev;
    return this.data;
  }

  // Returns this node if it satisfies the given predicate
  public ANode<T> find(IPred<T> pred) {
    if (pred.apply(this.data)) {
      return this;
    }
    else {
      return this.next.find(pred);
    }
  }

  // Removes the given node 
  public void removeGiven(ANode<T> given) {
    if (this.equals(given)) {
      this.prev.next = this.next;
      this.next.prev = this.prev;
    }
    else {
      this.next.removeGiven(given);
    }
  }

  // Returns true if the node contains the given item 
  public boolean containsHelp(T given) {
    if (this.data.equals(given)) {
      return true;
    }
    else {
      return this.next.containsHelp(given);
    }
  }
}

// To represent a list of mutable collection items 
interface ICollection<T> {

  // Returns true if the collection is empty 
  boolean isEmpty();

  // Adds the given item to the collection 
  void add(T item);

  // Removes the first item of the collection 
  T remove();
}

// To represent a stack
class Stack<T> implements ICollection<T> {
  Deque<T> contents = new Deque<T>();

  // Main Constructor 
  Stack(Deque<T> contents) {
    this.contents = contents;
  }

  //Convenience Constructor
  Stack() {
  }

  // Returns true if this stack in empty 
  public boolean isEmpty() {
    return this.contents.size() == 0;
  }

  // Removes the first item of the list 
  public T remove() {
    return this.contents.removeFromHead();
  }

  // Adds an item to the head of the list
  public void add(T item) {
    this.contents.addAtHead(item);
  }
}

// To represent a queue
class Queue<T> implements ICollection<T> {
  Deque<T> contents = new Deque<T>();

  // Convenience Constructor
  Queue() {
  }

  // Main Constructor 
  Queue(Deque<T> contents) {
    this.contents = contents;
  }

  // Returns true if the queue is empty 
  public boolean isEmpty() {
    return this.contents.size() == 0;
  }

  // Removes an item from the beginning of the list 
  public T remove() {
    return this.contents.removeFromHead();
  }

  // Adds an item to the tail of the list
  public void add(T item) {
    this.contents.addAtTail(item);
  }
}

// To represent utilities for array lists
class ArrayUtils {
  <T> ArrayList<T> reverse(ArrayList<T> source) {
    ArrayList<T> tempAList = new ArrayList<T>();
    Stack<T> tempStack = new Stack<T>(new Deque<T>(new Sentinel<T>()));
    for (int t = 0; t < source.size(); t++) {
      tempStack.add(source.get(t));
    }
    for (int i = 0; i < source.size(); i++) {
      tempAList.add(tempStack.remove());
    }
    return tempAList;
  }
}

// A predicate for boolean methods 
interface IPred<T> {
  boolean apply(T t);
}

// Finds the given String
class FindString implements IPred<String> {
  String str;

  FindString(String str) {
    this.str = str;
  }

  // finds the given String
  public boolean apply(String given) {
    return this.str.equals(given);
  }
}
