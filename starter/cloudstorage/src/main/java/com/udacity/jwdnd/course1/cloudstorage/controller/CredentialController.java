package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private FileService fileService;

    @PostMapping("/credentials")
    public String postOrUpdateCredentials(@ModelAttribute("credentialsForm") CredentialsForm credentialsForm, Model model) {
        String resultMessage;
        try {
            Integer credentialIdNumber = credentialsForm.getCredentialsId();
            if (credentialIdNumber != null) {
                int row = this.credentialsService.updateCredentials(credentialsForm);
                model.addAttribute("success", true);
                resultMessage = "Credential details for " + credentialsForm.getUrl() + " have been updated.";
            } else {
                int credentialsId = this.credentialsService.addCredentials(credentialsForm);
                model.addAttribute("success", true);
                resultMessage = "Credential details for " + credentialsForm.getUrl() + " have been saved.";
            }
        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("success", false);
            resultMessage = "Something went wrong!";
        }

        model.addAttribute("resultMessage", resultMessage);
        return "result";
    }

    @GetMapping("/credentials/delete/{id}")
    public String deleteCredential(@PathVariable("id") Integer id, @ModelAttribute("credentialsForm") CredentialsForm credentialsForm,
                                   @ModelAttribute("noteForm") NoteForm noteForm, Model model) {
        String resultMessage;
        try {
            System.out.println("the delete id is " + id.toString());
            int deletedItem = credentialsService.deleteCredential(id);
            model.addAttribute("success", true);
            resultMessage = "Credential details deleted successfully.";

        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("success", false);
            resultMessage = "Something went wrong!";
        }

        model.addAttribute("resultMessage", resultMessage);
        return "result";
    }
}
