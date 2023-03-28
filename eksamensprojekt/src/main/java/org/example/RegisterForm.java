package org.example;

public class RegisterForm extends Form{

    private String name;
    private String dateOfBirth;

    public RegisterForm(String name, String dateOfBirth, String email, String password) {
        super(email, password);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return this.name;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    @Override
    public void executeForm() {
        User newUser = new User(getName(), getDateOfBirth(), getEmail(), getPassword());
        getDataBase().addUserToDatabase(newUser);
        WelcomePage welPage = new WelcomePage(newUser);
        welPage.outputMessage();
        welPage.chat();
        //welPage.logoutMessage();

    }


}