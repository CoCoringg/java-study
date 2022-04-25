package prob2;

public class Prob2 {
	public static void main(String[] args) {
		/* 코드 작성 */
		int num = 1;
		int count = 0;
		for (int i=0; i<9; i++) {
			for(int j=1; j<=10; j++) {
				System.out.print(num+" ");
				num++;
			}
			System.out.println();
			count++;
			num=1;
			num+=count;
		}
	}
}
