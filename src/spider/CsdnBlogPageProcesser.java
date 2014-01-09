package spider;

import java.util.List;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * csdn≤©øÕ≈¿≥Ê¬ﬂº≠
 * @author oscfox
 * @date 20140106
 */
public class CsdnBlogPageProcesser implements PageProcessor {
	
	private Site site = Site.me().setDomain("blog.csdn.net");
	
	private String urlString ;
	private String name;
	
	public CsdnBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		urlString = url;
		name = urlString.split("/")[urlString.split("/").length - 1];
	}
	
	@Override
    public void process(Page page) {
        
        List<String> links = page.getHtml().links().regex("http://blog\\.csdn\\.net/"+name+"/article/details/\\d+").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//div[@class='details']/div[@class='article_title']/h3/span/a/text()").toString());
        page.putField("content", page.getHtml().$("div.article_content").toString());
        page.putField("tags",page.getHtml().xpath("//div[@class='tag2box']/a/text()").all());
        page.putField("link", page.getHtml().xpath("//div[@class='details']/div[@class='article_title']/h3/span/a/@href").toString());
        
    }

    @Override
    public Site getSite() {
        return site;
    }

    /*
    
    //≤‚ ‘
	public static void main(String[] args) {
		//≈¿»°≤©øÕ
        Spider.create(new CsdnBlogPageProcesser("http://blog.csdn.net/foxyaxin"))
        	.addUrl("http://blog.csdn.net/foxyaxin")
             .addPipeline(new GetBlogPipeline()).run();
        
        System.out.print(BlogList.getBlogList());
    }
    */
}