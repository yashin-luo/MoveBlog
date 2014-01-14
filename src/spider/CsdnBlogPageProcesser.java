package spider;

import java.util.Hashtable;
import java.util.List;
import common.OscBlogReplacer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * csdn���������߼�
 * @author oscfox
 * @date 20140106
 */
public class CsdnBlogPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setDomain("blog.csdn.net");
	
	private String urlString ;
	private String name;
	private String csdnCodeRex = "<pre\\s*.*\\s*class=\"(.*)\">"; 	//�������������ʽ
	private Hashtable<String, String> hashtable=null;				//����classӳ���ϵ
	
	public CsdnBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		urlString = url;
		name = urlString.split("/")[urlString.split("/").length - 1];
	}
	
	/**
	 * ��ȥ�������ݵȣ����������������д���Ĳ���ת��Ϊoschina���ʹ����ʽ
	 */
	@Override
    public void process(Page page) {
        
        List<String> links = page.getHtml().links().regex("http://blog\\.csdn\\.net/"+name+"/article/details/\\d+").all();
        page.addTargetRequests(links);
        page.putField("title", page.getHtml().xpath("//div[@class='details']/div[@class='article_title']/h3/span/a/text()").toString());
        page.putField("tags",page.getHtml().xpath("//div[@class='tag2box']/a/text()").all());
        page.putField("link", page.getHtml().xpath("//div[@class='details']/div[@class='article_title']/h3/span/a/@href").toString());
        
        String oldContent = page.getHtml().$("div.article_content").toString();
        if(null == oldContent){
        	return;
        }
        //��ʼ��ӳ���ϵ
        initMap();
		//���ù�����ӳ���ϵ
		OscBlogReplacer.setHashtable(hashtable);
		//��������ʽ,
    	String oscContent = OscBlogReplacer.replace(csdnCodeRex,oldContent);
        page.putField("content", oscContent);
	}

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * ��ʼ��ӳ���ϵ��ֻ��ʼ����������ͬ����class���Բ�һ���ġ�
     * �ֱ�Ϊ:csdn�� osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();
		hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");
	}
}