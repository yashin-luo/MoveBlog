package common;

public class AppConfigTool {
	
	public static String osc_host = "http://www.oschina.com";						//oschina����- ����
	//public static String osc_host = "http://www.oschina.net";						//oschina����
	
	//oschina open apiӦ����Ϣ: ��Ҫ���룡
	public static String client_secret = "FJLnihKY54W8p8z68gI9RraErpk5dFpY";			//Ӧ��˽Կ
	public static String client_id = "yKqX3IQWBvft0W8JXz0k";							//Ӧ��id
	public static String redirect_uri = "http://www.moveblog.com:8080/Oauth2Action";	//�ص���ַ
	
	//api �ӿ�
	public static String oauth2_token = "/action/openapi/token";					//oauth2_token api
	public static String blog_pub = "/action/openapi/blog_pub";						//blog_pub api
	public static String openapi_user = "/action/openapi/user";						//user api
}