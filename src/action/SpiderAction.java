
package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import beans.Blog;
import beans.BlogList;
import spider.CsdnBlogPageProcesser;
import spider.GetBlogPipeline;
import us.codecraft.webmagic.Spider;

/**
 * 爬虫调用action
 * @author oscfox
 *
 */
public class SpiderAction extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url=request.getParameter("url");
		String result="";
		List<Blog> blogList;
		
		BlogList.clearBlogList(); //清楚原有列表
		//爬取博客，结果存放在BLogList中
        Spider.create(new CsdnBlogPageProcesser(url))
        	.addUrl(url)
             .addPipeline(new GetBlogPipeline()).run();
        
        blogList = BlogList.getBlogList();
        result = new Gson().toJson(blogList);
		json_out(result, response);
	}
	
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
