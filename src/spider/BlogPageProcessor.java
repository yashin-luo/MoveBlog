package spider;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import common.OscBlogReplacer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * csdn���������߼�
 * @author oscfox
 * @date 20140114
 */
public class BlogPageProcessor implements PageProcessor{
	
	protected Site site = new Site();
	protected String url;
	protected String blogFlag;			//����url�����ݱ�־��
	protected String name;				//����ԭurl ��������
	protected List<String> codeBeginRex; 		//�������������ʽ
	protected List<String> codeEndRex; 		//�������������ʽ
	
	protected String linksRex;			//�����б���˱��ʽ
	protected String titlesRex;			//title�б���˱��ʽ
	protected String PagelinksRex;		//���ҳ�б���˱��ʽ
		
	protected String contentRex;		//���ݹ��˱��ʽ
	protected String titleRex;			//title���˱��ʽ
	protected String tagsRex;			//tags���˱��ʽ
	
	
	protected Hashtable<String, String> hashtable;	//����classӳ���ϵ
	
	
	/**
	 * ץȡ�������ݵȣ����������������д���Ĳ���ת��Ϊoschina���ʹ����ʽ
	 */
	@Override
    public void process(Page page) {
        if(url.contains(blogFlag)){
        	getPage(page);
        	page.putField("getlinks", false);
        } else {
			getLinks(page);
			page.putField("getlinks", true);
		}
	}
	
	/**
	 * ץȡ�����б�
	 * @param page
	 */
	private void getLinks(Page page) {
		List<String> links = page.getHtml().xpath(linksRex).all();
        List<String> titles = page.getHtml().xpath(titlesRex).all();
        
        page.putField("titles", titles);
        page.putField("links", links);
        
        List<String> Pagelinks = page.getHtml().links().regex(PagelinksRex).all();
        page.addTargetRequests(Pagelinks);
        
	}

	/**
	 * ץȡ��������
	 * @param page
	 */
	private void getPage(Page page){
        
        String title = page.getHtml().xpath(titleRex).toString();
        String content = page.getHtml().$(contentRex).toString();
        String tags = page.getHtml().xpath(tagsRex).all().toString();
        
        if(StringUtils.isBlank(content) || StringUtils.isBlank(title)){
        	return;
        }
        
        if(!StringUtils.isBlank(tags)){
        	tags = tags.substring(tags.indexOf("[")+1,tags.indexOf("]"));
        }

        OscBlogReplacer oscReplacer= new OscBlogReplacer(hashtable);	//���ù�����ӳ���ϵ
    	String oscContent = oscReplacer.replace(codeBeginRex, codeEndRex, content);		//��������ʽ
    	
        page.putField("content", oscContent);
        page.putField("title", title);
        page.putField("tags", tags);
	}
	

    @Override
    public Site getSite() {
        return site;
    }

}