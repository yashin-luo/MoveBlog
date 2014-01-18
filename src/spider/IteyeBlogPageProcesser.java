package spider;

import java.util.Hashtable;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Iteye博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class IteyeBlogPageProcesser  extends BlogPageProcessor{
	
	public IteyeBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("www.iteye.com");
		site.setSleepTime(1);
		
		blogFlag="/blog/";															//博客原url 的名字域
		codeRex = "<pre\\s*.*\\s*class=\"(.*)\".*>"; 								//代码过滤正则表达式
		
		linksRex="//div[@class='blog_main']/div[@class='blog_title']/h3/a/@href";	//链接列表过滤表达式
		titlesRex="//div[@class='blog_main']/div[@class='blog_title']/h3/a/text()";//title列表过滤表达式
		
		contentRex="div.blog_content";												//内容过滤表达式
		titleRex="//div[@class='blog_main']/div[@class='blog_title']/h3/a/text()";	//title过滤表达式
		tagsRex="//div[@class='news_tag']/a/text()";								//tags过滤表达式
		
		this.url=url;
		String urlString = url.split("//")[1];
		name = urlString.split("\\.")[0];
		
		//http://nodejs.iteye.com/?page=2
		PagelinksRex="http://"+name+"\\.iteye\\.com/\\?page=\\d+";					//类别页列表过滤表达式
		
		initMap();		
	}
	
	@Override
    public void process(Page page) {
		super.process(page);
	}

    @Override
    public Site getSite() {
        return super.getSite();
    }
    
    /**
     * 初始化映射关系，只初始化代码类型同样而class属性不一样的。
     * 分别为:csdn， osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();	//代码class映射关系
		hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");
	}
}