/*************************************************************************
 *  Compilation:  javac DirectedEulerianCycle.java
 *  Dependencies: Digraph.java Stack.java Queue.java StdOut.java
 *
 *  Find an Euler tour if one exists.
 *
 *  Runs in O(E + V) time.
 *
 *
 *************************************************************************/

public class DirectedEulerianCycle {
    private Stack<DirectedEdge> tour = new Stack<DirectedEdge>();
    private boolean isEulerian = true;
    private Stack<DirectedEdge> stack = new Stack<DirectedEdge>();
    private Queue<DirectedEdge>[] adj;

    public DirectedEulerianCycle(EdgeWeightedDigraph G) {

        // create local copy of adjacency lists
        adj = (Queue<DirectedEdge>[]) new Queue[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = new Queue<DirectedEdge>();
            for (DirectedEdge e : G.adj(v))
                adj[v].enqueue(e);
        }

        // find Eulerian tour
        stack.push(new DirectedEdge(0,0,0));
        while (!stack.isEmpty()) {
            DirectedEdge s = stack.pop();
            tour.push(s);
            DirectedEdge v = s;
            while (!adj[v.].isEmpty()) {
                stack.push(v);
                v = adj[v].dequeue();
            }
            if (v != s) isEulerian = false;
        }

        // check if all edges have been used
        for (int v = 0; v < G.V(); v++)
            if (!adj[v].isEmpty()) isEulerian = false;
    }

    // return Eulerian tour, or null if no such tour
    public Iterable<Integer> tour() {
        if (!isEulerian) return null;
        return tour;
    }

    // does the digraph have an Eulerian tour?
    public boolean isEulerian() {
        return isEulerian;
    }



    public static void main(String[] args) {
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);

        // random graph of V vertices and approximately E edges
        // with indegree[v] = outdegree[v] for all vertices
        Digraph G = new Digraph(V);
        int[] indegree  = new int[V];
        int[] outdegree = new int[V];
        int deficit = 0;
        for (int i = 0; i < E - deficit/2; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            if (v == w) { i--; continue; }
            G.addEdge(v, w);
            if (outdegree[v] >= indegree[v]) deficit++;
            else                             deficit--;
            outdegree[v]++;
            if (indegree[w] >= outdegree[w]) deficit++;
            else                             deficit--;
            indegree[w]++;
        }

        while (deficit > 0) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            if (v == w) continue;
            if (outdegree[v] >= indegree[v]) continue;
            if (indegree[w]  >= outdegree[w]) continue;
            G.addEdge(v, w);
            if (outdegree[v] >= indegree[v]) deficit++;
            else                             deficit--;
            outdegree[v]++;
            if (indegree[w] >= outdegree[w]) deficit++;
            else                             deficit--;
            indegree[w]++;
        }

        StdOut.println(G);
        DirectedEulerianCycle euler = new DirectedEulerianCycle(G);
        if (euler.isEulerian()) {
            for (int v : euler.tour()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("Not eulerian");
        }
    }

}