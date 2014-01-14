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
	
	private String name;
	//<pre class="brush:java;gutter:true;"> 
	private String codeRex = "<pre\\s*class=\"brush:(.*);.*;\">"; 	//�������������ʽ
	private Hashtable<String, String> hashtable=null;				//����classӳ���ϵ
	
	public CnBlogPageProcesser(String url) {
		this.site.setSleepTime(10);
		
		name = url.substring(url.indexOf("com/")+4);
	}
	
	/**
	 * ��ȥ�������ݵȣ����������������д���Ĳ���ת��Ϊoschina���ʹ����ʽ
	 */
	@Override
    public void process(Page page) {
		//http://www.cnblogs.com/scgw/
        List<String> links = page.getHtml().links().regex("http://www\\.cnblogs\\.com/"+name+"/p/.*\\.html").all();
        
        page.addTargetRequests(links);
        
        page.putField("title", page.getHtml().xpath("//div[@class='post']/h2/a/text()").toString());//title ��ǩ��Ψһ���е���ץ�еĲ���ץ
        page.putField("ags",page.getHtml().xpath("//div[@id='EntryTag']/a/text()").all()); //cnblogs��tags������ҳԴ���У���ȡ����
        page.putField("link", page.getHtml().xpath("//div[@class='post']/h2/a/@href").toString());
        
        String oldContent = page.getHtml().$("div.entry").toString();
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
     * �ֱ�Ϊ:cnblogs�� osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();
		
		/*hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");*/
	}
}