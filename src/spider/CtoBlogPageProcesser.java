package spider;

import java.util.Hashtable;
import java.util.List;

import common.OscBlogReplacer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 51cto博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class CtoBlogPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setDomain("blog.51cto.com");
	
	private String name="";	//博主名字域
								//<pre class="brush:js;toolbar:false;">
	private String codeRex = "<pre\\s*class=\"brush:(.*);.*\">"; 								//代码过滤正则表达式
	private Hashtable<String, String> hashtable = new Hashtable<String,String>();;				//代码class映射关系
	
	public CtoBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		String string=url.split("//")[1];
		name = string.split("\\.")[0];
	}
	
	/**
	 * 爬博客内容等，并将博客内容中有代码的部分转换为oschina博客代码格式
	 */
	@Override
    public void process(Page page) {
		//http://coffeescript.iteye.com/blog/2003321
        List<String> links = page.getHtml().links().regex("http://"+name+"\\.blog\\.51cto\\.com/.+/\\d+").all();
        page.addTargetRequests(links);
        
        String title = page.getHtml().xpath("//div[@class='showTitleBOx']/div[@class='showTitle']/text()").toString();
        String tags = page.getHtml().xpath("//div[@class='showTags']/a/text()").all().toString();
        String oldContent = page.getHtml().$("div.showContent").toString();

        if(null == oldContent || null == title){
        	return;
        }
        
        if(null != tags){
        	tags = tags.substring(tags.indexOf("[")+1,tags.indexOf("]"));
        }
        
        initMap();														//初始化映射关系
		OscBlogReplacer.setHashtable(hashtable);						//设置工具类映射关系
    	String oscContent = OscBlogReplacer.replace(codeRex,oldContent);//处理代码格式
    	
    	page.putField("title", title);
        page.putField("tags", tags);
        page.putField("content", oscContent);
	}

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 初始化映射关系，只初始化代码类型同样而class属性不一样的。
     * 分别为:iteye， osc
     */
	private void initMap() {
		
		hashtable.put("bash", "shell");
		hashtable.put("delphi", "pascal");
		hashtable.put("pl", "perl");
		hashtable.put("ps", "shell");
		hashtable.put("plain", "lua");
		hashtable.put("cf", "cpp");
		hashtable.put("diff", "shell");
		hashtable.put("erlang", "lua");
		hashtable.put("jfx", "js");
	}
}