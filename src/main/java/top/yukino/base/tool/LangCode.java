package top.yukino.base.tool;

import java.io.*;

/***
 * 文件编码转换
 */
public class LangCode {
	public static void FileCode(String in, String out, String inCode, String outCode) throws IOException {
		BufferedReader reader;
		BufferedWriter writer;
		String temp;
		File outFile = new File(out);
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(in)), inCode));
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), outCode));
		while ((temp = reader.readLine()) != null) {
			writer.write(temp);
			writer.newLine();
		}
		writer.close();
		reader.close();
	}
}
