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
import oschina.BlogApi;

@WebServlet("/action/usercatalog")
public class UserCatalogAction extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String client_id = request.getParameter("client_id");
		String authorname = request.getParameter("authorname");
		
		if(!StringUtils.isNumeric(client_id) || StringUtils.isBlank(authorname))
			return;
		
		String reString = BlogApi.getBlogUserCatalog(client_id,authorname);
		JsonMsg.json_out(JSON.toJSONString(reString), response);
	}
}
