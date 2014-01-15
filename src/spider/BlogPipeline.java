package spider;

import java.util.HashMap;
import java.util.Map;

import beans.Blog;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


/**
 * �ɹ�blog��������BlogList
 * @author oscfox
 * @date 
 */
public class BlogPipeline implements Pipeline{

	private Map<String, Object> fields = new HashMap<String, Object>();
	
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

    	BlogList.addBlog(oscBlog);
    	
    	//����
    	System.out.println("get page: " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    	
    }
}
