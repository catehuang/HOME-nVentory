package services;

import dataaccess.UserDB;
import java.util.List;
import models.Users;

public class AccountService {

    public Users login(String username, String password) {
        
        if (username == null || username.equals("") || password == null || password.equals("")){
            return null;
        }

        UserDB userDB = new UserDB();
        Users user = null;

        user = userDB.get(username);
        
        if (user == null) {
            return null;
        }
        
        if (password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    public Users get(String username) {
        UserDB userDB = new UserDB();
        Users user = null;

        user = userDB.get(username);
        return user;
    }

    public List<Users> getAll() {
        UserDB userDB = new UserDB();
        List<Users> users = userDB.getAll();
        return users;
    }

    public void insert(String username, String password, String email, String firstname, String lastname) throws Exception {
        Users user = new Users();
        UserDB userDB = new UserDB();

        if (username == null || username.equals("")) 
        {
            throw new Exception("empty_string");
        }
        else if (userDB.get(username) == null)
        {
            user.setUsername(username);
        }
        else {
            throw new Exception("username_exists");
        }

        if (password == null || password.equals("")) 
        {
            throw new Exception("empty_string");
        } 
        else if (password.length() > 20) 
        {
            throw new Exception("password_20");

        } else {
            user.setPassword(password);
        }

        if (email == null || email.equals("")) 
        {
            throw new Exception("empty_string");
        } 
        else if (email.length() > 50) 
        {
            throw new Exception("email_50");
        } 
        else 
        {
            user.setEmail(email);
        }

        if (firstname == null || firstname.equals("")) 
        {
            throw new Exception("empty_string");
        } 
        else if (firstname.length() > 50) 
        {
            throw new Exception("firstname_50");

        } 
        else 
        {
            user.setFirstName(firstname);
        }

        if (lastname == null || lastname.equals("")) 
        {
            throw new Exception("empty_string");
        } 
        else if (lastname.length() > 50) 
        {
            throw new Exception("lastname_50");
        } 
        else 
        {
            user.setLastName(lastname);
        }
        user.setIsAdmin(false);
        user.setActive(false);

        userDB.insert(user);
    }

    public void update(String username, String password, String email, String firstname, String lastname, String original) throws Exception {
        Users user = new Users(original);
        
        if (username == null || username.equals("")) {
            throw new Exception("empty_string");
        } else {
            user.setUsername(username);
        }

        if (password == null || password.equals("")) {
            throw new Exception("empty_string");
        } else if (password.length() > 20) {
            throw new Exception("password_20");

        } else {
            user.setPassword(password);
        }

        if (email == null || email.equals("")) {
            throw new Exception("empty_string");

        } else if (email.length() > 50) {
            throw new Exception("email_50");
        } else {
            user.setEmail(email);
        }

        if (firstname == null || firstname.equals("")) {
            throw new Exception("empty_string");
        } else if (firstname.length() > 50) {
            throw new Exception("firstname_50");

        } else {
            user.setFirstName(firstname);
        }

        if (lastname == null || lastname.equals("")) {
            throw new Exception("empty_string");
        } else if (lastname.length() > 50) {
            throw new Exception("lastname_50");

        } else {
            user.setLastName(lastname);
        }
        
        UserDB userDB = new UserDB();
        userDB.update(user);
    }

    public void delete(String username, String adminUser) throws Exception {
        if (username.equals(adminUser)){
            throw new Exception("admins_cannot_delete_themselves");
        } 
        else
        {
            UserDB userDB = new UserDB();
            Users user = userDB.get(username);
            userDB.delete(user);
        }
    }
}
