package com.tigxu.tool;

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
}
