import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.text.html.HTMLDocument.Iterator;

public class DFS {
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int NO_PARENT = -1;
    private Digraph G;
    private int source;
    
    private int parent[];
    private boolean marked[];    
    
    private int detectionTime[];
    private ArrayList<Integer> detectionOrder;
    
    private int fallbackTime[];
    private ArrayList<Integer> fallbackOrder;
    

    
    private int rootInDFSTree[];
    private ArrayList<Integer> sources;
    private int clock;

    
    public enum edgeType {BACK_EDGE, FRONT_EDGE,TREE_EDGE,CUT_EDGE,NO_EDGE};
    
    public DFS(Digraph G, int source){
    	this.G = new Digraph(G);
    	G.validateVertex(source);
    	this.source = source;
    	this.detectionTime = new int[G.V()];
    	this.detectionOrder = new ArrayList<Integer>(G.V());

    	this.parent = new int[G.V()];

    	this.fallbackTime = new int[G.V()];
    	this.fallbackOrder = new ArrayList<Integer>(G.V());
    	
    	this.marked = new boolean[G.V()];
    	this.sources = new ArrayList<Integer>();
    	this.rootInDFSTree = new int[G.V()];

    	this.clock=0;
    	
    	for(int i=0;i<G.V();++i){
    		detectionTime[i] = INFINITY;
    		parent[i] = NO_PARENT;
    		fallbackTime[i] = INFINITY;
    		marked[i] = false;
    	}
    	
    	/* Start DFS from given source
    	 * Continue to apply DFS if not all veritices are marked.
    	 * Note that since source may be larger than 0 we go over
    	 * the vertices in cyclic fashion.
    	 */
    	int currentSource = source , index=0;
    	while(currentSource<2*G.V()){
    		if(!marked[currentSource%G.V()]){
    			sources.add(currentSource%G.V());
    			dfs(currentSource%G.V(),index);
    			index++;
    		}
    		currentSource++;
    	}
    }
    
    
    public DFS(Digraph G){
    	this(G, 0);
    }
    
    private void clock(){
    	clock++;
    }
    
    private void dfs(int source, int originalSource){
    	marked[source] = true;
		detectionTime[source] = clock;
    	this.detectionOrder.add(source);
		this.rootInDFSTree[source] = originalSource; 
		clock();
    	for(int neighbor: G.adj(source)){
    		if(!marked[neighbor]){
    			parent[neighbor] = source;
    			dfs(neighbor,originalSource);
    		}
    	}
    	fallbackTime[source] = clock;
    	this.fallbackOrder.add(source); 
		clock();
    }
    
    
    private edgeType getEdgeType(int u,int v){
    	G.validateVertex(u);
    	G.validateVertex(v);
    	if((detectionTime[u]<detectionTime[v]) && (detectionTime[v]<fallbackTime[v]) && (fallbackTime[v]<fallbackTime[u])){
    		if(parent[v]==u){
    	    	return edgeType.TREE_EDGE;
    		}
    		else{
    	    	return edgeType.FRONT_EDGE;
    		}
    	}
    	else if((detectionTime[v]<detectionTime[u]) && (detectionTime[u]<fallbackTime[u]) && (fallbackTime[u]<fallbackTime[v])){
    		return edgeType.BACK_EDGE;
    	}
    	else if((detectionTime[v]<fallbackTime[v]) && (fallbackTime[v]<detectionTime[u]) && (detectionTime[u]<fallbackTime[u])){
    		return edgeType.CUT_EDGE;
    	}
    	return edgeType.NO_EDGE;
    }
    
    
    public boolean hasCycle(){
    	for(int u=0;u<G.V();++u){
    		for(int v:G.adj(u)){
    			if(rootInDFSTree[u] != rootInDFSTree[v]){
    				continue;
    			}
        		if(getEdgeType(u,v)==edgeType.BACK_EDGE) return true;
        	}
    	}
    	return false;
    }
    
    public boolean hasReachableCycle(){
    	for(int u=0;u<G.V();++u){
    		if(rootInDFSTree[u]!= this.source) continue;
    		for(int v:G.adj(u)){
    			if(rootInDFSTree[u] != rootInDFSTree[v]){
    				continue;
    			}
        		if(getEdgeType(u,v)==edgeType.BACK_EDGE) return true;
        	}
    	}
    	return false;
    }
    
    public Iterable<Integer> returnCycle(){
    	Stack<Integer> cycle = new Stack<Integer>();
    	int uStart=-1 ,uEnd=-1;
    	for(int u=0;u<G.V();++u){
    		for(int v:G.adj(u)){
    			if(rootInDFSTree[u] != rootInDFSTree[v]){
    				continue;
    			}
        		if(getEdgeType(u,v)==edgeType.BACK_EDGE){
        			uStart = v;
        			uEnd = u;
        			break;
        		}
        	}
    		if(uStart!=-1)break;
    	}
    	
    	if(uStart==-1){ 
    		return cycle;
    	}
    	int w = uEnd;
    	while(w!=uStart){
    		cycle.push(w);
    		w = parent[w];
    	}
    	cycle.push(uStart);
    	return cycle;
    }
    
    
    public boolean isConnected(int u){
    	G.validateVertex(u);
    	return fallbackTime[source]>detectionTime[u];
    }
    
    public boolean isConnected(){
    	for(int u=0;u<G.V();++u){
    		if(!isConnected(u)) return false;
    	}
    	return true;
    }
    
    public Iterable<Integer> getReachable(){
    	LinkedList<Integer> reachable = new LinkedList<Integer>();
    	for(int u=0;u<G.V();++u){
    		if(isConnected(u)){
    			reachable.add(u);
    		}
    	}
    	return reachable;
    }
    
    public Digraph DFSForest(){
    	EdgeIndicator edgeIndicator = new EdgeIndicator(){
    		public boolean edgeIndicator(int u, int v){
    			return parent[v]==u;
    		}
    	};
    	Digraph dfsForest = new Digraph(G.V(),edgeIndicator);
    	return dfsForest;
    }
    
    /**
     * 
     * @return Array of linked lists. Each cell i contains a list
     * of vertices which corresponds to the root i
     */
    public LinkedList<Integer>[] vertexByRoot(){
    	LinkedList<Integer>[] roots = new LinkedList[numTreesInDFSForest()];
    	for(int i=0;i<roots.length;++i){
    		roots[i] = new LinkedList<Integer>();
    	}
    	for(int i=0;i<G.V();++i){
    		roots[rootInDFSTree[i]].add(i);
    	}
    	return roots;
    }

    
    public int numTreesInDFSForest(){
    	return sources.size();
    }
    
    public int[] getOriginalSource(){
    	return this.rootInDFSTree;
    }
    
    public Iterable<Integer> getSources(){
    	return this.sources;
    }
    
    public Iterable< LinkedList<Integer>> nodesInDFSTree(){
    	ArrayList< LinkedList<Integer>> defTrees = new ArrayList< LinkedList<Integer>>(numTreesInDFSForest());
    	for(int i=0;i<G.V();++i){
    		defTrees.get(rootInDFSTree[i]).add(i);
    	}
    	return defTrees;
    }
    
    public int getSource(){
    	return this.source;
    }
    
    public int getSource(int index){
    	return this.sources.get(index);
    }
    
    public int getLastSource(){
    	return getSource(sources.size()-1);
    }
    
    public int getFirstSource(){
    	return getSource(0);
    }
    

    
    
    public static Iterable<Integer> getRoots(Digraph G){
    	DFS d = new DFS(G);
    	d = new DFS(G,d.getLastSource());
    	if(d.numTreesInDFSForest()!=1){
    		return new LinkedList<Integer>();//return empty iterator
    	}

    	return new DFS(G.reverse(),d.getSource()).getReachable();
    }
    
    
    public int fallbackOrder(int index){
    	return this.fallbackOrder.get(index);
    }
    
    public String toString(){
    	return DFSForest().toString();
    }
    
    public static void main(String args[]){
    	Digraph g = GraphGenerator.randomDirectedGraph(20, 0.1);
    	System.out.println(g.toString());
    	DFS d = new DFS(g);
    	System.out.println(d.numTreesInDFSForest());
    	//System.out.println(d.hasReachableCycle());
    	//System.out.println(d.hasCycle());
    	//System.out.println(d.returnCycle().toString());
    	for(int u : getRoots(g)){
    		System.out.println("Root: " + u + " Check: " + new DFS(g,u).numTreesInDFSForest());
    	}
    	
    }
}
