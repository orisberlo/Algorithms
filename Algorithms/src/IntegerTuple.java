public class IntegerTuple{
	private int[] dim;

	IntegerTuple(int[] dim){
		this.dim = new int[dim.length];
		System.arraycopy(dim, 0, this.dim, 0, dim.length);
	}
	
    public int[] Integer2Tuple(int n){
    	return Integer2Tuple(n,dim);
    }
    
    public int Tuple2Integer(int[] tuple){
    	return Tuple2Integer(tuple,dim);
    }
    
    
    public static int[] Integer2Tuple(int n, int[] dim){
    	int[] tuple = new int[dim.length];
    	int prod = 1;
    	for(int i=0;i<dim.length;++i)prod*=dim[i];
    	for(int i=0;i<dim.length;++i){
    		prod = prod/dim[i];
    		tuple[i] = n/prod;
    		n = n%prod;
    	}
    	return tuple;
    }
    
    public static int Integer2TupleCoordinate(int n, int[] dim,int d){
    	if(d < 0) throw new IllegalArgumentException("Index is negative.");
    	if(d > dim.length-1) throw new IllegalArgumentException("Index is out of bounds.");
    	int[] tuple = Integer2Tuple(n, dim);
    	return tuple[d];
    }
    
    
    public static int Tuple2Integer(int[] tuple,int[] dim){
    	int n=0;
    	int prod = 1;
    	for(int i=0;i<dim.length;++i)prod*=dim[i];
    	for(int i=0;i<dim.length;++i){
    		prod = prod/dim[i];
    		n+= tuple[i]*prod;
    	}
    	return n;
    }
    
    //Unit tests
    public static void main(String arg[]){
 	   int d1=7 , d2 = 4, d3=3, d4=1 ,d5=2;
 	   int[] dim = {d1, d2 , d3 , d4, d5};
 	   for(int i=0; i<d1*d2*d3*d4*d5 ; ++i){
 		   String s = "Coordinate:  "+i+"    Tuple:   (";
 		   s += IntegerTuple.Integer2Tuple(i, dim)[0];
 		   s+= ",";
 		   s+=IntegerTuple.Integer2Tuple(i, dim)[1];
 		   s+= ",";
 		   s+=IntegerTuple.Integer2Tuple(i, dim)[2];
 		   s+= ",";
 		   s+=IntegerTuple.Integer2Tuple(i, dim)[3];
 		   s+= ",";
 		   s+=IntegerTuple.Integer2Tuple(i, dim)[4];
 		   s+=")";
 		   s+="     Inverse Coordinate: ";
 		   s+= IntegerTuple.Tuple2Integer(IntegerTuple.Integer2Tuple(i,dim), dim);	   
 		   s+="\n";
 		   System.out.print(s);
 	   }
 	   
 	   IntegerTuple tuple = new IntegerTuple(dim);
 	   for(int i=0; i<d1*d2*d3*d4*d5 ; ++i){
 		   String s = "Coordinate:  "+i+"    Tuple:   (";
 		   s += tuple.Integer2Tuple(i)[0];
 		   s+= ",";
 		   s+=tuple.Integer2Tuple(i)[1];
 		   s+= ",";
 		   s+=tuple.Integer2Tuple(i)[2];
 		   s+= ",";
 		   s+=tuple.Integer2Tuple(i)[3];
 		   s+= ",";
 		   s+=tuple.Integer2Tuple(i)[4];
 		   s+=")";
 		   s+="     Inverse Coordinate: ";
 		   s+= tuple.Tuple2Integer(tuple.Integer2Tuple(i));	   
 		   s+="\n";
 		   System.out.print(s);
 	   }
    }
}
