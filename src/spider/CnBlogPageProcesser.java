package spider;

import java.util.Hashtable;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * cnblogs博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class CnBlogPageProcesser extends BlogPageProcessor{
	
	
public CnBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("www.cnblogs.com");
		site.setSleepTime(1);
		
		blogFlag="/p/";																//博客原url 的名字域
		codeRex = "<pre\\s*class=\"brush:(.*);.*\">"; 								//代码过滤正则表达式
		
		linksRex="//div[@class='postTitle']/a/@href";								//链接列表过滤表达式
		titlesRex="//div[@class='postTitle']/a/text()";								//title列表过滤表达式
		
		
		contentRex="div.cnblogs_post_body";											//内容过滤表达式
		titleRex="//a[@id='cb_post_title_url']/text()";								//title过滤表达式
		tagsRex="//div[@id='EntryTagad']/a/text()";									//tags过滤表达式
		
		
		this.url=url;
		
		if(!url.contains(blogFlag)){
			name = url.split("/")[url.split("/").length - 1];
		}
		//http://www.cnblogs.com/hadoopdev/default.html?page=3
		PagelinksRex="http://www\\.cnblogs\\.com/"+name+"/default.html?page=\\d+";	//分页链接
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
     * 分别为:cnblogs， osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();	//代码class映射关系
		/*hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");*/
	}
}