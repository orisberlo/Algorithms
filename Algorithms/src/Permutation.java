import java.security.AllPermission;
import java.util.Iterator;
import java.util.LinkedList;


public class Permutation {
	private int[] permutation;

	public Permutation(int[] permutation){
		if(!isPermutation(permutation)) throw new IllegalArgumentException("The input array is no a permutation.");
		this.permutation = new int[permutation.length];
		System.arraycopy(permutation, 0, this.permutation, 0, permutation.length);
	}

	public static boolean isPermutation(int [] permutation){
		boolean[] permutationCheck = new boolean[permutation.length];
		for(int i=0 ; i<permutation.length;++i){
			if(permutationCheck[permutation[i]]){
				return false;
			}
		}
		return true;
	}
	
	public Permutation inverse(){
		int[] inverse = new int[this.permutation.length];
		for(int i=0 ; i<permutation.length;++i){
			this.permutation[i] = permutation[i];
			inverse[permutation[i]] = i;
		}
		return new Permutation(inverse);
	}
	
	private LinkedList<LinkedList<Integer>> cyclicStructure(){
		LinkedList<LinkedList<Integer>> cycleStructure = new LinkedList<LinkedList<Integer>>();
		boolean[] marked = new boolean[permutation.length];
		int cyclePosition = 0 , current=0 , count=0;
		while(count<permutation.length){
			if(!marked[cyclePosition]){
				current=cyclePosition;
				LinkedList<Integer> cycle = new LinkedList<Integer>();
				do{
					cycle.add(current);
					current = permutation[current];
					marked[current] = true;
					count++;
				}
				while(current != cycle.getFirst());
				cycleStructure.add(cycle);
			}
			cyclePosition++;
		}
		return cycleStructure;
	}
	
	public int[] getPermutation(){
		return this.permutation;
	}

	
	public String toString(){
		String s = new String();
		s += "Permutation : ["+this.permutation[0];
		for(int i=1;i<permutation.length;++i){
			s+=","+permutation[i];
		}
		s += "]\n";
		return s;
	}
	
	
	private int countInversionNaive(){
		int invCount = 0;
		for (int i = 0; i < permutation.length - 1; ++i){
			for (int j = i+1; j < permutation.length; j++){
				if (permutation[i] > permutation[j]){
					invCount++;
				}
		    }
		}
		  return invCount;
	}
	
	public int countInversion(){
		int[] arr = new int [permutation.length];
		System.arraycopy(permutation, 0, arr, 0, arr.length);
		return countInversion(arr,0,arr.length-1);
	}
	
	public int[] permutationFromCycleStrcture(LinkedList<LinkedList<Integer>> cycleStructure){
		int size=0;
		for(LinkedList<Integer> cycle: cycleStructure){
			size+=cycle.size();
		}
		
		int[] permutation = new int[size];
		for(LinkedList<Integer> cycle: cycleStructure){
			int prev = cycle.getLast();
			for(int i : cycle){
				permutation[i] = prev;
				prev=i;
			}
		}
		return permutation;
	}
	
	public int[] permutationFromCycle(LinkedList<Integer> cycle , int length){
		int[] permutation = new int[length];
		for(int i=0 ; i<length ; ++i){
			permutation[i]=i;
		}
		
		int prev = cycle.getLast();
		for(int i : cycle){
			permutation[i] = prev;
			prev=i;
		}
		return permutation;
	}
	
	
	
	private int countInversion(int[] arr, int startPos, int endPos){
		if((endPos-startPos)==0){
			return 0;
		}
		int mid = (endPos+startPos)/2;
		int inv1 = countInversion(arr, startPos, mid);
		int inv2 = countInversion(arr, mid+1, endPos);
		int[] mergeArr = new int[endPos-startPos+1];
		int i=0,j=0,k=0 , delta=0;
		while(i<= (mid-startPos) && j<=(endPos-mid-1)){
			if(arr[mid+1+j]>arr[startPos+i]){
				mergeArr[k] = arr[startPos+i];
				i++;
				delta+=j;
			}
			else if(arr[mid+1+j]<arr[startPos+i]){
				mergeArr[k] = arr[mid+1+j];
				j++;
			}
			k++;
		}
		delta+= (mid-startPos+1-i)*j;
		System.arraycopy(arr, (i>(mid-startPos)?mid+1+j:startPos+i) , mergeArr, k, (endPos-startPos+1-k));
		System.arraycopy(mergeArr, 0 , arr, startPos, mergeArr.length);

		return (inv1+inv2+delta);
	}
	
	public int size(){
		return permutation.length;
	}
	
	public Permutation composeRight(Permutation p){
		if(p.size()!=this.size()) throw new IllegalArgumentException("Size of permutations must match");
		int[] composition = new int[p.size()];
		for(int i=0;i<p.size();++i){
			composition[i] = p.getIndex(this.getIndex(i));
		}
		return new Permutation(composition);
	}
	
	public int getIndex(int index){
		return permutation[index];
	}
	
	
	public int sign(){
		int sum = 0;
		for(LinkedList<Integer> cycle: this.cycleStructure()){
			sum+=cycle.size()+1;
		}
		return sum%2;
	}
	
	public Permutation composeLeft(Permutation p){
		if(p.size()!=this.size()) throw new IllegalArgumentException("Size of permutations must match");
		int[] composition = new int[p.size()];
		for(int i=0;i<p.size();++i){
			composition[i] = this.getIndex(p.getIndex(i));
		}
		return new Permutation(composition);
	}
	
	public Permutation conjugate(Permutation p){
		Permutation conjugate = p.composeRight(this.composeRight(p.inverse()));
		return conjugate;
	}
	
	public int order(){
		int ord = 1;
		for(LinkedList<Integer> cycle:this.cycleStructure()){
			ord = EucleadAlgorithm.lcm(ord, cycle.size());
		}
		return ord;
	}
	
	public Iterable<LinkedList<Integer>> cycleStructure(){
		return this.cyclicStructure();
	}
	
	public boolean isConjugate(Permutation p){
		if(p.size()!=this.size()) throw new IllegalArgumentException("Size of permutations must match");
		int[] cycleCount = new int[p.size()];
		for(LinkedList<Integer> cycle : this.cycleStructure()){cycleCount[cycle.size()]++;}
		for(LinkedList<Integer> cycle : p.cycleStructure()){cycleCount[cycle.size()]--;}
		for(int i=0;i<cycleCount.length;++i){
			if(cycleCount[i]!=0)return false;
		}
		return true;
	}
	
	
	private static LinkedList<Permutation> SJTAlgorithm(int size){
		LinkedList<Permutation> allPermutations = new LinkedList<Permutation>();
		int[] currentPermutation = new int[size];
		int[] direction = new int[size];
		int largestMobile , chosenElement , currentDirection;
		for(int i=0 ; i<size ; ++i){
			currentPermutation[i]=i;
			direction[i] = -1;
		}
		direction[0] = 0;
		while(true){
			allPermutations.add(new Permutation(currentPermutation));
			largestMobile=-1;
			for(int i=0;i<size;++i){
				if(direction[i]!=0){
					if(largestMobile<0){
						largestMobile=i;
					}
					else{
						largestMobile = (currentPermutation[largestMobile]<currentPermutation[i]?i:largestMobile);
					}
				}
			}
			if(largestMobile<0)break;
			//swap largest mobile
			currentDirection=direction[largestMobile];
			chosenElement=largestMobile+currentDirection;
			swap(currentPermutation,largestMobile,chosenElement);
			swap(direction,largestMobile,chosenElement);


			if(chosenElement==0 || chosenElement==size-1){
				direction[chosenElement] = 0;
			}
			else if(currentPermutation[chosenElement]<currentPermutation[chosenElement+currentDirection]){
				direction[chosenElement]=0;
			}
			for(int i=0;i<size;++i){
				if(currentPermutation[chosenElement]<currentPermutation[i]){
					if(i<chosenElement)direction[i] = 1;
					if(i>chosenElement)direction[i] = -1;
				}
			}
		}
		return allPermutations;
	}
	
	private static void swap(int arr[],int i , int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	public static Iterable<Permutation> allPermutations(int size){
		return SJTAlgorithm(size);
	}
	
	public static void main(String[] args){
		for(Permutation p:allPermutations(4)){
			System.out.println(p.toString());
		}
		
		int[] perm1 = {2,9,4,1,0,10,8,6,7,3,5};		
		int[] perm2 = {5,6,1,9,0,10,8,4,7,3,2};
		int[] perm3 = {1,2,3,4,5,6,7,8,0,10,9};
		int[] perm4 = {6,5,0,1,2,3,4};
		int[] perm5 = {1,2,3,0};
		int[] perm6 = {1,2,3,4,0};

		
		Permutation p = new Permutation(perm1);
		Permutation h = new Permutation(perm2);
		Permutation z = new Permutation(perm3);
		Permutation x = new Permutation(perm4);
		Permutation y = new Permutation(perm5);
		Permutation w = new Permutation(perm6);


		
		System.out.println(p.toString());
		System.out.println(y.toString());

		System.out.println(h.toString());
		System.out.println(z.toString());
		System.out.println(x.toString());

		System.out.println(p.inverse().toString());
		System.out.println(p.countInversion());
		System.out.println(p.countInversionNaive());
		System.out.println(p.sign());
		System.out.println(y.countInversion());
		System.out.println(h.sign());
		System.out.println(h.countInversion());

		System.out.println(z.sign());
		System.out.println(z.countInversion());
		
		System.out.println(x.sign());
		System.out.println(x.countInversion());

		System.out.println(w.sign());
		System.out.println(w.countInversion());
		

		System.out.println(p.conjugate(p).toString());
		System.out.println(p.isConjugate(p.conjugate(h)));
		System.out.println(h.isConjugate(z));
		System.out.println(p.order());
		System.out.println(h.order());
		System.out.println(z.order());
		System.out.println(x.order());


	}
}
