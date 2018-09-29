package rs.fon.rateit.model;

/**
 * Created by pavlepavle on 17-Jun-17.
 */

public class User {


    String username;
    String email;
    String password;
    int admin;

    public User(String username, String email, String password, int admin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public User() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return username;
    }
}
