import java.rmi.UnexpectedException;
import java.util.LinkedList;

public class Matrix {
	private FieldElement[][] mat;
	private int rowNum , colNum;
	private FieldElement zeroElement,unitElement;
	
	public Matrix(int n , int m , FieldElement f){
		if(n<=0) throw new IllegalArgumentException("n must be positive");
		if(m<=0) throw new IllegalArgumentException("m must be positive");
		this.mat = new FieldElement[n][m];
		rowNum = n;
		colNum = m;
		zeroElement = f.zeroElement();
		unitElement = f.unitElement();
		for(int i=0 ; i<rowNum;++i){
			for(int j=0;j<colNum;++j){
				mat[i][j] = f.cloneElement();
			}
		}
	}
	
	/*
	public Matrix(FieldElement[][] matrixValues, FieldElement f){
		if(matrixValues.length==0) throw new IllegalArgumentException("size must be positive");
		int n = matrixValues.length;
		int m = matrixValues[0].length;
		this.mat = new FieldElement[n][m];
		rowNum = n;
		colNum = m;
		zeroElement = f.zeroElement();
		unitElement = f.unitElement();
		for(int i=0 ; i<rowNum;++i){
			for(int j=0;j<colNum;++j){
				mat[i][j] = matrixValues[i][j];
			}
		}
	}
	*/
	
	public Matrix(LinkedList<Vector> columnVectors){
		if(columnVectors.isEmpty()) throw new IllegalArgumentException("size must be positive");
		Vector first = columnVectors.getFirst();
		int n = first.size();
		int m = columnVectors.size();
		this.mat = new FieldElement[n][m];
		rowNum = n;
		colNum = m;
		zeroElement = first.zeroElement();
		unitElement = first.unitElement();
		int j = 0;
		for(Vector v: columnVectors){
			if(v.size()!= n) throw new IllegalArgumentException("all vectors must have the same length");
			for(int i=0 ; i<v.size();++i){
				mat[i][j] = v.get(i);	
			}
			++j;
		}
	}
	
	public Matrix(int n , FieldElement f){
		this(n,n,f);
	}
	
	public Matrix(Matrix mat){
		rowNum = mat.rowNum;
		colNum = mat.colNum;
		this.mat = new FieldElement[rowNum][colNum];
		zeroElement = mat.zeroElement;
		unitElement = mat.unitElement;
		for(int i=0 ; i<rowNum;++i){
			for(int j=0;j<colNum;++j){
				this.mat[i][j] = mat.get(i, j).cloneElement();
			}
		}
	}
	
	public Matrix(Matrix m, FieldElement[] b) {
		this(m.rowNum(),m.colNum()+1,m.unitElement());
		if(m.rowNum()!=b.length) throw new IllegalArgumentException("Row number of a "+ m.rowNum()+"m must be equal to length of b " + b.length);
		for(int i=0 ; i<rowNum;++i){
			for(int j=0;j<colNum-1;++j){
				this.mat[i][j]= m.get(i, j);
			}
		}
		for(int i=0;i<rowNum;++i){
			this.mat[i][colNum-1]= b[i];
		}
	}

	public void setToIdentity(){
		if(!isSquare()) throw new IllegalAccessError("Matrix must be a square matrix");
		for(int i=0;i<rowNum();++i){
			for(int j=0;j<colNum();++j){
				set(i,j,(i==j?unitElement:zeroElement));
			}	
		}
	}
	
	public int rowNum(){
		return this.rowNum;
	}
	public int colNum(){
		return this.colNum;
	}
	
	public FieldElement get(int i,int j){
		if(i>=rowNum()) throw new IllegalArgumentException(" i must greater equal to 0 and small than n");
		if(j>=colNum()) throw new IllegalArgumentException(" i must greater equal to 0 and small than n");
		return this.mat[i][j];
	}
	
	public void add(int i,int j , FieldElement f){
		if(i>=rowNum()) throw new IllegalArgumentException(" i must greater equal to 0 and small than n");
		if(i>=colNum()) throw new IllegalArgumentException(" i must greater equal to 0 and small than n");
		this.mat[i][j].addElement(f);
	}
	
	public void set(int i,int j,FieldElement f){
		if(i>=rowNum()) throw new IllegalArgumentException(" i must greater equal to 0 and small than n");
		if(j>=colNum()) throw new IllegalArgumentException(" i must greater equal to 0 and small than n");
		this.mat[i][j]=f.cloneElement();
	}
	
	public Matrix Diagonal(int n , FieldElement defaultValue){
		Matrix mat = new Matrix(n,n,defaultValue);
		for(int i=0 ; i<n;++i){
			for(int j=0;j<n;++j){
				mat.set(i, j, defaultValue.zeroElement());
			}
		}
		return mat;
	}
	
	public Matrix add(Matrix mat){
		if(mat.rowNum()!= this.rowNum()) throw new IllegalArgumentException("Row size does not match");
		if(mat.colNum()!= this.colNum()) throw new IllegalArgumentException("Column size does not match");
		Matrix addition = new Matrix(mat.rowNum,mat.colNum,zeroElement);
		for(int i=0;i<mat.rowNum();++i){
			for(int j=0;j<colNum();++j){
				addition.set(i, j, this.get(i, j).add(mat.get(i, j)));
			}
		}
		return addition;
	}
	
	public Matrix multRight(Matrix mat){
		if(mat.rowNum()!= this.rowNum()) throw new IllegalArgumentException("Row size does not match");
		if(mat.colNum()!= this.colNum()) throw new IllegalArgumentException("Column size does not match");
		Matrix product = new Matrix(mat.rowNum,mat.colNum,zeroElement);
		for(int i=0;i<mat.rowNum();++i){
			for(int j=0;j<colNum();++j){
				FieldElement sum = zeroElement.cloneElement();
				for(int k=0;k<this.colNum();++k){
					FieldElement f = this.get(i, k).mult(mat.get(k, j));
					sum.addElement(f);
				}
				product.set(i, j, sum);
			}
		}
		return product;
	}
	
	public String toString(){
		String s = new String();
		for(int i=0 ; i<rowNum;++i){
			for(int j=0;j<colNum;++j){
				s+=this.get(i, j).toString() + " ";
			}
			s+= "\n";
		}
		return s;
	}
	
	public void elementaryOperation(ElementaryMatrix e){
    	switch(e.getType()){
    	case ROW_SWITCH:
    		for(int k=0 ; k<this.colNum();++k){
    			FieldElement temp = this.get(e.geti(), k);
        		this.set(e.geti(), k, this.get(e.getj(), k));
        		this.set(e.getj(), k, temp);
    		}
    		return;
    	case ROW_ADDITION:
    		for(int k=0 ; k<this.colNum();++k){
    			FieldElement f = this.get(e.geti(), k).mult(e.getScale()).add(this.get(e.getj(), k));
    			this.set(e.getj(), k, f);
    		}
    		
    		return;
    	case ROW_SCALING:
    		for(int k=0 ; k<this.colNum();++k){
    			FieldElement f = this.get(e.geti(), k).mult(e.getScale());
    			this.set(e.geti(), k, f);
    		}    		
    		return;
		}
	}
	
	public boolean isSquare(){
		return rowNum()==colNum();
	}
	
	public FieldElement det(){
		GaussElimination g = new GaussElimination(this);
		return g.det();
	}

	public Matrix inverse(){
		if(!isSquare()) throw new IllegalAccessError("Matrix must be a square matrix");
		Matrix inverse = new Matrix(rowNum(),colNum(), unitElement());
		inverse.setToIdentity();
		Echelon echelon = new Echelon(this); 
		if(echelon.pivots().size()==rowNum()) throw new IllegalArgumentException("matrix is singlar");
		for(ElementaryMatrix elementary: echelon.operations()){
			inverse.elementaryOperation(elementary);
		}
		return inverse;
	}
	
	/**
	 * Naive implementation of the permanent.
	 * @return Permanent of square matrix.
	 */
	public FieldElement permanentNaive(){
		if(!this.isSquare()) throw new IllegalArgumentException("cannot compute permanent for non-square matrix");
		int size = this.rowNum();
		FieldElement permanent = zeroElement;
		for(Permutation permutation: Permutation.allPermutations(size)){
			FieldElement f = unitElement;
			for(int i=0 ; i<size;++i){
				f = f.mult(get(i, permutation.getIndex(i)));
			}
			permanent = permanent.add(f);
		}
		return permanent;
	}
	
	
	/**
	 * Computation of the permanent of square matrix.
	 * The implementation uses Ryser formula and Gray code for speed up.
	 * The complexity is roughly O(n*2^n)
	 * 
	 * @return Permanent of square matrix.
	 */
	public FieldElement permanent(){
		if(!this.isSquare()) throw new IllegalArgumentException("cannot compute permanent for non-square matrix");
		int size = this.rowNum(),sign=(rowNum()%2==0?1:-1);
		FieldElement permanent = zeroElement , currentProduct;
		boolean[] columnMarked = new boolean[size];
		FieldElement[] rowSum = new FieldElement[size];
		for(int i=0;i<size;++i)rowSum[i] = zeroElement;
		for(int i: GrayCode.grayCode(size)){
			sign = sign*(-1);
			currentProduct = this.unitElement;
			columnMarked[i] = !columnMarked[i];
			for(int j=0;j<size;++j) rowSum[j] = (columnMarked[i]?rowSum[j].add(get(j, i)):rowSum[j].subtract(get(j, i)));
			for(int j=0;j<size;++j){
				currentProduct = currentProduct.mult(rowSum[j]);
				if(!currentProduct.nonZeroElement())break;
			}
			permanent = permanent.add((sign==1?currentProduct:currentProduct.negationElement()));
		}
		return permanent;
	}
	
	/**
	 * Solve the linear equation ax=0
	 * 
	 * @param a matrix
	 * @param b vector
	 * @return list of vectors which form a basis for the solution space
	 */
	public static LinkedList<FieldElement[]> solve(Matrix a){
		int numOfVariables = a.colNum();
		int numOfEquations = a.rowNum();
		Matrix mat = new Matrix(a);
		FieldElement zero = a.zeroElement();
		GaussElimination g = new GaussElimination(a);
		LinkedList<FieldElement[]> solutions = new LinkedList<FieldElement[]>();
		boolean[] isPivot = new boolean[numOfVariables];
		for(Integer[] pivot: g.pivots()){ // Flag variables which are pivots
			isPivot[pivot[1]] = true;
		}
		
		FieldElement[] solution;
		for(int i=0; i<numOfVariables  ; ++i){ // Loop on solutions - every non-pivot is a solution
			if(isPivot[i]) continue; // 
			solution = new FieldElement[numOfVariables];
			for(int k=0; k<solution.length  ; ++k) solution[k] = mat.zeroElement();
			solution[i] = mat.unitElement(); // this solution is 1 on i and zero on every other non-pivot
			boolean[] valueIsSet = new boolean[mat.colNum()]; 
			for(int j=numOfEquations-1;j>=0;--j){ // find solution via backward substitution
				FieldElement f = mat.zeroElement();
				for(int k=numOfVariables-1 ; k>=0; --k){
					if(isPivot[k] && !valueIsSet[k]){
						solution[k] = zero.subtract(f);
						solution[k] = solution[k].divide(g.get(j, k));
						valueIsSet[k] = true; //so that next loop we will not consider it as pivot
						break;
					}
					else{
						f = f.add(g.get(j, k).mult(solution[k]));
					}
				}
			}
			solutions.add(solution);
		}
		return solutions;
	}
	
	/**
	 * Solve the linear equation ax=b
	 * 
	 * @param a matrix
	 * @param b vector
	 * @return list of vectors which form a basis for the solution space
	 */
	public static LinkedList<FieldElement[]> solve(Matrix a , FieldElement[] b){
		if(a.rowNum()!=b.length) throw new IllegalArgumentException("vector b with size" + b.length + " does not match row size of a " + a.rowNum());
		LinkedList<FieldElement[]> solutions = new LinkedList<FieldElement[]>();

		
		int numOfVariables = a.colNum();
		int numOfEquations = a.rowNum();
		Matrix solVector = new Matrix(b.length,1,a.unitElement());
		for(int i=0 ; i<numOfEquations;++i) solVector.set(i, 0, b[i]);
		Matrix mat = new Matrix(a);

		GaussElimination g = new GaussElimination(a);
		for(ElementaryMatrix op: g.operations()){
			solVector.elementaryOperation(op);
		}
		boolean[] isPivot = new boolean[numOfVariables];
		for(Integer[] pivot: g.pivots()){ // Flag variables which are pivots
			isPivot[pivot[1]] = true;
		}
		
		FieldElement[] particularSolution = new FieldElement[numOfVariables];
		for(int k=0; k<particularSolution.length  ; ++k) particularSolution[k] = mat.zeroElement();			
		boolean[] valueIsSet = new boolean[mat.colNum()]; 
		for(int j=numOfEquations-1;j>=0;--j){ // find solution via backward substitution
			FieldElement f = mat.zeroElement();
			for(int k=numOfVariables-1 ; k>=0; --k){
				if(isPivot[k] && !valueIsSet[k]){
					particularSolution[k] = solVector.get(j,0).subtract(f);
					particularSolution[k] = particularSolution[k].divide(g.get(j, k));
					valueIsSet[k] = true; //so that next loop we will not consider it as pivot
					break;
				}
				else{
					f = f.add(g.get(j, k).mult(particularSolution[k]));
				}
			}
		}
		
		for(FieldElement[] homogeneousSolution : solve(a)){
			FieldElement[] solution = new FieldElement[numOfVariables];
			for(int i=0 ; i< solution.length ; ++i){
				solution[i] = homogeneousSolution[i].add(particularSolution[i]);
			}
			solutions.add(solution);
		}
		
		return solutions;
	}
	
	public FieldElement zeroElement() {
		return zeroElement.cloneElement();
	}

	public FieldElement unitElement() {
		return unitElement.cloneElement();
	}

	public int rank(){
		GaussElimination g = new GaussElimination(this);
		return g.pivots().size();
	}

	public boolean isFullRank(){
		return (rank()==(rowNum()<colNum()?rowNum():colNum()));
	}
	
	
	public boolean isSingular(){
		if(!isSquare()) throw new IllegalAccessError("Matrix must be a square matrix");
		return !isFullRank();
	}
	
	public static void main(String[] args){
		int mod = 23;
		int n = 3;
		int size = 4;

		ModularNumber x = new ModularNumber(0,mod);

		for(int i=0;i<n;++i){
			x.addElement(x.unitElement());
		}

		
		Matrix a = new Matrix(4,7,x.unitElement());
		a.set(2, 1, x.add(x));
		a.set(1, 0, x.mult(x));
		a.set(0, 1, x.add(x.add(x.unitElement())));
		a.set(0, 4, x.add(x.add(x.unitElement())).add(x.unitElement()));
		a.set(3, 3, x.add(x.add(x.unitElement())).add(x.unitElement()));
		a.set(3, 1, x.add(x.add(x.unitElement())));
		
		System.out.println(a.toString());
		ModularNumber[] b = new ModularNumber[4];
		b[0] = x;
		b[1] = x.mult(x.mult(x));
		b[2] = (ModularNumber) x.unitElement();
		b[3] = b[2].mult(b[2]);
		System.out.print("b: ");
		for(int i=0;i<b.length;++i){
			System.out.print(b[i] + " ");
		}
		System.out.println("");
		GaussElimination g = new GaussElimination(a);
		//System.out.println(g.detailedComputation());
		for(FieldElement[] solution :solve(a,b)){
			System.out.print("Solution: ");
			for(int i=0;i<solution.length;++i){
				System.out.print(solution[i].toString()+" ");
			}
			System.out.println(" ");
			System.out.println("Check:");
			boolean allCorrect = true;
			
			for(int i=0;i<a.rowNum();++i){
				FieldElement f = a.zeroElement();
				for(int j=0;j<a.colNum();++j){
					f = f.add(a.get(i, j).mult(solution[j]));
				}
				f = f.subtract(b[i]);
				if(f.nonZeroElement())allCorrect=false;
			}
			if(allCorrect){
				System.out.println("Ok!");
			}
			else{
				System.out.println("False!");
			}
		}
		
		/*
		Matrix h = new Matrix(size,x.unitElement());
		h.set(0, 0, x.zeroElement());
		//Should compute the permanent = size! - (size-1)! % mod
		System.out.println(h.permanent());
		System.out.println(h.permanentNaive()); // Should be equal to the permanent

		Matrix m = new Matrix(size,x.zeroElement());
		for(int i=0 ; i<size;++i){
			for(int j=i ; j<size;++j){
				m.add(i, j,x);
			}
		}

		//Should be upper triangular matrix with value n on the upper traingle and zero elsewhere
		System.out.println(m.toString());
		
		//Should be the identity
		System.out.println(m.inverse().multRight(m).toString());

		//Should be the identity
		System.out.println(m.multRight(m.inverse()).toString());

		//Should be n^size % mod
		System.out.println(m.det().toString());
		*/
		
		/*
		System.out.println(m.add(m).toString());
		System.out.println(m.add(z).toString());
		System.out.println(m.multRight(m).toString());
		m.setToIdentity();
		 */
	}
	/*
	 * 
	 * 
	 * public Matrix mult(Matrix mat){}
	 * public det double(Matrix mat){}
	*/
}
