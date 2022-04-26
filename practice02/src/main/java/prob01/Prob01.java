package prob01;

import java.util.Scanner;

public class Prob01 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner( System.in  );

		final int[] MONEYS = { 50000, 10000, 5000, 1000, 500, 100, 50, 10, 5, 1 };

		/* 코드 작성 */
		int count = 0;
		
		System.out.print("금액: ");
		int money = scanner.nextInt();
		
		if (money / MONEYS[0] > 0) {
			count = money / MONEYS[0];
			money = money - (MONEYS[0]*count);
			System.out.println("50000원: "+count+"개");
		}
		
		if (money / MONEYS[1] > 0) {
			count = money / MONEYS[1];
			money = money - (MONEYS[1]*count);
			System.out.println("10000원: "+count+"개");
		}
		
		if (money / MONEYS[2] > 0) {
			count = money / MONEYS[2];
			money = money - (MONEYS[2]*count);
			System.out.println("5000원: "+count+"개");
		}
		
		if (money / MONEYS[3] > 0) {
			count = money / MONEYS[3];
			money = money - (MONEYS[3]*count);
			System.out.println("1000원: "+count+"개");
		}
		
		if (money / MONEYS[4] > 0) {
			count = money / MONEYS[4];
			money = money - (MONEYS[4]*count);
			System.out.println("500원: "+count+"개");
		}
		
		if (money / MONEYS[5] > 0) {
			count = money / MONEYS[5];
			money = money - (MONEYS[5]*count);
			System.out.println("100원: "+count+"개");
		}
		
		if (money / MONEYS[6] > 0) {
			count = money / MONEYS[6];
			money = money - (MONEYS[6]*count);
			System.out.println("50원: "+count+"개");
		}
		
		if (money / MONEYS[7] > 0) {
			count = money / MONEYS[7];
			money = money - (MONEYS[7]*count);
			System.out.println("10원: "+count+"개");
		}
		
		if (money / MONEYS[8] > 0) {
			count = money / MONEYS[8];
			money = money - (MONEYS[8]*count);
			System.out.println("5원: "+count+"개");
		}
		
		if (money / MONEYS[9] > 0) {
			count = money / MONEYS[9];
			money = money - (MONEYS[9]*count);
			System.out.println("1원: "+count+"개");
		}
		
		scanner.close();
 	}
}