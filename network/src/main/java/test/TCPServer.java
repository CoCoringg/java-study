package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		try {
			// 1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			// 2. 바인딩(binding)
			// 	Socket에 InetSocketAddress(IP Address + Port)를 바인딩
			//	IPAddress(0.0.0.0) : 특정 호스트 IP를 바인딩 하지 않는다.
			//	backlog : 요정 queue(10) 
			serverSocket.bind(new InetSocketAddress("0.0.0.0", 5555), 10);
			
			// 3. accept
			// 클라이언트로 부터 요청을 기다린다.
			Socket socket = serverSocket.accept(); // blocking
			
			InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress(); //soket address return
			String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress(); // inetaddress return 
			int remoteHostPort = inetSocketAddress.getPort();
			
			System.out.println("[server] connected by client[" + remoteHostAddress +":" +remoteHostPort+ "]");
			
			try {
				// 4. IO Stream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				
				while(true) {
					// 5. 데이터 읽기 
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); // blocking
					if(readByteCount == -1) {
						// 클라이언트가 정상적으로 종료, close() 호출
						System.out.println("[server] closed by client"); 
						break;
					}
					
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("[server] received:"+ data);
					
					// 6. 데이터 쓰기 
					os.write(data.getBytes("utf-8"));
				}
				
			} catch(SocketException ex) {
				System.out.println("[server] suddenly closed by client");
			}
			  catch (IOException ex) {
				  System.out.println("[server] error:" + ex);
			} finally {
				try {
					if (socket != null && !socket.isClosed()) {
						socket.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
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

}
