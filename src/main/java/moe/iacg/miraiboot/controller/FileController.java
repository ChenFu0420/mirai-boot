package moe.iacg.miraiboot.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/File")
@Slf4j
public class FileController {
    @RequestMapping(value = "/recordMp3/{path}", method = RequestMethod.GET)
    @SneakyThrows
    public void downloadRecordMp3(@PathVariable String path, HttpServletResponse response) {


        File file = new File(FileUtil.normalize(Base64.decodeStr(path)));

//        if (!file.exists()) {
//            throw new FileNotFoundException("文件不存在");
//        }
        long size = FileUtil.size(file);
        response.setContentType("mp3;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        response.setContentLengthLong(size);
        response.addHeader("Accept-Ranges", "bytes");
        response.setBufferSize(10 * 1024 * 1024);
        response.addHeader("Content-Range", "bytes " + 0 + "-" + (size - 1) + "/" +size);
        view(file, response);
    }

    /**
     * 读取文件
     *
     * @param response
     */
    public static void view(File file, HttpServletResponse response) {


        try (FileInputStream is = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(is)) {

            OutputStream out = response.getOutputStream();
            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = bis.read(buf)) > 0) {
                out.write(buf, 0, bytesRead);
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
    }
}
