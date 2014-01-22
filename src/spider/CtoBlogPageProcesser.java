package spider;

import java.util.ArrayList;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * 51cto博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class CtoBlogPageProcesser extends BlogPageProcessor{
	/**
	 * 初始化 代码替换正则
	 */
	protected void initCodeRex(){
		super.initCodeRex();
		
		codeBeginRex.add("<pre.+?class=\"brush:(.+?);.*?\".*?>");		//代码过滤正则表达式
	}
	
	/**
	 * 初始化 获取链接列表xpath
	 */
	protected void initLinkXpath(){
		super.initLinkXpath();
		
		LinkXpath linkXpath = new LinkXpath();
		linkXpath.linksXpath="//div[@class='blogList']/div[@class='artHead']/div/h3[@class='artTitle']/a/@href";
		linkXpath.titlesXpath="//div[@class='blogList']/div[@class='artHead']/div/h3[@class='artTitle']/a/text()";
		
		linkXpaths.add(linkXpath);
		
	}
	
	/**
	 * 初始化
	 */
	protected void initArticleXpath(){
		super.initArticleXpath();
		
		ArticleXpath aXpath = new ArticleXpath();
		aXpath.contentXpath = "//div[@class='showContent']/html()";
		aXpath.tagsXpath = "//div[@class='showTags']/a/text()";
		aXpath.titleXpath = "//div[@class='showTitleBOx']/div[@class='showTitle']/text()";
		
		articleXpath.add(aXpath);
	}
	
	protected void initCodeHash(){
		super.initCodeHash();
		codeHashtable.put("bash", "shell");
		codeHashtable.put("delphi", "pascal");
		codeHashtable.put("pl", "perl");
		codeHashtable.put("ps", "shell");
		codeHashtable.put("plain", "lua");
		codeHashtable.put("cf", "cpp");
		codeHashtable.put("diff", "shell");
		codeHashtable.put("erlang", "lua");
		codeHashtable.put("jfx", "js");
	}

	protected void init() {
		initCodeRex();
		initLinkXpath();
		initArticleXpath();
		initCodeHash();
		
		PagelinksRex = new ArrayList<String>();			//类别页列表过滤表达式
		//http://tongcheng.blog.51cto.com/6214144/p-3
		PagelinksRex.add("http://"+name+"\\.blog\\.51cto\\.com/\\d+/p-\\d+");	
	}
	
	public CtoBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("http://blog.51cto.com/");
		site.setSleepTime(1);
		site.setCharset("gb2312");
		
		blogFlag="/\\d+/";																		//博客原url 的名字域
		
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