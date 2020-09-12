package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(className = "btn-primary")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) {
        inputUsername.sendKeys(username);
    }

    public void enterPassword(String password) {
        inputPassword.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void login() {
        enterUsername("testuser");
        enterPassword("password");
        clickLoginButton();
    }


}
