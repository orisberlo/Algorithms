import java.util.Stack;

public class WeightedGraph extends WeightedDigraph{
	
	public WeightedGraph(int V){
		super(V);
	}
	
	protected void validateWeightedGraph(){
    	if (E()%2!=0) throw new IllegalArgumentException("Number of edges is illegal");
	}
    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */

    
    /**
     * 
     * Given vertices {@code i},{@code j} add the edge i-j with given weight.
     * @param i vertex index.
     * @param j vertex index.
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public void addWeightedEdge(int i, int j, double weight){
    	this.addWeightedDirectedEdge(i,j,weight);
    	this.addWeightedDirectedEdge(j,i,weight);
    	validateWeightedGraph();
    }
    
    /**
    *
    * @param  v the vertex
    * @return the vertices adjacent to {@code v}, as an iterable
    * @throws IllegalArgumentException unless {@code 0 <= v < V}
    */
   public Iterable<Edge> adjEdge(int v) {
       return adjOutEdge(v);
   }
   
   /**
    * 
    * @param i vertex index
    * @return degree of input vertex {@code i}.  
    */
   public int degree(int i){
   	validateVertex(i);
   	return outDegree(i);
   }
   
   /**
    * Initializes a new graph that is a deep copy of {@code G}.
    *
    * @param  G the graph to copy
    */
   public WeightedGraph(WeightedGraph G) {
       super(G);
   }
   
}
