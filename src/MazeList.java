import java.util.ArrayList;
import java.util.Iterator;

// to represent utilities
class Utils {
  // converts the given array list into an IList
  <T> IList<T> flatten(ArrayList<T> alist) {
    IList<T> temp = new Empty<T>();
    for (int i = 0; i < alist.size(); i++) {
      temp = temp.add(alist.get(i));
    }
    return temp;
  }

  // converts the given 2D array list into an IList
  <T> IList<T> flatten2D(ArrayList<ArrayList<T>> alist) {
    Utils util = new Utils();
    IList<T> temp = new Empty<T>();
    for (int row = 0; row < alist.size(); row++) {
      temp = temp.append(util.flatten(alist.get(row)));
    }
    return temp;
  }

  // flattens a 2D array list into a 1D array list
  <T> ArrayList<T> flattenTo1D(ArrayList<ArrayList<T>> alist) {
    ArrayList<T> temp = new ArrayList<T>();
    for (int row = 0; row < alist.size(); row++) {
      for (int col = 0; col < alist.get(row).size(); col++) {
        temp.add(alist.get(row).get(col));
      }
    }
    return temp;
  }

  // checks if the given array list contains the given item
  <T> boolean arrContains(ArrayList<T> arr, T item) {
    for (int i = 0; i < arr.size(); i++) {
      T cur = arr.get(i);
      if (cur.equals(item)) {
        return true;
      }
    }
    return false;
  }
}

// to represent a list of T
interface IList<T> extends Iterable<T> {
  // calculates the size of this list
  int size();

  // adds the given item to this list
  IList<T> add(T given);

  // appends the given list onto this list
  IList<T> append(IList<T> given);

  // casts this list as a cons
  Cons<T> asCons();

  // is this a cons?
  boolean isCons();

  // creates an iterator for this list
  Iterator<T> iterator();
}

// to represent an empty list of T
class Empty<T> implements IList<T> {
  // calculates the size of this list
  public int size() {
    return 0;
  }

  // adds the given item to this list
  public IList<T> add(T given) {
    return new Cons<T>(given, this);
  }

  // appends the given list onto this list
  public IList<T> append(IList<T> given) {
    return given;
  }

  // casts this list as a cons
  public Cons<T> asCons() {
    throw new ClassCastException();
  }

  // is this a cons?
  public boolean isCons() {
    return false;
  }

  // creates an iterator for this list
  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }
}

// to represent a non-empty list of T
class Cons<T> implements IList<T> {
  T first;
  IList<T> rest;

  Cons(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // calculates the size of this list
  public int size() {
    return 1 + this.rest.size();
  }

  // adds the given item to this list
  public IList<T> add(T given) {
    return this.append(new Cons<T>(given, new Empty<T>()));
  }

  // appends the given list onto this list
  public IList<T> append(IList<T> given) {
    return new Cons<T>(this.first, this.rest.append(given));
  }

  // casts this list as a cons
  public Cons<T> asCons() {
    return this;
  }

  // is this a cons?
  public boolean isCons() {
    return true;
  }

  // creates an iterator for this list
  public Iterator<T> iterator() {
    return new IListIterator<T>(this);
  }
}


//To represent an iterator for lists
class IListIterator<T> implements Iterator<T> {
  IList<T> items;

  IListIterator(IList<T> items) {
    this.items = items;
  }

  // does the list have at least one more item?
  public boolean hasNext() {
    return this.items.isCons();
  }

  // gets the next item in the list
  // EFFECT: advances the iterator to the subsequent value
  public T next() {
    if (!this.hasNext()) {
      throw new IllegalArgumentException();
    }
    Cons<T> itemsAsCons = this.items.asCons();
    T answer = itemsAsCons.first;
    this.items = itemsAsCons.rest;
    return answer;
  }

  // EFFECT: removes the item just returned by next()
  public void remove() {
    throw new UnsupportedOperationException();
  }
}

