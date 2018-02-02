package com.zsglj.action;  
  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.io.PrintWriter;  
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;  

import org.apache.struts2.ServletActionContext;  

import com.opensymphony.xwork2.ActionSupport;  

/*****************************************************************
* 类名    :  CkeditorUpload
* 作者    :  王子豪
* 日期    :  2016-08-22
* 修改历史：
* 功能描述: 编辑框插件Ckeditor上传图片功能
******************************************************************/
public class CkeditorUpload extends ActionSupport {  
  
    private File upload;  
    private String uploadContentType;  
    private String uploadFileName;  
  
  
    public File getUpload() {  
        return upload;  
    }  
  
    public void setUpload(File upload) {  
          
        this.upload = upload;  
    }  
  
    public String getUploadContentType() {  
        return uploadContentType;  
    }  
  
    public void setUploadContentType(String uploadContentType) {  
        this.uploadContentType = uploadContentType;  
    }  
  
    public String getUploadFileName() {  
        return uploadFileName;  
    }  
  
    public void setUploadFileName(String uploadFileName) {  
        this.uploadFileName = uploadFileName;  
    }  
  
    /*****************************************************************
	* 方法名  :  execute
	* 作者    :  王子豪
	* 日期    :  2016-08-22
	* 输入参数：null
	* 输出参数：null
	* 功能描述: 获取要上传的图片，上传到tomcat指定目录，并将目录地址打印到页面
	* 修改历史：
	******************************************************************/
    public String execute() throws Exception {  
  
        HttpServletResponse response = ServletActionContext.getResponse();  
        HttpServletRequest request = ServletActionContext.getRequest();  
        response.setCharacterEncoding("utf-8");  
        PrintWriter out = response.getWriter();  
  
        // CKEditor提交的很重要的一个参数  
        String callback = ServletActionContext.getRequest().getParameter("CKEditorFuncNum");   
          
        String expandedName = "";  //文件扩展名  
        if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {  
            //IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg  
            expandedName = ".jpg";  
        }else if(uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")){  
            //IE6上传的png图片的headimageContentType是"image/x-png"  
            expandedName = ".png";  
        }else if(uploadContentType.equals("image/gif")){  
            expandedName = ".gif";  
        }else if(uploadContentType.equals("image/bmp")){  
            expandedName = ".bmp";  
        }else{  
            out.println("<script type=\"text/javascript\">");    
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');");   
            out.println("</script>");  
            return null;  
        }  
          
        if(upload.length() > 2048*1024){  
            out.println("<script type=\"text/javascript\">");    
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件大小不得大于2M');");   
            out.println("</script>");  
            return null;  
        }  
          
          
        InputStream is = new FileInputStream(upload);  
        String ckUploadImgPath = request.getParameter("ckUploadImgPath");
        String uploadPath = ServletActionContext.getServletContext()     
                .getRealPath("/"+ckUploadImgPath); 
        String nowTime = new SimpleDateFormat("yyyymmddHHmmss").format(new Date());// 当前时间  
        String fileName = request.getParameter("uuid")+"-"+nowTime;  //采用时间+UUID的方式随即命名  
        fileName += expandedName;  
        File toFile = new File(uploadPath, fileName);  
        OutputStream os = new FileOutputStream(toFile);     
        byte[] buffer = new byte[1024];     
        int length = 0;  
        while ((length = is.read(buffer)) > 0) {     
            os.write(buffer, 0, length);     
        }     
        is.close();  
        os.close();  
          
        // 返回“图像”选项卡并显示图片
        out.println("<script type=\"text/javascript\">");    
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + "/TomatoWebSys/"+ckUploadImgPath+"/" + fileName + "','')");    
        out.println("</script>");  
          
        return null;  
    }  
}