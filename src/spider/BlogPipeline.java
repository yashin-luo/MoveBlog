package spider;

import java.util.HashMap;
import java.util.Map;

import beans.Blog;
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
    	
    	Blog oscBlog = null;
    	try {
			oscBlog = new Blog(fields);
			oscBlog.setLink(resultItems.getRequest().getUrl());
		} catch (Exception e) {
			//e.printStackTrace();
			return ;
		}

    	BlogList.addBlog(user,oscBlog);
    	
    	//测试
    	System.out.println("get page: " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    	
    }
}
