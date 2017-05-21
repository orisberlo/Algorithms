
public class Edge implements Comparable<Edge>{
	private final int u,v;
	private final double weight;
	

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  weight the weight of this edge
     * @throws IllegalArgumentException if either {@code v} or {@code w} 
     *         is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
	public Edge(int i, int j , double weight){
        if (i < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (j < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
		this.u = i;
		this.v = j;
		this.weight=weight;
	}
	
    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * default weight 1.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  weight the weight of this edge
     * @throws IllegalArgumentException if either {@code v} or {@code w} 
     *         is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
	public Edge(int i, int j){
        if (i < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (j < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
		this.u = i;
		this.v = j;
		this.weight=1;
	}
	
    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return this.u;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *         endpoints of this edge
     */
    public int other(int vertex) {
        if      (vertex == this.u) return this.u;
        else if (vertex == this.v) return this.v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    /**
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format("%d-%d %.5f", u, v, weight);
    }

    /**
     * Unit tests the {@code Edge} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
    	// Run unit tests
    }
    
}
