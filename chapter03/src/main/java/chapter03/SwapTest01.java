package chapter03;

public class SwapTest01 {
	
	public static void main(String[] args) {
		int a = 10;
		int b = 20;
		
		System.out.println(a + ":" + b);
		
		/* swap */
		int temp = b;
		b = a;
		a = temp;
		
		System.out.println(a + ":" + b);
		
	}

}
