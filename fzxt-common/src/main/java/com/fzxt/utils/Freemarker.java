package com.fzxt.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * 创建人：FH 创建时间：2015年2月8日
 * @version
 */
public class Freemarker {

	/**
	 * 打印到控制台(测试用)
	 *  @param ftlName
	 */
	public static void print(String ftlName, Map<String,Object> root) throws Exception{
		try {
			Template temp = getTemplate(root.get("myproject").toString(),ftlName);		//通过Template可以将模板文件输出到相应的流
			temp.process(root, new PrintWriter(System.out));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出到输出到文件
	 * @param ftlName   ftl文件名
	 * @param root		传入的map
	 * @param outFile	输出后的文件全部路径
	 */
	public static void printFile(String ftlName, Map<String,Object> root, String outFile) throws Exception{
		try {
			File file = new File(outFile);
			if(!file.getParentFile().exists()){				//判断有没有父路径，就是判断文件整个路径是否存在
				file.getParentFile().mkdirs();			//不存在就全部创建
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
			Template template = getTemplate(root.get("myproject").toString(),ftlName);
			template.process(root, out);					//模版输出
			out.flush();
			out.close();
			System.out.println("======创建成功======"+outFile);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过文件名加载模版
	 * @param ftlName
	 */
	public static Template getTemplate(String project,String ftlName) throws Exception{
		try {
			Configuration cfg = new Configuration();  												//通过Freemaker的Configuration读取相应的ftl
			cfg.setEncoding(Locale.CHINA, "utf-8");
			cfg.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"\\"+project+ "/src/main/resources/createCodeftl"));		//设定去哪里读取相应的ftl模板文件
			Template temp = cfg.getTemplate(ftlName);												//在模板文件目录中找到名称为name的文件
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
