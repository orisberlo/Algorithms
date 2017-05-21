
public class ModularNumber implements FieldElement{
	int n;
	int characteristic;
	
	ModularNumber(int n, int p){
		//should check if p is prime
		this.characteristic=p;
		if(n<0){
			n+= p*(-n/p+1);
			n = n%p;
		}
		this.n= (n);
	}
	
	public int characteristic() {
		return characteristic;
	}

	public ModularNumber add(ModularNumber f) {
		if(this.characteristic!=f.characteristic) throw new IllegalArgumentException("Can't add two numbers with different characteristic");
		return new ModularNumber((this.n+f.n)%this.characteristic,this.characteristic);
	}

	public ModularNumber subtract(ModularNumber f) {
		if(this.characteristic!=f.characteristic) throw new IllegalArgumentException("Can't subtract two numbers with different characteristic");
		return new ModularNumber((this.n-f.n)%this.characteristic,this.characteristic);
	}

	public ModularNumber mult(ModularNumber f) {
		if(this.characteristic!=f.characteristic) throw new IllegalArgumentException("Can't multiply two numbers with different characteristic");
		return new ModularNumber((this.n*f.n)%this.characteristic,this.characteristic);	
		}


	public ModularNumber divide(ModularNumber f) {
		if(this.characteristic!=f.characteristic) throw new IllegalArgumentException("Can't divide two numbers with different characteristic");
		return f.inverse().mult(this);
	}

	public ModularNumber inverse() {
		int p = this.characteristic;
		return new ModularNumber((EucleadAlgorithm.inverse(this.n,p)+p)%p,p);
	}


	public ModularNumber negation() {
		int p =this.characteristic;
		return new ModularNumber((-this.n+p)%p,p);
	}



	public FieldElement subtract(FieldElement f) {
		return this.subtract((ModularNumber) f);
	}

	public FieldElement mult(FieldElement f) {
		return this.mult((ModularNumber) f);
	}


	public FieldElement divide(FieldElement f) {
		return this.divide((ModularNumber) f);
	}

	public FieldElement inverseElement() {
		return this.inverse();
	}


	public FieldElement negationElement() {
		return this.negation();
	}


	public FieldElement zeroElement() {
		return new ModularNumber(0,this.characteristic);
	}

	public FieldElement unitElement() {
		return new ModularNumber(1,this.characteristic);
	}

	public String toString(){
		String s = new String();
		s+=this.n;
		return s;
	}

	public boolean nonZero() {
		return this.n!=0;
	}
	
	public boolean nonZeroElement() {
		ModularNumber a = this;
		return a.nonZero();
	}
	
	public ModularNumber clone() {
		return new ModularNumber(this.n,this.characteristic());
	}

	
	public FieldElement cloneElement() {
		return this.clone();
	}

	public void addElement(ModularNumber f) {
		if(this.characteristic!= f.characteristic) throw new IllegalArgumentException("Can't add two numbers with different characteristic");
		this.n = (this.n+f.n)%this.characteristic; 
	}
	
	public void addElement(FieldElement f) {
		this.addElement((ModularNumber) f);
	}
	

	public void multElement(ModularNumber f) {
		if(this.characteristic!= f.characteristic) throw new IllegalArgumentException("Can't add two numbers with different characteristic");
		this.n = (this.n*f.n)%this.characteristic; 
	}
	
	public void multElement(FieldElement f) {
		this.multElement((ModularNumber) f);
	}
	
	public FieldElement add(FieldElement f) {
		return this.add((ModularNumber) f);
	}
}
