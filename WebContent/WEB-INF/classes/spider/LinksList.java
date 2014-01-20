package spider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;  

import beans.BlogLink;
/**
 * 爬虫获取的博客列表
 * @author oscfox
 *
 */
public class LinksList {

	//用户名，对应一个用户列表，如果用户为新用户则put新的列表
	private static Map<String, ConcurrentHashMap<String, BlogLink>> linkMap = new ConcurrentHashMap <String,ConcurrentHashMap<String, BlogLink>>();
	
	public static void addLinks(String user, List<BlogLink> links) {
		
		ConcurrentHashMap<String,BlogLink> linkList;
		
		if(linkMap.containsKey(user)){
			linkList= linkMap.get(user);
		} else{
			linkList = new ConcurrentHashMap<String,BlogLink>();
		}
		
		//put links 去重复
		for(int i=0; i<links.size(); ++i){
			String key = links.get(i).getLink();
			
			if(linkList.containsKey(key)){	//重复，不提交
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
			return new ArrayList<BlogLink>(hash.values());	//hash to list
		}
		
		return null;
	}
	
}