package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {
	
	public static void main(String[] args) {
		Scanner scanner = null;
		while(true) {
			try {
				scanner = new Scanner(System.in);


				System.out.print(">");
				String line = scanner.nextLine();

				if("exit".equals(line)) {
					scanner.close();
					break;
				}

				InetAddress[] inetAddresses = InetAddress.getAllByName(line);
//				System.out.println(inetAddresses.length);
				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(inetAddress.getHostName() + " : " + inetAddress.getHostAddress());
				}

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} 

			
		}
	}
}
	
