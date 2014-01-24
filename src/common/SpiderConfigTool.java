package common;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class SpiderConfigTool {
	
	private String configPath ="D:\\workspace\\MoveBlog\\build\\classes\\common\\Spider.xml";//this.getClass().getResource("")+"/Spider.xml";
	private File file;
	private SAXReader reader;
	private Document doc;
	private Node spiderNode;
	
	public SpiderConfigTool(String spiderName){
		
		try {
			file = new File(configPath);
			if(!file.isDirectory() && !file.isFile()){
				return;
			}
			reader = new SAXReader();
			doc = reader.read(file);
			spiderNode = getSpider(spiderName); 
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Node getSpiderNode(){
		return spiderNode;
	}
	
	@SuppressWarnings("unchecked")
	private Node getSpider(String spiderName){
		List<Node> list = doc.selectNodes("config/spider-cofig");
		
		for(Node i : list){
			if(i.selectSingleNode("name").getText().equals(spiderName)){
				return i;
			}
		}
		
		return null;
	}
	
	public Document getDoc(){
		return doc;
	}
	
}
