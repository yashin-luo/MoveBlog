package spider;

import java.util.ArrayList;
import java.util.List;

import beans.LinksList;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * csdn���������߼�
 * @author oscfox
 * @date 20140106
 */
public class CsdnLinksPageProcesser implements PageProcessor {
	
	private Site site = Site.me().setDomain("blog.csdn.net");
	
	private String urlString ;
	private String name;
	
	public CsdnLinksPageProcesser(String url) {
		urlString = url;
		name = urlString.split("/")[urlString.split("/").length - 1];
	}
	
	@Override
    public void process(Page page) {
        List<String> links= new ArrayList<String>();
        page.addTargetRequests(links);
        
        //�������ӣ�ֻҪ����������
        links = page.getHtml().links().regex("http://blog\\.csdn\\.net/" + name + "/article/details/\\d+").all();
        //������һ����ȥ��ҳ������
        page.addTargetRequests(links);
        //��������
        page.putField("links", links);
    }

    @Override
    public Site getSite() {
        return site;
    }

    //����
	public static void main(String[] args) {
		//��ȡ��������
        Spider.create(new CsdnLinksPageProcesser("http://blog.csdn.net/foxyaxin"))
        	.addUrl("http://blog.csdn.net/foxyaxin")
             .addPipeline(new LinkPipeline()).run();
        
        System.out.print(LinksList.getLinkList());
    }
}