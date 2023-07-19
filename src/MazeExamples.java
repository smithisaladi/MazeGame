import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import tester.Tester;

// to represent examples and tests
class ExamplesMazeGame {

  ///////////// TESTS FOR MAZE ///////////// 

  Maze w;

  // To start the game
  void testGame(Tester t) {
    w = new Maze();
    w.bigBang(Maze.MAZE_WIDTH * Maze.CELL_SIZE, Maze.MAZE_HEIGHT
        * Maze.CELL_SIZE, .05);
  }

  ArrayList<Edge> edges = new ArrayList<Edge>();
  Utils util = new Utils();
  HashMap<Vertex, Vertex> hm = new HashMap<Vertex, Vertex>();
  Player p1 = new Player(new Vertex(0, 0));

  Vertex v00 = new Vertex(0, 0);
  Vertex v10 = new Vertex(1, 0);
  Vertex v20 = new Vertex(2, 0);
  Vertex v01 = new Vertex(0, 1);
  Vertex v11 = new Vertex(1, 1);
  Vertex v21 = new Vertex(2, 1);

  Edge v00v10 = new Edge(this.v00, this.v10, 5);
  Edge v10v20 = new Edge(this.v10, this.v20, 3);
  Edge v01v11 = new Edge(this.v01, this.v11, 10);
  Edge v11v21 = new Edge(this.v11, this.v21, 12);
  Edge v00v01 = new Edge(this.v00, this.v01, 15);
  Edge v10v11 = new Edge(this.v10, this.v11, 20);
  Edge v20v21 = new Edge(this.v20, this.v21, 4);

  ArrayList<Edge> arrEdge1 = new ArrayList<Edge>();

  ArrayList<ArrayList<Vertex>> arrVertex = new ArrayList<ArrayList<Vertex>>();

  IList<Edge> mtEdge = new Empty<Edge>();
  IList<Edge> loe1 = new Cons<Edge>(this.v00v10, new Cons<Edge>(this.v10v20,
      this.mtEdge));

  // to initialize the data for tests
  void initDataMaze() {
    this.w = new Maze();

    this.v00 = new Vertex(0, 0);
    this.v10 = new Vertex(1, 0);
    this.v20 = new Vertex(2, 0);
    this.v01 = new Vertex(0, 1);
    this.v11 = new Vertex(1, 1);
    this.v21 = new Vertex(2, 1);

    this.hm = new HashMap<Vertex, Vertex>();
    hm.put(this.v00, this.v00);
    hm.put(this.v10, this.v00);
    hm.put(this.v20, this.v10);
    hm.put(this.v11, this.v21);
    hm.put(this.v21, this.v21);

    this.arrVertex = new ArrayList<ArrayList<Vertex>>();
    arrVertex.add(0, new ArrayList<Vertex>());
    arrVertex.add(1, new ArrayList<Vertex>());
    arrVertex.add(2, new ArrayList<Vertex>());
    arrVertex.get(0).add(this.v00);
    arrVertex.get(0).add(this.v01);
    arrVertex.get(1).add(this.v10);
    arrVertex.get(1).add(this.v11);
    arrVertex.get(2).add(this.v20);
    arrVertex.get(2).add(this.v21);

    this.v00v10 = new Edge(this.v00, this.v10, 5);
    this.v10v20 = new Edge(this.v10, this.v20, 3);
    this.v01v11 = new Edge(this.v01, this.v11, 10);
    this.v11v21 = new Edge(this.v11, this.v21, 12);
    this.v00v01 = new Edge(this.v00, this.v01, 15);
    this.v10v11 = new Edge(this.v10, this.v11, 20);
    this.v20v21 = new Edge(this.v20, this.v21, 4);

    this.arrEdge1 = new ArrayList<Edge>();
  }

  // to test the method drawPlayer
  void testDrawPlayer(Tester t) {
    t.checkExpect(this.p1.drawPlayer(),
        new RectangleImage(Maze.CELL_SIZE, Maze.CELL_SIZE,
            OutlineMode.SOLID, new Color(121, 171, 133)));
  }

  // to test the method equals for vertex
  void testVertexEqual(Tester t) {
    t.checkExpect(this.v00.equals(this.v00), true);
    t.checkExpect(this.v11.equals(this.v11), true);
    t.checkExpect(this.v00.equals(this.v01), false);
    t.checkExpect(this.v11.equals(this.v20), false);
  }

  // to test the method hashCode for vertex
  void testVertexHashCode(Tester t) {
    t.checkExpect(this.v00.hashCode(), 0);
    t.checkExpect(this.v11.hashCode(), 10000);
  }

  // to test the method drawVert
  void testDrawVert(Tester t) {
    t.checkExpect(this.v00.drawVert(), new RectangleImage(1,
        Maze.CELL_SIZE, OutlineMode.SOLID, Color.BLACK));
    t.checkExpect(this.v10.drawVert(), new RectangleImage(1,
        Maze.CELL_SIZE, OutlineMode.SOLID, Color.BLACK));
  }

  // to test the method drawHoriz
  void testDrawHoriz(Tester t) {
    t.checkExpect(this.v00.drawHoriz(), new RectangleImage(Maze.CELL_SIZE,
        1, OutlineMode.SOLID, Color.BLACK));
    t.checkExpect(this.v10.drawHoriz(), new RectangleImage(Maze.CELL_SIZE,
        1, OutlineMode.SOLID, Color.BLACK));
  }

  // to test the method equals for edge
  void testEdgeEqual(Tester t) {
    t.checkExpect(this.v00v01.equals(this.v00v01), true);
    t.checkExpect(this.v00v10.equals(this.v00v01), false);
  }

  // to test the method hashCode for edge
  void testEdgeHashCode(Tester t) {
    t.checkExpect(this.v00v01.hashCode(), 0);
    t.checkExpect(this.v11v21.hashCode(), -1454759936);
  }

  // to test the method compareTo for edge
  void testEdgeCompareTo(Tester t) {
    t.checkExpect(this.v00v01.compareTo(this.v01v11), 5);
    t.checkExpect(this.v00v10.compareTo(this.v20v21), 1);
  }

  // to test the method initVertices
  void testInitVertices(Tester t) {
    this.initDataMaze();
    ArrayList<ArrayList<Vertex>> arr = this.w.initVertices();
    t.checkExpect(arr.get(0).get(1), new Vertex(0, 1));
    t.checkExpect(arr.get(5).get(7), new Vertex(5, 7));
  }

  // to test the method initVertEdges
  void testInitVertEdges(Tester t) {
    this.initDataMaze();
    w.initVertEdges(this.arrVertex);
    t.checkExpect(this.v00.down, new Edge(this.v00, this.v01));
    t.checkExpect(this.v00.right, new Edge(this.v00, this.v10));
    t.checkExpect(this.v20.right, null);
  }

  // to test the method fixPlayer
  void testFixPlayer(Tester t) {
    this.initDataMaze();
    w.player = new Player(new Vertex(5, 5));
    w.fixPlayer();
    t.checkExpect(w.player.position.right, new Edge(w.player.position,
        new Vertex(6, 5)));
    t.checkExpect(w.player.position.left, new Edge(new Vertex(4, 5),
        w.player.position));
    t.checkExpect(w.player.position.up, new Edge(new Vertex(5, 4),
        w.player.position));
    t.checkExpect(w.player.position.down, new Edge(w.player.position,
        new Vertex(5, 6)));
  }

  // to test the method randomWeights
  void testRandomWeights(Tester t) {
    this.initDataMaze();
    w.initMaze();
    t.checkRange(w.worklist.get(0).weight, 0, 99);
    t.checkRange(w.worklist.get(5).weight, 0, 99);
  }

  // to test the method find
  void testFindMaze(Tester t) {
    this.initDataMaze();
    t.checkExpect(this.w.find(hm, this.v20), this.v00);
    t.checkExpect(this.w.find(hm, this.v00), this.v00);
    t.checkExpect(this.w.find(hm,  this.v11), this.v21);
  }

  // to test the method union
  void testUnion(Tester t) {
    this.initDataMaze();
    this.w.union(hm, this.v11, this.v20);
    t.checkExpect(hm.get(this.v21), this.v00);
  }

  // to test the method initReps
  void testInitReps(Tester t) {
    this.initDataMaze();
    ArrayList<Edge> arr = new ArrayList<Edge>();
    arr.add(this.v00v01);
    arr.add(this.v01v11);
    arr.add(this.v11v21);
    w.worklist = arr;
    HashMap<Vertex, Vertex> hm = w.initReps();
    t.checkExpect(hm.get(w.worklist.get(0).from), this.v00);
    t.checkExpect(hm.get(w.worklist.get(0).to), this.v01);
  }

  // to test the method isTreeComplete
  void testIsTreeComplete(Tester t) {
    this.initDataMaze();
    w.edgesInTree = new ArrayList<Edge>();
    t.checkExpect(w.isTreeComplete(), false);
    w.initMaze();
    t.checkExpect(w.isTreeComplete(), true);
  }

  // to test the method unionFind
  void testUnionFind(Tester t) {
    this.initDataMaze();
    w.initMaze();
    t.checkExpect(this.w.edgesInTree.size(), Maze.MAZE_HEIGHT *
        Maze.MAZE_WIDTH - 1);
    t.checkExpect(this.w.worklist.size() < this.w.edgesInTree.size(),
        true);
    t.checkExpect(this.w.worklist.contains(this.w.edgesInTree.get(0)),
        false);
  }

  // to test the method size
  void testSizeMaze(Tester t) {
    t.checkExpect(this.mtEdge.size(), 0);
    t.checkExpect(this.loe1.size(), 2);
  }

  // to test the method add
  void testAdd(Tester t) {
    t.checkExpect(this.mtEdge.add(this.v00v01),
        new Cons<Edge>(this.v00v01, this.mtEdge));
    t.checkExpect(this.loe1.add(this.v01v11), new Cons<Edge>(this.v00v10,
        new Cons<Edge>(this.v10v20, new Cons<Edge>(this.v01v11,
            this.mtEdge))));
  }

  // to test the method append
  void testAppend(Tester t) {
    t.checkExpect(this.mtEdge.append(this.loe1), this.loe1);
    t.checkExpect(this.loe1.append(this.mtEdge), this.loe1);
    t.checkExpect(this.loe1.append(this.loe1), new Cons<Edge>(this.v00v10,
        new Cons<Edge>(this.v10v20, new Cons<Edge>(this.v00v10,
            new Cons<Edge>(this.v10v20, this.mtEdge)))));
  }

  // to test the method asCons
  void testAsCons(Tester t) {
    t.checkException(new ClassCastException(), this.mtEdge, "asCons");
    t.checkExpect(this.loe1.asCons(), this.loe1);
  }

  // to test the method isCons
  void testIsCons(Tester t) {
    t.checkExpect(this.mtEdge.isCons(), false);
    t.checkExpect(this.loe1.isCons(), true);
  }

  // to test the method iterator
  void testIterator(Tester t) {
    t.checkExpect(this.mtEdge.iterator(), new IListIterator<Edge>(this.mtEdge));
    t.checkExpect(this.loe1.iterator(), new IListIterator<Edge>(this.loe1));
  }

  ///////////// TESTS FOR DEQUE ///////////// 

  Deque<String> deque1 = new Deque<String>();
  Sentinel<String> sentinel1 = new Sentinel<String>();

  Deque<String> deque2 = new Deque<String>();
  Sentinel<String> sentinel2 = new Sentinel<String>();
  Node<String> abc = new Node<String>("abc");
  Node<String> bcd = new Node<String>("bcd");
  Node<String> cde = new Node<String>("cde");
  Node<String> def = new Node<String>("def");

  Deque<String> deque3 = new Deque<String>();
  Sentinel<String> sentinel3 = new Sentinel<String>();
  Node<String> zxy = new Node<String>("zxy");
  Node<String> wuv = new Node<String>("wuv");
  Node<String> trs = new Node<String>("trs");
  Node<String> qop = new Node<String>("qop");
  Node<String> nlm = new Node<String>("nlm");

  IPred<String> findABC = new FindString("abc");
  IPred<String> findCDE = new FindString("cde");
  IPred<String> findDEF = new FindString("def");
  IPred<String> findTRS = new FindString("trs");

  Stack<String> stack1 = new Stack<String>();
  Stack<String> stack2 = new Stack<String>();

  ArrayList<Integer> ints = new ArrayList<Integer>();
  ArrayList<Integer> intsReversed = new ArrayList<Integer>();

  ArrayUtils ArrayUtils = new ArrayUtils();

  // to initialize the data for tests
  void initDataDeque() {
    this.deque1 = new Deque<String>();
    this.sentinel1 = new Sentinel<String>();

    this.deque2 = new Deque<String>();
    this.sentinel2 = new Sentinel<String>();
    this.abc = new Node<String>("abc");
    this.bcd = new Node<String>("bcd");
    this.cde = new Node<String>("cde");
    this.def = new Node<String>("def");
    this.deque2 = new Deque<String>(this.sentinel2);
    this.abc = new Node<String>("abc", this.bcd, this.sentinel2);
    this.bcd = new Node<String>("bcd", this.cde, this.abc);
    this.cde = new Node<String>("cde", this.def, this.bcd);
    this.def = new Node<String>("def", this.sentinel2, this.cde);

    this.deque3 = new Deque<String>();
    this.sentinel3 = new Sentinel<String>();
    this.zxy = new Node<String>("zxy");
    this.wuv = new Node<String>("wuv");
    this.trs = new Node<String>("trs");
    this.qop = new Node<String>("qop");
    this.nlm = new Node<String>("nlm");
    this.deque3 = new Deque<String>(this.sentinel3);
    this.zxy = new Node<String>("zxy", this.wuv, this.sentinel3);
    this.wuv = new Node<String>("wuv", this.trs, this.zxy);
    this.trs = new Node<String>("trs", this.qop, this.wuv);
    this.qop = new Node<String>("qop", this.nlm, this.trs);
    this.nlm = new Node<String>("nlm", this.sentinel3, this.qop);

    this.stack1 = new Stack<String>(this.deque1);
    this.stack2 = new Stack<String>(this.deque2);

    this.ints = new ArrayList<Integer>();
    this.intsReversed = new ArrayList<Integer>();
  }

  // to test the method size
  void testSize(Tester t) {
    this.initDataDeque();
    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque3.size(), 5);
  }

  // to test the method addAtHead
  void testAddAtHead(Tester t) {
    this.initDataDeque();
    this.deque1.addAtHead("abc");
    this.deque2.addAtHead("123");
    this.deque3.addAtHead("kij");
    t.checkExpect(this.deque1.header.next, new Node<String>("abc",
        this.deque1.header, this.deque1.header));
    t.checkExpect(this.sentinel2.next, new Node<String>("123", 
        this.abc, this.sentinel2));
    t.checkExpect(this.abc.prev, new Node<String>("123", this.abc,
        this.sentinel2));
    t.checkExpect(this.sentinel3.next, new Node<String>("kij", 
        this.zxy, this.sentinel3));
  }

  // to test the method addAtTail
  void testAddAtTail(Tester t) {
    this.initDataDeque();
    this.deque1.addAtTail("abc");
    this.deque2.addAtTail("efg");
    t.checkExpect(this.deque1.header.next, new Node<String>("abc",
        this.deque1.header, this.deque1.header));
    t.checkExpect(this.deque1.header.prev, new Node<String>("abc",
        this.deque1.header, this.deque1.header));
    t.checkExpect(this.sentinel2.prev, new Node<String>("efg", 
        this.sentinel2, this.def));
  }

  // to test the method removeFromHead
  void testRemoveFromHead(Tester t) {
    this.initDataDeque();
    t.checkException(new RuntimeException(), this.deque1, "removeFromHead");
    t.checkExpect(this.deque2.removeFromHead(), "abc");
    t.checkExpect(this.deque3.removeFromHead(), "zxy");
  }

  // to test the method removeFromTail
  void testRemoveFromTail(Tester t) {
    this.initDataDeque();
    t.checkException(new RuntimeException(), this.deque1, "removeFromTail");
    t.checkExpect(this.deque2.removeFromTail(), "def");
    t.checkExpect(this.deque2.removeFromTail(), "cde");
    t.checkExpect(this.deque3.removeFromTail(), "nlm");
  }

  // to test the method find
  void testFind(Tester t) {
    this.initDataDeque();
    t.checkExpect(this.deque1.find(this.findABC), this.sentinel1);
    t.checkExpect(this.deque2.find(this.findABC), this.abc);
    t.checkExpect(this.deque2.find(this.findCDE), this.cde);
    t.checkExpect(this.deque2.find(this.findDEF), this.def);
    t.checkExpect(this.deque2.find(this.findTRS), this.sentinel2);
  }

  // to test the method removeNode
  void testRemoveNode(Tester t) {
    this.initDataDeque();
    this.deque1.removeNode(this.sentinel1);
    this.deque2.removeNode(this.cde);
    this.deque2.removeNode(this.bcd);
    this.deque1.removeNode(this.qop);
    t.checkExpect(this.deque1.header, this.sentinel1);
    t.checkExpect(this.bcd.next, this.def);
    t.checkExpect(this.abc.next, this.def);
    this.deque2.removeNode(this.abc);
    this.deque2.removeNode(this.def);
    t.checkExpect(this.sentinel2.next, this.sentinel2);
    t.checkExpect(this.sentinel2.prev, this.sentinel2);
  }

  // to test the method count
  void testCount(Tester t) {
    this.initDataDeque();
    t.checkExpect(this.abc.count(), 4);
    t.checkExpect(this.sentinel1.count(), 0);
    t.checkExpect(this.sentinel2.count(), 0);
    t.checkExpect(this.def.count(), 1);
  }

  // to test the method remove
  void testRemove(Tester t) {
    this.initDataDeque();
    t.checkException(new RuntimeException(), this.sentinel1, "remove");
    t.checkExpect(this.abc.remove(), "abc");
    t.checkExpect(this.nlm.remove(), "nlm");
    t.checkExpect(this.bcd.remove(), "bcd");
  }

  // to test the method find for sentinels/nodes
  void testFind4Nodes(Tester t) {
    this.initDataDeque();
    t.checkExpect(this.sentinel1.find(this.findABC), this.sentinel1);
    t.checkExpect(this.bcd.find(this.findABC), this.sentinel2);
    t.checkExpect(this.abc.find(this.findABC), this.abc);
  }

  // to test the method removeGiven
  void testRemoveGiven(Tester t) {
    this.initDataDeque();
    this.abc.removeGiven(this.abc);
    t.checkExpect(this.sentinel2.next, this.bcd);
    this.abc.removeGiven(this.zxy);
    t.checkExpect(this.abc.next, this.bcd);
    this.sentinel1.removeGiven(this.sentinel1);
    t.checkExpect(this.sentinel1.next, this.sentinel1);
  }

  // to test the method sizeHelp
  void testSizeHelp(Tester t) {
    this.initDataDeque();
    t.checkExpect(this.sentinel1.sizeHelp(), 0);
    t.checkExpect(this.sentinel2.sizeHelp(), 4);
    t.checkExpect(this.sentinel3.sizeHelp(), 5);
  }

  // to test the method addAtHeadHelp
  void testAddAtHeadHelp(Tester t) {
    this.initDataDeque();
    this.sentinel1.addAtHeadHelp("123");
    t.checkExpect(this.sentinel1.next, new Node<String>("123", 
        this.sentinel1, this.sentinel1));
    this.sentinel2.addAtHeadHelp("123");
    t.checkExpect(this.sentinel2.next, new Node<String>("123", this.abc,
        this.sentinel2));
  }

  // to test the method addAtTailHelp
  void testAddAtTailHelp(Tester t) {
    this.initDataDeque();
    this.sentinel1.addAtTailHelp("123");
    t.checkExpect(this.sentinel1.next, new Node<String>("123", 
        this.sentinel1, this.sentinel1));
    t.checkExpect(this.sentinel1.prev, new Node<String>("123", 
        this.sentinel1, this.sentinel1));
    this.sentinel2.addAtTailHelp("123");
    t.checkExpect(this.sentinel2.prev, new Node<String>("123", 
        this.sentinel2, this.def));
  }

  // to test the method removeFromHeadHelp
  void testRemoveFromHeadHelp(Tester t) {
    this.initDataDeque();
    this.sentinel2.removeFromHeadHelp();
    t.checkExpect(this.sentinel2.next, this.bcd);
    this.sentinel2.removeFromHeadHelp();
    t.checkExpect(this.sentinel2.next, this.cde);
  }

  // to test the method removeFromTailHelp
  void testRemoveFromTailHelp(Tester t) {
    this.initDataDeque();
    this.sentinel2.removeFromTailHelp();
    t.checkExpect(this.sentinel2.prev, this.cde);
    this.sentinel2.removeFromTailHelp();
    t.checkExpect(this.sentinel2.prev, this.bcd);
  }

  // to test the method findHelp
  void testFindHelp(Tester t) {
    this.initDataDeque();
    t.checkExpect(this.sentinel1.findHelp(this.findABC), this.sentinel1);
    t.checkExpect(this.sentinel2.findHelp(this.findABC), this.abc);
    t.checkExpect(this.sentinel2.findHelp(this.findTRS), this.sentinel2);
  }

  // to test the method removeNodeHelp
  void testRemoveNodeHelp(Tester t) {
    this.initDataDeque();
    this.sentinel1.removeNodeHelp(this.sentinel1);
    t.checkExpect(this.sentinel1.next, this.sentinel1);
    this.sentinel2.removeNodeHelp(this.bcd);
    t.checkExpect(this.abc.next, this.cde);
    t.checkExpect(this.cde.prev, this.abc);
  }

  // to test the method count for nodes
  void testCount4Nodes(Tester t) {
    this.initDataDeque();
    t.checkExpect(this.abc.count(), 4);
    t.checkExpect(this.def.count(), 1);
    t.checkExpect(this.bcd.count(), 3);
  }

  // to test the method add
  void testadd(Tester t) {
    this.initDataDeque();
    this.stack1.add("123");
    t.checkExpect(this.stack1.contents.header.next, new Node<String>("123", 
        this.sentinel1, this.sentinel1));
  }

  // to test the method reverse
  void testReverse(Tester t) {
    this.initDataDeque();
    this.ints.add(1);
    this.ints.add(2);
    this.ints.add(3);
    this.intsReversed.add(3);
    this.intsReversed.add(2);
    this.intsReversed.add(1);
    t.checkExpect(this.ints.get(0), 1);
    t.checkExpect(this.intsReversed.get(0), 3);
    t.checkExpect(ArrayUtils.reverse(this.ints), this.intsReversed);
  }
}