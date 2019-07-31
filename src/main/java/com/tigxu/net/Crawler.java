package com.tigxu.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/***
 * 爬取招聘信息
 * @author bing
 *
 */
public class Crawler {
	public InputStream	openUrl(String url) {
        HttpURLConnection   connection  = null;
        try {
            connection  = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
            return connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
	}
	
	@Test
	public void test() throws IOException{
		Document	document	= Jsoup.connect("http://s.gxrc.com/sCareer?posType=5467").get();
		
		//System.out.println(document.select("a").last().html());
		System.out.println(document.select(".w1").last().text());
	}
	
	public void dotxt() throws IOException{
		//爬小说测试		
		  String url = "http://www.u33.cc/u151661/";
		  Document document =Jsoup.connect(url).get();
		  Elements elements = document.getElementsByTag("dd"); 
		  String tmp = ""; 
		  FileOutputStream	out		= new FileOutputStream("轻小说作家与歌姬.txt");
		  for(Element e:elements){ 
			  tmp = url+e.getElementsByTag("a").get(0).attr("href");
			  document =Jsoup.connect(tmp).get();
			  tmp=document.getElementsByClass("bookname").get(0).getElementsByTag("h1").html();
			  out.write(tmp.getBytes());
			  out.write("\n\t".getBytes());
			  tmp	= document.getElementById("content").html();
			  tmp=tmp.substring(0,tmp.indexOf("<script")).replaceAll("<br>", "").trim().replaceAll("\n", "\n\t"); 
			  out.write(tmp.getBytes());
			  out.write("\n".getBytes());
			  out.flush();
		  }
		 out.close();
	}
	/**
	 * JSOUP解析HTML
	 */
	public void jsoup1() {
		Document	document	= null;
		try {
			document	= Jsoup.connect("http://s.gxrc.com/sCareer?posType=5467").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elements	= document.getElementsByClass("rlOne");
		Elements tag		= null;
		for(Element e:elements) {
			tag	= e.getElementsByTag("a");
			for(Element l:tag) {
				System.out.println(l.attr("href"));
				System.out.println(l.attr("title"));
			}
		}
	}
	public void gxrc() {
        URL                 url         = null;
        HttpURLConnection   connection  = null;
        try {
            url         = new URL("https://tieba.baidu.com/f?kw=tolove&fr=ala0&tpl=5");
            connection  = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
            
            BufferedReader	reader	= new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedWriter	writer	= new BufferedWriter(new FileWriter(new File("tolove.html")));
            String	str	= null;
            while((str=reader.readLine())!=null) {
            	//System.out.println(str);
            	writer.write(str);
            	writer.newLine();
            }
            writer.flush();
            writer.close();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("end");
	}
}
