package com.lon.lon_v3.utils;



import com.lon.lon_v3.entity.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @projectName: lon_v3
 * @package: com.lon.lon_v3.utils
 * @className: FileUtils
 * @author: LONZT
 * @description: TODO
 * @date: 2023/5/25 8:56
 * @version: 1.0
 */
public class FileUtils {

    private final static String FILE_PATH = "E:\\file\\";

    public static List<FileVo> traverseFolder(String folderPath) {
        List<FileVo> fileNameList = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {

                    if (file.isDirectory()) {
                        // 如果是文件夹，则递归遍历子文件夹
                        traverseFolder(file.getAbsolutePath());
                    } else {
                        FileVo fileVo=new FileVo();
                        fileVo.setFileName(file.getName());
                        System.out.println(fileVo);
                        fileNameList.add(fileVo);
                        // 如果是文件，则输出文件名
                        System.out.println("File: " + file.getName());
                    }
                }
            }
        } else {
            System.out.println("文件夹不存在..");
        }
        return fileNameList;
    }



    /**
    	 * @param response:
    	 * @param isOnline:
      * @return void
     * @author lonzt
     * @description TODO
     * @date 2023/6/19 17:18
     */
    public static void download(HttpServletResponse response, String fileName,int isOnline) throws IOException {

        try{
			/*
			这里要使用ResourceUtils来获取到我们项目的根目录
			不能使用request.getServletContext().getRealPath("/")，这里获取的是临时文件的根目录（所以不能使用这个）
			*/
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());
            File file = new File(fileName);
            //如果下载的文件不存在
            if (!file.exists()) {
                response.setContentType("text/html;charset=utf-8");
                //response.getWriter().write(str);这种写入的就相当于生成了jsp/html，返回html/jsp，所以需要我们进行contentType的设置
                response.getWriter().write("下载的文件不存在");
                return;
            }

            InputStream in = new FileInputStream(fileName);
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
                response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "utf-8"));
            } else {
                //下载形式，一般跟application/octet-stream一起使用
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
                //如果单纯写这个也可以进行下载功能，表示以二进制流的形式
                response.setContentType("application/octet-stream");
            }
            //文件大小
            response.addHeader("Content-Length", "" + file.length());
            while (in.read(b) > 0) {
                response.getOutputStream().write(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * @param file:
      * @return String
     * @author lonzt
     * @description 上传单个文件
     * @date 2023/6/20 17:36
     */
    public static String upload(MultipartFile file){
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
//        log.info(file.toString());

        //原始文件名
        String originalFilename = file.getOriginalFilename();//abc.jpg
        System.out.println(originalFilename);
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName =originalFilename;//dfsdfdfd.jpg
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        //创建一个目录对象
        File dir = new File(FILE_PATH+format);
        //判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdirs();
        }
        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(FILE_PATH +format+"\\"+ fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * @param files:
     * @return String
     * @author lonzt
     * @description 上传多个文件
     * @date 2023/6/20 17:36
     */
    public static List<String> upload(MultipartFile[] files){
        List<String> list = new ArrayList<>();
        for (MultipartFile f : files) {
            list.add(upload(f));
        }
        return list;
    }


}
