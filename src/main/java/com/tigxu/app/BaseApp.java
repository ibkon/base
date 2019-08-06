package com.tigxu.app;

import com.tigxu.tool.Probability;

import java.util.HashMap;
import java.util.Map;

public class BaseApp {
	public static void main(String args[]) {
		 //for(int i=0;i<3;i++) { new Thread(new Run()).start(); }

		Map<String,Double> argss    = new HashMap<>();
		argss.put("a", 0.2);
		argss.put("b", 0.3);
		argss.put("c", 0.1);
		argss.put("d", 0.6);
		argss.put("e", 0.9);
		new Probability(argss);
		
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
