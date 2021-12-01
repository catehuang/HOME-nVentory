package services;

import dataaccess.UserDB;
import java.util.List;
import models.*;

public class AccountService {

    public User login(String email, String password) {
        
        if (email == null || email.equals("") || password == null || password.equals("")){
            return null;
        }

        UserDB userDB = new UserDB();
        User user = null;

        user = userDB.get(email);
        
        if (user == null) {
            return null;
        }
        
        if (password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    public User get(String email) {
        UserDB userDB = new UserDB();
        User user = null;

        user = userDB.get(email);
        return user;
    }

    public List<User> getAll() {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    public void insert(String email, String password, boolean active, String firstname, String lastname, int role) throws Exception {
        User user = new User();
        UserDB userDB = new UserDB();
        
        // check email
        if (email == null || email.equals("")) 
        {
            throw new Exception("empty_string");
        }
        else if (email.length() > 40)
        {
            throw new Exception("email_40");
        }
        else if (userDB.get(email) == null) // check this email is avaliable for registration
        {
            user.setEmail(email);
        }
        else {
            throw new Exception("email_exists");
        }

        // check password
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

        // check firstname
        if (firstname == null || firstname.equals("")) 
        {
            throw new Exception("empty_string");
        } 
        else if (firstname.length() > 20) 
        {
            throw new Exception("firstname_20");
        } 
        else 
        {
            user.setFirstName(firstname);
        }

        // check lastname
        if (lastname == null || lastname.equals("")) 
        {
            throw new Exception("empty_string");
        } 
        else if (lastname.length() > 20) 
        {
            throw new Exception("lastname_20");
        } 
        else 
        {
            user.setLastName(lastname);
        }
        
        user.setActive(active);
        user.setRole(new Role(role));
        
        userDB.insert(user);
    }

    public void update(String email, String password, boolean active, String firstname, String lastname, int role, String login_email) throws Exception {
        UserDB userDB = new UserDB();
        User login_user = userDB.get(login_email);
        User user = new User();
        
        // check email
        if (email == null || email.equals("")) 
        {
            throw new Exception("empty_string");
        }
        else if (email.length() > 40)
        {
            throw new Exception("email_40");
        }
        else if (userDB.get(email) == null) // check this email is avaliable for registration
        {
            user.setEmail(email);
        }
        else {
            throw new Exception("email_exists");
        }

        // check password
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

        // check firstname
        if (firstname == null || firstname.equals("")) 
        {
            throw new Exception("empty_string");
        } 
        else if (firstname.length() > 20) 
        {
            throw new Exception("firstname_20");
        } 
        else 
        {
            user.setFirstName(firstname);
        }

        // check lastname
        if (lastname == null || lastname.equals("")) 
        {
            throw new Exception("empty_string");
        } 
        else if (lastname.length() > 20) 
        {
            throw new Exception("lastname_20");
        } 
        else 
        {
            user.setLastName(lastname);
        }
        
        user.setActive(active);
        user.setRole(new Role(role));

        if (! email.equals(login_email))
        {
            delete(login_email, login_email);
        }
        userDB.update(user);
    }

    public void delete(String email, String adminUser) throws Exception {
        if (email.equals(adminUser)){
            throw new Exception("admins_cannot_delete_themselves");
        } 
        else
        {
            UserDB userDB = new UserDB();
            User user = userDB.get(email);
            userDB.delete(user);
        }
    }
}
