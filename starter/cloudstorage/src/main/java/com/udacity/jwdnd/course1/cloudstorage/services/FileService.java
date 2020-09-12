package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    private List<File> files;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;

    }

    @PostConstruct
    public void postConstruct() {
        this.files = new ArrayList<>();
    }

    public List<File> getFiles() {
        int userId = getLoggedInUserId();
        this.files = fileMapper.getFilesForUser(userId);
        return new ArrayList<>(this.files);
    }

    public File getFile(int fileId) {
        File file = fileMapper.getFile(fileId);
        return file;
    }

    public int uploadFile(MultipartFile fileUpload) {

        String filename = fileUpload.getOriginalFilename();
        String contenttype = fileUpload.getContentType();
        String filesize = String.valueOf(fileUpload.getSize());
        Integer userid = getLoggedInUserId();
        byte[] filedata = new byte[0];
        try {
            filedata = fileUpload.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(null, filename, contenttype, filesize, userid, filedata);

        int row = fileMapper.addFile(file);
        return file.getFileId();
    }

    public int deleteFile(int fileId) {
        int deletedFile = fileMapper.deleteFile(fileId);
        return deletedFile;
    }

    public int getLoggedInUserId() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userMapper.getUser(loggedInUsername);
        int userId = loggedInUser.getUserId();
        return userId;
    }

}
