
package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oschina.Oauth2Api;
import oschina.UserApi;
import beans.User;

/**
 * 获取认证action
 * @author oscfox
 *
 */
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
			json_out("授权码获取失败",response);
			return;
		}
		
		//根据oschina authorization_code 回调的code请求 access_token
		String access_token= Oauth2Api.getAccess_token(code);
		if(access_token.isEmpty()){//授权码获取失败
			json_out("access_token获取失败",response);
			return;
		}
		
		//根据access_token 获取User
		User user = UserApi.getUser(access_token);
		
		Users.put(Long.valueOf(user.getId()), access_token);
		
		//创建对象
        Cookie u = new Cookie("user",user.getId()) ;
        //设定有效时间  以s为单位
        u.setMaxAge(600) ;
        //设置Cookie路径和域名
        u.setPath("/") ;
        String userhref = user.getUrl();
        //创建对象
        Cookie linkcookie = new Cookie("href",userhref) ;
        //设定有效时间  以s为单位
        linkcookie.setMaxAge(600) ;
        //设置Cookie路径和域名
        linkcookie.setPath("/") ;
        //发送Cookie文件
        response.addCookie(u) ;
		
		response.sendRedirect("/index2.html");
		
		//String reString = user.toString();
		//json_out(reString,response);
	}

	/**
	 * 以json返回action结果
	 * @param json
	 * @param response
	 * @throws IOException
	 */
	public static void json_out(String json,HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("expires", "0");
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		out.print(json);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		Users = new ConcurrentHashMap<Long,String>();
		super.init(config);
	}
}
