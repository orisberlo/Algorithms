
import java.util.LinkedList;
import java.util.Stack;
import java.io.*;

public class Digraph {
    private final int V;
    protected int E;
    protected LinkedList<Integer>[] adj;
    private LinkedList<Integer>[] backwardAdj;

    
    
    
    private EdgeIndicator edgeIndicator;
    static EdgeIndicator defaultEdgeIndicator;
    private boolean useEdgeIndicator;
    /**
     * Initializes a new graph with {@code V} vertices.
     * @param V number of vertices in the graph.
     */
    public Digraph(int V){
    	if (V <= 0) throw new IllegalArgumentException("Number of vertices must be positive");
    	this.V = V;
    	this.E = 0;
    	this.defaultEdgeIndicator = new EdgeIndicator(){
        	public boolean edgeIndicator(int u, int v){
        		for(int j : adj(u)){
        			if(j==v) return true;
        		}
        		return false;
        	}
        };
        this.edgeIndicator = defaultEdgeIndicator;
        this.useEdgeIndicator = false;
        
    	this.adj = (LinkedList<Integer>[]) new LinkedList[V];
    	this.backwardAdj = (LinkedList<Integer>[]) new LinkedList[V];
    	
    	for(int i=0 ; i<V ; ++i){
    		this.adj[i] = new LinkedList<Integer>();
    		this.backwardAdj[i] = new LinkedList<Integer>();
    	}
    }
    
    /**
     * Initializes a new graph with edge tester with {@code V} vertices. 
     * The edges are set according to the edge tester.
     * @param V number of vertices in the graph.
     */
    public Digraph(int V, EdgeIndicator indicator){
    	this(V);
    	this.edgeIndicator = indicator;
    	for(int i=0;i<V;++i){
    		for(int j=0;j<V;++j){
    			if(indicator.edgeIndicator(i,j)){
    				addEdge(i,j);
    			}
    		}
    	}
        this.useEdgeIndicator = true;
    }

    /**
     * Verifies that current edge indicator is valid by comparing to
     * the edges in the graph.
     * If the edge indicator is not valid we set the useEdgeIndicator to false
     * and reset to default edge indicator. 
     */
    public void validateEdgeIndicator(){
    	for(int u=0;u<this.V();++u){
        	for(int v=0;v<this.V();++v){
        		if(defaultEdgeIndicator.edgeIndicator(u, v)!=edgeIndicator.edgeIndicator(u, v)){
        			useEdgeIndicator=false;
        			edgeIndicator = defaultEdgeIndicator;
        			return;
        		}
        	}
    	}
        this.useEdgeIndicator = true;
    }
    
    /**
     * Verify that given vertex is valid.
     * @param i vertex index.
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public void validateVertex(int i){
    	if(i < 0) throw new IllegalArgumentException("Vertex: " + i + " is negative.");
    	if(i > V-1) throw new IllegalArgumentException("Vertex: " + i + " is larger than |V|=" + this.V +".");
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
     * @return the vertices adjacent to {@code v}, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 
     * Given vertices {@code i},{@code j} add the edge i->j.
     * @param i vertex index.
     * @param j vertex index.
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public void addEdge(int i, int j){
    	validateVertex(i);
    	validateVertex(j);
    	this.E++;
    	this.adj[i].add(j);
    	this.backwardAdj[j].add(i);    
	}
    

    /**
     * Initializes a new graph that is a deep copy of {@code G}.
     *
     * @param  G the graph to copy
     */
    public Digraph(Digraph G) {
    	this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
            reverse.clear();
            for (int w : G.backwardAdj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
            	backwardAdj[v].add(w);
            }
        }
    }
    
    /**
     * Reverse the edges.
     *
     */
    public Digraph reverse() {
    	Digraph G = new Digraph(this);
    	LinkedList<Integer>[] temp = G.adj;
    	G.adj = G.backwardAdj; 
    	this.backwardAdj = temp;
    	return G;
    }
    

    /**
     * Initializes a new graph that is a deep copy of {@code G}.
     *
     * @param  G the graph to copy
     */
    public Digraph(Graph G) {
    	this((Digraph) G);
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
    	return backwardAdj[i].size();
    }

    /**
     * Check if edge {@code i} - {@code j} exists.
     * Override in case better implementation for specific graphs. 
     * 
     * @param i vertex index
     * @return out degree of input vertex {@code i}.  
     */
    public boolean edgeExists(EdgeIndicator indicator , int u, int v){
    	validateVertex(u);
    	validateVertex(v);
    	return indicator.edgeIndicator(u, v);
    }
    
    
    /**
     * Check if edge {@code i} - {@code j} exists.
     * Override in case better implementation for specific graphs. 
     * 
     * @param i vertex index
     * @return out degree of input vertex {@code i}.  
     */
    public boolean edgeExists(int u, int v){
    	EdgeIndicator indicator = new EdgeIndicator(){
    		public boolean edgeIndicator(int u, int v){
        		for(int j : adj(u)){
        			if(j==v) return true;
        		}
        		return false;
        	}
    	};
    	return indicator.edgeIndicator(u, v);
    }
    
    
    
    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,  
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
    	String NEWLINE = System.getProperty("line.separator");

        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Returns random permutation of given graph.
     * 
     * @param g is a graph.
     * @return random permutation of the graph.
     */
    public Digraph relabel(int[] labeling){
    	if(labeling.length != V) throw new IllegalArgumentException("labeling must have size equal to V");
    	Digraph relabedGraph = new Digraph(V);
    	for(int i=0;i<this.V();++i){
    		for(int j: this.adj(i)){
    			relabedGraph.addEdge(labeling[i], labeling[j]);
    		}
    	}
    	return relabedGraph;
    }
    
    
    /**
     * Unit tests the {@code Digraph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
 	   EdgeIndicator g = new EdgeIndicator(){
		   public boolean edgeIndicator(int u, int v){
			   return u!=v;
		   }
	};
	   
	   
	   //EdgeIndicator h = EdgeIndicator.TensorProduct(g, g, 8,2); 
	   //Digraph G = new Digraph(16,h);
	   Digraph G = GraphGenerator.randomDirectedGraph(10, 0.2);
	   System.out.print(G.toString());
	   G.reverse();
	   System.out.println(G.toString());
    }
}
