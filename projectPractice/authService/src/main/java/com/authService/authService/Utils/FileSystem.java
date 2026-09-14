package com.authService.authService.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystem {

    @Value("${file.upload.dir}")
    private String uploaddir;
    public String saveFile(MultipartFile multipartFile,String subfile) throws IOException {
        String fileSavingName=System.currentTimeMillis()+"_"+multipartFile.getOriginalFilename();
        Path uploadPath= Paths.get(uploaddir,subfile);


        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        Path flipath=uploadPath.resolve(fileSavingName);
        Files.write(flipath,multipartFile.getBytes());

        return subfile+"/"+fileSavingName;
    }
}
