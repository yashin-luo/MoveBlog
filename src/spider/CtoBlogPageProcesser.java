package spider;

import java.util.Hashtable;
import java.util.List;
import common.OscBlogReplacer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 51cto���������߼�
 * @author oscfox
 * @date 20140114
 */
public class CtoBlogPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setDomain("blog.51cto.com");
	
	private String urlString ;
	private String name;
	private String id;
	private String codeRex = "<pre\\s*class=\"brush:(.*);.*;\">"; 	//�������������ʽ
	private Hashtable<String, String> hashtable=null;				//����classӳ���ϵ
	
	public CtoBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		urlString = url;
		String string=urlString.split("//")[1];
		name = string.split("\\.")[0];
		id = url.split("/")[urlString.split("/").length - 1];
	}
	
	/**
	 * ��ȥ�������ݵȣ����������������д���Ĳ���ת��Ϊoschina���ʹ����ʽ
	 */
	@Override
    public void process(Page page) {
		//http://coffeescript.iteye.com/blog/2003321
        List<String> links = page.getHtml().links().regex("http://"+name+"\\.blog\\.51cto\\.com/"+id+"/\\d+").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//div[@class='showTitleBOx']/div[@class='showTitle']/text()").toString());
        page.putField("tags",page.getHtml().xpath("//div[@class='showTags']/a/text()").all());
        page.putField("link", page.getHtml().xpath("//div[@class='CopyrightStatement lh22']/a/@href").toString());
        
        String oldContent = page.getHtml().$("div.showContent").toString();
        if(null == oldContent){
        	return;
        }
        //��ʼ��ӳ���ϵ 
        initMap();
		//���ù�����ӳ���ϵ
		OscBlogReplacer.setHashtable(hashtable);
		//��������ʽ,
    	String oscContent = OscBlogReplacer.replace(codeRex,oldContent);
        page.putField("content", oscContent);
	}

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * ��ʼ��ӳ���ϵ��ֻ��ʼ����������ͬ����class���Բ�һ���ġ�
     * �ֱ�Ϊ:iteye�� osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();
		
		/*hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");*/
	}
}