
public interface FieldElement {
	public int characteristic();
	public FieldElement cloneElement();
	public FieldElement add(FieldElement f);
	public void addElement(FieldElement f);
	public FieldElement subtract(FieldElement f);	
	public FieldElement mult(FieldElement f);
	public void multElement(FieldElement f);
	public FieldElement divide(FieldElement f);
	public FieldElement inverseElement();
	public FieldElement negationElement();
	public FieldElement zeroElement();
	public FieldElement unitElement();
	public boolean nonZeroElement();
	public String toString();
}
