
package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.JsonMsg;

import oschina.BlogApi;
import spider.BlogList;
import spider.BlogPipeline;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import beans.Blog;

/**
 * 博客搬家action
 * @author oscfox
 *
 */
@WebServlet("/action/moveblog")
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
				break;
			}
		}
		
		if(user.isEmpty()){//授权码获取失败
			JsonMsg.json_out(JsonMsg.jsonError("请先授权!"),response);
			return;
		}
		
		String link = request.getParameter("link");
		
		if(link.isEmpty()){//id获取失败
			JsonMsg.json_out(JsonMsg.jsonError("link获取失败"),response);
			return;
		}
		
		Blog blog=BlogList.getBlog(link); //已存在，不用抓取
		
		if(null == blog){
			PageProcessor pageProcessor = SpiderAction.getBlogSitePageProcessor(link);
			if(null == pageProcessor){
				JsonMsg.json_out(JsonMsg.jsonError("暂不支持该博客网站!"),response);
				return;
			}
			//爬取博客，结果存放在BLogList中
	        Spider.create(pageProcessor)
	        	.addUrl(link)
	             .addPipeline(new BlogPipeline(user)).run();
	        blog=BlogList.getBlog(link);
		}
		

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
		long key = Long.valueOf(user);
		String token = Oauth2Action.Users.get(key);
		String reString = BlogApi.pubBlog(blog,token);	//根据access_token 导入blog
		
		JsonMsg.json_out(reString,response);
	}

}
