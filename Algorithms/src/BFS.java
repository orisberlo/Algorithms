import java.util.Stack;

public class BFS {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int NO_PARENT = -1;
    private Digraph G;
    private int parent[];
    private int dist[];
    private boolean marked[];
    private int source;
    private boolean isDirected;

    /**
     * Computes the shortest path between the source vertex {@code source}
     * and every other vertex in the graph {@code G}.
     * @param G the directed graph
     * @param source the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BFS(Digraph G, int source) {
    	if(source<0 || source>G.V()-1) throw new IllegalArgumentException("source must be a vertex of the graph.");
    	this.G = new Digraph(G);
    	this.source = source;
    	this.dist = new int[G.V()];
    	this.parent = new int[G.V()];
    	this.marked = new boolean[G.V()];
    	this.isDirected=true;
    	bfs();
    }
    
    /**
     * Initialize BFS with graph {@code G} and default source '0'.
     * @param G the directed graph
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BFS(Digraph G) {
    	this(G,0);
    }
    
    /**
     * Computes the shortest path between the source vertex {@code source}
     * and every other vertex in the graph {@code G}.
     * @param G the directed graph
     * @param source the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BFS(Graph G, int source) {
    	this((Digraph) G,source);
    	this.isDirected=false;
    }
    

    private void bfs(){
    	Stack<Integer> stack = new Stack<Integer>();
    	for(int i=0;i<G.V();++i){
    		dist[i] = INFINITY;
    		parent[i] = NO_PARENT;
    		marked[i] = false;
    	}
    	
    	dist[source] = 0;
    	stack.push(source);
    	int currentVertex;
    	while(!stack.empty()){
    		currentVertex = stack.pop();
    		for(int neighbor: G.adj(currentVertex)){
    			if(!marked[neighbor]){
    				marked[neighbor] = true;
    				dist[neighbor] = dist[currentVertex]+1; 
    				parent[neighbor] = currentVertex; 
    				stack.push(neighbor);
    			}
    		}
    	}
    }
    
    public int dist(int target){
    	return dist[target];
    }
    
    public boolean isConnected(int target){
    	return marked[target];
    }
    
    /**
     * 
     * @return true if the graph is connected. This only makes sense for undirected graphs.
     */
    public boolean isConnected(){
    	if(isDirected) throw new IllegalArgumentException("This method should only be invoked for undirected graphs");
    	for(int i=0;i<G.V();++i){
    		if(!isConnected(i))return false;
    	}
    	return true;
    }
    
    public int getDist(int u){
    	this.G.validateVertex(u);
    	return this.dist[u];
    }
    
    public static boolean isBipartite(Graph G){
    	BFS b = new BFS((Digraph) G);
    	for(int u=0;u<G.V();++u){
    		for(int v: b.G.adj(u)){
    			if((b.getDist(u)-b.getDist(v))%2==0) return false;
    		}
    	}
    	return true;
    }
    
    public Digraph BFSTree(){
    	EdgeIndicator edgeIndicator = new EdgeIndicator(){
    		public boolean edgeIndicator(int u, int v){
    			return parent[v]==u;
    		}
    	};
    	Digraph bfs_tree = new Digraph(G.V(),edgeIndicator);
    	return bfs_tree;
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
	   
	   
	   EdgeIndicator h = EdgeIndicator.TensorProduct(g, g, 18,2); 
	   EdgeIndicator h2 = EdgeIndicator.TensorProduct(g, g, 8,3); 

	   Digraph G = new Digraph(16,h);
	   Digraph G2 = new Digraph(16,h2);
	   Graph H = new Graph(G);
	   Graph H2 = new Graph(G2);
	   System.out.println(isBipartite(GraphGenerator.isomorphic(H)));
	   H = GraphGenerator.randomGraph(100, 0.2);
	   System.out.println(isBipartite(H));
	   System.out.println(isBipartite(H2));
    }
    
    
}
