package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import common.AppConfigTool;
import common.JsonMsg;
import oschina.BlogApi;

@WebServlet("/action/syscatalog")
public class SysCatalogAction extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String user_id = null;
		Cookie[] cookie = request.getCookies();

		if(cookie == null){
			JsonMsg.json_out(JsonMsg.jsonError("请先授权!"),response);
			return;
		}
		for (int i = 0; i < cookie.length; i++) {
			Cookie cook = cookie[i];
			if(cook.getName().equalsIgnoreCase("user")){ //获取键 
				user_id = cook.getValue().toString();
				break;
			}
		}
		
		String reString = BlogApi.getBlogSysCatalog(user_id);
		JsonMsg.json_out(reString, response);
	}
}
