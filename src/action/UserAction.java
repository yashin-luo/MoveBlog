package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import common.JsonMsg;

import oschina.UserApi;
import beans.User;



@WebServlet("/action/user")
public class UserAction extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		if(!StringUtils.isNumeric(user_id))
			return;

		String access_token =  Oauth2Action.Users().get(Long.valueOf(user_id));
	
		if(null == access_token){
			JsonMsg.json_out(JsonMsg.jsonError("请从新认证！",JsonMsg.ERROR_CODE_AUTH), response);
			return;
		}
		
		User user = UserApi.getUser(access_token);
		JsonMsg.json_out(JSON.toJSONString(user), response);
	}
}
