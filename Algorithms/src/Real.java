
public class Real implements FieldElement{

	public double r;
	public int characteristic() {
		return 0;
	}
	
	public Real(double x){
		this.r=x;
	}
	
	public Real add(Real x){
		return new Real(this.r+x.r);
	}
	
	public Real mult(Real x){
		return new Real(this.r*x.r);
	}
	
	public Real subtract(Real x){
		return new Real(this.r-x.r);
	}
	
	public Real divide(Real x){
		return new Real(this.r/x.r);
	}
	
	public FieldElement add(FieldElement f) {
		return (FieldElement) this.add(f);
	}

	public FieldElement subtract(FieldElement f) {
		return this.subtract(f);

	}

	public FieldElement mult(FieldElement f) {
		return this.mult(f);
	}

	public FieldElement divide(FieldElement f) {
		return this.divide(f);
	}

	public FieldElement inverseElement() {
		return this.divide(new Real(1));
	}

	public FieldElement negationElement() {
		return this.mult(new Real(-1));
	}

	public FieldElement zeroElement() {
		return new Real(0);
	}

	public FieldElement unitElement() {
		return new Real(0);
	}
	
	public String toString(){
		String s = new String();
		s+=this.r;
		return s;
	}

	@Override
	public boolean nonZeroElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FieldElement cloneElement() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addElement(FieldElement f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void multElement(FieldElement f) {
		// TODO Auto-generated method stub
		
	}

}
