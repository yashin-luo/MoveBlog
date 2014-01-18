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
	
	public static void addBlog(Blog blog) {
		
		if(blogMap.containsKey(blog.getLink())){
			//�Ѵ��ڲ��ͣ����쳣��û����
			blogMap.put(blog.getLink(), blog);
		} else{
			blogMap.put(blog.getLink(), blog);
		}
	}
	
	public static Blog getBlog(String link) {
		if(blogMap.containsKey(link)){
			return blogMap.remove(link);
		}
		return null;
	}
	
}
