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
		// jsp���ó�ʼ��
		file.initialize(pageContext);
		//�ļ���������
		FileParam param = new FileParam();
		// �ļ�Ŀ¼
		param.setFileDir(picDir);
		// �ļ���
		param.setFileName(pname);
		// �����1000���� 1M
		param.setFileSize(1000);	
		// �����ϴ����ļ��ܴ�С
		param.setFileTotalSize(100000);
		// �ļ���ʽ
		param.setFileType("jpg,gif");
		// �����ϴ�Ŀ¼�����ļ��� 
		param.setFileNum(100000);
		//�����Ƿ����ͼƬ���Ŵ���
		/*
	    param.setPic(true);
		JPEG jpeg = new JPEG(); ����ͼƬ���ŵĶ���
		jpeg.setPicHeight(150);  �ϴ�ͼƬ�����ŵĸ߶�
		jpeg.setPicWidth(200);   �ϴ�ͼƬ�����ŵĿ��
		jpeg.setPicPath("dirPicA");  ����ͼƬ��·���������þ��Ǻ��ϴ���ͼƬ��ͬ��Ŀ¼��
		jpeg.setPicName("picName");  ����ͼƬ�����ƣ������þ���ԭ���ϴ�ͼƬ���ļ������_small
		jpeg.setDelSourcePic(true);  �����ʱ�����ֻҪ����ͼ��������ҪԭʼͼƬ�Ļ���ֻҪ�������þͻ���ϴ���ԭͼ��ɾ��������ֻ��������ͼƬ��
		param.setJpeg(jpeg); ������Ŵ������
		*/
		//���ļ������ύ���ϴ���
		file.setFileParam(param);
		// ִ���ϴ�
		level=file.service(request);
 	    switch (level)
		{
			case -1:
				Msg="�ϴ��ɹ�!";
			case 0:
				Msg="�������δ֪!";
			case 1:
				Msg="ϵͳ��֧���ϴ����ļ���ʽ!";
			case 2:
				Msg="�ϴ����ļ����������ļ������ϴ��Ĵ�С200K!";
			case 3:
				Msg="�ϴ����ļ��������ļ������ϴ��Ĵ�С!";
			case 4:
				//Msg="�ϴ����ļ�������Ŀ¼�����ϴ��ļ�������";
				Msg="�ϴ��ɹ�!";
			
		}
	}
	catch(Exception e)
	{ 
		out.print("<script language=JavaScript>window.alert('"+e+"');window.history.back();</script>");
	} 

%>
<script language=JavaScript>window.alert('<%=Msg%>');</script>

