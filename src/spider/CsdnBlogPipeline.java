package spider;

import java.util.HashMap;
import java.util.Map;

import beans.Blog;
import beans.BlogList;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


/**
 * 将csdn博客代码部分转换为oschina博客类型，并保存至BlogList
 * @author oscfox
 * @date 
 */
public class CsdnBlogPipeline implements Pipeline {

	
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
    	Blog oscBlog = new Blog(fields);
    	
    	//处理代码格式
    	oscBlog.setContent(oscBlog.getContent());
    	
    	BlogList.addBlog(oscBlog);
    	
    }
}
