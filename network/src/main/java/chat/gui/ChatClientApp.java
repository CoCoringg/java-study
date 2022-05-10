package chat.gui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import chat.ChatServer;

public class ChatClientApp {
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 6666;

	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String name = null;
		Socket socket = null;
		Scanner scanner = null;

		try {
			scanner = new Scanner(System.in);
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			ChatServer.log("connected...");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			while( true ) {

				System.out.println("대화명을 입력하세요.");
				System.out.print(">>> ");
				name = scanner.nextLine();

				if (name.isEmpty() == false ) {
//					break;
					printWriter.println( "join:" + name );
					String data = br.readLine();
					
					if ("join:ok".equals(data)) {
						System.out.println("입장하였습니다. 즐거운 채팅 되세요");
						break;
					}
					
				} 
					System.out.println("대화명은 한 글자 이상 입력해야 합니다.\n");
			}
			
			new ChatWindow(name, socket).show();

//			scanner.close();

			// 1. create socket 
			// 2. connect server
			// 3. get iostream(pipline established)
			// 4. join protocol 처리 --> "join:둘리\n" pw.println("join:둘리");
			// join:ok <-- String line = br.readline(); line => join:ok
			// if ("join:ok".equals(line){}
			// 서버 : 들어와있는 사람들 구현 writers => chatusers 
			
			
		} catch( IOException ex ) {
			ChatServer.log( "error:" + ex );
		}
	}

}

		


