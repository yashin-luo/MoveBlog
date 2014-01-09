package beans;

public class User {
	
	private String id;
	private String email;
	private String name;
	private String gender;
	private String avatar;		//: "http://www.oschina.net/uploads/user/****",
	private String location;	//: "¹ã¶« ÉîÛÚ",
	private String url;			//: "http://home.oschina.net/****"
	
    @Override
    public String toString()
    {
        return "JSON1 [id=" + id + "email=" + email 
        		+ "name="+name+ "gender=" + gender 
        		+ "avatar="+ avatar + "location"+location
        		+"url"+url+"]";
    }

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}



	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}



	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}



	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}



	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}



	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}



	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}