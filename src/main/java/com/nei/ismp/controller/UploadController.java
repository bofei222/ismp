package com.nei.ismp.controller;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author bofei
 * @Date 2019/1/18 10:41
 * @Description
 */
@Controller
public class UploadController {

    public static void main(String[] args) {
        HttpClient client = HttpClients.createDefault();

    }
    /**
     * 下载
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/api/update", method = RequestMethod.GET)
    public ResponseEntity<String> update(HttpServletRequest request, HttpServletResponse response, ModelMap model)
            throws IOException {

        InputStream inputStream = null;
        ServletOutputStream out = null;
        String odexName = "file.apk";
        try {
            File file = new File("/data/apk/" + odexName);
            int fSize = Integer.parseInt(String.valueOf(file.length()));
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/x-download");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", String.valueOf(fSize));
            response.setHeader("Content-Disposition", "attachment;fileName=" + odexName);
            inputStream = new FileInputStream("/data/apk/" + odexName);
            long pos = 0;
            if (null != request.getHeader("Range")) {
                // 断点续传
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                try {
                    pos = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
                } catch (NumberFormatException e) {
                    pos = 0;
                }
            }
            out = response.getOutputStream();
            response.setHeader("Content-Range", new StringBuffer("bytes ").append(pos + "").append("-")
                    .append((fSize - 1) + "").append("/").append(fSize + "").toString());
            inputStream.skip(pos);
            byte[] buffer = new byte[1024 * 10];
            int length = 0;
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, length);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out)
                    out.flush();
                if (null != out)
                    out.close();
                if (null != inputStream)
                    inputStream.close();
            } catch (IOException e) {
            }
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
