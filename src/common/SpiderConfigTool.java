package common;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class SpiderConfigTool {

	private String configPath = "/Spider.xml";
	private File file;
	private SAXReader reader;
	private Document doc;
	private Node spiderNode;
	
	public SpiderConfigTool(String spiderName) throws DocumentException{

		file = new File(this.getClass().getResource("").getPath()+configPath);
		if(!file.exists()){
			return;
		}
		reader = new SAXReader();
		doc = reader.read(file);
		spiderNode = getSpider(spiderName); 
		
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
