package paint;

public class Main {

	public static void main(String[] args) {
		Point point1 = new Point();
		point1.setX(20);
		point1.setY(10);
		
//		point1.show();
		drawPoint(point1);
		
		Point point2 = new Point(50,100);
//		point2.show();
		drawPoint(point2);
//		drawPoint(new Point(50,100)); 도 가능
		
	}
	
	public static void drawPoint(Point point) {
		point.show();
	}

}
