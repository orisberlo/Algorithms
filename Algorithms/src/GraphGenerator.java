
public class GraphGenerator {

    // this class cannot be instantiated
    private GraphGenerator() { }
    
    
    /**
     * Returns a random simple graph on {@code V} vertices, with an 
     * edge between any two vertices with probability {@code p}. This is sometimes
     * referred to as the Erdos-Renyi random graph model.
     * @param V the number of vertices
     * @param p the probability of choosing an edge
     * @return a random simple graph on {@code V} vertices, with an edge between
     *     any two vertices with probability {@code p}
     * @throws IllegalArgumentException if probability is not between 0 and 1
     */
	public static Digraph randomDirectedGraph(int V, double p){
		if(p>1 || p<0) throw new IllegalArgumentException("Invalid probability: 0 <= p <= 1");
		Digraph G = new Digraph(V);
        for (int v = 0; v < V; v++){
            for (int w = 0; w < V; w++){
            	if(w==v){
            		continue;
            	}
                if (StdRandom.bernoulli(p)){
                    G.addEdge(v, w);
                }
            }
        }
        return G;
	}
	
    /**
     * Returns a random simple graph on {@code V} vertices, with an 
     * edge between any two vertices with probability {@code p}. This is sometimes
     * referred to as the Erdos-Renyi random graph model.
     * @param V the number of vertices
     * @param p the probability of choosing an edge
     * @return a random simple graph on {@code V} vertices, with an edge between
     *     any two vertices with probability {@code p}
     * @throws IllegalArgumentException if probability is not between 0 and 1
     */
	public static Graph randomGraph(int V, double p){
		if(p>1 || p<0) throw new IllegalArgumentException("Invalid probability: 0 <= p <= 1");
        Graph G = new Graph(V);
        for (int v = 0; v < V; v++)
            for (int w = v+1; w < V; w++)
                if (StdRandom.bernoulli(p)){
                    G.addEdge(v, w);
            }
        return G;
	}
	
    /**
     * Returns a random graph containing {@code V} vertices and {@code E} edges.
     * @param V the number of vertices
     * @param E the number of vertices
     * @return a random simple graph on {@code V} vertices, containing a total
     *     of {@code E} edges
     * @throws IllegalArgumentException if number of edges is negative.
     */
	public static Graph randomGraph(int V, int E){
		if(E < 0) throw new IllegalArgumentException("Number of edges must be positive");

		int u,v;
        Graph G = new Graph(V);
        while(G.E()<E){
        	u = StdRandom.uniform(V);
        	v = StdRandom.uniform(V);
        	G.addEdge(u, v);
        }
        return G;
	}

	
    /**
     * Returns a random simple graph containing {@code V} vertices and {@code E} edges.
     * @param V the number of vertices
     * @param E the number of vertices
     * @return a random simple graph on {@code V} vertices, containing a total
     *     of {@code E} edges
     * @throws IllegalArgumentException if no such simple graph exists
     */
	public static Graph randomSimpleGraph(int V, int E){
		if(E > V*(V-1)/2) throw new IllegalArgumentException("Too many edges");
		if(E < 0) throw new IllegalArgumentException("Number of edges must be positive");

		int u,v;
        Graph G = new Graph(V);
        while(G.E()<E){
        	u = StdRandom.uniform(V);
        	v = StdRandom.uniform(V);
        	if(!G.edgeExists(u, v) && u!=v){
        		G.addEdge(u, v);
        	}
        }
        return G;
	}
	
    /**
     * Returns the complete graph on {@code V} vertices.
     * @param V the number of vertices
     * @return the complete graph on {@code V} vertices
     */
    public static Graph complete(int V) {
        return randomGraph(V, 1);
    }
    
    /**
     * Returns a complete bipartite graph on {@code V1} and {@code V2} vertices.
     * @param V1 the number of vertices in one partition
     * @param V2 the number of vertices in the other partition
     * @return a complete bipartite graph on {@code V1} and {@code V2} vertices
     * @throws IllegalArgumentException if probability is not between 0 and 1
     */
    public static Graph completeBipartite(int numVertices0, int numVertices1) {
        return bipartite(numVertices0, numVertices1, numVertices0*numVertices1);
    }
    
    /**
     * Returns random permutation of given graph.
     * 
     * @param g directed graph.
     * @return random permutation of the graph.
     */
    public static Digraph isomorphic(Digraph g){
    	return g.relabel(StdRandom.permutation(g.V()));
    }

    /**
     * Returns random permutation of given graph.
     * 
     * @param g is a graph.
     * @return random permutation of the graph.
     */
    public static Graph isomorphic(Graph g){
    	Graph h = new Graph(g.V());
    	int[] shuffel = StdRandom.permutation(g.V());
    	for(int i=0;i<g.V();++i){
    		for(int j: g.adj(i)){
    			h.addEdge(shuffel[i], shuffel[j]);
    		}
    	}
    	return h;
    }
    
    /**
     * Returns a random simple bipartite graph on {@code V1} and {@code V2} vertices
     * with {@code E} edges.
     * @param numVertices0 the number of vertices in one partition
     * @param numVertices1 the number of vertices in the other partition
     * @param E the number of edges
     * @return a random simple bipartite graph on {@code V1} and {@code V2} vertices,
     *    containing a total of {@code E} edges
     * @throws IllegalArgumentException if no such simple bipartite graph exists
     */
    public static Graph bipartite(int numVertices0, int numVertices1, int E) {
        if (E > (long) numVertices0*numVertices1) throw new IllegalArgumentException("Too many edges");
        if (E < 0)            throw new IllegalArgumentException("Too few edges");
        Graph G = new Graph(numVertices0 + numVertices1);

        int[] vertices = new int[numVertices0 + numVertices1];
        for (int i = 0; i < numVertices0 + numVertices1; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);

        while (G.E() < E) {
            int i = StdRandom.uniform(numVertices0);
            int j = numVertices0 + StdRandom.uniform(numVertices1);
            if (!G.edgeExists(vertices[i], vertices[j])) {
                G.addEdge(vertices[i], vertices[j]);
            }
        }
        return G;
    }

    /**
     * Returns a random simple bipartite graph on {@code V1} and {@code V2} vertices,
     * containing each possible edge with probability {@code p}.
     * @param numVertices0 the number of vertices in one partition
     * @param numVertices1 the number of vertices in the other partition
     * @param p the probability that the graph contains an edge with one endpoint in either side
     * @return a random simple bipartite graph on {@code V1} and {@code V2} vertices,
     *    containing each possible edge with probability {@code p}
     * @throws IllegalArgumentException if probability is not between 0 and 1
     */
    public static Graph bipartite(int numVertices0, int numVertices1, double p) {
        if (p < 0.0 || p > 1.0)
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        int[] vertices = new int[numVertices0 + numVertices1];
        for (int i = 0; i < numVertices0 + numVertices1; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        Graph G = new Graph(numVertices0 + numVertices1);
        for (int i = 0; i < numVertices0; i++)
            for (int j = 0; j < numVertices1; j++)
                if (StdRandom.bernoulli(p))
                    G.addEdge(vertices[i], vertices[numVertices0+j]);
        return G;
    }

    /**
     * Returns a path graph on {@code V} vertices.
     * @param V the number of vertices in the path
     * @return a path graph on {@code V} vertices
     */
    public static Graph path(int V) {
        Graph G = new Graph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V-1; i++) {
            G.addEdge(vertices[i], vertices[i+1]);
        }
        return G;
    }

    /**
     * Returns a directed path graph on {@code V} vertices.
     * @param V the number of vertices in the path
     * @return a path graph on {@code V} vertices
     */
    public static Digraph directedPath(int V) {
        Graph G = new Graph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V-1; i++) {
            G.addEdge(vertices[i], vertices[i+1]);
        }
        return G;
    }
    
    /**
     * Returns a complete binary tree graph on {@code V} vertices.
     * @param V the number of vertices in the binary tree
     * @return a complete binary tree graph on {@code V} vertices
     */
    public static Graph binaryTree(int V) {
        Graph G = new Graph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 1; i < V; i++) {
            G.addEdge(vertices[i], vertices[(i-1)/2]);
        }
        return G;
    }

    /**
     * Returns a cycle graph on {@code V} vertices.
     * @param V the number of vertices in the cycle
     * @return a cycle graph on {@code V} vertices
     */
    public static Graph cycle(int V) {
        Graph G = new Graph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V-1; i++) {
            G.addEdge(vertices[i], vertices[i+1]);
        }
        G.addEdge(vertices[V-1], vertices[0]);
        return G;
    }

    /**
     * Returns a directed cycle graph on {@code V} vertices.
     * @param V the number of vertices in the cycle
     * @return a cycle graph on {@code V} vertices
     */
    public static Digraph directedCycle(int V) {
        Graph G = new Graph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V-1; i++) {
            G.addEdge(vertices[i], vertices[i+1]);
        }
        G.addEdge(vertices[V-1], vertices[0]);
        return G;
    }
    /**
     * Returns an Eulerian cycle graph on {@code V} vertices.
     *
     * @param  V the number of vertices in the cycle
     * @param  E the number of edges in the cycle
     * @return a graph that is an Eulerian cycle on {@code V} vertices
     *         and {@code E} edges
     * @throws IllegalArgumentException if either {@code V <= 0} or {@code E <= 0}
     */
    public static Graph eulerianCycle(int V, int E) {
        if (E <= 0)
            throw new IllegalArgumentException("An Eulerian cycle must have at least one edge");
        if (V <= 0)
            throw new IllegalArgumentException("An Eulerian cycle must have at least one vertex");
        Graph G = new Graph(V);
        int[] vertices = new int[E];
        for (int i = 0; i < E; i++)
            vertices[i] = StdRandom.uniform(V);
        for (int i = 0; i < E-1; i++) {
            G.addEdge(vertices[i], vertices[i+1]);
        }
        G.addEdge(vertices[E-1], vertices[0]);
        return G;
    }

    /**
     * Returns an Eulerian path graph on {@code V} vertices.
     *
     * @param  V the number of vertices in the path
     * @param  E the number of edges in the path
     * @return a graph that is an Eulerian path on {@code V} vertices
     *         and {@code E} edges
     * @throws IllegalArgumentException if either {@code V <= 0} or {@code E < 0}
     */
    public static Graph eulerianPath(int V, int E) {
        if (E < 0)
            throw new IllegalArgumentException("negative number of edges");
        if (V <= 0)
            throw new IllegalArgumentException("An Eulerian path must have at least one vertex");
        Graph G = new Graph(V);
        int[] vertices = new int[E+1];
        for (int i = 0; i < E+1; i++)
            vertices[i] = StdRandom.uniform(V);
        for (int i = 0; i < E; i++) {
            G.addEdge(vertices[i], vertices[i+1]);
        }
        return G;
    }

    /**
     * Returns a wheel graph on {@code V} vertices.
     * @param V the number of vertices in the wheel
     * @return a wheel graph on {@code V} vertices: a single vertex connected to
     *     every vertex in a cycle on {@code V-1} vertices
     */
    public static Graph wheel(int V) {
        if (V <= 1) throw new IllegalArgumentException("Number of vertices must be at least 2");
        Graph G = new Graph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);

        // simple cycle on V-1 vertices
        for (int i = 1; i < V-1; i++) {
            G.addEdge(vertices[i], vertices[i+1]);
        }
        G.addEdge(vertices[V-1], vertices[1]);

        // connect vertices[0] to every vertex on cycle
        for (int i = 1; i < V; i++) {
            G.addEdge(vertices[0], vertices[i]);
        }

        return G;
    }

    /**
     * Returns a star graph on {@code V} vertices.
     * @param V the number of vertices in the star
     * @return a star graph on {@code V} vertices: a single vertex connected to
     *     every other vertex
     */
    public static Graph star(int V) {
        if (V <= 0) throw new IllegalArgumentException("Number of vertices must be at least 1");
        Graph G = new Graph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++)
            vertices[i] = i;
        StdRandom.shuffle(vertices);

        // connect vertices[0] to every other vertex
        for (int i = 1; i < V; i++) {
            G.addEdge(vertices[0], vertices[i]);
        }

        return G;
    }

    /**
     * Returns a uniformly random {@code k}-regular graph on {@code V} vertices
     * (not necessarily simple). The graph is simple with probability only about e^(-k^2/4),
     * which is tiny when k = 14.
     *
     * @param V the number of vertices in the graph
     * @param k degree of each vertex
     * @return a uniformly random {@code k}-regular graph on {@code V} vertices.
     */
    public static Graph regular(int V, int k) {
        if (V*k % 2 != 0) throw new IllegalArgumentException("Number of vertices * k must be even");
        Graph G = new Graph(V);

        // create k copies of each vertex
        int[] vertices = new int[V*k];
        for (int v = 0; v < V; v++) {
            for (int j = 0; j < k; j++) {
                vertices[v + V*j] = v;
            }
        }

        // pick a random perfect matching
        StdRandom.shuffle(vertices);
        for (int i = 0; i < V*k/2; i++) {
            G.addEdge(vertices[2*i], vertices[2*i + 1]);
        }
        return G;
    }

    // http://www.proofwiki.org/wiki/Labeled_Tree_from_Prüfer_Sequence
    // http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.36.6484&rep=rep1&type=pdf
    /**
     * Returns a uniformly random tree on {@code V} vertices.
     * This algorithm uses a Prufer sequence and takes time proportional to <em>V log V</em>.
     * @param V the number of vertices in the tree
     * @return a uniformly random tree on {@code V} vertices
     */
    public static Graph tree(int V) {
        Graph G = new Graph(V);

        // special case
        if (V == 1) return G;

        // Prufer sequence: sequence of V-2 values between 0 and V-1
        // Prufer sequences of length V-2 is bijective to trees with V vertices.
        
        int[] prufer = new int[V-2];
        for (int i = 0; i < V-2; i++)
            prufer[i] = StdRandom.uniform(V);

        // degree of vertex v = 1 + number of times it appers in Prufer sequence
        int[] degree = new int[V];
        for (int v = 0; v < V; v++)
            degree[v] = 1;
        for (int i = 0; i < V-2; i++)
            degree[prufer[i]]++;
        
        /*
        // pq contains all vertices of degree 1
        MinPQ<Integer> pq = new MinPQ<Integer>();
        for (int v = 0; v < V; v++)
            if (degree[v] == 1) pq.insert(v);

        // repeatedly delMin() degree 1 vertex that has the minimum index
        for (int i = 0; i < V-2; i++) {
            int v = pq.delMin();
            G.addEdge(v, prufer[i]);
            degree[v]--;
            degree[prufer[i]]--;
            if (degree[prufer[i]] == 1) pq.insert(prufer[i]);
        }
        G.addEdge(pq.delMin(), pq.delMin());
        */
        return G;
    }

    /**
     * Unit tests the {@code GraphGenerator} library.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    	Digraph z = randomDirectedGraph(10, 0.2);
    	System.out.println(z.toString());
    	Digraph h = isomorphic(z);
    	System.out.println(h.toString());
    	
    }

}
