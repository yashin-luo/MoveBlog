package beans;


/**
 * 博客链接 beans
 * @author oscfox
 *
 */
public class BlogLink {
	
	//private String access_token="";	//	true	oauth2_token获取的access_token	
	
	private String 	title="";			//	true		博客标题	
	private String link="";				//	原博客链接
	private long id;					//	
	
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}