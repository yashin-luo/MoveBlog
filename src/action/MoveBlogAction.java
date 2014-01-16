
package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oschina.BlogApi;
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

		String user="";
		Cookie[] cookie = request.getCookies();

		for (int i = 0; i < cookie.length; i++) {
			Cookie cook = cookie[i];
			if(cook.getName().equalsIgnoreCase("user")){ //获取键 
				user = cook.getValue().toString();
			}
		}
		
		if(user.isEmpty()){//授权码获取失败
			json_out("请先授权!",response);
			return;
		}
		
		String index = request.getParameter("indexx");
		
		if(index.isEmpty()){//id获取失败
			json_out("index获取失败",response);
			return;
		}
		
		Blog blog = BlogList.getBlog(user, Integer.parseInt(index));
		/**
		 * 可设置blog非必要参数：
		 *	save_as_draft=0;	//	false		保存到草稿 是：1 否：0	0
		 *	catalog;			//	false		博客分类	
		*	abstracts="";		//	false		博客摘要	
		*	tags="";			//	false		博客标签，用逗号隔开	
		*	type=1;				//	false		原创：1、转载：4	1
		*	origin_url="";		//	false		转载的原文链接	
		*	privacy=0;			//	false		公开：0、私有：1	0
		*	deny_comment=0;		//	false		允许评论：0、禁止评论：1	0
		*	auto_content=0;		//	false		自动生成目录：0、不自动生成目录：1	0
		*	as_top=0;			//	false		非置顶：0、置顶：1	0
		 */
		
		String reString = BlogApi.pubBlog(blog,Oauth2Action.Users.get(user));	//根据access_token 导入blog
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
