package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialsId = #{credentialsId}")
    Credentials getCredentials(Integer credentialsId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credentials> getCredentialsForUser(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId)" +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialsId")
    int addCredential(Credentials credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} " +
            "WHERE credentialsId = #{credentialsId}")
    @Options(useGeneratedKeys = true, keyColumn = "credentialsId", keyProperty = "credentialsId")
    int updateCredentials(Credentials credentials);

    @Delete("DELETE FROM credentials WHERE credentialsId = #{credentialsId}")
    int deleteCredential(int credentialsId);
}
