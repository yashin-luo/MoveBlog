package spider;

import java.util.Hashtable;
import java.util.List;
import common.OscBlogReplacer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Iteye博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class IteyeBlogPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setDomain("www.iteye.com");
	
	private String urlString ;
	private String name;
	private String codeRex = "<pre\\s*.*\\s*class=\"(.*)\".*>"; 	//代码过滤正则表达式
	private Hashtable<String, String> hashtable=null;				//代码class映射关系
	
	public IteyeBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		urlString = url;
		String string=urlString.split("//")[1];
		name = string.split("\\.")[0];
	}
	
	/**
	 * 爬去博客内容等，并将博客内容中有代码的部分转换为oschina博客代码格式
	 */
	@Override
    public void process(Page page) {
		//http://coffeescript.iteye.com/blog/2003321
        List<String> links = page.getHtml().links().regex("http://"+name+"\\.iteye\\.com/blog/\\d+").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//div[@class='blog_main']/div[@class='blog_title']/h3/a/text()").toString());
        page.putField("tags",page.getHtml().xpath("//div[@class='news_tag']/a/text()").all());
        page.putField("link", page.getHtml().xpath("//div[@class='blog_main']/div[@class='blog_title']/h3/a/@href").toString());
        
        String oldContent = page.getHtml().$("div.blog_content").toString();
        if(null == oldContent){
        	return;
        }
        //初始化映射关系 
        initMap();
		//设置工具类映射关系
		OscBlogReplacer.setHashtable(hashtable);
		//处理代码格式,
    	String oscContent = OscBlogReplacer.replace(codeRex,oldContent);
        page.putField("content", oscContent);
	}

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 初始化映射关系，只初始化代码类型同样而class属性不一样的。
     * 分别为:iteye， osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();
		
		/*hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");*/
	}
}