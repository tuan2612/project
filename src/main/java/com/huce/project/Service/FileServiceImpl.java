package com.huce.project.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huce.project.Repository.FileRepository;
import com.huce.project.Repository.FolderRepository;
import com.huce.project.Repository.UserRepository;
import com.huce.project.Entity.AccessRightEntity;
import com.huce.project.Entity.EnumAccessType;
import com.huce.project.Entity.FileEntity;
import com.huce.project.Entity.FolderEntity;
import com.huce.project.Entity.UserEntity;
import com.huce.project.Repository.AccessRightRepository;
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository filerepo;
    @Autowired
    private FolderRepository folderrepo;
    @Autowired
    private AccessRightRepository accessrepo;
    @Autowired
    private UserRepository userrepo;
    @SuppressWarnings("null")
    @Override
    public void saveFile(String urlfolder,String namefile,String username) {
        FolderEntity folders=folderrepo.findByFoldername(urlfolder);
        FileEntity file=new FileEntity();
        FolderEntity folder=new FolderEntity();
        AccessRightEntity accsess=new AccessRightEntity();
        UserEntity users=userrepo.findByUsername(username);
        if(users!=null){
        if(folders==null){
            try {
            // Chuyển đổi đường dẫn thành đối tượng Path
            Path pathfolder = Paths.get(urlfolder);
            
            // Lấy thông tin cơ bản về tập tin
            BasicFileAttributes attributes = Files.readAttributes(pathfolder, BasicFileAttributes.class);

            // Lấy thời gian mở tập tin
            long openTimeInMillis = attributes.creationTime().toMillis();
            long size=attributes.size();
            // Chuyển đổi thời gian mở thành đối tượng Date
            Date openDate = new Date(openTimeInMillis);
            folder.setFoldername(urlfolder);
            folder.setFoldersize(size);
            folder.setCreatedAt(openDate);
            folderrepo.save(folder);
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
            long folder_id=folderrepo.findByFoldername(urlfolder).getFolderId();
            accsess.setUserId(users.getUserID());
            accsess.setAccessType(EnumAccessType.READ_WRITE);
            accsess.setFolderId(folder_id);
            accsess.setTypefof(0);
            accessrepo.save(accsess);
        }
        Path pathfile = Paths.get(urlfolder+"\\"+namefile);
        try {
            BasicFileAttributes attributes = Files.readAttributes(pathfile, BasicFileAttributes.class);
            long folder_id=folderrepo.findByFoldername(urlfolder).getFolderId();
            long openTimeInMillis = attributes.creationTime().toMillis();
            long size=attributes.size();
            // Chuyển đổi thời gian mở thành đối tượng Date
            Date openDate = new Date(openTimeInMillis);
            file.setFilename(namefile);
            file.setFiletype(namefile.substring(namefile.indexOf(".")+1));
            file.setFolderId(folder_id);
            file.setCreatedAt(openDate);
            file.setFileSize(size);
            filerepo.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
        }
    }
    

