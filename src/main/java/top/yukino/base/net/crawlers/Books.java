package top.yukino.base.net.crawlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/***
 * 小说爬虫
 */
public class Books extends Item {
    //章节列表
    private  List<String> list;
    public Books(){this.list=new ArrayList<>();}
    public synchronized List<String> getList(){return this.list;}
    @Override
    public void process(Page page) {
        Html        html        = page.getHtml();
        Selectable  selectable  = html.$(".bookinfo");
        if(selectable.get()!=null){
            //获取小说章节信息的链接
            String	bookInfo	= html.xpath("//div[@class=\"bookinfo\"]/h4/a/@href").get();
            if(bookInfo!=null){
                page.addTargetRequest("https://www.sbiquge.com"+bookInfo);
            }
            else{
                System.err.println("无法获取:"+page.getUrl().get()+" 目录信息");
            }
        }
        else{
            //读取目录列表
            selectable	= html.css(".listmain dl");
            if(selectable.get()!=null) {

                String ht = selectable.get();
                //删除最新更新的部分，让余下可以按顺序抓取
                Document document   = Jsoup.parse(ht.substring(0, ht.indexOf("<dt>")) + ht.substring(ht.lastIndexOf("</dt>") + 5));
                Elements elements   = document.select("dd a");
                String  pageUrl     = null;
                for (Element e : elements) {
                    pageUrl = "https://www.sbiquge.com" + e.attr("href");
                    getList().add(pageUrl);
                    page.addTargetRequest(pageUrl);
                }
            }
            else{
                //读取小说信息
                selectable	= html.css(".showtxt");
                if(selectable.get()!=null){
                    page.putField(page.getUrl().get(), selectable.get());
                }
                else {
                    System.err.println("暂未支持该站数据抓取：");
                    System.out.println(html.get());
                }
            }
        }
    }
}
