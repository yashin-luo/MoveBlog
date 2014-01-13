package common;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * 将爬取的博客代码部分转换为oschina博客代码类型
 * @author oscfox
 *
 */
public class OscBlogReplacer {
	
	//csdn, osc	不一致的css属性的对应关系
	private static Hashtable<String, String> hashtable = null;
	
	public static void setHashtable(Hashtable<String, String> hash){
		hashtable = hash;
	}
	
	public static String replace(String rex, String csdn) {
		String osc=csdn;
		
		//2.一致的css属性替换
		osc=osc.replaceAll(rex,"<pre class=\"brush:$1;toolbar:false\">");  
		//1.不一致的css属性替换
		for(Iterator itr = hashtable.keySet().iterator(); itr.hasNext();){ 
			String key = (String) itr.next(); 
			String value = (String) hashtable.get(key); 
			osc=osc.replaceAll("<pre class=\"brush:"+key+";toolbar:false\">", "<pre class=\"brush:"+value+";toolbar:false\">");
		} 
		
		return osc;
	}
}
