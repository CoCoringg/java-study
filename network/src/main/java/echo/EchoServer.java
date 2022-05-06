package echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	private static final int PORT = 6666;
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket();
			
			serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT), 10);
			log("starts... [port:" + PORT + "]");
			
			while(true) {
				Socket socket = serverSocket.accept(); 
				new EchoServerReceiveThread(socket).start();
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
				e.printStackTrace();
			}
		}
	}
	
	public static void log(String log) {
		System.out.println("[EchoServer] " + log);
	}
}
