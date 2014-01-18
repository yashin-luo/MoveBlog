package spider;

import java.util.Hashtable;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * 51cto���������߼�
 * @author oscfox
 * @date 20140114
 */
public class CtoBlogPageProcesser extends BlogPageProcessor{
	
	public CtoBlogPageProcesser(String url) {
		
		site = Site.me().setDomain("blog.51cto.com");
		site.setSleepTime(1);
		
		blogFlag="/\\d+/";																		//����ԭurl ��������
		codeRex.add("<pre\\.+?class=\"brush:(\\.+?);\\.*?\"\\.*?>");											//�������������ʽ
		
		linksRex="//div[@class='blogList']/div[@class='artHead']/div/h3[@class='artTitle']/a/@href";	//�����б���˱��ʽ
		titlesRex="//div[@class='blogList']/div[@class='artHead']/div/h3[@class='artTitle']/a/text()";	//title�б���˱��ʽ
		
		contentRex="div.showContent";																	//���ݹ��˱��ʽ
		titleRex="//div[@class='showTitleBOx']/div[@class='showTitle']/text()";							//title���˱��ʽ
		tagsRex="//div[@class='showTags']/a/text()";													//tags���˱��ʽ
		
		this.url=url;
		String urlString = url.split("//")[1];
		name = urlString.split("\\.")[0];
		
		//http://tongcheng.blog.51cto.com/6214144/p-3
		PagelinksRex="http://"+name+"\\.blog\\.51cto\\.com/\\d+/p-\\d+";	
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
     * �ֱ�Ϊ:iteye�� osc
     */
	private void initMap() {
		hashtable = new Hashtable<String,String>();	//����classӳ���ϵ
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