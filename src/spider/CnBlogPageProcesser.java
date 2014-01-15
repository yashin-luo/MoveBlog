package spider;

import java.util.Hashtable;
import java.util.List;

import common.OscBlogReplacer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * cnblogs���������߼�
 * @author oscfox
 * @date 20140114
 */
public class CnBlogPageProcesser implements PageProcessor{
	
	private Site site = Site.me().setDomain("www.cnblogs.com");
	private String name="";															//����ԭurl ��������
	//<pre class="brush:java;gutter:true;"> 
	private String codeRex = "<pre\\s*class=\"brush:(.*);.*\">"; 					//�������������ʽ
	private Hashtable<String, String> hashtable = new Hashtable<String,String>();	//����classӳ���ϵ
	
	public CnBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		name = url.split("/")[url.split("/").length - 1];	//��ȡ����������
	}
	
	/**
	 * ���������ݵȣ����������������д���Ĳ���ת��Ϊoschina���ʹ����ʽ
	 */
	@Override
    public void process(Page page) {
		//http://www.cnblogs.com/scgw/
        List<String> links = page.getHtml().links().regex("http://www\\.cnblogs\\.com/"+name+"/p/.*\\.html").all();
        page.addTargetRequests(links);
        
        String title = page.getHtml().xpath("//a[@id='cb_post_title_url']/text()").toString();//title ��ǩ��Ψһ���е���ץ�еĲ���ץ
        String oldContent = page.getHtml().xpath("//div[@id='cnblogs_post_body']").toString();
        String tags = page.getHtml().xpath("//div[@id='EntryTagad']/a/text()").all().toString();	//cnblogs��tags������ҳԴ���У���ȡ����
        
        if(null == oldContent || null == title){
        	return;
        }
        
        if(null != tags){
        	tags.substring(1,tags.length()-1);
        }
        
        initMap();														//��ʼ��ӳ���ϵ
		OscBlogReplacer.setHashtable(hashtable);						//���ù�����ӳ���ϵ
    	String oscContent = OscBlogReplacer.replace(codeRex,oldContent);//��������ʽ

    	page.putField("title", title);	
        page.putField("content", oscContent);
        page.putField("tags",tags); 
	}

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * ��ʼ��ӳ���ϵ��ֻ��ʼ����������ͬ����class���Բ�һ���ġ�
     * �ֱ�Ϊ:cnblogs�� osc
     */
	private void initMap() {
		/*hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");*/
	}
}