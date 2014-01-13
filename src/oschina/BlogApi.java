package oschina;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import beans.Blog;

import com.google.gson.Gson;

/**
 * oschina 获取博客api
 * @author oscfox
 *
 */
public class BlogApi {
	
	//测试用host
	private static String hostString = "http://www.oschina.com";
	//oschina host
	//private String hostString = "http://www.oschina.";
	
	private static String blogpubString = hostString + "/action/openapi/blog_pub";
	
	/**
	 * 将博客导入oschina
	 * @param blog
	 * @return
	 */
	public static String pubBlog(Blog blog, String access_token) {
		String title = blog.getTitle();
		String content = blog.getContent();
		String catalog = "";
		String tags = blog.getTags();
		String reString = pubBlog(access_token, title, content, catalog, tags, "418860");
		return reString;
	}
	
	/**
	 * 将博客导入oschina
	 * @param access_token
	 * @param title
	 * @param content
	 * @param catalog
	 * @param tags
	 * @param classification
	 * @return
	 */
	protected static String pubBlog(String access_token,String title, String content, String catalog, String tags, String classification) {
		HttpClient client = new HttpClient();
		//User-Agent
		client.getParams().setParameter(HttpMethodParams.USER_AGENT,
				"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803");
		
		PostMethod method = new PostMethod(blogpubString);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
		NameValuePair access_token_ = new NameValuePair("access_token",access_token);  
		NameValuePair title_ = new NameValuePair("title",title);
		NameValuePair content_ = new NameValuePair("content",content);
		NameValuePair catalog_ = new NameValuePair("catalog",catalog);
		NameValuePair tags_ = new NameValuePair("tags",tags);
		NameValuePair classification_ = new NameValuePair("classification",classification);
        method.setRequestBody(new NameValuePair[] { access_token_,title_,content_,catalog_,tags_,classification_});  
		
		String responsestr = "";
		
		try {
			client.executeMethod(method);
			responsestr = new String(method.getResponseBodyAsString()); 
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		method.releaseConnection(); 
		return new Gson().toJson(responsestr);
	}
	

}
