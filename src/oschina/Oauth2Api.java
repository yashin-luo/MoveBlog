package oschina;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import com.google.gson.Gson;
import common.AppConfigTool;

/**
 * oschina验证api
 * @author oscfox
 *
 */
public class Oauth2Api {
	/**
	 * 根据code获取oschina的 token
	 * @param code
	 * @return
	 */
	public static String getAccess_token(String code){
		HttpClient client = new HttpClient();
		//User-Agent
		client.getParams().setParameter(HttpMethodParams.USER_AGENT,
				"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803");
		
		String token_href = AppConfigTool.osc_host
				+AppConfigTool.oauth2_token
				+"?client_secret="+AppConfigTool.client_secret
				+"&client_id="+AppConfigTool.client_id
				+"&grant_type=authorization_code"
				+"&redirect_uri="+AppConfigTool.redirect_uri
				+"&code="+code;

		HttpMethod method = new GetMethod(token_href);
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
