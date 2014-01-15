package spider;

import java.util.Hashtable;
import java.util.List;

import common.OscBlogReplacer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Iteye���������߼�
 * @author oscfox
 * @date 20140114
 */
public class IteyeBlogPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setDomain("www.iteye.com");
	private String name="";															//����ԭurl ��������
							//<pre class="java" name="code">
	private String codeRex = "<pre\\s*.*\\s*class=\"(.*)\".*>"; 					//�������������ʽ
	private Hashtable<String, String> hashtable = new Hashtable<String,String>();	//����classӳ���ϵ
	
	public IteyeBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		String string=url.split("//")[1];
		name = string.split("\\.")[0];
	}
	
	/**
	 * ��ȥ�������ݵȣ����������������д���Ĳ���ת��Ϊoschina���ʹ����ʽ
	 */
	@Override
    public void process(Page page) {
		//http://coffeescript.iteye.com/blog/2003321
        List<String> links = page.getHtml().links().regex("http://"+name+"\\.iteye\\.com/blog/\\d+").all();
        page.addTargetRequests(links);
        
        String title = page.getHtml().xpath("//div[@class='blog_main']/div[@class='blog_title']/h3/a/text()").toString();
        String oldContent = page.getHtml().$("div.blog_content").toString();
        String tags = page.getHtml().xpath("//div[@class='news_tag']/a/text()").all().toString();
        
        if(null == oldContent || null == title){
        	return;
        }
        
        if(null != tags){
        	tags = tags.substring(tags.indexOf("[")+1,tags.indexOf("]"));
        }
        
        initMap();														//��ʼ��ӳ���ϵ
		OscBlogReplacer.setHashtable(hashtable);						//���ù�����ӳ���ϵ
    	String oscContent = OscBlogReplacer.replace(codeRex,oldContent);//��������ʽ
        
        page.putField("title", title);
        page.putField("tags", tags);
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
		hashtable.put("c", "cpp");
	}
}