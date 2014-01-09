package spider;

import java.util.HashMap;
import java.util.Map;

import beans.Blog;
import beans.BlogList;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


/**
 * csdn²©¿Íµ¼Èëoschina
 * @author oscfox
 * @date 20140106
 */
public class GetBlogPipeline implements Pipeline {

	
	private Map<String, Object> fields = new HashMap<String, Object>();
	
    @Override
    public void process(ResultItems resultItems, Task task) {
    	
    	/*
        System.out.println("get page: " + resultItems.getRequest().getUrl());
    	for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    	*/
    	fields = resultItems.getAll();
    	
    	BlogList.addBlog(new Blog(fields));
    	
    }
}
