package beans;

import java.util.Map;


/**
 * ���� beans
 * @author oscfox
 *
 */
public class Blog {
	
	//private String access_token="";	//	true	oauth2_token��ȡ��access_token	
	
	private String 	title="";			//	true		���ͱ���	
	private String 	content="";			//	true		��������	
	private String 	classification;		//	true		ϵͳ���ͷ���
	private String 	save_as_draft="0";	//	false		���浽�ݸ� �ǣ�1 ��0	0
	private String 	catalog;			//	false		���ͷ���	
	private String 	abstracts="";		//	false		����ժҪ	
	private String 	tags="";			//	false		���ͱ�ǩ���ö��Ÿ���	
	private String 	type="1";			//	false		ԭ����1��ת�أ�4	1
	private String 	origin_url="";		//	false		ת�ص�ԭ������	
	private String 	privacy="0";		//	false		������0��˽�У�1	0
	private String 	deny_comment="0";	//	false		�������ۣ�0����ֹ���ۣ�1	0
	private String 	auto_content="0";	//	false		�Զ�����Ŀ¼��0�����Զ�����Ŀ¼��1	0
	private String 	as_top="0";			//	false		���ö���0���ö���1	0
	
	private String link="";				//	ԭ��������
	private long id;					//	
	
	public Blog(Map<String, Object> blogMap) throws Exception {
		
		Object title=blogMap.get("title");						//	true		���ͱ���	
		Object content=blogMap.get("content");					//	true		��������	
		Object classification = blogMap.get("classification");	//	true		ϵͳ���ͷ���
		Object save_as_draft = blogMap.get("save_as_draft");	//	false		���浽�ݸ� �ǣ�1 ��0	0
		Object catalog = blogMap.get("catalog");				//	false		���ͷ���	
		Object abstracts = blogMap.get("abstracts");			//	false		����ժҪ	
		Object tags = blogMap.get("tags");						//	false		���ͱ�ǩ���ö��Ÿ���	
		Object type = blogMap.get("type");						//	false		ԭ����1��ת�أ�4	1
		Object origin_url = blogMap.get("origin_url");			//	false		ת�ص�ԭ������	
		Object privacy = blogMap.get("privacy");				//	false		������0��˽�У�1	0
		Object deny_comment = blogMap.get("deny_comment");		//	false		�������ۣ�0����ֹ���ۣ�1	0
		Object auto_content = blogMap.get("auto_content");		//	false		�Զ�����Ŀ¼��0�����Զ�����Ŀ¼��1	0
		Object as_top = blogMap.get("as_top");					//	false		���ö���0���ö���1	0
		Object link = blogMap.get("link");						//	ԭ��������
		Object id = blogMap.get("id");							//	
		
		if(null == content || null == title || null == classification){
			throw new Exception("blogȱ����Ҫ����");
		}

		this.setContent(content.toString());
		this.setTitle(title.toString());
		this.setClassification(classification.toString());
		
		if(null != save_as_draft){
			this.setSave_as_draft(save_as_draft.toString());
		}
		
		if(null != catalog){
			this.setCatalog(catalog.toString());
		}
		
		if(null != abstracts){
			this.setAbstracts(abstracts.toString());
		}
		
		if(null != tags){
			this.setTags(tags.toString());
		}
		
		if(null != type){
			this.setType(type.toString());
		}
		
		if(null != origin_url){
			this.setOrigin_url(origin_url.toString());
		}
		
		if(null != privacy){
			this.setPrivacy(privacy.toString());
		}
		
		if(null != deny_comment){
			this.setDeny_comment(deny_comment.toString());
		}
		
		if(null != auto_content){
			this.setAuto_content(auto_content.toString());
		}
		
		if(null != as_top){
			this.setAs_top(as_top.toString());
		}
		
		if(null != id){
			this.setId((long)id);
		}
		
		if(null != link){
			this.setLink(link.toString());
		}
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

	public String getTags(){
		return tags;
	}
	
	public String getTagString() {
		String string = tags.toString();
		return string.substring(1, string.lastIndexOf(']')); 
		 
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the classification
	 */
	public String getClassification() {
		return classification;
	}

	/**
	 * @param classification the classification to set
	 */
	public void setClassification(String classification) {
		this.classification = classification;
	}

	/**
	 * @return the save_as_draft
	 */
	public String getSave_as_draft() {
		return save_as_draft;
	}

	/**
	 * @param save_as_draft the save_as_draft to set
	 */
	public void setSave_as_draft(String save_as_draft) {
		this.save_as_draft = save_as_draft;
	}

	/**
	 * @return the catalog
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	/**
	 * @return the abstracts
	 */
	public String getAbstracts() {
		return abstracts;
	}

	/**
	 * @param abstracts the abstracts to set
	 */
	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the origin_url
	 */
	public String getOrigin_url() {
		return origin_url;
	}

	/**
	 * @param origin_url the origin_url to set
	 */
	public void setOrigin_url(String origin_url) {
		this.origin_url = origin_url;
	}

	/**
	 * @return the privacy
	 */
	public String getPrivacy() {
		return privacy;
	}

	/**
	 * @param privacy the privacy to set
	 */
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	/**
	 * @return the deny_comment
	 */
	public String getDeny_comment() {
		return deny_comment;
	}

	/**
	 * @param deny_comment the deny_comment to set
	 */
	public void setDeny_comment(String deny_comment) {
		this.deny_comment = deny_comment;
	}

	/**
	 * @return the auto_content
	 */
	public String getAuto_content() {
		return auto_content;
	}

	/**
	 * @param auto_content the auto_content to set
	 */
	public void setAuto_content(String auto_content) {
		this.auto_content = auto_content;
	}

	/**
	 * @return the as_top
	 */
	public String getAs_top() {
		return as_top;
	}

	/**
	 * @param as_top the as_top to set
	 */
	public void setAs_top(String as_top) {
		this.as_top = as_top;
	}
}