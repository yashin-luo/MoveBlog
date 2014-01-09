package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * ��ץȡ���Ĳ��������б�
 * @author oscfox
 * @date 20140106
 */
public class LinksList {

	private static HashMap<String, String> linksMap = new HashMap<String, String>();
	
	/**
	 * ��ȡ�����б�
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
	 * ���б��������ӣ�ȥ�ظ�
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
	 * ȡ��һ������
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
