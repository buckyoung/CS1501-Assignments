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
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

     // Deep copies for Eulerian
    public EdgeWeightedGraph(EdgeWeightedGraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Edge> reverse = new Stack<Edge>();
            for (Edge e : G.adj[v]) {
              if (!e.marked){ //consider on unmarked!
                reverse.push(new Edge(e.either(),e.other(e.either()), e.weight()));
              }
              
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
       //Connected components
       CC cc = new CC(this);
       System.out.println(cc.count()==1?"Network is connected.":"Network is not connected");
       
       Queue[] components = new Queue[cc.count()];
       for (int i = 0; i < cc.count(); i++){
         components[i] = new Queue();
       }
       
       for(int i = 0; i < V; i++){
         components[cc.id(i)].enqueue(i);
       }
       
       for (int i = 0; i < cc.count(); i++){
         System.out.println((i+1)+": " +components[i]);
       }
       
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
    public void changeWeight(int v, int w, double weight){
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
       CC cc = new CC(this);
       if(cc.count()==1){ //connected network!
         PrimMST mst = new PrimMST(this);
         for(Edge e : mst.edges()){
            System.out.println(e);
         }
         System.out.println("Total Edge Weight: " + mst.weight());
       } else { //not connected!!!!
         System.out.println("Network not connected: No MST.");
       }
         
         
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
    
    //eulerian cycle/path
    public void eulerian(){
      CC cc = new CC(this);
       if(cc.count()==1){ //connected network!
         //Count odd degrees
         int odd = 0;
         for (int v = 0; v < V; v++){
           int bagSize = adj[v].size();
           int upSize = 0; 
           for (Edge e: adj[v]){
             if (e.isActive()){
               upSize++;
             }
           }
           if ((upSize & 1) == 1){ //bitwise AND 1, if ==1 then ODD!
             odd++;
           }
         }
         if (odd == 2){ //Path!
           printEulerianPath();           
         } else if (odd == 0) { //Cycle!
           printEulerianCycle();
         } else { //Nope!
           System.out.println("No Eulerian cycle or path exists.");
         }
         
         
       } else { //not connected
          System.out.println("Network not connected: No Eulerian Path or Cycle.");
       }
    }
    
    //unmark all unless !active
    private void setAllEdgeMarks(){
      for (Edge e : this.edges()){
        if (e.isActive()){
          e.marked = false;
        } else { //not active
          e.marked = true;
        }
      }
    }

    //get edge
    private Edge getAnyEdge(){
      Edge result = null;
      LOOP: for (int v = 0; v < V; v++) {
            for (Edge e : adj(v)) {
                result = e;
                if (e != null)
                  break LOOP;
            }
        }
       return result;
    }
    //print cycle
    private void printEulerianCycle(){
                 System.out.println("Eulerian cycle exists.");
    setAllEdgeMarks();
           Stack<Integer> s = new Stack<Integer>();
           //Choose any vertex v and push onto stack
           Edge q = getAnyEdge();
           int v = q.either(); //vertex
           s.push(v);
           //While stack.!empty
           while (!s.isEmpty()){
           //peek at vertex u
             int u = s.peek();
             boolean foundUnmarkedEdge = false;
             
             //if it has an unmarked edge to another vertex w, push w to stack and mark u-w edge
             for (Edge e: adj[u]){       
               if (!e.marked){ //must be unmarked (and active -- but that is taken care of in setAllEdgeMarks())
                 foundUnmarkedEdge = true;  
                 int w = e.other(u);
                 s.push(w); // push
                 e.marked = true; //mark
                 for(Edge eee : adj[w]){ //mark the "direction coming back", too
                   if (eee.other(w) == u){
                     eee.marked = true;
                   }
                 }
                 break;
               }
             }
             
             //else pop u and print
             if(!foundUnmarkedEdge){
                System.out.print(s.pop());
                if (s.size() > 0){
                  System.out.print(" - ");
                }
             }
           } //end while
           
          System.out.print("\n");
    }
    
    //path
    private void printEulerianPath(){
         System.out.println("Eulerian path exists.");
         
         //Lets deep copy the graph
         //consider only actives:
         setAllEdgeMarks();
         EdgeWeightedGraph copy = new EdgeWeightedGraph(this);
         int startVertex = -1;
        
          //Find an Odd Degree'd vertex to start at
         for (int v = 0; v < copy.V(); v++){
           int bagSize = copy.adj[v].size();
           int upSize = 0; 
           for (Edge e: copy.adj[v]){
             if (e.isActive()){
               upSize++;
             }
           }
           
           if ((upSize & 1) == 1){ //bitwise AND 1, if ==1 then ODD!
             startVertex = v;
             break;
           }
         }
         //we now have a starting point! and a copy of the graph.
         //follow edges
         //chose one whose deletion would not disconnect the graph...
         
         Stack<Integer> stack = new Stack<Integer>();
         
         stack.push(startVertex);
  
         while(!stack.isEmpty()){
           //peek
           int u = stack.peek();
           boolean foundUnmarkedEdge = false;
           int unmarkedVertexes= 0;
           
           //get all the unmarked vertexes
           for(Edge e : copy.adj[u]){
             if (!e.marked){
               unmarkedVertexes++;
             }
           }
           
           
           
            //if it has an unmarked edge to another vertex w, push w to stack and mark u-w edge
           //unless it disconnects the graph, then try another edge...
           for (Edge e : copy.adj[u]){ //for each edge of the vertex
             if (!e.marked){
               //try to mark
               unmarkedVertexes--;
               e.setActive(false);
               
               //Check for disconnected components
               CC cc = new CC(copy);
               if (cc.count() != 1 && unmarkedVertexes>0){ //BAD! Disconnects graph, but if it is our only choice then oh well
                 //Try another edge...
                 e.setActive(true);//reset the active;
                 
               } else { //ok, good, follow the edge
                  foundUnmarkedEdge = true;
                  int w = e.other(u);
                  stack.push(w);
                  e.marked = true;
                 for(Edge eee : copy.adj[w]){ //mark the "direction coming back", too
                   if (eee.other(w) == u){
                     eee.marked = true;
                   }
                 }
                  break;
               }
             }
         }//end for
           
            //else pop u and print
             if(!foundUnmarkedEdge){
                System.out.print(stack.pop());
                if (stack.size() > 0){
                  System.out.print(" - ");
                }
             }
             
         }//end while
         
         System.out.print("\n");

    }
           
}
