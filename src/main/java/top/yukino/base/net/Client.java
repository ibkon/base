package top.yukino.base.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Client{
	private DatagramSocket		socket;
	private InetSocketAddress	address;
	public Client(String host,int port) {
		try {
			this.socket		= new DatagramSocket();
			this.address	= new InetSocketAddress(host, port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * UDP发送消息
	 * @param date	需要发送的数据
	 */
	public void udpSend(byte[] date) {
		DatagramPacket	packet	= new DatagramPacket(date, 0,date.length,address);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close() {
		this.socket.close();
	}
}
