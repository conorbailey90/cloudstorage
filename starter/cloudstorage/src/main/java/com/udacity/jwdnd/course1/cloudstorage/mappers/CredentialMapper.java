package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredentials(Integer credentialid);

    @Insert("INSERT INTO CREDENTIALS ( url, username, key, password, userid)" +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})" )
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int addCredential(Credential credential);
}
