package spider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;  
import beans.Blog;
/**
 * 爬虫获取的博客列表
 * @author oscfox
 *
 */
public class BlogList {

	//用户名，对应一个用户列表，如果用户为新用户则put新的列表
	private static Map<String, Blog> blogMap = new ConcurrentHashMap <String,Blog>();
	
	public static void addBlog(String user, Blog blog) {
		
		if(blogMap.containsKey(user)){
			//已存在博客，有异常，没处理
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
