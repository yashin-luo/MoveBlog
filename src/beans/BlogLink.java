package beans;


/**
 * �������� beans
 * @author oscfox
 *
 */
public class BlogLink {
	
	//private String access_token="";	//	true	oauth2_token��ȡ��access_token	
	
	private String 	title="";			//	true		���ͱ���	
	private String link="";				//	ԭ��������
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