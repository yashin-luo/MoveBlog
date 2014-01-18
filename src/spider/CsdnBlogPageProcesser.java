package spider;

import java.util.ArrayList;
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
public class CsdnBlogPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setDomain("blog.csdn.net");
	private String url="";
	private String name="";															//博客原url 的名字域
	private String codeRex = "<pre\\s*.*\\s*class=\"(.*)\">"; 						//代码过滤正则表达式
	private Hashtable<String, String> hashtable = new Hashtable<String,String>();	//代码class映射关系
	
	public CsdnBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		this.url = url;
		if(!url.contains("/article/details/")){
			name = url.split("/")[url.split("/").length - 1];
		}
	}
	
	/**
	 * 爬去博客内容等，并将博客内容中有代码的部分转换为oschina博客代码格式
	 */
	@Override
    public void process(Page page) {
        if(url.contains("/article/details/")){
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
		List<String> links = page.getHtml().xpath("//div[@class='list_item article_item']/div[@class='article_title']/h3/span/a/@href").all();
        List<String> titles = page.getHtml().xpath("//div[@class='list_item article_item']/div[@class='article_title']/h3/span/a/text()").all();
        
        page.putField("titles", titles);
        page.putField("links", links);
        
        String thisLink=page.getUrl().toString();
        		
        if(thisLink.endsWith(name)){
	        List<String> Pagelinks=page.getHtml().xpath("//div[@class='pagelist']/a/@href").all();   
	        
	        if(Pagelinks.isEmpty()){
	        	return;
	        }
	        
	        String end=Pagelinks.get(Pagelinks.size()-1);       
	        String endnum = end.split("/")[end.split("/").length-1];
	        List<String> tegerlinks = new ArrayList<String>();
	        thisLink=thisLink + "/article/list/";
	        
	        for(int i=2; i <= Integer.parseInt(endnum); ++i){
	        	tegerlinks.add(thisLink+i);
	        }
	        
	        page.addTargetRequests(tegerlinks);		//获取分页link去爬即可
        }
        
	}

	/**
	 * 抓取博客内容
	 * @param page
	 */
	private void getPage(Page page){
        
        String title = page.getHtml().xpath("//div[@class='details']/div[@class='article_title']/h3/span/a/text()").toString();
        String oldContent = page.getHtml().$("div.article_content").toString();
        String tags = page.getHtml().xpath("//div[@class='tag2box']/a/text()").all().toString();
        
        if(null == oldContent || null == title){
        	return;
        }
        
        if(null != tags){
        	tags = tags.substring(tags.indexOf("[")+1,tags.indexOf("]"));
        }
        
        initMap();														//初始化映射关系
		OscBlogReplacer.setHashtable(hashtable);						//设置工具类映射关系
    	String oscContent = OscBlogReplacer.replace(codeRex,oldContent);//处理代码格式
    	
        page.putField("content", oscContent);
        page.putField("title", title);
        page.putField("tags", tags);
	}
	

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 初始化映射关系，只初始化代码类型同样而class属性不一样的。
     * 分别为:csdn， osc
     */
	private void initMap() {
		hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");
	}
}