package com.zm.borrowmoneyandriodapp.controller;

import com.zm.borrowmoneyandriodapp.components.annotation.RequireRole;
import com.zm.borrowmoneyandriodapp.util.StaticUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zm.borrowmoneyandriodapp.common.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-08-25 10:31:36
 * https://github.com/zenmin/ProjectTemplate
 */

@Api(tags = "二维码上传")
@RequestMapping("/api/qrcode")
@RestController
public class QrcodeController {

    @Value("${br.filepath}")
    private String filePath;

    @PostMapping("/upload")
    @RequireRole
    public ResponseEntity uploadFile(MultipartFile file, Integer type) throws IOException {
        String payType = "";
        if (type == 1) {
            payType = "wechat";
        } else if (type == 2) {
            payType = "alipay";
        } else {
            return ResponseEntity.success(false);
        }
        String originalFilename = "/" + payType + ".jpg";
        String path = StaticUtil.uploadFile(file.getBytes(), filePath, originalFilename);
        return ResponseEntity.success(StringUtils.isNotBlank(path));
    }

}