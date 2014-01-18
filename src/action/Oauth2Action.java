
package action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import common.JsonMsg;

import oschina.Oauth2Api;
import oschina.UserApi;
import beans.User;

/**
 * 获取认证action
 * @author oscfox
 *
 */
@WebServlet("/Oauth2Action")
public class Oauth2Action extends HttpServlet {
	
	public static ConcurrentHashMap<Long, String> Users;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		
		if(code.isEmpty()){//授权码获取失败
			JsonMsg.json_out(JsonMsg.jsonError("授权码获取失败"),response);
			return;
		}
		
		//根据oschina authorization_code 回调的code请求 access_token
		String access_token= Oauth2Api.getAccess_token(code);
		if(StringUtils.isBlank(access_token)){//授权码获取失败
			JsonMsg.json_out(JsonMsg.jsonError("access_token获取失败"),response);
			return;
		}
		
		//根据access_token 获取User
		User user = UserApi.getUser(access_token);
		
		Users().put(Long.valueOf(user.getId()), access_token);
		
        Cookie u = new Cookie("user",user.getId()) ;
        u.setMaxAge(600) ;
        u.setPath("/") ;
        String userhref = user.getUrl();
        Cookie linkcookie = new Cookie("href",userhref) ;
        linkcookie.setMaxAge(600) ;    
        linkcookie.setPath("/") ;
        Cookie username = new Cookie("username",URLEncoder.encode(user.getName(),"UTF-8")) ;
        username.setMaxAge(600) ;    
        username.setPath("/") ;
        response.addCookie(u) ;
		response.sendRedirect("/index.html");
		
		//String reString = user.toString();
		//json_out(reString,response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		Users = new ConcurrentHashMap<Long,String>();
		super.init(config);
	}
	
	public static ConcurrentHashMap<Long, String> Users(){
		if(Users==null){
			Users = new ConcurrentHashMap<Long,String>();
		}
		return Users;
	}
}
