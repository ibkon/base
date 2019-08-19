package com.tigxu.app;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.tigxu.tool.*;
import com.tigxu.ui.MainInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class BaseApp {
	public static void main(String args[]) throws IOException{
		SynFile	syn	= new SynFile();
		//syn.del("D:\\NULL\\sg\\True HD ENB\\366 True HD ENB4.1中配版","D:\\NULL\\sg\\True HD ENB\\test",SynFile.MOD_FILE|SynFile.MOD_EDIR|SynFile.MOD_HASH|SynFile.MOD_NAME);
		syn.del("D:\\NULL\\sg\\True HD ENB\\366 True HD ENB4.1中配版","D:\\NULL\\sg\\True HD ENB\\test",SynFile.MOD_FILE|SynFile.MOD_EDIR);
	}
}
