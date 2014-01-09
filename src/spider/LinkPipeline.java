package spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.LinksList;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


/**
 * 抓取csdn博客主页的所有博客链接
 * @author oscfox
 * @date 20140106
 */
public class LinkPipeline implements Pipeline {

	
	private Map<String, Object> fields = new HashMap<String, Object>();
	
    @SuppressWarnings("unchecked")
	@Override
    public void process(ResultItems resultItems, Task task) {
    	
    	fields = resultItems.getAll();

        System.out.println("get page: " + resultItems.getRequest().getUrl());
    	for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
    	
    	List<String> linksList = (ArrayList<String>)fields.get("links");
    	
    	try {
    		LinksList.addLinks(linksList);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
}
