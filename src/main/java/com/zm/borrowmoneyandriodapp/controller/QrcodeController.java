package com.zm.borrowmoneyandriodapp.controller;

import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zm.borrowmoneyandriodapp.common.ResponseEntity;
import com.zm.borrowmoneyandriodapp.service.QrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-08-25 10:31:36
 * https://github.com/zenmin/ProjectTemplate
 */

@Api(tags = "二维码地址")
@RestController
@RequestMapping("/api/qrcode")
public class QrcodeController {

    @Value("${br.filepath}")
    private String filePath;

    @Autowired
    QrcodeService qrcodeService;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(MultipartFile multipartFile, Integer type) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String payType = "";
        if (type == 1) {
            payType = "wechat";
        }
        if (type == 2) {
            payType = "alipay";
        }
        originalFilename = payType + originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        StaticUtil.uploadFile(multipartFile.getBytes(), filePath, originalFilename);
        return ResponseEntity.success(filePath + originalFilename);
    }
}