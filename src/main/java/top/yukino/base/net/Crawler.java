package top.yukino.base.net;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import javax.print.Doc;

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
	public void test(){
		String	s="()荒凉的街道上，一只丧尸正在无意识地游荡着。";
		System.out.println(s.replaceAll("\\(\\)", ""));
	}
	/**
	 * web magic爬虫框架
	 */
	@Test
	public void wmc() {
		PageProcessor	pp	= new PageProcessor() {
			Site	site	= Site.me().setRetryTimes(2).setSleepTime(1500);
			private Map<String,String> key;
			@Override
			public void process(Page page) {
				if(key==null){
					key=new HashMap<>();
					key.put("sè","色");
					key.put("xing", "性");
					key.put("cāo", "操");

				}
				Html html	= page.getHtml();
				System.out.println(html);
                Selectable	selectable	= html.css(".bookinfo");

				if(selectable.get() != null){

					//搜索到小说信息
					String	bookUri	= html.xpath("//div[@class=\"bookinfo\"]/h4/a/@href").get();
					if(bookUri!=null){
						page.addTargetRequest("https://www.sbiquge.com"+bookUri);
					}
				}
				else {
					selectable	= html.css(".listmain dl");
					if(selectable.get()!=null){
						//读取目录列表
						String	ht	= selectable.get();
						Document	document	= Jsoup.parse(ht.substring(0,ht.indexOf("<dt>"))+ht.substring(ht.lastIndexOf("</dt>")+5));
						Elements	elements	= document.select("dd a");
						int i=0;
						for(Element	e:elements){
							page.addTargetRequest("https://www.sbiquge.com"+e.attr("href"));
							break;
						}
					}
					else {
						selectable	= html.css(".showtxt");
						if(selectable.get()!=null){
                            System.out.println(selectable.get());
                                /*if(selectable.get()!=null){
                                    String	ht	= selectable.get().trim().replaceAll("&nbsp;", "");
                                    ht	= ht.replaceAll("<br>", "");
                                    ht	= ht.substring(ht.indexOf(">")+1, ht.lastIndexOf("</div>"));
                                    ht	= ht.replaceAll("\\(\\)", "");
                                    ht	= ht.substring(0,ht.indexOf(page.getUrl().get()));
                                    ht	= ht.replaceAll("\n\n","\n");
                                    StringBuilder	stringBuilder	= new StringBuilder();
                                    for(String s:ht.split("\n")){
                                        s=s.trim();
								if(s.length()>0){
									for(String skey:key.keySet()){
										s=s.replaceAll(skey,key.get(skey));
									}
									stringBuilder.append(s);
								}
							}*/
							//page.putField("第一章", stringBuilder.toString());
						}
						else {
							System.out.println("无法解析的html");
						}
					}
				}
			}
			@Override
			public Site getSite() {
				return site;
			}
		};
		String	url	= "https://www.sbiquge.com/s.php?q="+"我的女友是丧尸";
		HttpClientDownloader hcd	= new HttpClientDownloader();
		hcd.setProxyProvider(SimpleProxyProvider.from(new Proxy("127.0.0.1",1080)));

		Spider.create(pp).addUrl(url).thread(1).addPipeline(new Pipeline() {
			@Override
			public void process(ResultItems resultItems, Task task) {
				//在获取章节链接时将章节名保存到有序容器，然后按序读取并保存到持久层
				System.out.println(resultItems);
			}
		}).setDownloader(hcd).run();
	}
	public void testat() {
		HttpGet		httpGet		= new HttpGet("http://www.jd.com");
		httpGet.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		httpGet.addHeader("Accept-Language","zh-CN,zh;q=0.9,en-GB;q=0.8,en;q=0.7");
		httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
		RequestConfig	config	= RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
		httpGet.setConfig(config);
		CloseableHttpClient	build	= HttpClientBuilder.create().build();
		try {
			CloseableHttpResponse	httpResponse	= build.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()==200) {
				HttpEntity	entity	= httpResponse.getEntity();
				System.out.println(EntityUtils.toString(entity));
			}
			else {
				System.err.println(httpResponse.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	public void testr() throws IOException{
		//类选择器
		//Document	document	= Jsoup.connect("http://s.gxrc.com/sCareer?posType=5467").get();
		//System.out.println(document.select("a").last().html());
		//System.out.println(document.select(".w1").last().text());
		
		String	regex	= "[a-zA-z]+://[^\\s]*";
		String	url		= "http://s.gxrc.com/sCareer?posType=5467";
		Pattern	pattern	= Pattern.compile(regex);
		Matcher	matcher	= pattern.matcher(url);
		if(matcher.find()) {
			System.out.println(matcher.group());
		}
		
		/**/
		
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
