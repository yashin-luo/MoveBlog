package spider;

import java.util.Hashtable;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * 51cto博客爬虫逻辑
 * @author oscfox
 * @date 20140114
 */
public class CtoBlogPageProcesser extends BlogPageProcessor{
	
	public CtoBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("blog.51cto.com");
		site.setSleepTime(1);
		
		blogFlag="/\\d+/";																		//博客原url 的名字域
		codeRex.add("<pre\\.+?class=\"brush:(\\.+?);\\.*?\"\\.*?>");											//代码过滤正则表达式
		
		linksRex="//div[@class='blogList']/div[@class='artHead']/div/h3[@class='artTitle']/a/@href";	//链接列表过滤表达式
		titlesRex="//div[@class='blogList']/div[@class='artHead']/div/h3[@class='artTitle']/a/text()";	//title列表过滤表达式
		
		contentRex="div.showContent";																	//内容过滤表达式
		titleRex="//div[@class='showTitleBOx']/div[@class='showTitle']/text()";							//title过滤表达式
		tagsRex="//div[@class='showTags']/a/text()";													//tags过滤表达式
		
		this.url=url;
		String urlString = url.split("//")[1];
		name = urlString.split("\\.")[0];
		
		//http://tongcheng.blog.51cto.com/6214144/p-3
		PagelinksRex="http://"+name+"\\.blog\\.51cto\\.com/\\d+/p-\\d+";	
		initMap();		
	}
	
	@Override
    public void process(Page page) {
		super.process(page);
	}

    @Override
    public Site getSite() {
        return super.getSite();
    }


    /**
     * 初始化映射关系，只初始化代码类型同样而class属性不一样的。
     * 分别为:iteye， osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();	//代码class映射关系
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