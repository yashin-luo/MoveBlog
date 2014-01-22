package spider;

import java.util.ArrayList;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Iteye博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class IteyeBlogPageProcesser  extends BlogPageProcessor{
	
	/**
	 * 初始化 代码替换正则
	 */
	protected void initCodeRex(){
		super.initCodeRex();
		
		codeBeginRex.add("<pre.+?class=\"(.+?)\".*?>"); 	//代码过滤正则表达式
	}
	
	/**
	 * 初始化 获取链接列表xpath
	 */
	protected void initLinkXpath(){
		super.initLinkXpath();
		
		LinkXpath linkXpath = new LinkXpath();
		linkXpath.linksXpath="//div[@class='blog_main']/div[@class='blog_title']/h3/a/@href";
		linkXpath.titlesXpath="//div[@class='blog_main']/div[@class='blog_title']/h3/a/text()";
		
		linkXpaths.add(linkXpath);
		
	}
	
	/**
	 * 初始化
	 */
	protected void initArticleXpath(){
		super.initArticleXpath();
		
		ArticleXpath aXpath = new ArticleXpath();
		aXpath.contentXpath = "//div[@class='blog_content']/html()";
		aXpath.tagsXpath = "//div[@class='news_tag']/a/text()";
		aXpath.titleXpath = "//div[@class='blog_main']/div[@class='blog_title']/h3/a/text()";
		
		articleXpath.add(aXpath);
	}
	
	protected void initCodeHash(){
		super.initCodeHash();
		codeHashtable.put("csharp", "c#");
		codeHashtable.put("javascript", "js");
		codeHashtable.put("objc", "cpp");
		codeHashtable.put("diff", "html");
		codeHashtable.put("lisp", "lua");
	}

	protected void init() {
		initCodeRex();
		initLinkXpath();
		initArticleXpath();
		initCodeHash();
		
		PagelinksRex = new ArrayList<String>();			//类别页列表过滤表达式
		//http://nodejs.iteye.com/?page=2
		PagelinksRex.add("http://"+name+"\\.iteye\\.com/\\?page=\\d+");					//类别页列表过滤表达式
	}
	
	public IteyeBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("www.iteye.com");
		site.setSleepTime(1);
		
		blogFlag="/blog/";															//博客原url 的名字域
		this.url=url;
		String urlString = url.split("//")[1];
		name = urlString.split("\\.")[0];
		
		init();		
	}
	
	@Override
    public void process(Page page) {
		super.process(page);
	}

    @Override
    public Site getSite() {
        return super.getSite();
    }
    
}