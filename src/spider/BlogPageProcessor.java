package spider;

import java.util.Hashtable;
import java.util.List;

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
	
	protected Site site = new Site();
	protected String url;
	protected String blogFlag;			//博客url的内容标志域
	protected String name;				//博客原url 的名字域
	protected String codeRex;			//代码过滤正则表达式
	
	protected String linksRex;			//链接列表过滤表达式
	protected String titlesRex;			//title列表过滤表达式
	protected String PagelinksRex;		//类别页列表过滤表达式
		
	protected String contentRex;		//内容过滤表达式
	protected String titleRex;			//title过滤表达式
	protected String tagsRex;			//tags过滤表达式
	
	
	protected Hashtable<String, String> hashtable;	//代码class映射关系
	
	
	/**
	 * 抓取博客内容等，并将博客内容中有代码的部分转换为oschina博客代码格式
	 */
	@Override
    public void process(Page page) {
        if(url.contains(blogFlag)){
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
		List<String> links = page.getHtml().xpath(linksRex).all();
        List<String> titles = page.getHtml().xpath(titlesRex).all();
        
        page.putField("titles", titles);
        page.putField("links", links);
        
        List<String> Pagelinks = page.getHtml().links().regex(PagelinksRex).all();
        page.addTargetRequests(Pagelinks);
        
       /* 
        String thisLink=page.getUrl().toString();
        		
        if(thisLink.endsWith(name)){
	        List<String> Pagelinks=page.getHtml().xpath(PagelinksRex).all();   
	        
	        if(Pagelinks.isEmpty()){
	        	return;
	        }
	        
	        String end=Pagelinks.get(Pagelinks.size()-1);       
	        String endnum = end.split(pageNumRex)[end.split(pageNumRex).length-1];
	        List<String> tegerlinks = new ArrayList<String>();
	        thisLink=thisLink + blogFlag;
	        
	        for(int i=2; i <= Integer.parseInt(endnum); ++i){
	        	tegerlinks.add(thisLink+i);
	        }
	        
	        page.addTargetRequests(tegerlinks);		//获取分页link去爬即可
        }*/
        
	}

	/**
	 * 抓取博客内容
	 * @param page
	 */
	private void getPage(Page page){
        
        String title = page.getHtml().xpath(titleRex).toString();
        String content = page.getHtml().$(contentRex).toString();
        String tags = page.getHtml().xpath(tagsRex).all().toString();
        
        if(null == content || null == title){
        	return;
        }
        
        if(null != tags){
        	tags = tags.substring(tags.indexOf("[")+1,tags.indexOf("]"));
        }

        OscBlogReplacer oscReplacer= new OscBlogReplacer(hashtable);	//设置工具类映射关系
    	String oscContent = oscReplacer.replace(codeRex,content);		//处理代码格式
    	
        page.putField("content", oscContent);
        page.putField("title", title);
        page.putField("tags", tags);
	}
	

    @Override
    public Site getSite() {
        return site;
    }

}