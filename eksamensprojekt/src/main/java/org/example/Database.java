package org.example;

import java.util.ArrayList;

public class Database {
    private static ArrayList<User> dataBase = new ArrayList<User>();

    static {
        dataBase.add(new User("Tom", "t@gmail.com", "pass"));
        dataBase.add(new User("Bryan", "b@gmail.com", "password"));
        dataBase.add(new User("Tarence", "tt@gmail.com", "passwordismypassword"));
    }



    public boolean isUserRegistered(String email, String password) {
        boolean returnVal = false;
        for(User regedUser : dataBase) {
            if(regedUser.getEmail().contentEquals(email)&&regedUser.getPassword().contentEquals(password)) {
                returnVal = true;
            }
        }
        return returnVal;
    }

    public User getUser(String email, String password) {
        User returnVal = new User();
        for(User regedUser : dataBase) {
            if(regedUser.getEmail().contentEquals(email)&&regedUser.getPassword().contentEquals(password)) {
                returnVal = regedUser;
            }
        }
        return returnVal;
    }

    public void addUserToDatabase(User newUser) {
        dataBase.add(newUser);
    }
}
