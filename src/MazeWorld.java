import java.util.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

// To represent a player
class Player {
  Vertex position;
  IList<Vertex> visited; 

  Player(Vertex position) {
    this.position = position;
    this.visited = new Empty<Vertex>();
  }

  // draws this player
  WorldImage drawPlayer() {
    return new RectangleImage(Maze.CELL_SIZE, Maze.CELL_SIZE,
        OutlineMode.SOLID, new Color(121, 171, 133));
  }
}

// T represent a vertex
class Vertex {
  Edge right;
  Edge down;
  Edge left;
  Edge up;
  boolean rightFlag = true; 
  boolean downFlag = true; 
  int x;
  int y;
  ArrayList<Vertex> adj = new ArrayList<Vertex>();
  boolean isVisited = false; 

  // Convenience Constructor 
  Vertex() {
  }

  // Convenience Constructor
  Vertex(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Convenience Constructor
  Vertex(Edge right, Edge down) {
    this.right = right;
    this.down = down;
  }

  // Convenience Constructor
  Vertex(Edge right, Edge left, Edge up, Edge down) {
    this.right = right;
    this.left = left;
    this.up = up;
    this.down = down;
  }

  // Returns true if this vertex is equal to the given object 
  public boolean equals(Object other) {
    if (!(other instanceof Vertex)) {
      return false;
    }
    else {
      Vertex that = (Vertex) other;
      return this.x == that.x &&
          this.y == that.y;
    }
  }

  // To produce a hashCode for the vertex 
  public int hashCode() {
    return this.x * this.y * 10000;
  }

  // To draw the vertex's down edge 
  WorldImage drawVert() {
    return new RectangleImage(1, Maze.CELL_SIZE, OutlineMode.SOLID,
        Color.BLACK);
  }

  // To draw the vertex's right edge 
  WorldImage drawHoriz() {
    return new RectangleImage(Maze.CELL_SIZE, 1, OutlineMode.SOLID,
        Color.BLACK);
  }
}

// To represent an edge
class Edge implements Comparable<Edge> {
  Vertex from;
  Vertex to;
  int weight;

  // Convenience Constructor
  Edge(Vertex from, Vertex to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  // Convenience Constructor
  Edge(int weight) {
    this.weight = weight;
  }

  // Convenience Constructor
  Edge(Vertex from, Vertex to) {
    this.from = from;
    this.to = to;
  }

  // Returns true if the edge is equal to the given object 
  public boolean equals(Object other) {
    if (!(other instanceof Edge)) {
      return false;
    }
    else {
      Edge that = (Edge) other;
      return (this.from.equals(that.from) && this.to.equals(that.to))
          || (this.from.equals(that.to) && this.to.equals(that.from));
    }
  }

  // To produces a hashCode for this edge
  public int hashCode() {
    return this.from.hashCode() * this.to.hashCode() * 10000;
  }

  // compares this edge to the given edge
  public int compareTo(Edge o) {
    return this.weight - o.weight;
  }
}

// To represent a maze
class Maze extends World {
  ArrayList<ArrayList<Vertex>> arrVert;
  ArrayList<Edge> edgesInTree;
  ArrayList<Edge> worklist;
  HashMap<Vertex, Vertex> map = new HashMap<Vertex, Vertex>();
  IList<Vertex> searchPath = new Empty<Vertex>();
  Vertex key;
  IList<Vertex> vertices;
  Player player;
  Vertex endPoint; // represents the vertex that must be reached to win
  static final int MAZE_HEIGHT = 50;
  static final int MAZE_WIDTH = 80;
  static final int CELL_SIZE = 10;

  Maze() {
    this.initMaze();
  }

  // To create a 2D array list of vertices
  public ArrayList<ArrayList<Vertex>> initVertices() {
    ArrayList<ArrayList<Vertex>> temp = new ArrayList<ArrayList<Vertex>>();
    for (int i = 0; i < MAZE_WIDTH; i++) {
      ArrayList<Vertex> arrVertex = new ArrayList<Vertex>();
      for (int j = 0; j < MAZE_HEIGHT; j++) {
        arrVertex.add(new Vertex(i, j));
      }
      temp.add(arrVertex);
    }
    return temp;
  }

  // To return an array list of the right and down edges
  public ArrayList<Edge> initVertEdges(ArrayList<ArrayList<Vertex>> vert) {
    ArrayList<Edge> arr = new ArrayList<Edge>();
    for (int j = 0; j < vert.size(); j++) {
      for (int i = 0; i < vert.get(j).size(); i++) {
        Vertex cur = vert.get(j).get(i);
        if (cur.x < vert.size() - 1) {
          cur.right = new Edge(cur, vert.get(j + 1).get(i));
          arr.add(cur.right);
        }
        if (cur.y < vert.get(j).size() - 1) {
          cur.down = new Edge(cur, vert.get(j).get(i + 1));
          arr.add(cur.down);
        }
        if (cur.x > 0) {
          cur.left = new Edge(cur, vert.get(j - 1).get(i));
        }
        if (cur.y > 0) {
          cur.up = new Edge(cur, vert.get(j).get(i - 1));
        }
      }
    }
    return arr;
  }

  // To set all the edge booleans of the vertices to false if they are
  // in the spanning tree
  public void fixVertEdges(ArrayList<ArrayList<Vertex>> vert) {
    for (int j = 0; j < vert.size(); j++) {
      for (int i = 0; i < vert.get(j).size(); i++) {
        Vertex cur = vert.get(j).get(i);
        if (this.edgesInTree.contains(cur.right)) {
          cur.rightFlag = false;
        }
        if (this.edgesInTree.contains(cur.down)) {
          cur.downFlag = false;
        }
      }
    }
  }

  // To set the neighbors of all the vertices
  public void fixVertAdj(ArrayList<ArrayList<Vertex>> vert) {
    for (int j = 0; j < vert.size(); j++) {
      for (int i = 0; i < vert.get(j).size(); i++) {
        Vertex cur = vert.get(j).get(i);
        if (cur.x == 0 || this.worklist.contains(cur.left)) {
          // don't add anything
        }
        else {
          cur.adj.add(vert.get(cur.x - 1).get(cur.y));
        }
        if (cur.x == MAZE_WIDTH - 1 || this.worklist.contains(cur.right)) {
          // don't add anything
        }
        else {
          cur.adj.add(vert.get(cur.x + 1).get(cur.y));
        }
        if (cur.y == 0 || this.worklist.contains(cur.up)) {
          // don't add anything
        }
        else {
          cur.adj.add(vert.get(cur.x).get(cur.y - 1));
        }
        if (cur.y == MAZE_HEIGHT - 1 || this.worklist.contains(cur.down)) {
          // don't add anything
        }
        else {
          cur.adj.add(vert.get(cur.x).get(cur.y + 1));
        }
      }
    }
  }

  // To fix the edges of the player's current, right, and down vertices
  public void fixPlayer() {
    if (this.player.position.x == 0) {
      this.player.position.left = null;
    }
    if (this.player.position.x == MAZE_WIDTH) {
      this.player.position.right = null;
    }
    if (this.player.position.y == 0) {
      this.player.position.up = null;
    }
    if (this.player.position.y == MAZE_HEIGHT) {
      this.player.position.down = null;
    }
    else {
      this.player.position.right = new Edge(this.player.position,
          new Vertex(this.player.position.x + 1, this.player.position.y));
      this.player.position.left =
          new Edge(new Vertex(this.player.position.x - 1,
              this.player.position.y), this.player.position);
      this.player.position.up =
          new Edge(new Vertex(this.player.position.x,
              this.player.position.y - 1), this.player.position);
      this.player.position.down = new Edge(this.player.position,
          new Vertex(this.player.position.x, this.player.position.y + 1));
    }
  }

  // To randomizes the weights of the edges in the worklist
  public void randomWeights(ArrayList<Edge> edges) {
    for (int i = 0; i < edges.size(); i++) {
      edges.get(i).weight = new Random().nextInt(100);
    }
  }

  // To find the representative of the given key
  Vertex find(HashMap<Vertex, Vertex> map, Vertex key) {
    Vertex val = map.get(key);
    while (!map.get(val).equals(val)) {
      val = map.get(val);
    }
    return val;
  }

  // Updates the to's representative to the from's representative
  void union(HashMap<Vertex, Vertex> map, Vertex to, Vertex from) {
    map.put(this.find(map, to), this.find(map, from));
  }

  // Creates a hash map where each vertex's representative is initialized to itself
  public HashMap<Vertex, Vertex> initReps() {
    HashMap<Vertex, Vertex> map = new HashMap<Vertex, Vertex>();
    for (int i = 0; i < this.worklist.size(); i++) {
      Vertex to = this.worklist.get(i).to;
      Vertex from = this.worklist.get(i).from;
      map.put(to, to);
      map.put(from, from);
    }
    return map;
  }

  // Determines if there is a complete spanning tree
  public boolean isTreeComplete() {
    return (MAZE_HEIGHT * MAZE_WIDTH) - 1 == this.edgesInTree.size();
  }

  // Updates the edgesInTree with edges from the worklist after they
  // have been properly represented
  public void unionFind() {
    Collections.sort(worklist);
    HashMap<Vertex, Vertex> map = this.initReps();
    int i = 0;
    while (!this.isTreeComplete() && i < this.worklist.size() - 1) {
      Vertex to = this.worklist.get(i).to;
      Vertex from = this.worklist.get(i).from;
      if (this.find(map, to).equals(this.find(map, from))) {
        i = i + 1;
      }
      else {
        this.edgesInTree.add(this.worklist.remove(i));
        this.union(map, to, from);
      }
    }
  }

  // Performs search or DFS on this maze depending on whether a queue or a stack
  // is given, respectively
  public HashMap<Vertex, Vertex> search(ICollection<Vertex> list) {
    HashMap<Vertex, Vertex> cameFromEdge = new HashMap<Vertex, Vertex>();
    ICollection<Vertex> worklist = list;
    worklist.add(this.arrVert.get(0).get(0));
    while (!worklist.isEmpty()) {
      Vertex next = worklist.remove();
      if (next.isVisited) {
        // discard it
      }
      else if (next.equals(this.endPoint)) {
        return cameFromEdge;
      }
      else {
        next.isVisited = true;
        for (Vertex v : next.adj) {
          worklist.add(v);
          cameFromEdge.put(v, next);
        }
      }
    }
    return cameFromEdge;
  }

  // Initializes this maze
  void initMaze() {
    this.arrVert = this.initVertices();
    edgesInTree = new ArrayList<Edge>();
    vertices = new Empty<Vertex>();
    this.worklist = this.initVertEdges(this.arrVert);
    this.randomWeights(worklist);
    this.unionFind();
    this.fixVertEdges(arrVert);
    this.fixVertAdj(this.arrVert);
    this.map = new HashMap<Vertex, Vertex>();
    this.searchPath = new Empty<Vertex>();
    this.player = new Player(this.arrVert.get(0).get(0));
    this.fixPlayer();
    this.endPoint = this.arrVert.get(MAZE_WIDTH - 1).get(MAZE_HEIGHT - 1);
  }

  // Moves the player/changes maps/performs search or DFS according to
  // the given key
  public void onKeyEvent(String ke) {
    this.fixPlayer();
    // checks if there is a wall in the way
    boolean rightWall = this.worklist.contains(this.player.position.right) ||
        (this.player.position.x == MAZE_WIDTH - 1);
    boolean leftWall = this.worklist.contains(this.player.position.left) ||
        (this.player.position.x == 0);
    boolean topWall = this.worklist.contains(this.player.position.up) ||
        (this.player.position.y == 0);
    boolean bottomWall = this.worklist.contains(this.player.position.down) ||
        (this.player.position.y == MAZE_HEIGHT - 1);
    if (ke.equals("right") && !rightWall) {
      this.player.visited = this.player.visited.add(this.player.position);
      this.player.position = new Vertex(this.player.position.x + 1,
          this.player.position.y);
    }
    else if (ke.equals("left") && !leftWall) {
      this.player.visited = this.player.visited.add(this.player.position);
      this.player.position = new Vertex(this.player.position.x - 1,
          this.player.position.y);
    }
    else if (ke.equals("up") && !topWall) {
      this.player.visited = this.player.visited.add(this.player.position);
      this.player.position = new Vertex(this.player.position.x,
          this.player.position.y - 1);
    }
    else if (ke.equals("down") && !bottomWall) {
      this.player.visited = this.player.visited.add(this.player.position);
      this.player.position = new Vertex(this.player.position.x,
          this.player.position.y + 1);
    }
    // to restart the game
    else if (ke.equals("r")) {
      this.initMaze();
    }
    // to perform bfs
    else if (ke.equals("b")) {
      this.initMaze();
      this.searchPath = new Empty<Vertex>();
      this.map = this.search(new Queue<Vertex>());
      this.key = this.arrVert.get(0).get(0);
    }
    // to perform dfs
    else if (ke.equals("d")) {
      this.initMaze();
      this.searchPath = new Empty<Vertex>();
      this.map = this.search(new Stack<Vertex>());
      this.key = this.arrVert.get(0).get(0);
    }
  }

  // EFFECT: animates the bfs/dfs search
  public void onTick() {
    if (this.map.isEmpty()) {
      // don't do anything
    }
    else {
      this.searchPath = this.searchPath.add(key);
      key = this.map.get(key);
    }
  }

  // To draw this maze
  public WorldScene makeScene() {
    WorldScene bg = new WorldScene(Maze.MAZE_WIDTH * Maze.CELL_SIZE,
        Maze.MAZE_HEIGHT * Maze.CELL_SIZE);
    Utils util = new Utils();
    IList<Vertex> vert = util.flatten2D(arrVert);
    IListIterator<Vertex> iter = new IListIterator<Vertex>(vert);
    WorldImage player = this.player.drawPlayer();
    IListIterator<Vertex> playerIter =
        new IListIterator<Vertex>(this.player.visited);
    WorldImage endPoint = new RectangleImage(CELL_SIZE, CELL_SIZE,
        OutlineMode.SOLID, new Color(227, 161, 161));
    IListIterator<Vertex> pathIter = new IListIterator<Vertex>(this.searchPath);
    // draws the endpoint
    bg.placeImageXY(endPoint, (this.endPoint.x + 1) * CELL_SIZE -
        CELL_SIZE / 2, (this.endPoint.y + 1) * CELL_SIZE - CELL_SIZE / 2);
    // draws the player's path
    while (playerIter.hasNext()) {
      Vertex cur = playerIter.next();
      Color col = new Color(121, 171, 133);
      WorldImage path = new RectangleImage(CELL_SIZE, CELL_SIZE,
          OutlineMode.SOLID, col.brighter());
      bg.placeImageXY(path, (cur.x + 1) * CELL_SIZE - CELL_SIZE / 2,
          (cur.y + 1) * CELL_SIZE - CELL_SIZE / 2);
    }
    // draws the search
    if (this.map.isEmpty()) {
      // don't draw anything
    }
    else {
      WorldImage path = new RectangleImage(CELL_SIZE, CELL_SIZE,
          OutlineMode.SOLID, new Color(109, 171, 191));
      bg.placeImageXY(path, (key.x + 1) * CELL_SIZE - CELL_SIZE / 2,
          (key.y + 1) * CELL_SIZE - CELL_SIZE / 2);
    }
    // draws the search's path
    while (pathIter.hasNext()) {
      Vertex cur = pathIter.next();
      Color col = new Color(109, 171, 191);
      WorldImage searchPath = new RectangleImage(CELL_SIZE, CELL_SIZE,
          OutlineMode.SOLID, col.brighter());
      bg.placeImageXY(searchPath, (cur.x + 1) * CELL_SIZE - CELL_SIZE / 2,
          (cur.y + 1) * CELL_SIZE - CELL_SIZE / 2);
    }
    // draws the walls
    while (iter.hasNext()) {
      Vertex cur = iter.next();
      if (cur.rightFlag) {
        bg.placeImageXY(cur.drawVert(), (cur.x + 1) * CELL_SIZE,
            (cur.y + 1) * CELL_SIZE - CELL_SIZE / 2);
      }
      if (cur.downFlag) {
        bg.placeImageXY(cur.drawHoriz(), (cur.x + 1) * CELL_SIZE -
            CELL_SIZE / 2, (cur.y + 1) * CELL_SIZE );
      }
    }
    // draws the player at the starting position
    bg.placeImageXY(player, (this.player.position.x + 1) * CELL_SIZE -
        CELL_SIZE / 2, (this.player.position.y + 1) * CELL_SIZE -
        CELL_SIZE / 2);
    return bg;
  }

  // To display the final image once the player has reached the end point
  public WorldEnd worldEnds() {
    WorldScene bg = this.makeScene();
    WorldImage win = new TextImage("YOU WON!", MAZE_HEIGHT * CELL_SIZE / 4,
        Color.BLACK);
    if (this.player.position.equals(this.endPoint)) {
      bg.placeImageXY(win, MAZE_WIDTH * CELL_SIZE / 2,
          MAZE_HEIGHT * CELL_SIZE / 2);
      return new WorldEnd(true, bg);
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }
}
