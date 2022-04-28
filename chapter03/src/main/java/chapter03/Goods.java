package chapter03;

public class Goods {
	public static int countOfGoods;
	private String name; 
	private int price;
	private int countStock;
	private int countSold;
	
	// 생성자 1개라도 없으면 컴파일러가 기본 생성자 넣어줌
	// but 1개라도 있으면 기본 생성자 만들어야 함
	public Goods() {
		this(null, 0, 0, 0);
	}
	
	public Goods(String name, int price, int countStock, int countSold) {
		countOfGoods++;
		
		this.name = name;
		this.price = price;
		this.countSold = countSold;
		this.countStock = countStock;
	}
	
	public Goods(String name) {
//		countOfGoods++;
//		this.name = name;
		this(name, 0,0,0);
	}
	
	
	// private 로 정보은닉 => getter, setter 만들어서 씀 
	// source에 Generate Getters and Setters.. 로 한번에 만들 수 있음
	public int getCountSold() {
		return countSold;
	}

	public void setCountSold(int countSold) {
		this.countSold = countSold;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName( ) {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		// 데이터 보호
		if (price < 0) {
			price = 0;
		}
		this.price = price;
	}

	public int getCountStock() {
		return countStock;
	}

	public void setCountStock(int countStock) {
		this.countStock = countStock;
	}
	
	public void showwInfo() {
		System.out.println(
				"name:"+name+
				", price:" + price +
				", countStock:" + countStock +
				", countSold:" + countSold
				);
	}

	public int calcDiscountPrice(double discountRate) {
		return (int)(discountRate * price);
	}
	
	
	
	
}
