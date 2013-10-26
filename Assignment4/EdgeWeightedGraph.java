/*
 *  Note: This code was adapted from Section 4.3 of Algorithms, 4th Edition by Robert Sedgewick and Kevin Wayne.
 *  Modifications and additions were made by Buck Young in order to complete an assignment for the University of Pittsburgh.
 */
public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    /**
     * Initializes an empty edge-weighted graph with <tt>V</tt> vertices and 0 edges.
     * param V the number of vertices
     * @throws java.lang.IllegalArgumentException if <tt>V</tt> < 0
     */
    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Graph must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }
    
    /**  
     * Initializes an edge-weighted graph from an input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices and edge weights,
     * with each entry separated by whitespace.
     * @param in the input stream
     */
    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            int weight = in.readInt();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    /**
     * Initializes a new edge-weighted graph that is a deep copy of <tt>G</tt>.
     * @param G the edge-weighted graph to copy
     */
    public EdgeWeightedGraph(EdgeWeightedGraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Edge> reverse = new Stack<Edge>();
            for (Edge e : G.adj[v]) {
                reverse.push(e);
            }
            for (Edge e : reverse) {
                adj[v].add(e);
            }
        }
    }


    /**
     * Returns the number of vertices in the edge-weighted graph.
     * @return the number of vertices in the edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the edge-weighted graph.
     * @return the number of edges in the edge-weighted graph
     */
    public int E() {
        return E;
    }

    /**
     * Adds the undirected edge <tt>e</tt> to the edge-weighted graph.
     * @param e the edge
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    /**
     * Returns the edges incident on vertex <tt>v</tt>.
     * @return the edges incident on vertex <tt>v</tt> as an Iterable
     * @param v the vertex
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
     */
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    /**
     * Returns all edges in the edge-weighted graph.
     * To iterate over the edges in the edge-weighted graph, use foreach notation:
     * <tt>for (Edge e : G.edges())</tt>.
     * @return all edges in the edge-weighted graph as an Iterable.
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v ) {
                    list.add(e);
                }
                // only add one copy of each self loop
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    /**
     * Returns a string representation of the edge-weighted graph.
     * This method takes time porportional to <em>E</em> + <em>V</em>.
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *   followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append("Total Vertices: " + V + "\nTotal Edges: " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    //------------------------------------------My Functions!-----------------------------//
    //graph report
    public void report(){
       System.out.println(this);
       //TODO
       System.out.println("TODO: Network is connected... connected components!");
    }
    
    //Enables edge v w and edge w v 
    public void edgeUp(int v, int w){
      boolean found = false;
      for (Edge e : adj[v]){
        if (e.other(v) == w){
          //FOUND
          found = true;
          if (e.isActive()){
            System.out.println("Edge " +v+ "-" +w+ " is already up.");
          } else { //it was down
            e.setActive(true);
            System.out.println("Edge " +v+ "-" +w+ " has come up.");
          }
          break;
          }
        }
      if (!found){
        System.out.println("Could not find connection between " +v+ " and " +w);
      } else { //find it for the reverse
       for (Edge f : adj[w]){
            if (f.other(w) == v){
              //FOUND
              f.setActive(true);
              break;
            }
      }
      }
    }
    
    //Disables edge v w and edge w v
    public void edgeDown(int v, int w){
      boolean found = false;
      for (Edge e : adj[v]){
        if (e.other(v) == w){
          //FOUND
          found = true;
          if(e.isActive()){
            e.setActive(false);
            System.out.println("Edge " +v+ "-" +w+ " has gone down.");
          } else { //e is already down
            System.out.println("Edge " +v+ "-" +w+ " is already down.");
          }
          break;
          }
        }
      if (!found){
        System.out.println("Could not find connection betweeen " +v+ " and " +w);
      } else { //find it for the reverse
       for (Edge f : adj[w]){
            if (f.other(w) == v){
              //FOUND
              f.setActive(false);
              break;
            }
      }
      }
       
    }
    
    //changes the weight of edge v w and edge w v
    public void changeWeight(int v, int w, int weight){
      boolean found = false;
      
      for (Edge e : adj[v]){
        if (e.other(v) == w){
          //FOUND
          found = true;
          if (weight <= 0){
            e.setActive(false);
            System.out.println("Edge " +v+ "-" +w+ " went down because weight was <= 0.");
          } else {
            e.setActive(true);
            e.setWeight(weight);
            System.out.println("Edge " +v+ "-" +w+ " is active with a new weight of " + weight);
          }
          break;
          }
        }
      if (!found){
        //CREATE
        addEdge(new Edge(v, w, weight));
        System.out.println("Edge " +v+ "-" +w+ " was created with a weight of " + weight);
      } else { //find it for the reverse
       for (Edge f : adj[w]){
            if (f.other(w) == v){
              //FOUND
              if (weight <= 0){
                f.setActive(false);
              } else {
                f.setActive(true);
                f.setWeight(weight);
              }
              break;
            }
      }
      }
       
    }
    
    //minimally spanning tree
    public void mst(){
             PrimMST mst = new PrimMST(this);
         for(Edge e : mst.edges()){
            System.out.println(e);
         }
         System.out.println("Total Edge Weight: " + mst.weight());
    }
    
    //shortest path
    public void shortest(int i, int j){
      EdgeWeightedDigraph g = new EdgeWeightedDigraph(this);
      DijkstraSP sp = new DijkstraSP(g, i); //from i to every other node
      if (sp.hasPathTo(j)){ //has path
        System.out.println("Total Length: " + sp.distTo(j));
        for (DirectedEdge e : sp.pathTo(j)){
          System.out.print(e+"   ");
        }
        System.out.print("\n");
      } else { //no path to
        System.out.println("There is no path from " + i + " to " + j);
      }
      
    }

}