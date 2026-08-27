package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;

public class AuthService {
    private final UserRepository repo=new UserRepository();
    public User login(String email,String password){
        User u=repo.findByEmail(email);
        if(u==null || !u.isActive()) return null;
        return u.getPassword().equals(password) ? u : null;
    }
}
