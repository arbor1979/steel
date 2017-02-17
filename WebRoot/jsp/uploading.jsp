<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="com.ntsky.file.*"%>
<%@ page import="java.io.*" %>
<% 
   String picDir="/upload";
   String pname= request.getParameter("id");
   File picFile=new File(request.getSession().getServletContext().getRealPath(picDir+"/"+pname+".gif"));
   if(picFile.exists()) picFile.delete();
   picFile=new File(request.getSession().getServletContext().getRealPath(picDir+"/"+pname+".jpg"));
   if(picFile.exists()) picFile.delete();
   String Msg="";
   int level=0;
   FILE file = new FileHandle();
   try
   {
		// jsp调用初始化
		file.initialize(pageContext);
		//文件参数对象
		FileParam param = new FileParam();
		// 文件目录
		param.setFileDir(picDir);
		// 文件名
		param.setFileName(pname);
		// 这里的1000代表 1M
		param.setFileSize(1000);	
		// 允许上传的文件总大小
		param.setFileTotalSize(100000);
		// 文件格式
		param.setFileType("jpg,gif");
		// 设置上传目录最大的文件数 
		param.setFileNum(100000);
		//设置是否进行图片缩放处理
		/*
	    param.setPic(true);
		JPEG jpeg = new JPEG(); 创建图片缩放的对象
		jpeg.setPicHeight(150);  上传图片后缩放的高度
		jpeg.setPicWidth(200);   上传图片后缩放的宽度
		jpeg.setPicPath("dirPicA");  缩放图片的路径，不设置就是和上传的图片在同个目录下
		jpeg.setPicName("picName");  缩放图片的名称，不设置就是原来上传图片的文件后加上_small
		jpeg.setDelSourcePic(true);  如果有时候觉得只要缩略图，而不需要原始图片的话，只要这样设置就会把上传的原图给删除掉，而只保留缩略图片。
		param.setJpeg(jpeg); 添加缩放处理参数
		*/
		//将文件参数提交给上传类
		file.setFileParam(param);
		// 执行上传
		level=file.service(request);
 	    switch (level)
		{
			case -1:
				Msg="上传成功!";
			case 0:
				Msg="代表错误未知!";
			case 1:
				Msg="系统不支持上传的文件格式!";
			case 2:
				Msg="上传的文件超过单个文件允许上传的大小200K!";
			case 3:
				Msg="上传的文件超过总文件允许上传的大小!";
			case 4:
				//Msg="上传的文件超过该目录允许上传文件的总数";
				Msg="上传成功!";
			
		}
	}
	catch(Exception e)
	{ 
		out.print("<script language=JavaScript>window.alert('"+e+"');window.history.back();</script>");
	} 

%>
<script language=JavaScript>window.alert('<%=Msg%>');</script>

