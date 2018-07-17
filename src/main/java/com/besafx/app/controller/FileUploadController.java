package com.besafx.app.controller;

import com.besafx.app.config.DropboxManager;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.Future;

@RestController
public class FileUploadController {

    private final static Logger log = LoggerFactory.getLogger(FileUploadController.class);

    private SecureRandom random;

    @Autowired
    private DropboxManager dropboxManager;

    @PostConstruct
    public void init() {
        random = new SecureRandom();
    }

    @RequestMapping(value = "/uploadCompanyLogo", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadCompanyLogo(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/Madar/Companies/" + fileName);
        if (task.get()) {
            Future<String> task11 = dropboxManager.shareFile("/Madar/Companies/" + fileName);
            return task11.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/uploadContactPhoto", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadContactPhoto(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/Madar/Contacts/" + fileName);
        if (task.get()) {
            Future<String> task11 = dropboxManager.shareFile("/Madar/Contacts/" + fileName);
            return task11.get();
        } else {
            return null;
        }
    }
}
