package prob3;

import java.util.Scanner;

public class Prob3 {
	
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		/* 코드 작성 */
		System.out.print("숫자를 입력하세요: ");
		int number = scanner.nextInt();
		int sum = 0;
		if (number % 2 == 1) {
			for (int i=1; i<=number; i++) {
				if (i % 2 == 1) {
					sum += i;
				}
			}
			System.out.println("결과 값: "+sum);
		}
		else {
			for (int i=1; i<=number; i++) {
				if(i % 2 == 0) {
					sum += i;
				}
			}
			System.out.println("결과 값: "+sum);
		}
		scanner.close();
	}
}
