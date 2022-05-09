package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ChatClientThread extends Thread {
	private Socket socket;
	private BufferedReader bufferedReader;
	
	public ChatClientThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		/* reader를 통해 읽은 데이터 콘솔에 출력하기 (message 처리) */
		try {
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			while (true) {
				String data = bufferedReader.readLine();
				System.out.println(data);
				
			}
		} catch (SocketException ex) {
			System.out.println("채팅을 종료하였습니다.");
		}
		catch (IOException ex) {
			ChatServer.log( "error:" + ex );
		} 
	}


}
