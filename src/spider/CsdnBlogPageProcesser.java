package spider;

import java.util.Hashtable;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * csdn���������߼�
 * @author oscfox
 * @date 20140114
 */
public class CsdnBlogPageProcesser extends BlogPageProcessor{
	
	public CsdnBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("blog.csdn.net");
		site.setSleepTime(1);
		
		blogFlag="/article/details/";																	//����ԭurl ��������
		codeBeginRex.add("<pre.*?class=\"(.+?)\".*?>"); 												//�������������ʽ
		
		//<textarea class="java" cols="50" rows="15" name="code">
		codeBeginRex.add("<textarea.*?class=\"(.+?)\".*?>" );
		codeEndRex.add("</textarea>");		//</textarea>
		
		linksRex="//div[@class='list_item article_item']/div[@class='article_title']/h3/span/a/@href";	//�����б���˱��ʽ
		titlesRex="//div[@class='list_item article_item']/div[@class='article_title']/h3/span/a/text()";//title�б���˱��ʽ
		
		contentRex="div.article_content";																//���ݹ��˱��ʽ
		titleRex="//div[@class='details']/div[@class='article_title']/h3/span/a/text()";				//title���˱��ʽ
		tagsRex="//div[@class='tag2box']/a/text()";														//tags���˱��ʽ
		
		this.url=url;
		
		if(!url.contains(blogFlag)){
			name = url.split("/")[url.split("/").length - 1];
		}
		
		//http://blog.csdn.net/cxhzqhzq/article/list/2
		PagelinksRex="http://blog\\.csdn\\.net/"+name+"/article/list/\\d+";								//���ҳ�б���˱��ʽ
		
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