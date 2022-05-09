package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;


public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	private List<Writer> listWriters;

	
	public ChatServerThread( Socket socket, List<Writer> listWriters  ) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		//1. Remote Host Information
		InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress(); //soket address return
		String remoteHostAddress = inetSocketAddress.getAddress().getHostAddress(); // inetaddress return 
		int remoteHostPort = inetSocketAddress.getPort();
		
		ChatServer.log("connected by client["+ remoteHostAddress+":"+ remoteHostPort + "]");
		
		try {
		//2. 스트림 얻기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			//3. 요청 처리 			
			while( true ) {
				String request = br.readLine();
				if( request == null ) {
					ChatServer.log( "클라이언트로 부터 연결 끊김" );
					doQuit(printWriter);
					break;
				}

				// 4. 프로토콜 분석
				String[] tokens = request.split(":");
				if ("join".equals(tokens[0])) {
					doJoin(tokens[1], printWriter);
				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				} else if ("quit".equals(tokens[0])) {
					doQuit(printWriter);
				} else {
					ChatServer.log("error: 알 수 없는 요청(" + tokens[0] + ")");
				}

			}
		} catch(SocketException ex) {
			System.out.println("[server] suddenly closed by client");
		} catch (IOException ex) {
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
	}
	
	

	private void doQuit(  Writer writer ) {
		removeWriter( writer );

		String data = nickname + "님이 퇴장 하였습니다."; 
		broadcast( data );
	}

	private void removeWriter( Writer writer ) {
		synchronized( listWriters ) {
			listWriters.remove(writer);
		}
	}


	private void doMessage(String data) {
		String message = nickname + ":" + data;
		broadcast(message);
	}

	private void doJoin(String nickname, Writer writer) {
		this.nickname = nickname;
		
		String data = nickname + "님이 참여하였습니다.";
		broadcast(data);
		// writer pool에 저장
		addWriter(writer);
		
		// ack
		((PrintWriter) writer).println("join:ok");
		
		
		
	}

	private void addWriter(Writer writer) {
		synchronized( listWriters ) {
		      listWriters.add( writer );
		   }
	}
	
	private void broadcast(String data) {
		synchronized( listWriters ) {
			for (Writer writer : listWriters ) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
			}
		}
	}
}
