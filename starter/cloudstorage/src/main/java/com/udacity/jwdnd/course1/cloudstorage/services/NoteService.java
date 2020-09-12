package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

    private List<Note> notes;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;

    }

    @PostConstruct
    public void postConstruct() {
        this.notes = new ArrayList<>();

    }

    public List<Note> getNotes() {
        int userId = getLoggedInUserId();
        this.notes = noteMapper.getNotesForUser(userId);
        return new ArrayList<>(this.notes);
    }

    public int addNote(NoteForm noteForm) {
        int userId = getLoggedInUserId();
        String noteTitle = noteForm.getNoteTitle();
        String noteDescription = noteForm.getNoteDescription();
        Note note = new Note(null, noteTitle, noteDescription, userId);
        int row = noteMapper.addNote(note);
        System.out.println(note.getNoteId());
        return note.getNoteId();
    }

    public int updateNote(NoteForm noteForm) {
        int noteId = noteForm.getNoteId();
        int userId = getLoggedInUserId();
        Note note = new Note(noteId, noteForm.getNoteTitle(), noteForm.getNoteDescription(), userId);
        int row = noteMapper.updateNote(note);
        return row;
    }

    public int deleteNote(int noteId) {
        int deletedNote = noteMapper.deleteNote(noteId);
        return deletedNote;
    }

    public int getLoggedInUserId() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userMapper.getUser(loggedInUsername);
        int userId = loggedInUser.getUserId();
        return userId;
    }

}
