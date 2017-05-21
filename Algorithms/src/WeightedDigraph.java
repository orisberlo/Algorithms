import java.util.LinkedList;
import java.util.Stack;

public class WeightedDigraph {

    private final int V;
    protected int E;
    private LinkedList<Edge>[] backward_adj;
    private LinkedList<Edge>[] adj;
    
    /**
     * Initializes a new graph with {@code V} vertices.
     * @param V number of vertices in the graph.
     */
    public WeightedDigraph(int V){
    	if (V <= 0) throw new IllegalArgumentException("Number of vertices must be positive");
    	this.V = V;
    	this.E = 0;
    	this.backward_adj = (LinkedList<Edge>[]) new LinkedList[V];
    	this.adj = (LinkedList<Edge>[]) new LinkedList[V];
    	for(int i=0 ; i<V ; ++i){
    		this.backward_adj[i] = new LinkedList<Edge>();
    		this.adj[i] = new LinkedList<Edge>();
    	}
    }
    	
    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    /**
     *
     * @param  v the vertex
     * @return the edges adjacent to {@code v}, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> adjInEdge(int v) {
        validateVertex(v);
        return backward_adj[v];
    }
    
    /**
    *
    * @param  v the vertex
    * @return the vertices adjacent to {@code v}, as an iterable
    * @throws IllegalArgumentException unless {@code 0 <= v < V}
    */
   public Iterable<Edge> adjOutEdge(int v) {
       validateVertex(v);
       return adj[v];
   }
   
    /**
     * Verify that given vertex is valid.
     * @param i vertex index.
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    protected void validateVertex(int i){
    	if(i < 0) throw new IllegalArgumentException("Vertex: " + i + " is negative.");
    	if(i > V-1) throw new IllegalArgumentException("Vertex: " + i + " is larger than |V|=" + this.V +".");
    }
    
    /**
     * 
     * Given vertices {@code i},{@code j} add the edge i->j .
     * @param i vertex index.
     * @param j vertex index.
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public void addWeightedDirectedEdge(int i, int j, double weight){
    	validateVertex(i);
    	validateVertex(j);
    	this.E++;
    	Edge e= new Edge(i,j,weight);
    	this.adj[i].add(e);
    	this.backward_adj[j].add(e);
    }
    
    /**
     * 
     * @param i vertex index
     * @return out degree of input vertex {@code i}.  
     */
    public int outDegree(int i){
    	validateVertex(i);
    	return adj[i].size();
    }
    
    /**
     * 
     * @param i vertex index
     * @return in degree of input vertex {@code i}.  
     */
    public int inDegree(int i){
    	validateVertex(i);
    	return backward_adj[i].size();
    }
    

    /**
     * Initializes a new graph that is a deep copy of {@code G}.
     *
     * @param  G the graph to copy
     */
    public WeightedDigraph(WeightedDigraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Edge> reverse = new Stack<Edge>();
            for (Edge e : G.backward_adj[v]) {
                reverse.push(e);
            }
            for (Edge e : reverse) {
                backward_adj[v].add(e);
            }
            
            reverse.clear();
            for (Edge e : G.adj[v]) {
                reverse.push(e);
            }
            for (Edge e : reverse) {
                adj[v].add(e);
            }
        }
    }

    
    public WeightedDigraph reverse(){
    	WeightedDigraph reverse = new WeightedDigraph(this.V);
    	reverse.E = this.E;
    	System.arraycopy(this.adj, 0, reverse.backward_adj, 0, this.V);
    	System.arraycopy(this.backward_adj, 0, reverse.adj, 0, this.V);
    	return reverse;
    }
    

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
       	String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
    
    public boolean hasNegativeWeights(){
    	for(int v=0 ; v<this.V() ; ++v){
    		for(Edge e : adj[v]){
    			if(e.weight()<0) return true;
    		}
    	}
    	return false;
    }
 
    
    /**
     * Unit tests the {@code Digraph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    }
}
