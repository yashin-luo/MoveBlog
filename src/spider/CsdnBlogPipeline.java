package spider;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import common.OscBlogReplacer;
import beans.Blog;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


/**
 * 处理代码格式并保存至BlogList
 * @author oscfox
 * @date 
 */
public class CsdnBlogPipeline implements Pipeline {

	
	private Map<String, Object> fields = new HashMap<String, Object>();
	
	private String csdnCodeRex = "<pre\\s*.*\\s*class=\"(.*)\">";
	private Hashtable<String, String> hashtable=null;
	
	protected void initHash() {
		hashtable = new Hashtable<String,String>();
		hashtable.put("csharp", "c#");
		hashtable.put("javascript", "js");
		hashtable.put("objc", "cpp");
	}
	
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
    	String csdnContent = oscBlog.getContent();
    	initHash();
    	OscBlogReplacer.setHashtable(hashtable);
    	String oscContent = OscBlogReplacer.replace(csdnCodeRex,csdnContent);
    	oscBlog.setContent(oscContent);
    	
    	BlogList.addBlog(oscBlog);
    	
    }
}
