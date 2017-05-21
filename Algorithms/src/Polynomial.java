
public class Polynomial {
	private FieldElement[] polynomial;
	public FieldElement zeroElement;
	
	public FieldElement zeroElement(){
		return zeroElement.zeroElement();
	}
	
	public FieldElement unitElement(){
		return zeroElement.unitElement();
	}
	
	public Polynomial(FieldElement zeroElement){
		this.polynomial = new FieldElement[0];
		this.zeroElement = zeroElement;
	}
	
	public Polynomial(FieldElement[] p,FieldElement zeroElement){
		int degree = p.length-1;
		while(!p[degree].nonZeroElement()){
			degree--;
			if(degree<0)break;
		}
		this.polynomial = new FieldElement[degree+1];
		for(int i=0 ; i<=degree;++i){
			this.polynomial[i] = p[i].cloneElement();
		}
		this.polynomial = new FieldElement[degree+1];
		for(int i=0 ; i<=degree;++i){
			this.polynomial[i] = p[i].cloneElement();
		}
		this.zeroElement = zeroElement.zeroElement();
	}
	
	public Polynomial(Polynomial p){
		this(p.polynomial,p.zeroElement);
	}
	
	public int degree(){
		return this.polynomial.length-1;
	}
	
	FieldElement leading(){
		return this.polynomial[this.polynomial.length-1].cloneElement();
	}
	
	public boolean zeroPolynomial(){
		return degree()<0;
	}
	
	FieldElement get(int index){
		if(index<0) throw new IllegalArgumentException("index must be non negative");
		if(index>degree()) return zeroElement.zeroElement();
		return this.polynomial[index];
	}
	
	private static void validateSameField(FieldElement f1 , FieldElement f2){
		if(f1.getClass()!=f2.getClass()) throw new IllegalArgumentException("polynomials not over the same field");
		try{
			f1.add(f2);
		}
		catch(Exception e){
			throw new IllegalArgumentException("polynomials not over the same field");
		}
	}
	
	
	public static Polynomial add(Polynomial p1,Polynomial p2){
		Polynomial q1,q2;
		if(p1.degree()>p2.degree()){
			q1 = new Polynomial(p1);
			q2 = p2;
		}
		else {
			q1 = new Polynomial(p2);
			q2 = p1;
		}
		for(int i=0 ; i<= q1.degree();++i){
			q1.add(i, q2.get(i));
		}
		return q1;
	}
	
	public void mult(Polynomial p){
		validateSameField(p.zeroElement,zeroElement());
		if(p.zeroPolynomial())return;
		int newDegree = this.degree()+p.degree();
		for(int i=0 ; i<=newDegree;++i){
			FieldElement f = zeroElement.cloneElement();
			for(int j=0 ; j<newDegree;++j){
				f = f.add(this.get(j).add(p.get(newDegree-j)));
			}
		}
	}
	
	public static Polynomial mult(Polynomial p1,Polynomial p2){
		validateSameField(p1.zeroElement,p2.zeroElement);
		if(p1.zeroPolynomial() || p2.zeroPolynomial()) return new Polynomial(p1.zeroElement);
		int newDegree = p1.degree()+p2.degree();
		FieldElement[] p = new FieldElement[newDegree+1];
		for(int i=0 ; i<=newDegree;++i){
			FieldElement f = p1.zeroElement.cloneElement();
			p[i] = f.zeroElement();
			for(int j=0 ; j<=i;++j){
				f.addElement(p1.get(j).mult(p2.get(i-j)));
			}
			p[i].addElement(f);
		}
		return new Polynomial(p,p1.zeroElement);
	}
	
	public void negation(){
		if(this.zeroPolynomial())return;
		for(int i=0 ; i<=degree();++i){
			this.polynomial[i] = this.get(i).negationElement();
		}
	}
	
	public static Polynomial negation(Polynomial p1){
		Polynomial p = new Polynomial(p1);
		p.negation();
		return p;
	}
	
	public static Polynomial subtract(Polynomial p1,Polynomial p2){
		return add(p1,negation(p2));
	}
	
	
	public static Polynomial monomial(int d, FieldElement f){
		FieldElement[] p = new FieldElement[d+1];
		for(int i=0;i<d;++i){
			p[i]=f.zeroElement();
		}
		p[d+1]=f.cloneElement();
		return new Polynomial(p,f.zeroElement());
	}
	
	public Polynomial monomial(int d){
		FieldElement[] p = new FieldElement[d+1];
		for(int i=0;i<d;++i){
			p[i]=zeroElement();
		}
		p[d+1] = unitElement();
		return new Polynomial(p,zeroElement());
	}
	
	private void set(int d, FieldElement f){
		validateSameField(f,zeroElement());
		if(d>degree()) throw new IllegalArgumentException("the degree of the polynomial is less than d");
		this.polynomial[d+1] = f.cloneElement();
	}
	
	private void add(int d, FieldElement f){
		validateSameField(f,zeroElement());
		if(d>degree()) throw new IllegalArgumentException("the degree of the polynomial is less than d");
		this.polynomial[d] = this.polynomial[d].add(f);
	}
	
	public void mult(int d, FieldElement f){
		validateSameField(f,zeroElement());
		this.polynomial[d].mult(f);
	}
	
	public Polynomial mult(FieldElement f){
		Polynomial p = new Polynomial(this);
		for(int i=0;i<this.polynomial.length;++i){
			p.polynomial[i].multElement(f);
		}
		return p;
	}
	
	public void add(FieldElement f){
		validateSameField(f,zeroElement());	
	}
	
	
	public String toString(){
		String s = new String();
		for(int i=0;i<=degree();++i){
			if(!this.get(i).nonZeroElement()) continue;
			s+=this.get(i).toString();
			if(i!=0) s+= "*X^"+i;
			if(i<degree())s+="+";
		}
		s+="\n";
		return s;
	}
	
	public Polynomial[] division(Polynomial q){
		int degree = this.degree()-q.degree();
		Polynomial[] result = new Polynomial[2];
		FieldElement[] current = new FieldElement[this.polynomial.length];
		for(int i=0;i<current.length;++i){
			current[i] = this.polynomial[i].cloneElement();
		}
		
		if(degree<0){
			result[0] = new Polynomial(zeroElement());
			result[1] = new Polynomial(current,zeroElement());
			return result;
		}
		

		FieldElement[] res = new FieldElement[degree+1];
		FieldElement leadingInverse = q.leading().inverseElement();

		
		for(int i=degree;i>=0;--i){
			if(!current[i+q.degree()].nonZeroElement())continue;
			FieldElement f = current[i+q.degree()].mult(leadingInverse);
			if(!f.nonZeroElement()){
				res[i] = zeroElement();
				continue;
			}
			
			for(int j=0 ; j<=q.degree() ; j++){
				current[i+j] = current[i+j].subtract(f.mult(q.get(j)));
			}
			res[i] = f;
		}
		
		result[0] = new Polynomial(res,zeroElement());
		result[1] = new Polynomial(current,zeroElement());
		return result;
	}
	
	public static void main(String[] args){
		ModularNumber x0 = new ModularNumber(0,17);
		ModularNumber x1 = new ModularNumber(1,17);
		ModularNumber x2 = new ModularNumber(2,17);
		ModularNumber x3 = new ModularNumber(3,17);
		ModularNumber x4 = new ModularNumber(4,17);
		ModularNumber y0 = new ModularNumber(0,7);
		
		FieldElement[] arr1 = {x4,x2,x1,x1,x4,x1};
		FieldElement[] arr2 = {x1,x2,x3};
		
		Polynomial p = new Polynomial(arr1,x0);
		Polynomial q = new Polynomial(arr2,x0);
		System.out.print(p.toString());
		System.out.print(q.toString());
		
		Polynomial[] res = p.division(q);
		Polynomial s = res[0];
		Polynomial reminder = res[1];
		System.out.print(s.toString());
		System.out.print(reminder.toString());
		System.out.print(add(mult(s,q),reminder).toString());
		
	}
}
