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
public class BlogPageProcessor implements PageProcessor{
	
	protected Site site = new Site();
	protected String url;
	protected String blogFlag;			//����url�����ݱ�־��
	protected String name;				//����ԭurl ��������
	protected String codeRex;			//�������������ʽ
	
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
        
       /* 
        String thisLink=page.getUrl().toString();
        		
        if(thisLink.endsWith(name)){
	        List<String> Pagelinks=page.getHtml().xpath(PagelinksRex).all();   
	        
	        if(Pagelinks.isEmpty()){
	        	return;
	        }
	        
	        String end=Pagelinks.get(Pagelinks.size()-1);       
	        String endnum = end.split(pageNumRex)[end.split(pageNumRex).length-1];
	        List<String> tegerlinks = new ArrayList<String>();
	        thisLink=thisLink + blogFlag;
	        
	        for(int i=2; i <= Integer.parseInt(endnum); ++i){
	        	tegerlinks.add(thisLink+i);
	        }
	        
	        page.addTargetRequests(tegerlinks);		//��ȡ��ҳlinkȥ������
        }*/
        
	}

	/**
	 * ץȡ��������
	 * @param page
	 */
	private void getPage(Page page){
        
        String title = page.getHtml().xpath(titleRex).toString();
        String content = page.getHtml().$(contentRex).toString();
        String tags = page.getHtml().xpath(tagsRex).all().toString();
        
        if(null == content || null == title){
        	return;
        }
        
        if(null != tags){
        	tags = tags.substring(tags.indexOf("[")+1,tags.indexOf("]"));
        }

        OscBlogReplacer oscReplacer= new OscBlogReplacer(hashtable);	//���ù�����ӳ���ϵ
    	String oscContent = oscReplacer.replace(codeRex,content);		//��������ʽ
    	
        page.putField("content", oscContent);
        page.putField("title", title);
        page.putField("tags", tags);
	}
	

    @Override
    public Site getSite() {
        return site;
    }

}