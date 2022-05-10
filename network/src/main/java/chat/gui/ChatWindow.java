package chat.gui;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;

import chat.ChatClientThread;
import chat.ChatServer;

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private Socket socket;
	private String name;

	public ChatWindow(String name, Socket socket) { // socket 받을 수 있도록 바꿔야함
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 80);
		this.socket = socket;
		this.name = name;
	}

	public void show() {
		/*
		 * 1. UI 초기화
		 */
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);
//		buttonSend.addActionListener(
//				(ActionEvent actionEvent) -> {
//					System.out.println("clicked!!");
//				}
//				actionEvent -> System.out.println("clicked!!");
//		);
		
		buttonSend.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent actionEvent ) {
				sendMessage();
			}
		});

		// Textfield
		textField.setColumns(80);
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if(keyCode == KeyEvent.VK_ENTER) {
					sendMessage();
				}
			}
			
		});

		// Pannel
		pannel.setBackground(Color.LIGHT_GRAY);
		pannel.add(textField);
		pannel.add(buttonSend);
		frame.add(BorderLayout.SOUTH, pannel);

		// TextArea
		textArea.setEditable(false);
		frame.add(BorderLayout.CENTER, textArea);

		// Frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				finish();
			}
		});
		frame.setVisible(true);
		frame.pack();
		
		/*
		 * 2. IOStream (Pipeline established)
		 */
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			
			String data = textField.getText();
//			printWriter.println("message:"+data); 
			
		} catch (IOException ex) {
			ChatServer.log( "error:" + ex );
		}
		

		/*
		 * 3. Chat Client Thread 생성하고 실행 (밑에 만든거)
		 */
		new ChatClientThread(socket).start();
	}
	
	private void sendMessage() {
		String message = textField.getText();
		System.out.println("메세지 보내는 프로토콜 구현:"+message);
		textField.setText("");
		textField.requestFocus();
		
		// Chat Client Thread에서 서버로부터 받은 메세지가 있다고 치고~~(가짜데이터)
		updateTextArea(name+" : "+message);
		
	}
	
	private void updateTextArea(String message) {
		textArea.append(message);
		textArea.append("\n");
	}
	
	private void finish() {
		System.out.println("소켓 닫기 or 방나가기(quit) 프로토콜 구현");
		System.exit(0);
	}
	
	/**
	 * 
	 * @author 청출어람_PC09
	 * Receive Thread from Chat Server
	 *
	 */
	private class ChatClientThread extends Thread {
		
		private Socket socket;
		private BufferedReader bufferedReader;
		
		public ChatClientThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
//			String message = "둘리:안녕";
//			updateTextArea(message); 
			/* reader를 통해 읽은 데이터 콘솔에 출력하기 (message 처리) */
			try {
				this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				while (true) {
					String data = bufferedReader.readLine();
					System.out.println(data);
					updateTextArea(data);
					
				}
			} catch (SocketException ex) {
				System.out.println("채팅을 종료하였습니다.");
			}
			catch (IOException ex) {
				ChatServer.log( "error:" + ex );
			} 
		}
	}
}
