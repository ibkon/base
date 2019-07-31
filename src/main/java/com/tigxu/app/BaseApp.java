package com.tigxu.app;

public class BaseApp {
	public static void main(String args[]) {
		 for(int i=0;i<3;i++) { new Thread(new Run()).start(); }
		
		
		//保持主线程不退出
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
}
