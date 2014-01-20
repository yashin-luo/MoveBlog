package spider;

import java.util.Hashtable;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * csdn博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class CsdnBlogPageProcesser extends BlogPageProcessor{
	
	public CsdnBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("blog.csdn.net");
		site.setSleepTime(1);
		
		blogFlag="/article/details/";																	//博客原url 的名字域
		codeBeginRex.add("<pre.*?class=\"(.+?)\".*?>"); 												//代码过滤正则表达式
		
		//<textarea class="java" cols="50" rows="15" name="code">
		codeBeginRex.add("<textarea.*?class=\"(.+?)\".*?>" );
		codeEndRex.add("</textarea>");		//</textarea>
		
		linksRex="//div[@class='list_item article_item']/div[@class='article_title']/h3/span/a/@href";	//链接列表过滤表达式
		titlesRex="//div[@class='list_item article_item']/div[@class='article_title']/h3/span/a/text()";//title列表过滤表达式
		
		contentRex="div.article_content";																//内容过滤表达式
		titleRex="//div[@class='details']/div[@class='article_title']/h3/span/a/text()";				//title过滤表达式
		tagsRex="//div[@class='tag2box']/a/text()";														//tags过滤表达式
		
		this.url=url;
		
		if(!url.contains(blogFlag)){
			name = url.split("/")[url.split("/").length - 1];
		}
		
		//http://blog.csdn.net/cxhzqhzq/article/list/2
		PagelinksRex="http://blog\\.csdn\\.net/"+name+"/article/list/\\d+";								//类别页列表过滤表达式
		
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