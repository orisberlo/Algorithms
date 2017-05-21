import java.util.LinkedList;

public class Echelon {
	private Matrix mat;
	private Matrix echelon;
	private LinkedList<ElementaryMatrix> operations;
	private LinkedList<Integer[]> pivots;

	public Echelon(Matrix mat){
		this.mat = new Matrix(mat);
		GaussElimination g = new GaussElimination(mat);  		
		this.echelon = g.getGauss();
		this.operations = g.operations();
		this.pivots = g.pivots();
		//Eliminate all rows below

		ElementaryMatrix e;
		for(Integer[] pivot: pivots){
			FieldElement f = echelon.get(pivot[0], pivot[1]).inverseElement();
			e = new ElementaryMatrix(pivot[0], echelon.colNum(), f , getMatrix().unitElement());
			operations.add(e);
			echelon.elementaryOperation(e);
			for(int k=pivot[0]-1;k>=0;--k){
				f = echelon.get(k, pivot[1]).negationElement();
				e = new ElementaryMatrix(pivot[0],k, echelon.colNum(), f , getMatrix().unitElement());
				operations.add(e);
				echelon.elementaryOperation(e);
			}
		}
	}	

	
	
	public int numPivots(){
		return this.pivots.size();
	}

	
	public Matrix getMatrix(){
		return this.mat;
	}
	
	
	public LinkedList<ElementaryMatrix> operations(){
		return this.operations;
	}

	public LinkedList<Integer[]> pivots(){
		return this.pivots;
	}
	
	
	/**
	 * 
	 * @return String containing detailed calculation of the operations.
	 */
	public String computation(){
		String s = new String();
		for(ElementaryMatrix e: this.operations){
			s+=e.operationString();
		}
		return s;
	}
	
	/**
	 * 
	 * @return String containing detailed calculation of the operations.
	 */
	public String detailedComputation(){
		String s = new String();
		Matrix m = new Matrix(this.mat);
		s+= "Matrix:\n";
		s+=mat.toString();
		for(ElementaryMatrix e: this.operations){
			s+= "****************************************************************\n";
			s+=e.operationString();
			m.elementaryOperation(e);
			s+=mat.toString();
		}
		
		return s;
	}
	
	public String toString(){
		return this.echelon.toString();
	}
	
	public static void main(String args[]){
		ModularNumber x0 = new ModularNumber(0,17);
		ModularNumber x1 = new ModularNumber(1,17);
		ModularNumber x2 = new ModularNumber(2,17);
		ModularNumber x3 = new ModularNumber(3,17);
		ModularNumber x4 = new ModularNumber(4,17);
		
		Matrix m1 = new Matrix(2,2,x1);
		m1.set(0, 1, x2);
		System.out.println(m1.toString());
		Matrix m = new Matrix(6,6,x3);
		Matrix m3 = new Matrix(6,6,x1);

		m.set(1, 1, x4.zeroElement());
		m.set(1, 2, x4);

		m.set(1, 0, x3);
		m.set(2,2, x2);
		
		for(int i=0;i<6;++i){
			m3.set(i,i, new ModularNumber(i+5,17));
		}
		Echelon g; 
		m3.set(0, 0, x4);
		System.out.println(m3.toString());
		g = new Echelon(m3);
		System.out.println(g.detailedComputation());
		//System.out.println(g.inverse().toString());
		//System.out.println(g.inverse().multRight(m3).toString());
		//System.out.println(m3.multRight(g.inverse()).toString());
		 
	}
}
