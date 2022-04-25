package prob5;

public class Prob5 {

	public static void main(String[] args) {
		
		for (int i=1; i<=99;i++) {
			int a = i/10, b = i % 10;
			if ((a==3||a==6||a==9)&&(b==3||b==6||b==9)) {
				System.out.println(i+" 짝짝");
			}
			else if (a==3||a==6||a==9) {
				System.out.println(i+" 짝");
			}
			else if (b==3||b==6||b==9) {
				System.out.println(i+" 짝");
			}
		}
	}
}
