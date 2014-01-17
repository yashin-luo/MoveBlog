package spider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;  
import beans.Blog;
/**
 * �����ȡ�Ĳ����б�
 * @author oscfox
 *
 */
public class BlogList {

	//�û�������Ӧһ���û��б�����û�Ϊ���û���put�µ��б�
	private static Map<String, Blog> blogMap = new ConcurrentHashMap <String,Blog>();
	
	public static void addBlog(String user, Blog blog) {
		
		if(blogMap.containsKey(user)){
			//�Ѵ��ڲ��ͣ����쳣��û����
			blogMap.put(user, blog);
		} else{
			blogMap.put(user, blog);
		}
	}
	
	public static Blog getBlog(String user) {
		if(blogMap.containsKey(user)){
			Blog blog = blogMap.get(user);
			blogMap.remove(user);
			return blog;
		}
		return null;
	}
	
}
