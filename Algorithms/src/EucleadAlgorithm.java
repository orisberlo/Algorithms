
public class EucleadAlgorithm {
	private int n,m;
	private int gcd;
	private int a,b;
	
	public EucleadAlgorithm(int n, int m){
		if(n==0 || m==0) throw new IllegalArgumentException("Argument must be nonzero");
		this.n = n;
		this.m = m;
		this.a=0;
		this.b=1;
		eucleadAlgorithm(n,m);
	}
	
	private void eucleadAlgorithm(int n, int m){
		if(n<0){
			eucleadAlgorithm(-n, m);
			this.a=-this.a;
			return;
		}
		else if(m<0){
			eucleadAlgorithm(n, -m);
			this.b=-this.b;
			return;
		}
		else if(n<m){ 
			eucleadAlgorithm(m, n);
			int tmp = this.a;
			this.a = this.b;
			this.b = tmp;
			return;
		}
		this.gcd=m;
		int d = n%m;
		if(d==0){return;}
		this.gcd=d;
		eucleadAlgorithm(m, d);
		int tmp = this.a;
		this.a = this.b;
		this.b = tmp-this.b*(n/m);
	}
	
	public int getGcd(){
		return this.gcd;
	}
	
	public static int gcd(int n,int m){
		EucleadAlgorithm g = new EucleadAlgorithm(n, m);
		return g.getGcd();
	}

	static int lcm(int n, int m){
		EucleadAlgorithm g = new EucleadAlgorithm(n,m);
		return n*m/g.getGcd();
	}
	
	static int inverse(int n, int m){
		EucleadAlgorithm g = new EucleadAlgorithm(n,m);
		if(g.getGcd()!=1) throw new IllegalArgumentException("Cannot compute inverse if n,m are not coprime");
		return g.a%m;
	}
	
	public String toString(){
		String s = new String();
		s+= " Numbers : " + this.n + "," + this.m+"\n";
		s+=" GCD: " + this.gcd + " = (" + this.a + ")*(" + this.n + ")+ (" + this.b + ")*(" + this.m + ")\n";
		
		return s;
	}
	
	public static void main(String[] args){
		EucleadAlgorithm e = new EucleadAlgorithm(93,173);
		System.out.println(e.toString());
		System.out.println(lcm(93,173));
	}
	
}
