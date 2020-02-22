package top.yukino.base.net.crawlers;

import org.junit.Test;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/***
 * 爬虫调度器
 */
public class Scheduling {
	private PageProcessor page;

	@Test
	public void run() {
		page = new Books();
		Spider.create(page).addUrl("https://www.sbiquge.com/s.php?q=" + "我的女友是丧尸")
				.addPipeline(new BookPipline(((Books) page).getList())).thread(2).run();
	}
}
