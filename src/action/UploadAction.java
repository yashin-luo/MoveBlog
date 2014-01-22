
package action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.google.gson.Gson;

import common.JsonMsg;
import beans.Blog;
import beans.BlogLink;
import spider.BlogList;
import spider.LinksList;

/**
 * 上传wordpress xml文件
 * @author oscfox
 *
 */

@WebServlet("/action/upload")
public class UploadAction extends HttpServlet {

	private String savePath="/xmlfile";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

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
		
		if(StringUtils.isBlank(user)){//授权码获取失败
			JsonMsg.json_out(JsonMsg.jsonError("请先授权!"),response);
			return;
		}
		
		String filePath = request.getServletContext().getRealPath("/") + savePath;
		
		File cheakFile=new File(filePath);
		if  (!cheakFile.exists()  && !cheakFile.isDirectory()){       
		    cheakFile .mkdir();    
		} 
		
		File file = saveFile(filePath, request);	//保存文件
		
		if(null == file){
			JsonMsg.json_out(JsonMsg.jsonError("xml文件上传失败!文件过大或者文件格式有误！文件大小要求 < 10M,格式: .xml"), response);
			return;
		}
		
		//解析文件
		if(!analyzeXml(user, file)){
        	JsonMsg.json_out(JsonMsg.jsonError("xml文件解析有误，请检查格式后重试!"), response);
        	return;
        }
        
        List<BlogLink> linkList=LinksList.getLinkList(user);
        if(null == linkList){
        	JsonMsg.json_out(JsonMsg.jsonError("链接有误或抓取超时！"), response);
        	return;
        }
        
        String result="";
        result = new Gson().toJson(linkList);
        JsonMsg.json_out(result, response);
	}
	
	/**
	 * 保存
	 * @param path
	 * @param request
	 * @return
	 */
	private File saveFile(String path, HttpServletRequest request){
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(10*1024*1024); 
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(10*1024*1024);
		
		try {
			List<FileItem> items = upload.parseRequest(request);
			//限制文件大小，规定文件格式
			FileItem item = (FileItem) items.get(0);
			String fileName = item.getName();
			
			if(!fileName.endsWith(".xml")){
	        	return null;
			}
			
			File tempFile = new File(item.getName());

			File file = new File(path, tempFile.getName());	//上传文件的保存路径
			item.write(file);								//保存文件
			
			return file;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解析文件
	 * @param user
	 * @param file
	 * @return
	 */
	private boolean analyzeXml(String user, File file){
		try {
			//读取xml
            SAXReader reader = new SAXReader();
            Document doc = reader.read(file);
            
            List<Node> l = doc.selectNodes("rss/channel");

            if (l.size() == 0) {
            	throw new Exception("not a good rss-xml file");
            }
            Node channel = l.get(0);
            
            List<Node> itemList = channel.selectNodes("item");	//获取item列表
                
            List<BlogLink> links=new ArrayList<BlogLink>();		//生成该用户博客列表
            for(Node i : itemList){
            	Blog blog = new Blog(i);
            	BlogList.addBlog(blog);
                links.add(new BlogLink(blog));
            }
                
            LinksList.addLinks(user, links);
            return true;
		   
		} catch(Exception e){
			e.printStackTrace();
		}
	
		return false;
	}
}
