import java.util.LinkedList;

public class ConnectedComponents {
	private Digraph G;
	private Digraph sccGraph;

	private int[] connectedComponent;
	private int[] connectedComponentLabels;
	private LinkedList<Integer>[] connectedComponentList;
	
	private int numConnectedComponent;
	
	public ConnectedComponents(Digraph G){
		this.G = G;
		this.connectedComponent = new int[G.V()];
		findConnectedComponents();
	}
	
	private void findConnectedComponents(){
		DFS dfs = new DFS(G);
		System.out.println(dfs.toString());

		Digraph reverse = G.reverse();
		int[] labeling = new int[G.V()];

		for(int i=0;i<G.V();++i){
			labeling[dfs.fallbackOrder(i)] = G.V()-1-i;
		}
		Permutation p = new Permutation(labeling);

		System.out.println(reverse.toString());

		reverse = reverse.relabel(p.getPermutation());
		DFS dfsReverse = new DFS(reverse);
		
		this.numConnectedComponent = dfsReverse.numTreesInDFSForest();
		this.connectedComponentLabels = new int[this.numConnectedComponent];
		int index=0;
		for(int i:dfsReverse.getSources()){
			this.connectedComponentLabels[index] = i;
			index++;
		}
		
		LinkedList<Integer>[] list = dfsReverse.vertexByRoot();
		this.connectedComponentList = new LinkedList[numConnectedComponent];
		for(int i=0 ; i<numConnectedComponent;++i){
			this.connectedComponentList[i] = new LinkedList<Integer>();
			for(int u: list[i]){
				this.connectedComponentList[i].add(p.inverse().getIndex(u));
			}
		}
		
		int[] connectedComponentWithOldLabels = new int[G.V()];
		System.arraycopy(dfsReverse.getOriginalSource(), 0, connectedComponentWithOldLabels, 0, G.V());
		for(int i=0 ; i<G.V();++i){
			this.connectedComponent[i] = connectedComponentWithOldLabels[p.getIndex(i)];
		}
		
		this.sccGraph = new Digraph(this.numConnectedComponent);
		for(int u=0;u<G.V();++u){
			for(int v: G.adj(u)){
				if(connectedComponent[u]!=connectedComponent[v]){
					this.sccGraph.addEdge(connectedComponent[u], connectedComponent[v]);
				}
			}
		}
	}
	
	public int getConnectedComponents(int u){
		this.G.validateVertex(u);
		return connectedComponent[u];
	}
	
	public static void main(String[] args){
		   Digraph G = new Digraph(10);
		   G.addEdge(0, 2);
		   G.addEdge(0, 9);
		   G.addEdge(2, 6);
		   G.addEdge(2, 7);
		   G.addEdge(3, 1);
		   G.addEdge(3, 5);
		   G.addEdge(4, 5);
		   G.addEdge(5, 0);
		   G.addEdge(5, 4);
		   G.addEdge(5, 6);
		   G.addEdge(6, 0);
		   G.addEdge(6, 2);
		   G.addEdge(9, 0);
		   G.addEdge(9, 6);
		   System.out.print(G.toString());
		   ConnectedComponents scc = new ConnectedComponents(G);
		   System.out.print(scc.sccGraph.toString());
		   for(LinkedList<Integer> l : scc.connectedComponentList){
			   System.out.println(l.toString());
		   }
		   for(int i=0;i<G.V();++i){
			   System.out.println("Vertex: "+ i + "  CC:" + scc.getConnectedComponents(i));
		   }
	}
	
	
	
	
}
