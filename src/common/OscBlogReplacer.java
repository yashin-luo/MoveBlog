package common;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * ����ȡ�Ĳ��ʹ��벿��ת��Ϊoschina���ʹ�������
 * @author oscfox
 *
 */
public class OscBlogReplacer {
	
	//csdn, osc	��һ�µ�css���ԵĶ�Ӧ��ϵ
	private Hashtable<String, String> hashtable = null;
	
	public OscBlogReplacer(Hashtable<String, String> hashtable) {
		this.hashtable = hashtable;
	}
	
	public void setHashtable(Hashtable<String, String> hash){
		hashtable = hash;
	}
	
	@SuppressWarnings("rawtypes")
	public String replace(String rex, String csdn) {
		String osc=csdn;
		
		//2.һ�µ�css�����滻
		osc=osc.replaceAll(rex,"<pre class=\"brush:$1;toolbar:false\">");  
		//1.��һ�µ�css�����滻
		for(Iterator itr = hashtable.keySet().iterator(); itr.hasNext();){ 
			String key = (String) itr.next(); 
			String value = (String) hashtable.get(key); 
			osc=osc.replaceAll("<pre class=\"brush:"+key+";toolbar:false\">", "<pre class=\"brush:"+value+";toolbar:false\">");
		} 
		
		return osc;
	}
}
