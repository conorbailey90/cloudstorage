package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialsService credentialService;
    @Autowired
    private EncryptionService encryptionService;

    @PostMapping("/files")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload,
                                   Model model) {
        String resultMessage;
        try {
            int row = this.fileService.uploadFile(fileUpload);
            model.addAttribute("success", true);
            resultMessage = "File uploaded successfully.";
        } catch (Exception e) {
            System.out.println(e);
            resultMessage = "Something went wrong!";
        }

        model.addAttribute("resultMessage", resultMessage);
        return "result";

    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile(@RequestParam String fileId) {

        int id = Integer.parseInt(fileId);
        File file = fileService.getFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFilename() + "\"").body(new
                        ByteArrayResource(file.getFiledata()));
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam("fileId") String fileId,
                             Model model) {
        String resultMessage;
        try {
            int id = Integer.parseInt(fileId);
            int deletedFile = fileService.deleteFile(id);
            model.addAttribute("success", true);
            resultMessage = "File deleted successfully!";

        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("success", false);
            resultMessage = "Something went wrong!";
        }
        model.addAttribute("resultMessage", resultMessage);

        return "result";
    }
}
