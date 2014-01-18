package spider;

import java.util.Hashtable;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * cnblogs���������߼�
 * @author oscfox
 * @date 20140114
 */
public class CnBlogPageProcesser extends BlogPageProcessor{
	
	
public CnBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("www.cnblogs.com");
		site.setSleepTime(1);
		
		blogFlag="/p/";																//����ԭurl ��������
		codeRex = "<pre\\s*class=\"brush:(.*);.*\">"; 								//�������������ʽ
		
		linksRex="//div[@class='postTitle']/a/@href";								//�����б���˱��ʽ
		titlesRex="//div[@class='postTitle']/a/text()";								//title�б���˱��ʽ
		
		
		contentRex="div.cnblogs_post_body";											//���ݹ��˱��ʽ
		titleRex="//a[@id='cb_post_title_url']/text()";								//title���˱��ʽ
		tagsRex="//div[@id='EntryTagad']/a/text()";									//tags���˱��ʽ
		
		
		this.url=url;
		
		if(!url.contains(blogFlag)){
			name = url.split("/")[url.split("/").length - 1];
		}
		//http://www.cnblogs.com/hadoopdev/default.html?page=3
		PagelinksRex="http://www\\.cnblogs\\.com/"+name+"/default.html?page=\\d+";	//��ҳ����
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
     * �ֱ�Ϊ:cnblogs�� osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();	//����classӳ���ϵ
		/*hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");*/
	}
}