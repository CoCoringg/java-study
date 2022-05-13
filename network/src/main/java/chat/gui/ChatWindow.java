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
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import chat.ChatServer;

public class ChatWindow {

	private Frame frame;
	private Panel pannel;
	private Button buttonSend;
	private TextField textField;
	private TextArea textArea;
	private TextArea textArea2;
	private Socket socket;
	private PrintWriter printWriter;
	private String name;
	private List<String> users = new ArrayList<String>();


	public ChatWindow(String name, Socket socket) { // socket 받을 수 있도록 바꿔야함
		frame = new Frame(name);
		pannel = new Panel();
		buttonSend = new Button("Send");
		textField = new TextField();
		textArea = new TextArea(30, 60);
		textArea2 = new TextArea(10,20);
		this.name = name;
		this.socket = socket;
		
	}

	public void show() {
		/*
		 * 1. UI 초기화
		 */
		textArea.append("입장하였습니다. 즐거운 채팅 되세요");
		textArea.append("\n");
		
		textArea2.append("채팅 유저 목록");
		textArea2.append("\n");
		users.add(name);
		
		// Button
		buttonSend.setBackground(Color.GRAY);
		buttonSend.setForeground(Color.WHITE);		
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
		textArea2.setEditable(false);
		frame.add(BorderLayout.WEST, textArea);
		frame.add(BorderLayout.EAST, textArea2);

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
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
		}  catch (IOException ex) {
			ChatServer.log( "error:" + ex );
		}


		/*
		 * 3. Chat Client Thread 생성하고 실행 (밑에 만든거)
		 */
		
		new ChatClientThread(socket).start();
	}
	
	private void sendMessage() {
		
		String message = textField.getText();
		if("quit".equals(message)) {
			finish();
		}
		
		if("".equals(message)) {
			return;
		}
		
		printWriter.println("message:"+message);
		
		textField.setText("");
		textField.requestFocus();
	}
	
	
	private void updateTextArea(String message) {
		textArea.append(message);
		textArea.append("\n");
	}
	
	private void updateTextArea2(String user) {
		textArea2.append(user);
		textArea2.append("\n");
	}
	
	private void finish() {
		printWriter.println("quit");
		System.exit(0);
	}
	
	private class ChatClientThread extends Thread {
		
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
//					String[] tokens = data.split(":");
//					if("user".equals(tokens[0])) {
//						users.add(tokens[1]);
//					} else {
//						updateTextArea(data);
//					}
//					for(String user:users) {
//						updateTextArea2(user);
//					}
					
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
