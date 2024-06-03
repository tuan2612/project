package com.huce.project.service;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.huce.project.model.FileInfo;

public interface FilesStorageService {
  public void init();

  public void save(MultipartFile file);

  public Resource load(String filename);

  public void deleteAll();

  public Stream<Path> loadAll(String pathfolder);
  List<FileInfo>FileInfos(String rootFolderPath,String pathfolder);
  List<String> files(String username,String foldername);
  
}
