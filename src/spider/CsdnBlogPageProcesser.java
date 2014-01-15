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
 * @date 20140114
 */
public class CsdnBlogPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setDomain("blog.csdn.net");
	private String name="";															//����ԭurl ��������
	private String codeRex = "<pre\\s*.*\\s*class=\"(.*)\">"; 						//�������������ʽ
	private Hashtable<String, String> hashtable = new Hashtable<String,String>();	//����classӳ���ϵ
	
	public CsdnBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		name = url.split("/")[url.split("/").length - 1];
	}
	
	/**
	 * ��ȥ�������ݵȣ����������������д���Ĳ���ת��Ϊoschina���ʹ����ʽ
	 */
	@Override
    public void process(Page page) {
        
        List<String> links = page.getHtml().links().regex("http://blog\\.csdn\\.net/"+name+"/article/details/\\d+").all();
        page.addTargetRequests(links);
        
        String title = page.getHtml().xpath("//div[@class='details']/div[@class='article_title']/h3/span/a/text()").toString();
        String oldContent = page.getHtml().$("div.article_content").toString();
        String tags = page.getHtml().xpath("//div[@class='tag2box']/a/text()").all().toString();
        
        if(null == oldContent || null == title){
        	return;
        }
        
        if(null != tags){
        	tags = tags.substring(tags.indexOf("[")+1,tags.indexOf("]"));
        }
        
        initMap();														//��ʼ��ӳ���ϵ
		OscBlogReplacer.setHashtable(hashtable);						//���ù�����ӳ���ϵ
    	String oscContent = OscBlogReplacer.replace(codeRex,oldContent);//��������ʽ
    	
        page.putField("content", oscContent);
        page.putField("title", title);
        page.putField("tags", tags);
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
		hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");
	}
}