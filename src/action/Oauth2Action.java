
package action;

import java.io.IOException;
import java.io.PrintWriter;

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
		String username = user.getName();
		//创建对象
        Cookie c = new Cookie("user",username) ;
        //设定有效时间  以s为单位
        c.setMaxAge(60) ;
        //设置Cookie路径和域名
        c.setPath("/") ;
        //发送Cookie文件
        response.addCookie(c) ;
		
		response.sendRedirect("/index.html");
		
		//String reString = user.toString();
		//json_out(reString,response);
	}


	/**
	 * 以json返回action结果
	 * @param json
	 * @param response
	 * @throws IOException
	 */
	private void json_out(String json,HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("expires", "0");
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		out.print(json);
	}
}
