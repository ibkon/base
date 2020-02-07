package com.tigxu.tool;

import java.io.*;

/***
 * 文件编码转换
 */
public class LangCode {
    public static void FileCode(String in,String out,String inCode,String outCode) throws IOException{
        BufferedReader  reader;
        BufferedWriter  writer;
        String          temp;
        File            outFile = new File(out);
        reader      = new BufferedReader(new InputStreamReader(new FileInputStream(new File(in)),inCode));
        if(!outFile.exists()){
            outFile.createNewFile();
        }
        writer      = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),outCode));
        while((temp=reader.readLine())!=null){
            writer.write(temp);
            writer.newLine();
        }
        writer.flush();
        writer.close();
        reader.close();
    }
    public static void gbk2utf8(String in,String out){
        try {
            FileCode(in,out,"GBK","UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
