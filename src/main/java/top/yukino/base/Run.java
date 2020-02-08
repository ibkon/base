package com.tigxu.app;

import java.util.Random;

import com.tigxu.net.Client;
import com.tigxu.net.Service;
import com.tigxu.tool.Hash;

public class Run implements Runnable{
	private static Run		obj;
	private static int		thID;

	public static synchronized Run getInstance() {
		if(obj == null) {
			thID	= 0;
			obj		= new Run();
		}
		return obj;
	}
	
	/**
	 * 获取线程ID
	 * @return
	 */
	public static synchronized Integer	getThID() {
		return thID++;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Integer	id	= getThID();
		if(id==-1) {
			//启动服务器
			Service	service = new Service(1314);
			service.udpServer();
		}
		else {
			if(id==2) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//启动客户端
			Client	client	= new Client("localhost", 1314);
			String	data	= "";
			for(int i=0;i<1+new Random().nextInt(5);i++) {
				if(id>2)
					data	= id+":"+Hash.uuid();
				else 
					data	= Hash.md5("exit");
				client.udpSend(data.getBytes());
				try {
					Thread.sleep(100*new Random().nextInt(10));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
