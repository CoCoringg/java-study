package chat;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ChatServer {
	private static final int PORT = 6666;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		List<Writer> listWriters = new ArrayList<Writer>();
		try {
			serverSocket = new ServerSocket();
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			log("연결 기다림" + hostAddress + ":" + PORT);

			while(true) {
				Socket socket = serverSocket.accept(); 
				new ChatServerThread(socket, listWriters).start();
			}
		} catch (IOException e) {
			System.out.println("[server] error:" + e);
		} finally {
			try {
				// close 상태가 아닌지 확인 후 닫기
				if(serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				System.out.println("[server] error:" + e);
			}
		}
	}
	
	public static void log(String log) {
		System.out.println("[ChatServer] " + log);
	}

}
