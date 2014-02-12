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

@WebServlet("/action/syscatalog")
public class SysCatalogAction extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String client_id = request.getParameter("client_id");
		
		if(!StringUtils.isNumeric(client_id))
			return;
		
		String reString = BlogApi.getBlogSysCatalog(client_id);
		JsonMsg.json_out(JSON.toJSONString(reString), response);
	}
}
