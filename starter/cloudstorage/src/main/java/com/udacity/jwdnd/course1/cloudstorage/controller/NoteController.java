package com.udacity.jwdnd.course1.cloudstorage.controller;

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
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private EncryptionService encryptionService;

    @PostMapping("/note")
    public String postOrUpdateNote(@ModelAttribute("noteForm") NoteForm noteForm, Model model) {

        String resultMessage;

        try {
            Integer noteIdNumber = noteForm.getNoteId();
            System.out.println(noteIdNumber);
            if (noteIdNumber != null) {
                // Note already exists
                int row = this.noteService.updateNote(noteForm);
                resultMessage = "Note updated successfully.";
            } else {
                // Create new note
                int noteId = this.noteService.addNote(noteForm);
                resultMessage = "Note created successfully.";
            }
            model.addAttribute("success", true);

        } catch (Exception e) {
            System.out.println(e);
            resultMessage = "Something went wrong!.";
            model.addAttribute("success", false);
        }
        model.addAttribute("resultMessage", resultMessage);
        return "result";
    }

    @GetMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable("id") Integer id,
                             Model model) {
        String resultMessage;

        System.out.println("the delete id is " + id.toString());
        try {
            int deletedItem = noteService.deleteNote(id);
            resultMessage = "Note deleted successfully.";
            model.addAttribute("success", true);
        } catch (Exception e) {
            System.out.println(e);
            resultMessage = "Something went wrong!.";
            model.addAttribute("success", false);
        }
        model.addAttribute("resultMessage", resultMessage);
        return "result";
    }
}
