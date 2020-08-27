package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

//    private List<Note> notes;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper){
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;

    }

//    @PostConstruct
//    public void postConstruct(){
//        this.notes = new ArrayList<>();
//
//    }

//    public void addNote(NoteForm noteForm){
//        Note note = new Note();
//        note.setNoteid(1);
//        note.setNotedescription(noteForm.getNoteDescription());
//        note.setNotetitle(noteForm.getNoteTitle());
//        note.setUserid(1);
//        notes.add(note);
//    }

    public int addNote(NoteForm noteForm){

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userMapper.getUser(loggedInUsername);
        String noteTitle = noteForm.getNoteTitle();
        String noteDescription = noteForm.getNoteDescription();
        int row = noteMapper.addNote(new Note(null, noteTitle,noteDescription,loggedInUser.getUserId()));
        System.out.println(row);
        return row;
    }


    public List<Note> getNotes(){
//        return new ArrayList<>(this.notes);
        return new ArrayList<>();
    }
}
