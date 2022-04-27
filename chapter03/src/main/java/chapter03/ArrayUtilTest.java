package chapter03;

import java.util.Arrays;

public class ArrayUtilTest {

	public static void main(String[] args) {
		double[] d1 = ArrayUtil.intToDouble(new int[] {10, 20, 30, 40});
		
//		for( double d: d1) {
//			System.out.print(d+" ");
//		}
		
		System.out.println(Arrays.toString(d1));
		
		double[] a1 = ArrayUtil.doubleToInt(new double[] {10.1, 20.2, 30.3, 40.4});
		double[] a2 = ArrayUtil.concat(new int[] {1,2,3}, new int[] {4,5,6});

	}

}
