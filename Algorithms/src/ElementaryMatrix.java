
public class ElementaryMatrix {
    private static final int UNDEFINED=-1;
    public enum Type {ROW_SCALING , ROW_ADDITION , ROW_SWITCH , IDENTITY};
    private Type type;
    private int i,j,size;
    private FieldElement scale;
    private FieldElement unitElement;
    private FieldElement zeroElement;

    /**
     * Constructor for identity elementary matrix
     * @param size
     * @param unitElement
     * 
     */
    ElementaryMatrix(int size,FieldElement unitElement){
    	this.type = Type.IDENTITY;
    	this.size = size;
    	this.i = UNDEFINED;
    	this.j = UNDEFINED;
    	this.scale=null;
    	this.unitElement = unitElement;
    	this.zeroElement = unitElement.zeroElement();
    }
    
  /**
   * Constructor for row switch matrix. Switches row i with row j
   * @param i - row index
   * @param j - row index
   * @param size - of matrix
   * @param unitElement
   */
    
    ElementaryMatrix(int i, int j,int size,FieldElement unitElement){
    	if(i==j) throw new IllegalArgumentException("i,j must be different");
    	this.type = Type.ROW_SWITCH;
    	this.size = size;
    	this.i = i;
    	this.j = j;
    	this.scale=null;
    	this.unitElement = unitElement;
    	this.zeroElement = unitElement.zeroElement();
    }
    
  /**
   * Constructor for row addition. Adds scale times row i for row j. 
   * @param i - row index
   * @param j - row index
   * @param size - size of matrix
   * @param scale - scaling element
   * @param unitElement
   */
    ElementaryMatrix(int i, int j,int size, FieldElement scale,FieldElement unitElement){
    	if(i==j) throw new IllegalArgumentException("i,j must be different");
    	this.type = Type.ROW_ADDITION;
    	this.size = size;
    	this.i = i;
    	this.j = j;
    	this.scale = scale;
    	this.unitElement = unitElement;
    }
    
    /**
     * Constructor for row scaling matrix. Multiply row i by scale.
     * @param i - row index
     * @param size - size of matrix
     * @param scale - scaling element
     * @param unitElement
     */
    ElementaryMatrix(int i,int size, FieldElement scale,FieldElement unitElement){
    	this.type = Type.ROW_SCALING;
    	this.size = size;
    	this.i = i;
    	this.j = UNDEFINED;
    	this.scale = scale;
    	this.unitElement = unitElement;
    }
    
    public Type getType(){
    	return type;
    }
    public int geti(){
    	return this.i;
    }
    public int getj(){
    	return this.j;
    }
    
    public FieldElement getScale(){
    	return this.scale;
    }
    
    public ElementaryMatrix inverse(){
    	switch(this.type){
		case ROW_SCALING:
			return new ElementaryMatrix(this.i,this.size,scale.inverseElement(),unitElement);
		case ROW_ADDITION:
			return new ElementaryMatrix(this.i,this.j,this.size,scale.negationElement(),unitElement);
		case ROW_SWITCH:
			return new ElementaryMatrix(this.i,this.j,this.size,unitElement);
		}
    	return new ElementaryMatrix(this.j,this.i,this.size,unitElement);
    }
    
    public Matrix getMatrix(){
    	Matrix mat = new Matrix(this.size,this.size,unitElement);
    	switch(this.type){
    	case ROW_SWITCH:
    		mat.set(i, j, unitElement);
    		mat.set(j, i, unitElement);
    		mat.set(i, i, zeroElement);
    		mat.set(j, j, zeroElement);
        	return mat;
		case ROW_SCALING:
			mat.set(i, i, scale);
			return mat;
		case ROW_ADDITION:
			for(int k=0 ; k<size;++k){
				mat.set(j, k, mat.get(i, k).mult(scale).add(mat.get(j, k)));
			}
			return mat;
		default:
			return mat;
		}
    }
    
    public FieldElement det(){
    	switch(this.type){
    	case ROW_SWITCH:
        	return unitElement;
		case ROW_SCALING:
    		return scale;
		case ROW_ADDITION:
			return unitElement;
		default:
			return zeroElement;
		}
    }
    
    public String operationString(){
    	String s = new String();
    	s+= "Operation: ";
    	switch(this.type){
    	case ROW_ADDITION:
    		s+= "Row("+this.j+") -> Row("+this.j+") + " + this.scale+" * Row("+this.i+")\n";
    		break;
    	case ROW_SWITCH:
    		s+= "Row("+this.j+") <-> Row("+this.i+")\n";
    		break;
    	case ROW_SCALING:
    		s+= "Row("+this.i+") -> "+this.scale+" * Row("+this.i+")\n";
    		break;    		
    	default:
    		s+="No operation\n";
    	}	
    	return s;
    }
    
    public String toString(){
    	return this.getMatrix().toString();
    }
    
    public static void main(String[] args){
		ModularNumber x = new ModularNumber(1,17);
		ModularNumber y = new ModularNumber(15,17);
    	ElementaryMatrix e = new ElementaryMatrix(3,5,y,x);
    	System.out.println(e.getMatrix().toString());
    	System.out.println(e.inverse().getMatrix().toString());
    	System.out.println(e.toString());
    	System.out.println(e.operationString());
    }
}
