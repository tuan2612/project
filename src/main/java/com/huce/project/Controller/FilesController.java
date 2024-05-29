package com.huce.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.huce.project.message.ResponseMessage;
import com.huce.project.model.FileInfo;
import com.huce.project.service.FilesStorageService;
import com.huce.project.service.SessionService;

import jakarta.servlet.http.HttpSession;





@Controller
@CrossOrigin(origins = "*")
public class FilesController {

  @Autowired
  FilesStorageService storageService;
  SessionService ssservice=new SessionService();
  @PostMapping("/upload/{username}")
  public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") MultipartFile[] files,@PathVariable String username) {

    String message = "";     
    try {
      List<String> fileNames = new ArrayList<>();

      Arrays.asList(files).stream().forEach(file -> {
        storageService.save(file);
        System.out.println(file.getOriginalFilename());
        fileNames.add(file.getOriginalFilename());
      });

      message = "Uploaded the files successfully: " + fileNames;
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Fail to upload files!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
  

    @GetMapping("/files/{username}/{pathfolder}")
    public ResponseEntity<?> getListFiles(@PathVariable String username, @PathVariable String pathfolder) {
      String rootFolderPath = "D:\\StoreFileUser\\"+pathfolder;
      Path rootPath = Path.of(rootFolderPath);
        List<FileInfo> fileInfos = storageService.loadAll(pathfolder).map(path -> {
            String filename = path.getFileName().toString();
            Path fullPath=rootPath.resolve(filename);
            try {
                BasicFileAttributes attrs = Files.readAttributes(fullPath, BasicFileAttributes.class);
                String size = String.valueOf(attrs.size());
                String dateCreate = String.valueOf(attrs.creationTime());
                return new FileInfo(filename, size, dateCreate);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }


  @GetMapping("/file/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
}