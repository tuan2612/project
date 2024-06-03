package com.huce.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.huce.project.entity.AccessRightEntity;
import com.huce.project.entity.EnumAccessType;
import com.huce.project.entity.FileEntity;
import com.huce.project.entity.FolderEntity;
import com.huce.project.message.ResponseMessage;
import com.huce.project.model.FileInfo;
import com.huce.project.repository.AccessRightRepository;
import com.huce.project.repository.FileRepository;
import com.huce.project.repository.FolderRepository;
import com.huce.project.repository.UserRepository;
import com.huce.project.service.FileService;
import com.huce.project.service.FilesStorageService;
import com.huce.project.service.SessionService;

import jakarta.servlet.http.HttpSession;





@Controller
@CrossOrigin(origins = "*")
public class FilesController {

  @Autowired
  FilesStorageService storageService;
  @Autowired
  FileService fileService;
  @Autowired
  UserRepository userrepo;
  @Autowired
  FileRepository filerepo;
  @Autowired
  FolderRepository folderrepo;
  @Autowired
  private AccessRightRepository accessrepo;
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
      if(pathfolder.contains("|")){
        pathfolder=pathfolder.replace("|", "\\");
      }
      List<FileInfo> fileInfos=new ArrayList<>();
      String rootFolderPath = "D:\\StoreFileUser\\"+pathfolder;
      fileInfos=storageService.FileInfos(rootFolderPath, pathfolder);

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
    @GetMapping("/fileshare/{username}/{pathfolder}")
    public ResponseEntity<?> getListFileshare(@PathVariable String username, @PathVariable String pathfolder) {
      if(pathfolder.contains("|")){
        pathfolder=pathfolder.replace("|", "\\");
      }
      String rootFolderPath = "D:\\StoreFileUser\\"+pathfolder;
      List<String> folderss=storageService.files(username, rootFolderPath);
      Path rootPath = Path.of(rootFolderPath);
      List<FileInfo> fileInfos=new ArrayList<>();
      for (String folder : folderss) {
        Path fullPath=rootPath.resolve(folder);
        BasicFileAttributes attrs;
        try {
          attrs = Files.readAttributes(fullPath, BasicFileAttributes.class);
          String size = String.valueOf(attrs.size());
          String dateCreate = String.valueOf(attrs.creationTime());
          FileInfo fileinfo=new FileInfo(folder.substring(folder.lastIndexOf('/')+1), size, dateCreate);
          fileInfos.add(fileinfo);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
                
      }
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

  @GetMapping("/file/{filename}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    String pathfile="D:\\StoreFileUser\\"+filename.replace("|", "\\");
    Resource file = storageService.load(pathfile);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
  @PostMapping("/share/{username}/{filename}")
  public ResponseEntity<String> postAccess(@PathVariable String username,@PathVariable String filename){
    UUID userid=userrepo.findByUsername(username).getUserID() ;
    if(filename.contains(".")){
      String folder=filename.substring(0,filename.lastIndexOf("|"));
      if(folder.contains("|")){folder=folder.replace("|", "\\");}
      String file=filename.substring(filename.lastIndexOf("|")+1);
      Long folderid=folderrepo.findByFoldername("D:\\StoreFileUser\\"+folder).getFolderId();
      Long file_id=filerepo.findByFilenameAndFolderid(file, folderid).getFileId();
      AccessRightEntity access=new  AccessRightEntity();
      access.setAccessType(EnumAccessType.READ);
      access.setFofId(file_id);
      access.setTypefof(1);
      access.setUserId(userid);
      accessrepo.save(access);
    }
    else{
      if(filename.contains("|")){filename=filename.replace("|", "\\");}
      String folderpath="D:\\StoreFileUser\\"+filename;
      Long folderid=folderrepo.findByFoldername("D:\\StoreFileUser\\"+filename).getFolderId();
      AccessRightEntity access=new  AccessRightEntity();
      access.setAccessType(EnumAccessType.READ);
      access.setFofId(folderid);
      access.setTypefof(0);
      access.setUserId(userid);
      accessrepo.save(access);
    }
    return ResponseEntity.status(HttpStatus.OK).body("chia sẻ thành công");
  }
  @PostMapping("remove/{username}/{filename}")
  public ResponseEntity<String> Remove(@PathVariable String filename,@RequestHeader("name") String headerValue,@PathVariable String username){ 
    if(headerValue.equals(username)){
      filename=filename.replace("|", "\\");
      filename="D:\\StoreFileUser\\"+filename;
      try {
      if(filename.contains(".")){
        String folderpath = filename.substring(0, filename.lastIndexOf("\\"));
        String filepath = filename.substring( filename.lastIndexOf("\\")+1);
        FolderEntity folder=folderrepo.findByFoldername(folderpath);
        long id_fol=folder.getFolderId();
        if(folder!=null){
          long file_id=filerepo.findByFilenameAndFolderid(filepath,folder.getFolderId()).getFileId();
          AccessRightEntity access=accessrepo.findByFofIdAndTypefof(id_fol, 1);
          if(file_id!=0&&access!=null&&access.getAccessType()==EnumAccessType.READ_WRITE){
          filerepo.deleteByFileId(file_id);
          accessrepo.deleteByFofIdAndTypefof(file_id,1);
        }
        }
      }
      else{
        FolderEntity folder=folderrepo.findByFoldername(filename);
        long folder_id=folder.getFolderId();
        System.out.println("1234566789");
        AccessRightEntity access=accessrepo.findByFofIdAndTypefof(folder_id, 0);
        System.out.println(access+"12345667");
        if(folder!=null&&access.getAccessType()==EnumAccessType.READ_WRITE){
          folderrepo.deleteByFolderId(folder_id);
          List<FileEntity> files=filerepo.findByFolderid(folder_id);
          if(files.size()!=0){
            for (FileEntity fileEntity : files) {
              filerepo.deleteByFileId(fileEntity.getFileId());
              accessrepo.deleteByFofIdAndTypefof(fileEntity.getFileId(), 1);
            }
          }
          accessrepo.deleteByFofIdAndTypefof(folder_id, 0);
        }
      }

      return ResponseEntity.status(HttpStatus.OK).body("chia sẻ thành công");
              
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
    }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("bạn không có quyền");
  }
}