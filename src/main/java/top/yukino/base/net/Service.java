package com.tigxu.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Service {
	private DatagramSocket	socket;
	public Service(int listenPort) {
		try {
			this.socket	= new DatagramSocket(listenPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * UDP服务
	 */
	public void udpServer() {
		//缓冲
		byte[]			buffer	= new byte[0x2000];
		while(true) {
			DatagramPacket	packet	= new DatagramPacket(buffer, 0,buffer.length);
			try {
				this.socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(new String(packet.getData()));
		}
	}
	public void close() {
		this.socket.close();
	}
}
