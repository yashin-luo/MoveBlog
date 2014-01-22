package spider;

import java.util.ArrayList;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * csdn博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class CsdnBlogPageProcesser extends BlogPageProcessor{
	
	/**
	 * 初始化 代码替换正则
	 */
	protected void initCodeRex(){
		super.initCodeRex();
		
		codeBeginRex.add("<pre.*?class=\"(.+?)\".*?>"); 												//代码过滤正则表达式
		
		//<textarea class="java" cols="50" rows="15" name="code">
		codeBeginRex.add("<textarea.*?class=\"(.+?)\".*?>" );
		codeEndRex.add("</textarea>");		//</textarea>
	}
	
	/**
	 * 初始化 获取链接列表xpath
	 */
	protected void initLinkXpath(){
		super.initLinkXpath();
		
		LinkXpath linkXpath = new LinkXpath();
		linkXpath.linksXpath="//div[@class='list_item article_item']/div[@class='article_title']/h3/span/a/@href";
		linkXpath.titlesXpath="//div[@class='list_item article_item']/div[@class='article_title']/h3/span/a/text()";
		
		linkXpaths.add(linkXpath);
		
	}
	
	/**
	 * 初始化
	 */
	protected void initArticleXpath(){
		super.initArticleXpath();

		ArticleXpath aXpath = new ArticleXpath();
		aXpath.contentXpath = "//div[@class='article_content']/html()";
		aXpath.tagsXpath = "//div[@class='tag2box']/a/text()";
		aXpath.titleXpath = "//div[@class='details']/div[@class='article_title']/h3/span/a/text()";
		
		articleXpath.add(aXpath);
	}

    /**
     * 初始化映射关系，只初始化代码类型同样而class属性不一样的。
     * 分别为:csdn， osc
     */
	protected void initCodeHash() {
		super.initCodeHash();
		codeHashtable.put("csharp", "c#");
		codeHashtable.put("javascript", "js");
		codeHashtable.put("objc", "cpp");
	}
	
	protected void init() {
		initCodeRex();
		initLinkXpath();
		initArticleXpath();
		initCodeHash();
		
		PagelinksRex = new ArrayList<String>();			//类别页列表过滤表达式
		//http://blog.csdn.net/cxhzqhzq/article/list/2
		PagelinksRex.add("http://blog\\.csdn\\.net/"+name+"/article/list/\\d+");								//类别页列表过滤表达式
		
	}
	
	public CsdnBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("blog.csdn.net");
		site.setSleepTime(1);
		
		blogFlag="/article/details/";																	//博客原url 的名字域
		
		this.url=url;
		
		if(!url.contains(blogFlag)){
			name = url.split("/")[url.split("/").length - 1];
		}
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