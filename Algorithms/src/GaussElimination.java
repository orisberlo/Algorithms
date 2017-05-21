import java.util.LinkedList;

import javax.naming.ldap.UnsolicitedNotificationListener;


public class GaussElimination {
	private Matrix mat;
	private Matrix gauss;
	private LinkedList<Integer[]> pivots;
	private LinkedList<ElementaryMatrix> operations;

	public GaussElimination(Matrix mat){
		this.mat = new Matrix(mat);
		this.gauss = new Matrix(mat);
		this.operations = new LinkedList<ElementaryMatrix>();
		this.pivots = new LinkedList<Integer[]>(); 
		int currentRow=0 , m =(gauss.rowNum()<gauss.colNum()?gauss.rowNum():gauss.colNum());
		ElementaryMatrix e;
		for(int k=0 ; k< m;++k){
			int pivotRow=-1;
			for(int t=currentRow; t< gauss.rowNum();++t){
				if(gauss.get(t, k).nonZeroElement()){
					pivotRow=t;
					Integer[] pivot = new Integer[2];
					pivot[0] = currentRow;
					pivot[1] = k;
					pivots.add(pivot);
					break;
				}
			}
			if(pivotRow<0){
				//Matrix is singular...
				//Note that currentRow is not incremented
				continue;
			}
			
			//Switch to pivot
			if(currentRow!= pivotRow){
				e = new ElementaryMatrix(currentRow, pivotRow, gauss.colNum(), gauss.unitElement());
				operations.add(e);
				gauss.elementaryOperation(e);
			}
			
			//Eliminate all rows below
			FieldElement inv = gauss.get(currentRow, k).inverseElement().negationElement();
			for(int t=currentRow+1; t< gauss.rowNum();++t){
				FieldElement f = inv.mult(gauss.get(t, k));
				if(!f.nonZeroElement()) continue;
				e = new ElementaryMatrix(k, t, gauss.colNum(), f , gauss.unitElement());
				gauss.elementaryOperation(e);
				operations.add(e);
			}
			currentRow++;
		}
	}
	
	public LinkedList<ElementaryMatrix> operations(){
		return this.operations;
	}

	public LinkedList<Integer[]> pivots(){
		return this.pivots;
	}
	
	
	public int numPivots(){
		return this.pivots.size();
	}
	
	public int rank(){
		return pivots.size();
	}

	public boolean isFullRank(){
		return (rank()==this.mat.rowNum());
	}
	
	public boolean isSingular(){
		if(!mat.isSquare()) throw new IllegalAccessError("Matrix must be a square matrix");
		return (rank()<mat.rowNum());
	}
	
	public FieldElement det(){
		if(!mat.isSquare()) throw new IllegalAccessError("Matrix must be a square matrix");
		GaussElimination g = new GaussElimination(this.mat); 
		if(g.isSingular()){ // Check if singular
			return mat.zeroElement();
		}
		
		FieldElement det = mat.unitElement();
		
		for(int i=0;i<gauss.rowNum();++i){
			det=det.mult(gauss.get(i, i));
		}
		return det;
	}
	

	
	public Matrix getMatrix(){
		return mat;
	}
	
	public FieldElement get(int i , int j){
		return gauss.get(i, j);
	}
	
	public Matrix getGauss(){
		return gauss;
	}
	
	public Matrix[] LU(){
		Matrix[]  LU = new Matrix[3];
		LU[0] = new Matrix(this.mat);
		LU[1] = this.gauss;
		LU[2] = new Matrix(this.mat);
		LU[0].setToIdentity();
		LU[2].setToIdentity();
		
		for(ElementaryMatrix e:operations){
			switch(e.getType()){
			case ROW_SWITCH:
				LU[2].elementaryOperation(e);
				break;
			case ROW_ADDITION:
				FieldElement f = e.getScale().negationElement();
				LU[0].set(e.getj(), e.geti(), f);
				break;
			
			}	
		}
		return LU;
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
			s+=m.toString();
		}
		
		return s;
	}
	
	public String toString(){
		return this.gauss.toString();
	}
	
	public static void main(String args[]){
		ModularNumber w = new ModularNumber(2,17);
		Matrix m = new Matrix(6,6,w);
		for(int i=0;i<5;++i){
			m.set(i,i, new ModularNumber(i+5,17));
		}
		m.set(0, 0, w.zeroElement());
		m.set(1, 0, w.zeroElement());

		GaussElimination g = new GaussElimination(m);
		System.out.println(g.detailedComputation());
		Matrix[] lu = g.LU();
		System.out.println(lu[2].multRight(m).toString());
		System.out.println(lu[0].multRight(lu[1]).toString());
		System.out.println(m.toString());
		System.out.println(lu[0].toString());
		System.out.println(lu[1].toString());
		System.out.println(lu[2].toString());
		
	}
}
