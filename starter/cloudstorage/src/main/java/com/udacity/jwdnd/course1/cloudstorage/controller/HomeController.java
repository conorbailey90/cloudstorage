package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialsService credentialService;
    @Autowired
    private EncryptionService encryptionService;

    @GetMapping
    public String getHome(@ModelAttribute("credentialsForm") CredentialsForm credentialsForm,
                          @ModelAttribute("noteForm") NoteForm noteForm,
                          @ModelAttribute("fileForm") FileForm fileForm, Model model) {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(loggedInUsername);
        if (loggedInUsername != null) {

            model.addAttribute("files", this.fileService.getFiles());
            model.addAttribute("notes", this.noteService.getNotes());
            model.addAttribute("credentials", this.credentialService.getCredentials());
            model.addAttribute("encryptionService", this.encryptionService);
        }
        return "home";
    }

    @PostMapping("/logout")
    public String logout() {
        System.out.println("works");
        return "redirect:/login?logout";
    }

}
