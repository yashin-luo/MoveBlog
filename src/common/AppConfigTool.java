package common;

public class AppConfigTool {
	
	public static String osc_host = "http://www.oschina.com";						//oschina域名- 测试
	//public static String osc_host = "http://www.oschina.net";						//oschina域名
	
	//oschina open api应用信息: 需要申请！
	public static String client_secret = "FJLnihKY54W8p8z68gI9RraErpk5dFpY";			//应用私钥
	public static String client_id = "yKqX3IQWBvft0W8JXz0k";							//应用id
	public static String redirect_uri = "http://www.moveblog.com:8080/Oauth2Action";	//回调地址
	
	//api 接口
	public static String oauth2_token = "/action/openapi/token";					//oauth2_token api
	public static String blog_pub = "/action/openapi/blog_pub";						//blog_pub api
	public static String openapi_user = "/action/openapi/user";						//user api
}
