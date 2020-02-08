package top.yukino.base.net.crawlers;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class BookPipline implements Pipeline {
    private List<String> list;
    public BookPipline(List<String> list){
        this.list   = list;
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        int     count   = 0;
        String  html    = null;
        while(count<list.size()){
            html    = resultItems.get(list.get(count));
            if(html==null){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("成功抓取："+list.get(count));
                count++;
            }
        }
    }
}
