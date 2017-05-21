import java.util.LinkedList;

public class Vector {
	private FieldElement[] vector;
	
	public Vector(int n, FieldElement f){
		if(n<=0) throw new IllegalArgumentException("vector size must be positive");
		this.vector = new FieldElement[n];
		for(int i=0 ; i<n ; ++i){
			this.vector[i] = f.cloneElement();
		}
	}
	
	public Vector(FieldElement[] v){
		if(v.length<=0) throw new IllegalArgumentException("vector size must be positive");
		this.vector = new FieldElement[v.length];
		for(int i=0 ; i<v.length ; ++i){
			this.vector[i] = v[i].cloneElement();
		}
	}
	
	
	public Vector(Vector u){
		this.vector = new FieldElement[u.size()];
		for(int i=0 ; i<u.size() ; ++i){
			this.vector[i] = u.get(i).cloneElement();
		}
	}
	
	public int size(){
		return vector.length;
	}
	
	public FieldElement get(int index){
		return vector[index];
	}
	
	void set(int index,FieldElement f){
		vector[index] = f.cloneElement();
	}
	
	public FieldElement unitElement(){
		return vector[0].unitElement();
	}

	public FieldElement zeroElement(){
		return vector[0].zeroElement();
	}
	
	public static boolean isLinearlyIndependent(LinkedList<Vector> vectors){
		Matrix mat = new Matrix(vectors);
		if(vectors.size()>vectors.get(0).size()) return false;
		return mat.isFullRank();
	}
	
	public Vector add(Vector v){
		if(v.size()!=this.size()) throw new IllegalArgumentException("vectors must have the same size");
		Vector u = new Vector(v);
		for(int i=0;i<u.size();++i){
			u.set(i, v.get(i).add(this.get(i)));
		}
		return u;
	}

	public FieldElement dotProduct(Vector v){
		if(v.size()!=this.size()) throw new IllegalArgumentException("vectors must have the same size");
		FieldElement f = v.zeroElement();
		for(int i=0;i<v.size();++i){
			f = f.add(v.get(i).mult(this.get(i)));
		}
		return f;
	}
	
	public Vector negation(){
		Vector u = new Vector(this);
		for(int i=0;i<u.size();++i){
			u.set(i, u.get(i).negationElement());
		}
		return u;
	}
	
	public Vector subtract(Vector v){
		if(v.size()!=this.size()) throw new IllegalArgumentException("vectors must have the same size");
		return this.add(v.negation());
	}
	
	
	public String toString(){
		String s = new String();
		s+="( ";
		for(int i=0;i<size()-1;++i){
			s+=get(i).toString()+" , ";
		}
		s+=get(size()-1).toString()+" )";
		return s;
	}
	
	public static void main(String[] args){
		FieldElement[] u0 = new FieldElement[4];
		FieldElement[] u1 = new FieldElement[4];
		FieldElement[] u2 = new FieldElement[4];
		
		ModularNumber x0 = new ModularNumber(0,5);
		ModularNumber x1 = new ModularNumber(1,5);
		ModularNumber x2 = new ModularNumber(2,5);
		ModularNumber x3 = new ModularNumber(3,5);
		ModularNumber x4 = new ModularNumber(4,5);
		
		for(int i=0 ; i<4;++i){
			u0[i] = x0.zeroElement();
			u1[i] = x0.zeroElement();
			u2[i] = x0.zeroElement();
		}
		
		u0[0] = x1.cloneElement();
		u0[1] = x2.cloneElement();
		u1[0] = x4.cloneElement();
		u1[2] = x3.cloneElement();
		u1[1] = x2.cloneElement();
		u2[0] = x1.cloneElement();
		u2[1] = x0.cloneElement();
		u2[2] = x4.cloneElement();
		
		Vector v0 = new Vector(u0);
		Vector v1 = new Vector(u1);
		Vector v2 = new Vector(u2);
		Vector v3 = new Vector(u2);
		
		System.out.println(v0.toString());
		System.out.println(v1.toString());
		System.out.println(v2.toString());
		
		LinkedList<Vector> list = new LinkedList<Vector>();
		list.add(v0);
		list.add(v1);
		list.add(v2);
		
		System.out.println(isLinearlyIndependent(list));
		
		list.add(v3);
		System.out.println(isLinearlyIndependent(list));
		list.removeLast();
		v3.set(3, x2);
		list.add(v3);
		System.out.println(isLinearlyIndependent(list));
		list.removeLast();
		v3 = v2.add(v1).subtract(v0);
		list.add(v3);
		System.out.println(isLinearlyIndependent(list));
	}
}
