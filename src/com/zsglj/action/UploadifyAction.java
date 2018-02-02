/*****************************************************************
* 文件名  :  UploadifyAction.java
* 作者    :  王子豪
* 版权    :  西红柿科技（武汉）有限公司
* 日期    :  2016-08-20
* 修改历史：
* 描述     : 插件Uploadify的上传文件功能
******************************************************************/
package com.zsglj.action;

import java.io.File;  
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
 
/*****************************************************************
* 类名    :  UploadifyAction
* 作者    :  王子豪
* 日期    :  2016-08-22
* 修改历史：
* 功能描述: 上传插件Uploadify上传文件执行的action
******************************************************************/
public class UploadifyAction extends ActionSupport{  
    private File uploadify;  
    public File getUploadify() {  
        return uploadify;  
    }  
    public void setUploadify(File uploadify) {  
        this.uploadify = uploadify;  
    }  
    private String uploadifyFileName;  
    public String getUploadifyFileName() {  
        return uploadifyFileName;  
    }  
    public void setUploadifyFileName(String uploadifyFileName) {  
        this.uploadifyFileName = uploadifyFileName;  
    }  
  
    /*****************************************************************
    * 方法名  :  uploaFile
    * 作者    :  王子豪
    * 日期    :  2016-08-20
    * 输入参数：无
    * 输出参数：null 
    * 功能描述: 使用uploadify插件上传文件
    * 修改历史：
    ******************************************************************/
    public String uploaFile(){  
        HttpServletRequest request = ServletActionContext.getRequest();  
        HttpServletResponse response = ServletActionContext.getResponse();  
        String savePath = request.getSession().getServletContext().getRealPath("");  
        PrintWriter out = null;  
        String resultStr = "";  
        String extName = "";// 扩展名  
        String newFileName = "";// 新文件名  
        try {  
            response.setCharacterEncoding("utf-8");  
            out = response.getWriter();  
              
            //获取文件的扩展名  
            if (uploadifyFileName.lastIndexOf(".") >= 0) {  
                extName = uploadifyFileName.substring(uploadifyFileName.lastIndexOf("."));  
            }  
            //从jsp页面获取新的文件名称  
            String nowTime = request.getParameter("uuid");
            newFileName = nowTime + extName;  
//            String nowTime = new SimpleDateFormat("yyyymmddHHmmss").format(new Date());// 当前时间  
              
            //设置文件保存路径  
            String savepathMid = request.getParameter("savepathMid");  
            savePath = savePath + savepathMid;  
            File file = new File(savePath);  
            if(!file.exists()){  
                file.mkdirs();  
            }  
            //先删除后保存
            deleteFile(savePath+newFileName);
            uploadify.renameTo(new File(savePath+newFileName));  
            resultStr = uploadifyFileName+","+savepathMid+newFileName;  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{
            out.print(resultStr); 
            out.flush();
        } 
        return null;
    }  
    public boolean deleteFile(String sPath) {  
    	boolean flag = false;  
        File file = new File(sPath);  
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    }  
              
}  