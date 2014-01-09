package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 爬虫获取的博客列表
 * @author oscfox
 *
 */
public class BlogList {

	private static long blogIndex=0;
	private static Map<String,Blog> blogMap = new HashMap<String,Blog>();
	
	public static void addBlog(Blog blog) {
		String index=""+ blogIndex;
		blog.setId(blogIndex);
		blogIndex = blogIndex + 1;
		blogMap.put(index, blog);
	}
	
	public static void clearBlogList() {
		blogIndex = 0;
		blogMap.clear();
	}
	
	@SuppressWarnings("rawtypes")
	public static Blog getNextBlog() {
		Iterator<Entry<String, Blog>> iter = blogMap.entrySet().iterator(); 
		if (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    return (Blog)entry.getValue();
		} 
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Blog> getBlogList() {
		List<Blog> blogList = new ArrayList<Blog>();
		
		Iterator<Entry<String, Blog>> iter = blogMap.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    blogList.add((Blog)entry.getValue());
		} 
		
		return blogList;
	}
	
	public static Blog getBlog(long blogIndex) {
		String index=""+ blogIndex;
		return blogMap.get(index);
	}
	
}
