package org.example;

public class RegisterForm extends Form{

    private String name;

    public RegisterForm(String name, String email, String password) {
        super(email, password);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void executeForm() {
        User newUser = new User(getName(), getEmail(), getPassword());
        getDataBase().addUserToDatabase(newUser);
        WelcomePage welPage = new WelcomePage(newUser);
        welPage.outputMessage();
        welPage.chat();
        //welPage.logoutMessage();

    }


}