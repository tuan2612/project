package com.huce.project.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.StandardCopyOption;
import com.huce.project.Service.FileService;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
  private final String pathroot="F:\\StoreFileUser";
  private final Path root = Paths.get("F:\\StoreFileUser");
  @Autowired
  FileService filesv;
  @Override
  public void init() {
      File directory = new File(pathroot);
        if (directory.exists() && directory.isDirectory()) {
          System.out.println("Thư mục tồn tại trên ổ đĩa D:");
      } else {
          System.out.println("Thư mục không tồn tại trên ổ đĩa D:");
          try {
            Files.createDirectories(root);
          } catch (IOException e) {
            e.printStackTrace();
          }
      }
      
  }

  @Override
  public void save(MultipartFile file) {
    String urlfile=file.getOriginalFilename();
    String urlroot=pathroot;
    Path pathUrlroot=root;
    String username=urlfile.substring(0,urlfile.indexOf("/"));
    while (urlfile.contains("/")) {
      int index=urlfile.indexOf("/");
      File subDir = new File(urlroot,urlfile.substring(0, index) );
      pathUrlroot = Paths.get(urlroot, urlfile.substring(0, index));
      urlroot=urlroot+"\\"+urlfile.substring(0,index);
        urlfile=urlfile.substring(index+1);
      if (!subDir.exists() || !subDir.isDirectory()) {
      try {
        // Tạo thư mục mới
        Files.createDirectory(pathUrlroot);
        System.out.println("Thư mục mới đã được tạo thành công!");
    } catch (IOException e) {
        System.out.println("Không thể tạo thư mục mới: " + e.getMessage());
    } 
    }
    }
    if(urlfile.contains(".")){
    try {
      int index=0;
      pathUrlroot=Paths.get(urlroot,urlfile);
      while(Files.exists(pathUrlroot)){
        index++;
        pathUrlroot=Paths.get(urlroot,urlfile.substring(0,urlfile.indexOf("."))+"("+String.valueOf(index)+")"+urlfile.substring(urlfile.indexOf(".")));
      }
      Files.copy(file.getInputStream(), pathUrlroot,StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }}
    else{
      pathUrlroot = Paths.get(urlroot, urlfile);
      try {
        Files.createDirectory(pathUrlroot);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    filesv.saveFile(urlroot, urlfile, username);
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }

  
}
