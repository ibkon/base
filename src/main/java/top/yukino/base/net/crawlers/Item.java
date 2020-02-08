package top.yukino.base.net.crawlers;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/***
 * 爬虫抽象类
 */
public abstract class Item implements PageProcessor {
    private Site    site;
    public void search(String item){
        System.out.println("search:"+item);
    }
    @Override
    public Site getSite() {
        if(site==null){
            site    = Site.me().setRetryTimes(2).setSleepTime(1500);
        }
        return site;
    }
}
