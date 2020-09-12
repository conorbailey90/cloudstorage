package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;

    private List<Credentials> credentials;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void postConstruct() {
        this.credentials = new ArrayList<>();
    }

    public List<Credentials> getCredentials() {
        int userId = getLoggedInUserId();
        this.credentials = credentialsMapper.getCredentialsForUser(userId);
        return new ArrayList<>(this.credentials);
    }

    public int addCredentials(CredentialsForm credentialsForm) {
        int userId = getLoggedInUserId();
        String url = credentialsForm.getUrl();
        String username = credentialsForm.getUsername();
        String password = credentialsForm.getPassword();

        // Generate key
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        // Generate encrypted password using the above key
        String encryptedPassword = this.encryptionService.encryptValue(password, encodedKey);
        System.out.println("The encrypted is: " + encryptedPassword);

        String decryptedPassword = this.encryptionService.decryptValue(encryptedPassword, encodedKey);
        System.out.println("the decrypted password is: " + decryptedPassword);

        Credentials newCredentials = new Credentials(null, url, username, encodedKey, encryptedPassword, userId);
        int row = credentialsMapper.addCredential(newCredentials);
        System.out.println(newCredentials.getCredentialsId());
        return newCredentials.getCredentialsId();

    }

    public int updateCredentials(CredentialsForm credentialsForm) {
        Credentials credentials = credentialsMapper.getCredentials(credentialsForm.getCredentialsId());
        credentials.setUrl(credentialsForm.getUrl());
        credentials.setUsername(credentialsForm.getUsername());
        String encryptedPassword = encryptionService.encryptValue(credentialsForm.getPassword(), credentials.getKey());
        credentials.setPassword(encryptedPassword);
        int row = credentialsMapper.updateCredentials(credentials);
        return row;
    }

    public int deleteCredential(int credentialsId) {
        int deletedCredentials = credentialsMapper.deleteCredential(credentialsId);
        return deletedCredentials;
    }


    public int getLoggedInUserId() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userMapper.getUser(loggedInUsername);
        int userId = loggedInUser.getUserId();
        return userId;
    }
}
