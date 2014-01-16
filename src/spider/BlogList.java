package spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;  
import beans.Blog;
/**
 * �����ȡ�Ĳ����б�
 * @author oscfox
 *
 */
public class BlogList {

	//�û�������Ӧһ���û��б�����û�Ϊ���û���put�µ��б�
	private static Map<String, ArrayList<Blog>> blogMap = new ConcurrentHashMap <String,ArrayList<Blog>>();
	
	public static void addBlog(String user, Blog blog) {
		
		ArrayList<Blog> blogList;
		
		if(blogMap.containsKey(user)){
			blogList= blogMap.get(user);
		} else{
			blogList = new ArrayList<Blog>();
		}
		
		blogList.add(blog);
		blogMap.put(user, blogList);
	}
	
	public static void clearBlogList(String user) {
		blogMap.remove(user);
	}
	
	
	public static List<Blog> getBlogList(String user) {
		if(blogMap.containsKey(user)){
			return blogMap.get(user);
		}
		
		return null;
	}
	
	public static Blog getBlog(String user, int blogIndex) {
		if(blogMap.containsKey(user)){
			return blogMap.get(user).get(blogIndex);
		}
		return null;
	}
	
}
