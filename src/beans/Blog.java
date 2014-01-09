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
	public Blog(Map<String, Object> blogMap) {
		this.setContent(blogMap.get("content").toString());
		this.setLink(blogMap.get("link").toString());
		this.setTitle(blogMap.get("title").toString());
		this.setTags((ArrayList<String>)blogMap.get("tags"));
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