package top.yukino.base.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
/***
 * 获取UUID及计算hash
 * @author bing
 *
 */
public class Hash {
	/**
	 * 获取一个UUID
	 * @return	格式化UUID
	 */
	public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
	}
	/**
	 * 计算文件哈希值
	 * @param file		输入文件
	 * @param outCode	算法，可选MD5、SHA256，默认SHA256
	 * @return			HASH值
	 */
	public static String fHash(File	file,String outCode) {
		String		code= null;
		if(file!=null&&file.exists()&&file.isFile()) {
			InputStream	in	= null;
			try {
				in	= new FileInputStream(file);
				switch (outCode.toUpperCase()) {
				case "MD5":
					code	= DigestUtils.md5Hex(in);
					break;
				case "SHA256":
				default:
					code	= DigestUtils.sha256Hex(in);
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				if(in!=null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return code;
	}
	public static String fHash(String file,String outCode) {
		return fHash(new File(file), outCode);
	}
	
	/**
	 * 计算字符串哈希值
	 * @param str		输入字符串
	 * @param outCode	算法，可选MD5、SHA256，默认SHA256
	 * @return			字符串HASH值
	 */
	public static String sHash(String str,String outCode) {
		String	code	= null;
		if(str!=null&&str.length()>0) {
			switch (outCode.toUpperCase()) {
			case "MD5":
				code	= DigestUtils.md5Hex(str);
				break;
			case "SHA256":
			default:
				code	= DigestUtils.sha256Hex(str);
				break;
			}
		}
		return code;
	}
}
