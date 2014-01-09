package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 已抓取到的博客链接列表
 * @author oscfox
 * @date 20140106
 */
public class LinksList {

	private static HashMap<String, String> linksMap = new HashMap<String, String>();
	
	/**
	 * 获取博客列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getLinkList() {
		List<String> linkList = new ArrayList<String>();
		
		Iterator<Entry<String, String>> iter = linksMap.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    linkList.add(entry.getValue().toString());
		} 
		
		return linkList;
	}

	/**
	 * 往列表增加链接，去重复
	 * @param links
	 */
	public static void addLinks(List<String> links){
		int i;
		String link;
		for(i=0; i<links.size(); ++i){
			link = links.get(i);
			if(! linksMap.containsValue(link)){
				linksMap.put(link,link);
			}
		}
	}
	
	/**
	 * 取下一个链接
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getNextLink() {
		Iterator<Entry<String, String>> iter = linksMap.entrySet().iterator(); 
		if (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    return entry.getValue().toString();
		} 
		return null;
	}

	

}
