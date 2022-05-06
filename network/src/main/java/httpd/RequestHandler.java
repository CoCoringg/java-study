package httpd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private Socket socket;
	
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			// get IOStream
			OutputStream outputStream = socket.getOutputStream();

			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog("connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );
					
			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
//			outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
//			outputStream.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) );
//			outputStream.write( "\r\n".getBytes() );
//			outputStream.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			String request = null;
			
			while(true) {
				String line = br.readLine();
				
				// 브라우저가 연결을 끊으면,
				if (line == null) {
					break;
				}
				
				// SimpleHttpServer는 요청 헤더만 읽음
				if ("".equals(line)) {
					break;
				}
				
				// 헤더의 첫 번째 라인만 읽음
				if (request == null) {
					request = line;
					break;
				}
			}
			
			// 요청 처리
			// consoleLog(request);
			String[] tokens = request.split(" ");
			
			if("GET".equals(tokens[0])) {
				consoleLog("request:" + tokens[1]);
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			} else {
				System.out.println("!!!!!!!!!!!!!" + tokens[0]);
				// method: POST, PUT, DELETE, HEAD, CONNECT
				// SimpleHttpServer에서는 무시(400 Bad Request)
				response400Error(outputStream, tokens[1], tokens[2]);
			}
			
		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
				
			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}

	private void responseStaticResource(
			OutputStream outputStream, String url, String protocol) throws  IOException{
		// welcome file set
		if ("/".equals(url)) {
			url = "/index.html";
		}
		
		File file = new File("./webapp"+ url);
		
		if (!file.exists()) {
			 response404Response(outputStream, url, protocol);
			return;
		}
		
		// nio
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		// response
		outputStream.write((protocol + " 200 OK\n").getBytes("UTF-8"));
		outputStream.write(("Content-Type:" +contentType +"; charset=utf-8\n").getBytes("UTF-8"));
		outputStream.write("\n".getBytes());
		outputStream.write(body);
	}
	
	private void response400Error(OutputStream outputStream, String url, String protocol) throws IOException {
		/*
		 * HTTP/1.1 400 Bad Request\n
		 * content-Type: text/html; charset=utf-8\n
		 * \n
		 * /error/400.html 내용
		*/
		
		url = "/400.html";
		
		File file = new File("./webapp/error"+ url);
		
		// nio
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		// response
		outputStream.write((protocol + " 400 Bad Request\n").getBytes("UTF-8"));
		outputStream.write(("Content-Type:" +contentType +"; charset=utf-8\n").getBytes("UTF-8"));
		outputStream.write("\n".getBytes());
		outputStream.write(body);
		
	}

	private void response404Response(OutputStream outputStream, String url, String protocol) throws IOException {
		/*
		 * HTTP/1.1 404 File Not Found\n
		 * content-Type: text/html; charset=utf-8\n
		 * \n
		 * /error/404.html 내용
		*/
		
		url = "/404.html";
		
		File file = new File("./webapp/error"+ url);
		
		// nio
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		// response
		outputStream.write((protocol + " 404 File Not Found\n").getBytes("UTF-8"));
		outputStream.write(("Content-Type:" +contentType +"; charset=utf-8\n").getBytes("UTF-8"));
		outputStream.write("\n".getBytes());
		outputStream.write(body);
		
	}

	public void consoleLog( String message ) {
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}
}
