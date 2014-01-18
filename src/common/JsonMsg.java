package common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class JsonMsg {
	
	public static String jsonError(String json){
		return new Gson().toJson("{err:'"+json+"'}");
	}
	
	public static String jsonSuccess(String json){
		return new Gson().toJson("{sce:'"+json+"'}");
	}
	
	public static void json_out(String json,HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("expires", "0");
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		
		out.print(json);
	}

}
