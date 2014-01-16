package oschina;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import beans.User;
import com.google.gson.Gson;
import common.AppConfigTool;

/**
 * oschina 获取用户信息api
 * @author oscfox
 *
 */
public class UserApi {
	
	private static String type="json";
	
	/**
	 * 根据access_token 获取用户信息
	 * @param access_token
	 * @return
	 */
	public static User getUser(String access_token) {
		HttpClient client = new HttpClient();
		//User-Agent
		client.getParams().setParameter(HttpMethodParams.USER_AGENT,
				"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803");
		
		PostMethod method = new PostMethod(AppConfigTool.osc_host+ AppConfigTool.openapi_user);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
		
		NameValuePair access_token_ = new NameValuePair("access_token",access_token);  
		NameValuePair type_ = new NameValuePair("type",type);
		
        method.setRequestBody(new NameValuePair[] { access_token_,type_});  
		
		String responsestr = "";
		
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
			User user = gson.fromJson(responsestr, User.class);
			return user;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	

}
