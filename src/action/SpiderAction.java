
package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import common.JsonMsg;

import beans.BlogLink;
import spider.BlogPipeline;
import spider.CnBlogPageProcesser;
import spider.CsdnBlogPageProcesser;
import spider.IteyeBlogPageProcesser;
import spider.CtoBlogPageProcesser;
import spider.LinksList;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬虫调用action
 * @author oscfox
 *
 */

@WebServlet("/action/spider")
public class SpiderAction extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String result="";
		
		String url=request.getParameter("url");
		
		String user="";
		Cookie[] cookie = request.getCookies();

		for (int i = 0; i < cookie.length; i++) {
			Cookie cook = cookie[i];
			if(cook.getName().equalsIgnoreCase("user")){ //获取键 
				user = cook.getValue().toString();
				break;
			}
		}
		
		if(user.isEmpty()){//授权码获取失败
			JsonMsg.json_out(JsonMsg.jsonError("请先授权!"),response);
			return;
		}
		
		LinksList.clearLinkList(user);		//清除已抓取的博客列表
		
		PageProcessor pageProcessor = getBlogSitePageProcessor(url);
		if(null == pageProcessor){
			JsonMsg.json_out(JsonMsg.jsonError("暂不支持该博客网站!"),response);
			return;
		}
		
		//爬取博客，结果存放在BLogList中
        Spider.create(pageProcessor)
        	.addUrl(url)
             .addPipeline(new BlogPipeline(user)).run();
        
        List<BlogLink> linkList=LinksList.getLinkList(user);
        result = new Gson().toJson(linkList);
        JsonMsg.json_out(result, response);
	}
	
	/**
	 * //根据url选择博客类型
	 * @param url
	 * @return
	 */
	public static PageProcessor getBlogSitePageProcessor(String url){
		if(url.contains("www.cnblogs.com")){
			
			return new CnBlogPageProcesser(url);
			
		}else if(url.contains("blog.csdn.net")){
			
			return new CsdnBlogPageProcesser(url);
			
		}else if(url.contains("blog.51cto.com")){
			
			return new CtoBlogPageProcesser(url);
		
		}else if(url.contains("iteye.com")){
			
			return new IteyeBlogPageProcesser(url);
		
		}else {
			
			return null;
		}
	}
	

	
}
