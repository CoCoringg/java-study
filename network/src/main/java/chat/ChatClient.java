package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static final String SERVER_IP = "192.168.56.1";
	private static final int SERVER_PORT = 6666;
	
	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;
		
		try {
			//1. 키보드 연결
			scanner = new Scanner(System.in);
			//2. socket 생성
			socket = new Socket();
			//3. 연결
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			ChatServer.log("connected...");
			//4. reader/writer 생성
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			//5. join 프로토콜
			System.out.print("닉네임>>" );
			String nickname = scanner.nextLine();
			printWriter.println( "join:" + nickname );
			String data = br.readLine();
			
			if ("join:ok".equals(data)) {
				System.out.println("입장하였습니다. 즐거운 채팅 되세요");
			} 
			
			//6. ChatClientReceiveThread 시작
			new ChatClientThread(socket).start();

			//7. 키보드 입력 처리
			while( true ) {
//				System.out.print( ">>" );
				String input = scanner.nextLine();

				if( "quit".equals( input ) == true ) {
					// 8. quit 프로토콜 처리
					printWriter.println("quit");
					break;
				} else {
					// 9. 메시지 처리
					printWriter.println("message:" + input);
				}
			}

		} catch( IOException ex ) {
			ChatServer.log( "error:" + ex );
		} finally {
			//10. 자원정리
			try {
				if (scanner != null) {
					scanner.close();
				}
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException ex) {
				ChatServer.log( "error:" + ex );
			}
		} 

	}

}
