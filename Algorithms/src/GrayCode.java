import java.util.LinkedList;

public class GrayCode {
	/*
	 * This function converts an unsigned binary
	 * number to reflected binary Gray code.
	 *
	 * The operator >> is shift right. The operator ^ is exclusive or.
	 */
	public static int binaryToGray(int num)
	{
	    return num ^ (num >> 1);
	}

	/*
	 * This function converts a reflected binary
	 * Gray code number to a binary number.
	 * Each Gray code bit is exclusive-ored with all
	 * more significant bits.
	 */
	public static int grayToBinary(int num)
	{
	    int mask;
	    for (mask = num >> 1; mask != 0; mask = mask >> 1)
	    {
	        num = num ^ mask;
	    }
	    return num;
	}

	/*
	 * A more efficient version, for Gray codes of 32 or fewer bits.
	 */
	static int grayToBinary32(int num)
	{
	    num = num ^ (num >> 16);
	    num = num ^ (num >> 8);
	    num = num ^ (num >> 4);
	    num = num ^ (num >> 2);
	    num = num ^ (num >> 1);
	    return num;
	}
	
	public static Iterable<Integer> grayCode(int n){
		LinkedList<Integer> list = new LinkedList<Integer>();
		LinkedList<Integer> auxList = new LinkedList<Integer>();
		if(n==1){
			list.add(0);
			return list;
		}
		for(Integer i : grayCode(n-1)){
			list.add(i);
			auxList.add(i);
		}
		list.add(n-1);
		for(Integer i : auxList){
			list.add(i);
		}
		return list;
	}
	
	public static void main(String[] args){
		//System.out.println(Integer.toBinaryString(binaryToGray(5)));
		for(Integer i : grayCode(4)){
			System.out.println(i);
		}
	}
}
