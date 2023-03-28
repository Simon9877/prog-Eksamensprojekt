package org.example;

public class LoginForm extends Form{
    public LoginForm(String email, String password) {
        super(email, password);
    }

    @Override
    public void executeForm() {
        if(getDataBase().isUserRegistered(getEmail(), getPassword())) {
            User newUser = getDataBase().getUser(getEmail(), getPassword());
            WelcomePage welPage = new WelcomePage(newUser);
            welPage.outputMessage();
            welPage.chat();
            //welPage.logoutMessage();
        }else {
            //put error handling here in time.
            System.out.println("User is not registered");
        }


    }
}