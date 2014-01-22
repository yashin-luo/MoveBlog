package spider;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import common.OscBlogReplacer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * csdn博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class BlogPageProcessor implements PageProcessor{
	
	class LinkXpath{
		public String linksXpath;	//链接列表过滤表达式
		public String titlesXpath;	//title列表过滤表达式
	}
	
	class ArticleXpath{
		public String contentXpath;	//内容过滤表达式
		public String titleXpath;	//title过滤表达式
		public String tagsXpath; 	//tags过滤表达式
	}
	
	protected Site site = new Site();
	protected String url;
	protected String blogFlag;				//博客url的内容标志域
	protected String name;					//博客原url 的名字域
	protected List<String> codeBeginRex; 	//代码过滤正则表达式
	protected List<String> codeEndRex; 		//代码过滤正则表达式
	
	protected List<LinkXpath> linkXpaths;		//获取链接表达式
	protected List<ArticleXpath> articleXpath;	//获取文件表达式
	
	protected List<String> PagelinksRex;			//类别页列表过滤表达式
			
	protected Hashtable<String, String> codeHashtable;	//代码class映射关系
	
	
	/**
	 * 初始化 代码替换正则
	 */
	protected void initCodeRex(){
		codeBeginRex = new ArrayList<String>(); 		//代码过滤正则表达式
		codeEndRex  = new ArrayList<String>(); 			//代码过滤正则表达式
	}
	
	/**
	 * 初始化 获取链接列表xpath
	 */
	protected void initLinkXpath(){
		linkXpaths = new ArrayList<LinkXpath>();		//获取链接表达式
	}
	
	/**
	 * 初始化
	 */
	protected void initArticleXpath(){
		articleXpath = new ArrayList<ArticleXpath>();	//获取文件表达式
	}
	
	protected void initCodeHash(){
		codeHashtable = new Hashtable<String, String>();
	}

	
	/**
	 * 抓取博客内容等，并将博客内容中有代码的部分转换为oschina博客代码格式
	 */
	@Override
    public void process(Page page) {
		
		Pattern p=Pattern.compile(blogFlag);
		Matcher m=p.matcher(url);
		boolean result=m.find();
		
        if(result){
        	getPage(page);
        	page.putField("getlinks", false);
        } else {
			getLinks(page);
			page.putField("getlinks", true);
		}
	}
	
	/**
	 * 抓取链接列表
	 * @param page
	 */
	private void getLinks(Page page) {
		List<String> links = page.getHtml().xpath(linkXpaths.get(0).linksXpath).all();
        List<String> titles = page.getHtml().xpath(linkXpaths.get(0).titlesXpath).all();
        
        for(int i=1; i < linkXpaths.size() && titles.size() == 0; ++i){
        	links = page.getHtml().xpath(linkXpaths.get(i).linksXpath).all();
            titles = page.getHtml().xpath(linkXpaths.get(i).titlesXpath).all();
        }
        
        page.putField("titles", titles);
        page.putField("links", links);
        
        List<String> Pagelinks = page.getHtml().links().regex(PagelinksRex.get(0)).all();
        
        for(int i=1; i < PagelinksRex.size() && Pagelinks.size() == 0; ++i){
        	Pagelinks = page.getHtml().links().regex(PagelinksRex.get(i)).all();
        }
        
        page.addTargetRequests(Pagelinks);
	}

	/**
	 * 抓取博客内容
	 * @param page
	 */
	private void getPage(Page page){
        
        String title = page.getHtml().xpath(articleXpath.get(0).titleXpath).toString();
        String content = page.getHtml().xpath(articleXpath.get(0).contentXpath).toString();
        String tags = page.getHtml().xpath(articleXpath.get(0).tagsXpath).all().toString();
        
        for(int i=1; i < PagelinksRex.size() && null == title; ++i){
        	title = page.getHtml().xpath(articleXpath.get(i).titleXpath).toString();
        	content = page.getHtml().xpath(articleXpath.get(i).contentXpath).toString();
            tags = page.getHtml().xpath(articleXpath.get(i).tagsXpath).all().toString();
        }
        
        
        if(StringUtils.isBlank(content) || StringUtils.isBlank(title)){
        	return;
        }
        
        if(!StringUtils.isBlank(tags)){
        	tags = tags.substring(tags.indexOf("[")+1,tags.indexOf("]"));
        }

        OscBlogReplacer oscReplacer= new OscBlogReplacer(codeHashtable);	//设置工具类映射关系
    	String oscContent = oscReplacer.replace(codeBeginRex, codeEndRex, content);		//处理代码格式
    	
        page.putField("content", oscContent);
        page.putField("title", title);
        page.putField("tags", tags);
	}
	

    @Override
    public Site getSite() {
        return site;
    }
    

}