package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HomeController {

    private NoteService noteService;

    public HomeController(NoteService noteService){
        this.noteService = noteService;
    }

    @GetMapping
    public String getHome(@ModelAttribute("noteForm") NoteForm noteForm, Model model){
//        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println("Logged in user is " + loggedInUsername);
        model.addAttribute("notes", this.noteService.getNotes());
        return "home";
    }

    // Note section

    @PostMapping("/note")
    public String postNote(@ModelAttribute("noteForm") NoteForm noteForm, Model model){
        int noteId = this.noteService.addNote(noteForm);
        model.addAttribute("notes", this.noteService.getNotes());
        return "home";
    }

    @GetMapping("/note/deletenote/{id}")
    public String deleteNote(@PathVariable("id") Integer id, Model model){

        return "home";
    }



}
