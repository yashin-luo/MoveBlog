
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
import spider.BlogList;
import spider.BlogPipeline;
import spider.CnBlogPageProcesser;
import spider.CsdnBlogPageProcesser;
import spider.IteyeBlogPageProcesser;
import spider.CtoBlogPageProcesser;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

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
		
		String user=request.getParameter("user");
		
		BlogList.clearBlogList(user); 	//清除原有列表
		
		PageProcessor pageProcessor = getBlogSitePageProcessor(url);
		
		//爬取博客，结果存放在BLogList中
        Spider.create(pageProcessor)
        	.addUrl(url)
             .addPipeline(new BlogPipeline(user)).run();
        
        blogList = BlogList.getBlogList(user);
        result = new Gson().toJson(blogList);
		json_out(result, response);
	}
	
	/**
	 * //根据url选择博客类型
	 * @param url
	 * @return
	 */
	private PageProcessor getBlogSitePageProcessor(String url){
		if(url.contains("www.cnblogs.com")){
			
			return new CnBlogPageProcesser(url);
			
		}else if(url.contains("blog.csdn.net")){
			
			return new CsdnBlogPageProcesser(url);
			
		}else if(url.contains("blog.51cto.com")){
			
			return new CtoBlogPageProcesser(url);
		
		}else if(url.contains("www.iteye.com")){
			
			return new IteyeBlogPageProcesser(url);
		
		}else {
			
			return new IteyeBlogPageProcesser(url);
		}
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
