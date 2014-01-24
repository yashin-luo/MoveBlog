package spider;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Node;

import common.OscBlogReplacer;
import common.SpiderConfigTool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * csdn博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class BlogPageProcessor implements PageProcessor{
	
	public class LinkXpath{
		public String linksXpath;	//链接列表过滤表达式
		public String titlesXpath;	//title列表过滤表达式
	}
	
	public class ArticleXpath{
		public String contentXpath;	//内容过滤表达式
		public String titleXpath;	//title过滤表达式
		public String tagsXpath; 	//tags过滤表达式
	}
	
	private Site site = new Site();
	private String url;
	private String blogFlag;				//博客url的内容标志域
	private List<String> codeBeginRex; 	//代码过滤正则表达式
	private List<String> codeEndRex; 		//代码过滤正则表达式
	private List<LinkXpath> linkXpaths;		//获取链接表达式
	private List<ArticleXpath> articleXpaths;	//获取文件表达式
	private List<String> PagelinksRex;				//类别页列表过滤表达式
	private Hashtable<String, String> codeHashtable;	//代码class映射关系
	private SpiderConfigTool spiderConfig; 

	public BlogPageProcessor(String url) throws Exception{
		if(url.endsWith("/")){
			url = url.substring(0, url.length()-1);
		}
		this.url=url;
		
		String spiderName="";	//切割域名 ：类似：csdn.net, 51cto.com, cnblogs.com, iteye.com
		
		Pattern p=Pattern.compile("\\.([a-zA-Z0-9]+\\.[a-zA-Z]+)");
		Matcher m=p.matcher(url);
		
		if(m.find()){
			spiderName = m.group(1);
		} else {
			throw new Exception("不支持的网站！");
		}
		
		spiderConfig = new SpiderConfigTool(spiderName);
		
		init();
	}
	
	private void init(){
		String domain = spiderConfig.getSpiderNode().selectSingleNode("domain").getText();
		site = Site.me().setDomain(domain);
		String charset = spiderConfig.getSpiderNode().selectSingleNode("charset").getText();
		site.setCharset(charset);
		site.setSleepTime(1);
		
		blogFlag = spiderConfig.getSpiderNode().selectSingleNode("blog-flag").getText();
		
		initPageRex();
		initCodeRex();
		initLinkXpath();
		initArticleXpath();
		initCodeHash();
	}
	
	/**
	 * 初始化 代码替换正则
	 */
	@SuppressWarnings("unchecked")
	private void initCodeRex(){
		codeBeginRex = new ArrayList<String>(); 		//代码过滤正则表达式
		codeEndRex  = new ArrayList<String>(); 			//代码过滤正则表达式
		
		List<Node> list = spiderConfig.getSpiderNode().selectNodes("code-begin-rex");
		for(Node n:list){
			codeBeginRex.add(n.getText());
		}
		
		list = spiderConfig.getSpiderNode().selectNodes("code-end-rex");
		for(Node n:list){
			codeEndRex.add(n.getText());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void initPageRex(){
		PagelinksRex = new ArrayList<String>();
		//page-links-rex
		List<Node> list = spiderConfig.getSpiderNode().selectNodes("page-links-rex");
		for(Node pagelink:list){
			String page = pagelink.getText();
			String string=url.replaceAll("\\.", "\\\\\\.");
			String temString= string+page;
			PagelinksRex.add(temString);
		}
	}
	
	/**
	 * 初始化 获取链接列表xpath
	 */
	@SuppressWarnings("unchecked")
	private void initLinkXpath(){
		linkXpaths = new ArrayList<LinkXpath>();		//获取链接表达式
		
		List<Node> list = spiderConfig.getSpiderNode().selectNodes("link-xpath");
		for(Node node : list){
			String link = node.selectSingleNode("links-xpath").getText();
			String title = node.selectSingleNode("titles-xpath").getText();
			LinkXpath linkXpath = new LinkXpath();
			linkXpath.linksXpath=link;
			linkXpath.titlesXpath=title;
			linkXpaths.add(linkXpath);
		}
	}
	
	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	private void initArticleXpath(){
		articleXpaths = new ArrayList<ArticleXpath>();	//获取文件表达式
		
		List<Node> list = spiderConfig.getSpiderNode().selectNodes("article-xpath");
		for(Node node : list){
			String content = node.selectSingleNode("content-xpath").getText();
			String title = node.selectSingleNode("title-xpath").getText();
			String tags = node.selectSingleNode("tags-xpath").getText();
			ArticleXpath articleXpath = new ArticleXpath();
			articleXpath.contentXpath=content;
			articleXpath.titleXpath=title;
			articleXpath.tagsXpath = tags;
			
			articleXpaths.add(articleXpath);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void initCodeHash(){
		codeHashtable = new Hashtable<String, String>();
		
		List<Node> list = spiderConfig.getSpiderNode().selectNodes("code-hashtable");
		for(Node node : list){
			String key = node.selectSingleNode("key").getText();
			String osc = node.selectSingleNode("osc").getText();
			codeHashtable.put(key, osc);
		}
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
        
        List<String> alllinks = page.getHtml().links().all();
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
        
        String title = page.getHtml().xpath(articleXpaths.get(0).titleXpath).toString();
        String content = page.getHtml().xpath(articleXpaths.get(0).contentXpath).toString();
        String tags = page.getHtml().xpath(articleXpaths.get(0).tagsXpath).all().toString();
        
        for(int i=1; i < articleXpaths.size() && null == title; ++i){
        	title = page.getHtml().xpath(articleXpaths.get(i).titleXpath).toString();
        	content = page.getHtml().xpath(articleXpaths.get(i).contentXpath).toString();
            tags = page.getHtml().xpath(articleXpaths.get(i).tagsXpath).all().toString();
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