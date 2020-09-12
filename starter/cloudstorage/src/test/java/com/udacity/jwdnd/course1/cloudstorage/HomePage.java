package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout")
    private WebElement logoutButton;

    // Navigation tabs

    @FindBy(id = "nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    // Notes tab

    @FindBy(id = "add-note")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-submit")
    private WebElement noteSubmitButton;

    @FindBy(id = "edit-note")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note")
    private WebElement deleteNoteButton;

    @FindBy(id = "row-note-title")
    private WebElement rowNoteTitle;

    @FindBy(id = "row-note-description")
    private WebElement rowNoteDescription;


    // Credentials tab

    @FindBy(id = "add-credential")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credential-submit")
    private WebElement credentialSubmitButton;

    @FindBy(className = "edit-credential")
    private WebElement editCredentialsButton;

    @FindBy(className = "delete-credential")
    private WebElement deleteCredentialsButton;

    @FindBy(id = "credentials-row")
    private WebElement credentialsRow;

    @FindBy(className = "row-password")
    private WebElement rowPassword;

    @FindBy(className = "row-url")
    private WebElement rowUrl;

    @FindBy(className = "row-username")
    private WebElement rowUsername;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //Methods

    public void clickLogout() {
        logoutButton.click();
    }

    public void clickNotesTab() {
        navNotesTab.click();
    }

    // Notes methods

    public void inputNoteTitle(String title) {
        noteTitle.clear();
        noteTitle.sendKeys(title);
    }

    public String getRowNoteTitle() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(rowNoteTitle.getText());
        return rowNoteTitle.getText();
    }

    public void inputNoteDescription(String description) {
        noteDescription.clear();
        noteDescription.sendKeys(description);
    }

    public String getRowNoteDescription() {
        return rowNoteDescription.getText();
    }

    public void addNote() throws InterruptedException {
        Thread.sleep(500);
        navNotesTab.click();
        Thread.sleep(500);
        addNoteButton.click();
        Thread.sleep(500);
        inputNoteTitle("Test Note");
        inputNoteDescription("Test note description");
        noteSubmitButton.click();
    }

    public void editNote() throws InterruptedException {
        Thread.sleep(500);
        navNotesTab.click();
        Thread.sleep(500);
        editNoteButton.click();
        Thread.sleep(500);
        inputNoteTitle("Updated Test Note");
        inputNoteDescription("Updated test note description");
        noteSubmitButton.click();
    }

    public void deleteNote() throws InterruptedException {
        clickNotesTab();
        Thread.sleep(500);
        deleteNoteButton.click();
        Thread.sleep(500);
    }

    public boolean checkNewNoteIsDisplayed() throws InterruptedException {
        // Checks note is displayed
        Thread.sleep(500);
        navNotesTab.click();
        Thread.sleep(500);
        return rowNoteTitle.isDisplayed();

    }

    public boolean checkNoteIsDeleted() throws InterruptedException {
        Thread.sleep(500);
        navNotesTab.click();
        Thread.sleep(500);
        try {
            return rowNoteTitle.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Element is not present, hence has been deleted");
            return true;
        }


    }

    // Credentials methods

    public void addCredentials() throws InterruptedException {
        Thread.sleep(500);
        navCredentialsTab.click();
        Thread.sleep(500);
        addCredentialButton.click();
        Thread.sleep(500);
        credentialUrl.sendKeys("https://www.testurl.com");
        credentialUsername.sendKeys("testuser");
        credentialPassword.sendKeys("password");
        credentialSubmitButton.click();
    }

    public void editCredentials() throws InterruptedException {
        Thread.sleep(500);
        credentialUrl.sendKeys(" updated");
        credentialUsername.sendKeys(" updated");
        credentialPassword.sendKeys("updated");
        credentialSubmitButton.click();
    }

    public void deleteCredentials() throws InterruptedException {
        Thread.sleep(500);
        deleteCredentialsButton.click();
    }

    public boolean checkCredentialsAreDisplayed() throws InterruptedException {
        Thread.sleep(500);
        navCredentialsTab.click();
        Thread.sleep(500);
        return credentialsRow.isDisplayed();

    }

    public boolean checkPasswordIsEncrypted() throws InterruptedException {
        Thread.sleep(500);
        return rowPassword.getText() != "password";
    }

    public boolean checkPasswordUnencryptedInModal() throws InterruptedException {
        Thread.sleep(500);
        editCredentialsButton.click();
        return credentialPassword.getAttribute("value").equals("password");
    }

    public boolean checkEditedCredentials() throws InterruptedException {
        Thread.sleep(500);
        navCredentialsTab.click();
        Thread.sleep(500);
        editCredentialsButton.click();
        if (credentialUrl.getAttribute("value").equals("https://www.testurl.com updated")
                && credentialUsername.getAttribute("value").equals("testuser updated")
                && credentialPassword.getAttribute("value").equals("passwordupdated")
        ) {
            return true;
        }
        return false;
    }

    public boolean checkCredentialsAreDeleted() throws InterruptedException {
        Thread.sleep(500);
        navCredentialsTab.click();
        Thread.sleep(500);
        try {
            return credentialsRow.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Element is not present, hence has been deleted");
            return true;
        }

    }

}
