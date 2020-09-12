package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES ORDER BY noteId DESC")
    List<Note> getNotes();

    @Select("SELECT * FROM NOTES WHERE userId = #{userId} ORDER BY noteId DESC")
    List<Note> getNotesForUser(int userId);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(int noteId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) " +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyColumn = "noteId", keyProperty = "noteId")
    int addNote(Note note);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    @Options(useGeneratedKeys = true, keyColumn = "noteId", keyProperty = "noteId")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNote(int noteId);
}
