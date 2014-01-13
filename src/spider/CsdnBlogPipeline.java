package spider;

import java.util.HashMap;
import java.util.Map;

import beans.Blog;
import beans.BlogList;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


/**
 * ��csdn���ʹ��벿��ת��Ϊoschina�������ͣ���������BlogList
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
    	
    	//��������ʽ
    	oscBlog.setContent(oscBlog.getContent());
    	
    	BlogList.addBlog(oscBlog);
    	
    }
}
