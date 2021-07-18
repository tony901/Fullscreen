package com.tony.fullscreen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;

/**
 * @Author: Tony Peng
 * @Date: 2021/7/9 10:20
 **/
@Slf4j
@RestController
@RequestMapping("/fullscreen")
public class FullscreenController {

    @GetMapping("/active")
    public void active(HttpServletRequest request) {
        String clientIp = request.getRemoteHost();
        String fileName = "F11";
        String command = "set ws=createObject(\"WScript.Shell\")\nws.SendKeys \"{F11}\"";
        String fileUrl = FileSystemView.getFileSystemView().getHomeDirectory() + "\\" + fileName + ".vbs";
        File file = new File(fileUrl);
        try {
            if (!file.exists()) {
                FileWriter fw = new FileWriter(fileUrl);
                fw.write(command);
                fw.close();
            }
            String[] cmd = new String[]{"wscript", fileUrl};
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (Exception e) {
            log.error("Client IP【{}】press F11 failed! cause by:" + e.getMessage(), clientIp);
        }
        log.info("Client IP【{}】F11 activated", clientIp);
    }
}