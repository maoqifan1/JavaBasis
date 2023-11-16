package com.maoqifan.file;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RestController
public class FileDownload {

    @GetMapping("/download")
    // 1. 以文件流的形式一次性读取到内存，通过响应流输出到客户端
    public void download(String path, HttpServletResponse response) {
        try {
            // 获取文件
            File file = new File(path);
            // 获取文件名
            String fileName = file.getName();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            // 将文件写入输入流
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            // 清空response
            response.reset();

            // 设置响应头
            response.setCharacterEncoding("utf-8");

            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));


            // 告知浏览器文件大小
            response.addHeader("Content-Length", String.valueOf(file.length()));

            // 告知浏览器文件类型
            response.setContentType("application/octet-stream");

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            outputStream.write(buffer);
            outputStream.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 2. 将输入流中的数据循环写入到响应输出流中，而不是一次性读取到内存，通过响应输出流输出到前端
    @GetMapping("/downloadLocal")
    public void downloadLocal(String path, HttpServletResponse response) {
        // 读到流中

        try (InputStream inputStream = new FileInputStream(path)) {
            response.reset();
            response.setContentType("application/octet-stream");
            String filename = new File(path).getName();
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 下载网络文件到本地
    @GetMapping("/downloadNetwork")
    public void downloadNet(String netAddress, String path) throws IOException {
        URL url = new URL(netAddress);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(path);

        int byteSum = 0;
        int byteRead;
        byte[] buffer = new byte[1024];
        while ((byteRead = inputStream.read(buffer)) != -1) {
            byteSum += byteRead;
            fileOutputStream.write(buffer, 0, byteRead);
        }
        fileOutputStream.close();
    }

    // 网络文件获取到服务器后，经服务器处理后响应给前端
    @GetMapping("/downloadNetworkToClient")
    public void networkDownload(String netAddress, String filename, boolean isOnLine, HttpServletResponse response) throws IOException {
        URL url = new URL(netAddress);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();

        response.reset();
        response.setContentType(conn.getContentType());
        if (isOnLine) {
            response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
        }

        int len;
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
    }
}
