package prob05;

import java.util.Random;
import java.util.Scanner;

public class Prob05 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner( System.in );

		while( true ) {
			
			/* 게임 작성 */

			// 정답 램덤하게 만들기
			Random random = new Random();
			int correctNumber = random.nextInt( 100 ) + 1;
			System.out.println("수를 결정하였습니다. 맞추어보세요");
			// 정답 맞추기
			
			int count = 1;
			int start = 1;
			int end = 100;
			boolean correct = true;
			
			while (correct) {
				System.out.println(start+"-"+end);
				System.out.print(count+">>");
				int num = scanner.nextInt();
				
				if (num == correctNumber) {
					System.out.println("맞았습니다.");
					correct = false;
				}
				else if (num > correctNumber) {
					System.out.println("더낮게");
					end = num;
					
				}
				else if (num < correctNumber) {
					System.out.println("더높게");
					start = num;
				}
				count++;
			}

			
			//새 게임 여부 확인하기
			System.out.print( "다시 하겠습니까(y/n)>>" );
			String answer = scanner.next();
			if( "y".equals( answer ) == false ) {
				break;
			}
		}
		
		scanner.close();
	}

}