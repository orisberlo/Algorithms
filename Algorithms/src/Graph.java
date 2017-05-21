import java.util.LinkedList;
import java.util.Stack;



public class Graph extends Digraph{
    public Graph(int V){
    	super(V);
    }
    
    /**
     * 
     * Given vertices {@code i},{@code j} add the edge i-j.
     * @param i vertex index.
     * @param j vertex index.
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public void addEdge(int i, int j){
    	validateVertex(i);
    	validateVertex(j);
    	this.adj[i].add(j);
    	this.adj[j].add(i);
    	E++;
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
    * Initializes a new graph that is a deep copy of {@code G}.
    *
    * @param  G the graph to copy
    */
   public Graph(Graph G) {
	   super(G);
   }
   
   /**
    * Initializes a new graph that is a deep copy of {@code G}.
    *
    * @param  G the graph to copy
    */
   public Graph(Digraph G) {
	   super(G);
   }
   
   /**
    * Unit tests the {@code Digraph} data type.
    *
    * @param args the command-line arguments
    */
   public static void main(String[] args) {
   }
}

