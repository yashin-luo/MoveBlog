package common;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

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
	public String replace(List<String> rexBegin, List<String> rexEnd, String oldString) {
		String osc=oldString;
		
		//��ǩͷ
		for(int i=0; i<rexBegin.size(); ++i){
			osc=osc.replaceAll(rexBegin.get(i),"<pre class=\"brush:$1;toolbar:true\">");  
		}
		
		//��ǩβ
		for(int i=0; i<rexEnd.size(); ++i){
			osc=osc.replaceAll(rexEnd.get(i),"</pre>");  
		}
		
		
		//1.��һ�µ�css�����滻
		for(Iterator itr = hashtable.keySet().iterator(); itr.hasNext();){ 
			String key = (String) itr.next(); 
			String value = (String) hashtable.get(key); 
			osc=osc.replaceAll("<pre class=\"brush:"+key+";toolbar:true\">", "<pre class=\"brush:"+value+";toolbar:true\">");
		} 
		
		return osc;
	}
}
