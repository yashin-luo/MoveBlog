package spider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;  

import beans.BlogLink;
/**
 * �����ȡ�Ĳ����б�
 * @author oscfox
 *
 */
public class LinksList {

	//�û�������Ӧһ���û��б�����û�Ϊ���û���put�µ��б�
	private static Map<String, ConcurrentHashMap<String, BlogLink>> linkMap = new ConcurrentHashMap <String,ConcurrentHashMap<String, BlogLink>>();
	
	public static void addLinks(String user, List<BlogLink> links) {
		
		ConcurrentHashMap<String,BlogLink> linkList;
		
		if(linkMap.containsKey(user)){
			linkList= linkMap.get(user);
		} else{
			linkList = new ConcurrentHashMap<String,BlogLink>();
		}
		
		//put links ȥ�ظ�
		for(int i=0; i<links.size(); ++i){
			String key = links.get(i).getLink();
			
			if(linkList.containsKey(key)){	//�ظ������ύ
				continue;
			}
			
			linkList.put(key, links.get(i));
		}
		
		linkMap.put(user, linkList);
	}
	
	public static void clearLinkList(String user) {
		linkMap.remove(user);
	}
	
	public static List<BlogLink> getLinkList(String user) {
		ConcurrentHashMap<String, BlogLink> hash;
		if(linkMap.containsKey(user)){
			hash = linkMap.get(user);
			
			//hash to list
			List<BlogLink> linkList = new ArrayList<BlogLink>(hash.values());
			return linkList;
		}
		
		return null;
	}
	
	public static BlogLink getLink(String user, int blogIndex) {
		if(linkMap.containsKey(user)){
			return linkMap.get(user).get(blogIndex);
		}
		return null;
	}
	
}
