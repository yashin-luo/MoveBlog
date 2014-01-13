package oschina;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import com.google.gson.Gson;

/**
 * oschina验证api
 * @author oscfox
 *
 */
public class Oauth2Api {

	
	//测试用host
	private static String hostString = "http://www.oschina.com";
	//oschina host
	//private String hostString = "http://www.oschina.";\
	
	/*
	private static String client_secret="7uAqJvTYjSQKv3NMK8KSapMRDohhmTgV";	//应用私钥
	private static String client_id="J2pBh55ca84TzkGZ1ZYv";	//应用id
	private static String redirect_uri="http://www.moveblog.com:8080/";	//回调地址
	*/
	private static String client_secret="FJLnihKY54W8p8z68gI9RraErpk5dFpY";	//应用私钥
	private static String client_id="yKqX3IQWBvft0W8JXz0k";	//应用id
	private static String redirect_uri="http://www.moveblog.com:8080/Oauth2Action";	//回调地址
	
	private static String access_token="";
	private static String code="";
	
	private static String token_href="/action/openapi/token?"
			+"client_secret="+client_secret
			+"&client_id="+client_id
			+"&grant_type=authorization_code"
			+"&redirect_uri="+redirect_uri
			+"&code=";
	
	/**
	 * 根据code获取oschina的 token
	 * @param code
	 * @return
	 */
	public static String GetToken(String code){
		HttpClient client = new HttpClient();
		//User-Agent
		client.getParams().setParameter(HttpMethodParams.USER_AGENT,
				"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803");
		
		String token_ =hostString + token_href + code;
		HttpMethod method = new GetMethod(token_);
		String responsestr = new String();
		
		try {
			client.executeMethod(method);
			responsestr = new String(method.getResponseBodyAsString()); 
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		method.releaseConnection(); 

		Gson gson = new Gson();
		try {
			JsonData jsonData = gson.fromJson(responsestr, JsonData.class);
			return jsonData.getToken();
		} catch (Exception e) {
			
			// TODO: handle exception
		}
        
		return null;
        
	}
	
	public static String getAccess_token(String code_) {
		if(code.equals(code_))
			return access_token;
		code = code_;
		access_token = GetToken(code);
		return access_token;
	}
	
	public static String getAccess_token() {
		return access_token;
	}

	public static void setAccess_token(String access_token) {
		Oauth2Api.access_token = access_token;
	}

	/**
	 * oschina返回的access_token
	 * @author oscfox
	 *
	 */
    class JsonData//返回参数，保护access_token
    {
        private String access_token;
        private String token_type;
        private String expires_in;
        
        public JsonData() {
			
		}
        
        public String getToken() {
			return access_token;
		}
        
        @Override
        public String toString()
        {
            return "JSON1 [access_token=" + access_token + "token_type=" + token_type + "expires_in="+expires_in+ "]";
        }
    }
}
