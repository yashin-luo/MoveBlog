package oschina;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import beans.Blog;
import com.google.gson.Gson;
import common.AppConfigTool;

/**
 * oschina 获取博客api
 * @author oscfox
 *
 */
public class BlogApi {
	/**
	 * 将博客导入oschina
	 * @param blog
	 * @param access_token
	 * @return
	 */
	public static String pubBlog(Blog blog, String access_token) {
		
		String reString = pubBlog(
				access_token, 
				blog.getTitle(),
				blog.getContent(),
				blog.getAbstracts(),
				blog.getOrigin_url(),
				blog.getTags(),
				blog.getAs_top(),
				blog.getAuto_content(),
				blog.getCatalog(),
				blog.getDeny_comment(),
				blog.getClassification(),
				blog.getType(),
				blog.getSave_as_draft(),
				blog.getPrivacy());
		
		return reString;
	}
	
	/**
	 * 将博客导入oschina
	 * @param access_token
	 * @param title
	 * @param content
	 * @param abstracts
	 * @param origin_url
	 * @param tags
	 * @param as_top
	 * @param auto_content
	 * @param catalog
	 * @param deny_comment
	 * @param classification
	 * @param type
	 * @param save_as_draft
	 * @param privacy
	 * @return
	 */
	protected static String pubBlog(
			String access_token,
			String title, 
			String content, 
			String abstracts,
			String origin_url, 
			String tags, 
			String as_top, 
			String auto_content, 
			String catalog, 
			String deny_comment, 
			String classification,
			String type,
			String save_as_draft,
			String privacy) {
		
		HttpClient client = new HttpClient();
		//User-Agent
		client.getParams().setParameter(HttpMethodParams.USER_AGENT,
				"Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803");
		
		PostMethod method = new PostMethod(AppConfigTool.osc_host+AppConfigTool.blog_pub);
		
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
		
		NameValuePair access_token_ = new NameValuePair("access_token",access_token);  
		NameValuePair title_ = new NameValuePair("title",title);
		NameValuePair content_ = new NameValuePair("content",content);
		NameValuePair catalog_ = new NameValuePair("catalog",catalog);
		NameValuePair tags_ = new NameValuePair("tags",tags);
		NameValuePair classification_ = new NameValuePair("classification",classification);
		NameValuePair abstracts_ = new NameValuePair("abstracts",abstracts);  
		NameValuePair origin_url_ = new NameValuePair("origin_url",origin_url);
		NameValuePair as_top_ = new NameValuePair("as_top",as_top);
		NameValuePair auto_content_ = new NameValuePair("auto_content",auto_content);
		NameValuePair deny_comment_ = new NameValuePair("deny_comment",deny_comment);
		NameValuePair type_ = new NameValuePair("type",type);
		NameValuePair save_as_draft_ = new NameValuePair("save_as_draft",save_as_draft);
		NameValuePair privacy_ = new NameValuePair("privacy",privacy);
		
        method.setRequestBody(
        		new NameValuePair[] {access_token_,title_,content_,classification_,
        				catalog_,tags_,abstracts_,origin_url_,as_top_,auto_content_,
        				deny_comment_,type_,save_as_draft_,privacy_} );  
		
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
