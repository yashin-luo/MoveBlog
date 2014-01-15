package spider;

import java.util.Hashtable;
import java.util.List;

import common.OscBlogReplacer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * cnblogs博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class CnBlogPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setDomain("www.cnblogs.com");
	private String name="";															//博客原url 的名字域
	//<pre class="brush:java;gutter:true;"> 
	private String codeRex = "<pre\\s*class=\"brush:(.*);.*\">"; 					//代码过滤正则表达式
	private Hashtable<String, String> hashtable = new Hashtable<String,String>();	//代码class映射关系
	
	public CnBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		name = url.split("/")[url.split("/").length - 1];	//获取博主名字域
	}
	
	/**
	 * 爬博客内容等，并将博客内容中有代码的部分转换为oschina博客代码格式
	 */
	@Override
    public void process(Page page) {
		//http://www.cnblogs.com/scgw/
        List<String> links = page.getHtml().links().regex("http://www\\.cnblogs\\.com/"+name+"/p/.*\\.html").all();
        page.addTargetRequests(links);
        
        String title = page.getHtml().xpath("//a[@id='cb_post_title_url']/text()").toString();//title 标签不唯一，有的能抓有的不能抓
        String oldContent = page.getHtml().xpath("//div[@id='cnblogs_post_body']").toString();
        String tags = page.getHtml().xpath("//div[@id='EntryTagad']/a/text()").all().toString();	//cnblogs中tags不在网页源码中，获取不了
        
        if(null == oldContent || null == title){
        	return;
        }
        
        if(null != tags){
        	tags.substring(1,tags.length()-1);
        }
        
        initMap();														//初始化映射关系
		OscBlogReplacer.setHashtable(hashtable);						//设置工具类映射关系
    	String oscContent = OscBlogReplacer.replace(codeRex,oldContent);//处理代码格式

    	page.putField("title", title);	
        page.putField("content", oscContent);
        page.putField("tags",tags); 
	}

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 初始化映射关系，只初始化代码类型同样而class属性不一样的。
     * 分别为:cnblogs， osc
     */
	private void initMap() {
		/*hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");*/
	}
}