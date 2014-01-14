package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * ²©¿Í beans
 * @author oscfox
 *
 */
public class Blog {

	private long id;
	private String link = "";
	private String title = "";
	private String content = "";
	private List<String> tags;
	
	@SuppressWarnings("unchecked")
	public Blog(Map<String, Object> blogMap) throws Exception {
		
		Object content = blogMap.get("content");
		Object title = blogMap.get("title");
		
		if(null == content || null == title){
			throw new Exception("");
		}
		
		
		try {
			this.setTags((ArrayList<String>)blogMap.get("tags"));	
			this.setLink(blogMap.get("link").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		this.setContent(content.toString());
		this.setTitle(title.toString());
		
	}
	
	public Blog(String url){
		this.link = url;
	}
	
	public Blog(String link,String title,String content){
		this.link = link;
		this.title = title;
		this.content = content;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTags() {
		String string = tags.toString();
		String str = string.substring(1, string.lastIndexOf(']'));  
		return str;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
}