package com.shadow404.fileupdate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author 华成志
 */
@Controller
public class UploadController {
    private static String UPLOADED_FOLDER="D://temp//";
    @GetMapping("/")
    private String index(){
        return "upload";
    }
    @GetMapping("/more")
    public String uploadMore(){
        return "uploadMore";
    }
    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","请选择一个文件上传！");
            return "redirect:/uploadStatus";
        }
        try {
            byte[] bytes=file.getBytes();
            Path path= Paths.get(UPLOADED_FOLDER+file.getOriginalFilename());
            Files.write(path,bytes);
            redirectAttributes.addFlashAttribute("message","你成功上传了 '"+file.getOriginalFilename()+"'");
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/uploadStatus";
    }
    @PostMapping("/uploadMore")
    public String moreFileUpload(@RequestParam("file") MultipartFile[] files,RedirectAttributes redirectAttributes){
        if(files.length==0){
            redirectAttributes.addFlashAttribute("message","请选择一个文件上传");
            return "redirect:/uploadStatus";
        }
        for (MultipartFile file:files){
            try {
                byte[] bytes=file.getBytes();
                Path path=Paths.get(UPLOADED_FOLDER+file.getOriginalFilename());
                Files.write(path,bytes);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        redirectAttributes.addFlashAttribute("message","你成功上传了所有文件！");
        return "redirect:/uploadStatus";
    }
    @GetMapping("/uploadStatus")
    public String uploadStatus(){
        return "uploadStatus";
    }
}
