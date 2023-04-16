// Denne klasse håndterer alt vedrørende databaseoperationer.

package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    // Angiver databaseforbindelsesparametre.
    private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/chat?useSSL=false&allowPublicKeyRetrieval=true";
    private final static String DATABASE_USER = "chat";
    private final static String DATABASE_PASSWORD = "chat";
    // Angiver SQL-forespørgsler til at finde brugerinformationer og indsætte nye brugere.
    private final static String SELECT_USER_SQL = "SELECT * FROM users WHERE email=? AND password=?";
    // Angiver SQL-forespørgsel for at indsætte bruger.
    private final static String INSERT_USER_SQL = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
    // Angiver SQL-forespørgsel for at oprette brugertabel.
    private final static String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(255), email VARCHAR(255), password VARCHAR(255), PRIMARY KEY (id))";

    // Opretter forbindelse til databasen og opretter brugertabel, hvis den ikke allerede eksisterer.
    public Database() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tjekker, om en bruger er registreret i databasen med den angivne email og password.
    public boolean isUserRegistered(String email, String password) {
        boolean returnVal = false;
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_SQL)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    returnVal = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    // Henter en bruger fra databasen med den angivne email og password.
    public User getUser(String email, String password) {
        User returnVal = null;
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_SQL)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    returnVal = new User(name, email, password);
                    returnVal.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    // Tilføjer en ny bruger til databasen med de angivne oplysninger.
    public void addUserToDatabase(User newUser) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER_SQL)) {
            stmt.setString(1, newUser.getName());
            stmt.setString(2, newUser.getEmail());
            stmt.setString(3, newUser.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}