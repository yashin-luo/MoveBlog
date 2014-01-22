package spider;

import java.util.ArrayList;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * cnblogs博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class CnBlogPageProcesser extends BlogPageProcessor{
	/**
	 * 初始化 代码替换正则
	 */
	protected void initCodeRex(){
		super.initCodeRex();
		
		codeBeginRex.add("<pre.+?class=\"brush:(.+?);.+?\".*?>"); 
	}
	
	/**
	 * 初始化 获取链接列表xpath
	 */
	protected void initLinkXpath(){
		super.initLinkXpath();
		
		LinkXpath linkXpath = new LinkXpath();
		linkXpath.linksXpath="//div[@class='postTitle']/a/@href";
		linkXpath.titlesXpath="//div[@class='postTitle']/a/text()";
		
		linkXpaths.add(linkXpath);
		
		linkXpath = new LinkXpath();
		linkXpath.linksXpath="//div[@class='post']/h2/a/@href";
		linkXpath.titlesXpath="//div[@class='post']/h2/a/text()";
		
		linkXpaths.add(linkXpath);
	}
	
	/**
	 * 初始化
	 */
	protected void initArticleXpath(){
		super.initArticleXpath();
		
		ArticleXpath aXpath = new ArticleXpath();
		aXpath.contentXpath = "//div[@id='cnblogs_post_body']/html()";
		aXpath.tagsXpath = "//div[@id='EntryTagad']/a/text()";
		aXpath.titleXpath = "//a[@id='cb_post_title_url']/text()";
		
		articleXpath.add(aXpath);
		
		aXpath = new ArticleXpath();
		aXpath.contentXpath = "//div[@id='cnblogs_post_body']/html()";
		aXpath.tagsXpath = "//div[@id='EntryTagad']/a/text()";
		aXpath.titleXpath = "//div[@class='post']/h2/a/text()";
		
		articleXpath.add(aXpath);
	}
	
	protected void initCodeHash(){
		super.initCodeHash();
	}

	protected void init() {
		initCodeRex();
		initLinkXpath();
		initArticleXpath();
		initCodeHash();
		
		PagelinksRex = new ArrayList<String>();			//类别页列表过滤表达式
		//http://www.cnblogs.com/hadoopdev/default.html?page=3
		PagelinksRex.add("http://www\\.cnblogs\\.com/"+name+"/default\\.html\\?page=\\d+");	//分页链接
		PagelinksRex.add("http://www\\.cnblogs\\.com/"+name+"/default\\.aspx\\?page=\\d+");	//分页链接
		
	}
	
	public CnBlogPageProcesser(String url) {
	
		site = Site.me().setDomain("www.cnblogs.com");
		site.setSleepTime(1);
		
		blogFlag="/p/|/archive/";																	//博客原url 的名字域		
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