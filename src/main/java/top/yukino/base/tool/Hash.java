package com.tigxu.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
/***
 * 获取UUID及计算hash
 * @author bing
 *
 */
public class Hash {
	public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
	}
	public static String md5(String data) {
		return DigestUtils.md5Hex(data);
	}
	public static String md5(File file){
		try {
			FileInputStream in	= new FileInputStream(file);
			String			hash= DigestUtils.md5Hex(in);
			in.close();
			return hash;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
