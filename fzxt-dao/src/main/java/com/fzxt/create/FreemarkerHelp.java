package com.fzxt.create;

import com.fzxt.utils.Freemarker;

import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FreemarkerHelp {
	   //用户自定义
		private static String rootPath = "com.fzxt";//目录

		private static String packageName ="courseType";//包名
		private static String tablename = "fzxt_course_type";//表名
		private static String objectName = "CourseType";//类名
		private static String remark = "科目分类";	 //注释
		private static String xmname = "fzxt-miniapp";//controller类项目名称

		private static String project = "fzxt-dao";//生成代码项目名称
		private static String driverClassName = "com.mysql.jdbc.Driver";
		private static String url = "jdbc:mysql://182.92.200.44:3306/fzxt?characterEncoding=utf-8";
		private static String username = "root";
		private static String password = "root";

		private static String keyType="String";//主键类型

		private static Pattern linePattern = Pattern.compile("_(\\w)");
		/**
		 * mian方法运行生成代码
		 * @param args
		 * @throws Exception 
		 */
		public static void main(String[] args) throws Exception {
			//开始生成
			Map<String,Object> data = new HashMap<String,Object>();		//创建数据模型
			data.put("rootPath", rootPath);
			data.put("fieldList", getFields());
			data.put("remark", remark);
			data.put("myproject", project);	//模板项目名称
			data.put("packageName", packageName);						//包名
			data.put("objectName", objectName);							//类名
			data.put("objectNameLower", lowerStr(objectName));		//类名(首字母改成 小写)
			data.put("tablename",tablename);								//表前缀	
			data.put("nowDate", new Date());
			data.put("keyType", keyType);//主键类型
			createMain(data);
			//System.out.println(lineToHump("ttt_sss"));
		}
		 /**
		    * 把输入字符串的首字母改成大写
		    * 
		    * @param str
		    * @return
		    */
		    private static String upperStr(String str) {
		        char[] ch = str.toCharArray();
		        if (ch[0] >= 'a' && ch[0] <= 'z') {
		            ch[0] = (char) (ch[0]-32);
		        }
		        return new String(ch);
		    }
		    /**
		     * 把输入字符串的首字母改成 小写
		     * 
		     * @param str
		     * @return
		     */
		    private static String lowerStr(String str) {
		    	char[] ch = str.toCharArray();
		    	if (ch[0] >= 'A' && ch[0] <= 'Z') {
		    		ch[0] = (char) (ch[0]+32);
		    	}
		    	return new String(ch);
		    }
		/**
		 * 获取字段信息
		 * @return
		 */
		public static List<String[]> getFields() {
			List<String[]> fieldList = new ArrayList<String[]>();  
			try {
				//获取数据库连接
				Connection conn = null;
				try {
					Class.forName(driverClassName);
					conn = DriverManager.getConnection(url, username, password);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	            DatabaseMetaData dbmd=conn.getMetaData();
	            //获取结果集
	            ResultSet rs = dbmd.getColumns(null, "%", tablename, "%");
	            int i=0;
	            while(rs.next()) { 
	            	String type = rs.getString("TYPE_NAME");//字段类型
	            	String name = rs.getString("COLUMN_NAME");//字段名称
	            	String remark = rs.getString("REMARKS");//字段注释
	            	String[] field= new String[6];
					field[0]=name;
	            	field[2]=remark;
	            	field[4]=upperStr(name);
					field[5]=lineToHump(name);//下划线转驼峰
	        		if(type.equals("DATETIME")){
	        			field[1]="Date";
	            		field[3]="否";
	        		} else	if(type.equals("INT")){
	            			field[1]="Integer";
	                		field[3]="是";
	                		if (i==0){
								keyType="Integer";
							}
	        		} else{
	        			field[1]="String";
	            		field[3]="是";
	        		}
	            	fieldList.add(field);
	        		i++;
	            }
	    		return fieldList;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return fieldList;
	        }
		}
		/** 下划线转驼峰 */
		public static String lineToHump(String str) {
			str = str.toLowerCase();
			Matcher matcher = linePattern.matcher(str);
			StringBuffer sb = new StringBuffer();
			while (matcher.find()) {
				matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
			}
			matcher.appendTail(sb);
			return sb.toString();
		}
		/**
		 * 生成代码主要方法
		 * @throws Exception
		 */
		private static void createMain(Map<String, Object> data) throws Exception {
		    String javaPath =  "/src/main/java/"+ rootPath.replaceAll("\\.", "/")+"/";//Java包名
			/*生成model*/
		    Freemarker.printFile("modelTemplate.ftl", data, System.getProperty("user.dir")+"/fzxt-model"+javaPath+"/model/"+objectName+ ".java");
			/*生成controller*/
		    Freemarker.printFile("contollerTemplate.ftl", data, System.getProperty("user.dir")+"/"+xmname+""+javaPath+"/controller/"+objectName+ "Controller.java");
			/*生成service*/
		  //  Freemarker.printFile("serviceImplTemplate.ftl", data, System.getProperty("user.dir")+"/"+xmname+javaPath+"/service/impl/"+objectName+"ServiceImpl.java");
		  //  Freemarker.printFile("serviceTemplate.ftl", data, System.getProperty("user.dir")+"/"+xmname+javaPath+"/service/"+objectName+"Service.java");
			/*生成DAO*/
		 //   Freemarker.printFile("daojavaTemplate.ftl", data,System.getProperty("user.dir")+"/fzxt-dao"+javaPath+"/mapper/"+objectName+"Mapper.java");
		//    Freemarker.printFile("daoxmlTemplate.ftl", data,System.getProperty("user.dir")+"/fzxt-dao"+"/src/main/resources/mapper/"+objectName+"Mapper.xml");
			/*生成SQL脚本*/
//		    Freemarker.print("mysql_SQL_Template.ftl", data);  //控制台打印
//		    Freemarker.print("oracle_SQL_Template.ftl", data);  //控制台打印
			/*生成jsp页面*/
		   // Freemarker.printFile("jsp_list_Template.ftl", data,jsppath+"/list.jsp");
		    //Freemarker.printFile("jsp_edit_Template.ftl", data,jsppath+"/edit.jsp");
		}
}
