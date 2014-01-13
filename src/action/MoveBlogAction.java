
package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oschina.BlogApi;
import oschina.Oauth2Api;
import spider.BlogList;
import beans.Blog;

/**
 * 博客搬家action
 * @author oscfox
 *
 */
public class MoveBlogAction extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String access_token= Oauth2Api.getAccess_token();
		if(access_token.isEmpty()){//授权码获取失败
			json_out("请先授权!",response);
			return;
		}
		
		String id = request.getParameter("id");
		if(id.isEmpty()){//id获取失败
			json_out("",response);
			return;
		}
		
		Blog blog = BlogList.getBlog(Long.parseLong(id));
		//根据access_token 导入blog
		String reString = BlogApi.pubBlog(blog,access_token);
		json_out(reString,response);
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
