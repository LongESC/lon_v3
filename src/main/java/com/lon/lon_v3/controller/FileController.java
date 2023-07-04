package com.lon.lon_v3.controller;

import com.lon.lon_v3.comomon.Result;
import com.lon.lon_v3.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;





/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.controller
 * @className: FileController
 * @author: LONZT
 * @description: TODO
 * @date: 2023/5/25 8:45
 * @version: 1.0
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private static final String filePath= "E:\\file\\excel\\";

    @RequestMapping("/getFilesList")
    public Result getFilesList(){

        return Result.success(FileUtils.traverseFolder(filePath));

    }

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){

        return Result.success(FileUtils.upload(file));
    }

    @PostMapping("/uploads")
    public Result upload(MultipartFile[] files){

        return Result.success(FileUtils.upload(files));
    }

    /**
     * @param request:
    	 * @param response:
    	 * @param name:
    	 * @param isOnline:
      * @return void
     * @author lonzt
     * @description TODO
     * @date 2023/5/25 10:20
     */

    @RequestMapping("/excel/download")
    @ResponseBody
    public void excelDownload(HttpServletRequest request, HttpServletResponse response, String name, @RequestParam(defaultValue = "1") int isOnline) throws IOException {
        try (ServletOutputStream outputStream = response.getOutputStream();) {
			/*
			这里要使用ResourceUtils来获取到我们项目的根目录
			不能使用request.getServletContext().getRealPath("/")，这里获取的是临时文件的根目录（所以不能使用这个）
			*/
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());
            String realPath = filePath + name;
            File file = new File(realPath);
            //如果下载的文件不存在
            if (!file.exists()) {
                response.setContentType("text/html;charset=utf-8");
//                //response.getWriter().write(str);这种写入的就相当于生成了jsp/html，返回html/jsp，所以需要我们进行contentType的设置
//                System.out.println("下载的文件不存在");
                outputStream.write(100);
                return;
            }
            InputStream in = new FileInputStream(realPath);
            int read;
            //byte[] b = new byte[in.available()];创建一个输入流大小的字节数组，然后把输入流中所有的数据都写入到数组中
            byte[] b = new byte[1024];
			/*
			1、Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
			2、attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
			3、filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
			*/
			/*	注意:文件名字如果是中文会出现乱码：解决办法：
				1、将name 替换为 new String(filename.getBytes(), "ISO8859-1");
				2、将name 替换为 URLEncoder.encode(filename, "utf-8");
			*/
            if (isOnline == 0) {
                //在线打开
                response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(name, "utf-8"));
            } else {
               response.setContentType("application/vnd.ms-excel");
                //下载形式，一般跟application/octet-stream一起使用
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "utf-8"));
                //如果单纯写这个也可以进行下载功能，表示以二进制流的形式
                response.setContentType("application/octet-stream");
            }
            //文件大小
            response.addHeader("Content-Length", "" + file.length());
            while ((read = in.read(b)) > 0) {
                outputStream.write(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
            throw e;
        }
    }
/**
 * @param request:
	 * @param response:
	 * @param name:
	 * @param isOnline:
  * @return void
 * @author lonzt
 * @description TODO
 * @date 2023/5/25 14:51
 */
    @RequestMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response, String name, @RequestParam(defaultValue = "0") int isOnline) throws IOException {

        try (ServletOutputStream outputStream = response.getOutputStream();) {
			/*
			这里要使用ResourceUtils来获取到我们项目的根目录
			不能使用request.getServletContext().getRealPath("/")，这里获取的是临时文件的根目录（所以不能使用这个）
			*/
            String path = "E:\\";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());
            String realPath = path + "file/" + format + "/" + name;
            File file = new File(realPath);
            //如果下载的文件不存在
            if (!file.exists()) {
                response.setContentType("text/html;charset=utf-8");
                //response.getWriter().write(str);这种写入的就相当于生成了jsp/html，返回html/jsp，所以需要我们进行contentType的设置
                response.getWriter().write("下载的文件不存在");
                return;
            }

            InputStream in = new FileInputStream(realPath);
            int read;

            //byte[] b = new byte[in.available()];创建一个输入流大小的字节数组，然后把输入流中所有的数据都写入到数组中
            byte[] b = new byte[1024];
			/*
			1、Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
			2、attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
			3、filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
			*/
			/*	注意:文件名字如果是中文会出现乱码：解决办法：
				1、将name 替换为 new String(filename.getBytes(), "ISO8859-1");
				2、将name 替换为 URLEncoder.encode(filename, "utf-8");
			*/
            if (isOnline == 0) {
                //在线打开
                response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(name, "utf-8"));
            } else {
                //下载形式，一般跟application/octet-stream一起使用
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "utf-8"));
                //如果单纯写这个也可以进行下载功能，表示以二进制流的形式
                response.setContentType("application/octet-stream");
            }
            //文件大小
            response.addHeader("Content-Length", "" + file.length());
            while ((read = in.read(b)) > 0) {
                outputStream.write(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


}
