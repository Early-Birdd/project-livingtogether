package com.example.projectlivingtogether.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originFileName, byte[] fileData) throws Exception{

        UUID uuid = UUID.randomUUID();

        String extension = originFileName.substring(originFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadUrl = uploadPath + "/" +savedFileName;

        FileOutputStream fos = new FileOutputStream(fileUploadUrl);

        fos.write(fileData);
        fos.close();

        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{

        File deleteFile = new File(filePath);

        if(deleteFile.exists()){

            deleteFile.delete();
            log.info("파일이 삭제되었습니다.");
        }else{

            log.info("파일이 현재 존재하지 않습니다.");
        }
    }
}
