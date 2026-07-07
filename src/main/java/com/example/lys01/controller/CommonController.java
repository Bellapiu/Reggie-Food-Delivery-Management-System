package com.example.lys01.controller;

import com.example.lys01.Result.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${filepath.path}")
    private String basepath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        File dir = new File(basepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        File fileUp = new File(basepath + File.separator + originalFilename);
        file.transferTo(fileUp);
        return R.success(originalFilename);
    }
    
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        File file = new File(basepath + File.separator + name);
        if (!file.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        FileInputStream in = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("image/jpeg");
        
        byte[] bytes = new byte[1024];
        int len;
        while ((len = in.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        
        out.flush();
        in.close();
        out.close();
    }
}
