package spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.Blog;
import beans.BlogLink;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


/**
 * 成功blog并保存至BlogList
 * @author oscfox
 * @date 
 */
public class BlogPipeline implements Pipeline{

	private Map<String, Object> fields = new HashMap<String, Object>();
	
	private String user;
	
	public BlogPipeline(String user){
		this.user = user;
	}
	
    @Override
    public void process(ResultItems resultItems, Task task) {

    	fields = resultItems.getAll();
    	
    	if((boolean)fields.get("getlinks")){
    		List<String> titles = (ArrayList<String>)fields.get("titles");
    		List<String> links = (ArrayList<String>)fields.get("links");
    		
    		List<BlogLink> linklist = new ArrayList<BlogLink>();
    		
    		for(int i=0; i<titles.size(); ++i){
    			BlogLink blogLink = new BlogLink();
    			blogLink.setTitle(titles.get(i));
    			blogLink.setLink(links.get(i));
    			linklist.add(blogLink);
    		}
    		
    		LinksList.addLinks(user, linklist);
    		
    	} else{
    	
	    	Blog oscBlog = null;
	    	try {
				oscBlog = new Blog(fields);
				oscBlog.setLink(resultItems.getRequest().getUrl());
			} catch (Exception e) {
				//e.printStackTrace();
				return ;
			}
	
	    	BlogList.addBlog(user,oscBlog);
	    	List<BlogLink> links=new ArrayList<BlogLink>();
	    	BlogLink blogLink = new BlogLink();
	    	blogLink.setLink(oscBlog.getLink());
	    	blogLink.setTitle(oscBlog.getTitle());
	    	links.add(blogLink);
	    	LinksList.addLinks(user, links);
    	}
    	
    }
}
