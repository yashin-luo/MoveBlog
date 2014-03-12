package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import common.JsonMsg;
import oschina.BlogApi;

@WebServlet("/action/syscatalog")
public class SysCatalogAction extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String client_id = request.getParameter("client_id");
		
		if(StringUtils.isBlank(client_id))
			return;
		
		String reString = BlogApi.getBlogSysCatalog(client_id);
		JsonMsg.json_out(reString, response);
	}
}
