
package action;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.google.gson.Gson;
import common.JsonMsg;
import beans.BlogLink;
import spider.BlogPipeline;
import spider.BlogPageProcessor;
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
		
		String url = request.getParameter("url");
		
		if(StringUtils.isBlank(url))
			return;
		
		if(!url.contains("http://")){
			url="http://"+url;
		}
		
		String user="";
		Cookie[] cookie = request.getCookies();

		for (int i = 0; i < cookie.length; i++) {
			Cookie cook = cookie[i];
			if(cook.getName().equalsIgnoreCase("user")){ //获取键 
				user = cook.getValue().toString();
				break;
			}
		}
		
		if(StringUtils.isBlank(user)){//授权码获取失败
			JsonMsg.json_out(JsonMsg.jsonError("请先授权!"),response);
			return;
		}
		
		PageProcessor pageProcessor=null;
		try {
			pageProcessor = new BlogPageProcessor(url);
		} catch (Exception e) {
			e.printStackTrace();
			JsonMsg.json_out(JsonMsg.jsonError("暂不支持该博客网站!"),response);
			return;
		}
		LinksList.clearList(user);
		//爬取博客，结果存放在BLogList中
        Spider.create(pageProcessor)
        	.addUrl(url)
             .addPipeline(new BlogPipeline(user)).run();
        
        List<BlogLink> linkList=LinksList.getLinkList(user);
        if(null == linkList){
        	JsonMsg.json_out(JsonMsg.jsonError("链接有误或抓取超时！"), response);
        }
        result = new Gson().toJson(linkList);
        JsonMsg.json_out(result, response);
	}
}
