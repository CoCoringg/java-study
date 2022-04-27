package chapter03;

import mypackage.Goods2;

public class EventGoods2 extends Goods2 {
	private float discountRate = 0.5f;
	
	public int getDiscountPrice() {
		// protected는 자식에서 접근이 가능하다.
		int discountPrice = (int)(discountRate * price);	// int 명시적으로 써줌 아니면 에러남
		// int 와 float 계산하면 => float로 나옴
		// 값이 짤릴 수 있기 때문에 에러 => 데이터가 변할 수 있기 때문
		
		return discountPrice;	
	}
}
