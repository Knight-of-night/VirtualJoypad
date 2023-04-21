/**
 * 
 */
package io.github.Knight_of_night.virtualjoypad.util;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Knight-of-night
 *
 */
public class DataSender extends Thread {
	
	public String dataReceive = "";
	public ParseData dataParser;
	
	private boolean running = false;
	private InetSocketAddress addr;
	private Socket client;
	private PrintStream writer;
	private Scanner reader;

	/**
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * 
	 */
	public DataSender(String dstIp, int dstPort, ParseData dataParser) {
		this.addr = new InetSocketAddress(dstIp, dstPort);
		this.dataParser = dataParser;
	}
	
	@Override
	public void run() {
		client = new Socket();
		
		try {
			client.connect(addr, 2000);
			writer = new PrintStream(client.getOutputStream());
			reader = new Scanner(client.getInputStream());
			reader.useDelimiter("\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		running = true;
		
		while(running) {
			if(reader.hasNext()) {
				dataReceive = reader.next();
				if (dataParser != null) {
					dataParser.run(dataReceive);
				}
				
			}
		}
		
		// 关闭
		reader.close();
		writer.close();
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.run();
	}

	public void close() throws IOException{
		running = false;
	}
	
	public boolean isConnected() {
		return client.isConnected();
	}
	
	public boolean send(String data) {
		if(!running) {
			return false;
		}
		
//		writer.println(data);
		new SendThread(data).start();
		
		return true;
	}
	
	// 发送也需要使用线程
	class SendThread extends Thread {
		
		private String dataToSend;
		
		public SendThread(String data) {
			dataToSend = data;
		}

		@Override
		public void run() {
			
//			writer.println(dataToSend);
			writer.print(dataToSend);
			
			super.run();
		}
		
	}

}
