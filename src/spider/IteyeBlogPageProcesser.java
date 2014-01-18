package spider;

import java.util.Hashtable;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Iteye���������߼�
 * @author oscfox
 * @date 20140114
 */
public class IteyeBlogPageProcesser  extends BlogPageProcessor{
	
	public IteyeBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("www.iteye.com");
		site.setSleepTime(1);
		
		blogFlag="/blog/";															//����ԭurl ��������
		codeRex = "<pre\\s*.*\\s*class=\"(.*)\".*>"; 								//�������������ʽ
		
		linksRex="//div[@class='blog_main']/div[@class='blog_title']/h3/a/@href";	//�����б���˱��ʽ
		titlesRex="//div[@class='blog_main']/div[@class='blog_title']/h3/a/text()";//title�б���˱��ʽ
		
		contentRex="div.blog_content";												//���ݹ��˱��ʽ
		titleRex="//div[@class='blog_main']/div[@class='blog_title']/h3/a/text()";	//title���˱��ʽ
		tagsRex="//div[@class='news_tag']/a/text()";								//tags���˱��ʽ
		
		this.url=url;
		String urlString = url.split("//")[1];
		name = urlString.split("\\.")[0];
		
		//http://nodejs.iteye.com/?page=2
		PagelinksRex="http://"+name+"\\.iteye\\.com/\\?page=\\d+";					//���ҳ�б���˱��ʽ
		
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
     * ��ʼ��ӳ���ϵ��ֻ��ʼ����������ͬ����class���Բ�һ���ġ�
     * �ֱ�Ϊ:csdn�� osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();	//����classӳ���ϵ
		hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");
	}
}