package chapter03;

public class ArrayUtil {
	public static double[] intToDouble(int[] a) {
		double[] result = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			result[i] = a[i];
			
		}
		return result;
	}

	public static int[] doubleToInt(double[] ds) {
		int[] result = new int[ds.length];
		for (int i=0;i<ds.length;i++) {
			result[i] = (int)ds[i];
		}
		return result;
	}

	public static int[] concat(int[] is, int[] is2) {
		int[] result = new int[is.length+is2.length];
		
		for (int i=0;i<is.length;i++) {
			result[i] = is[i];
		}
		int index = 0;
		for (int i=is.length;i<is.length+is2.length;i++) {
			result[i] = is2[index];
			index++;
		}
		return result;
	}


}
