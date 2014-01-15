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
	
	private String name="";	//����������
								//<pre class="brush:js;toolbar:false;">
	private String codeRex = "<pre\\s*class=\"brush:(.*);.*\">"; 								//�������������ʽ
	private Hashtable<String, String> hashtable = new Hashtable<String,String>();;				//����classӳ���ϵ
	
	public CtoBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		String string=url.split("//")[1];
		name = string.split("\\.")[0];
	}
	
	/**
	 * ���������ݵȣ����������������д���Ĳ���ת��Ϊoschina���ʹ����ʽ
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