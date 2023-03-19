package zw.co.rapiddata.Config.Service;

import zw.co.rapiddata.Config.Models.Users;
import zw.co.rapiddata.Config.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String saveUser(Users user) {
        if(userRepository.existsByEmail(user.getEmail())){
            return "User Already Exists!";
        }
        userRepository.save(user);
        return "User Saved!";
    }
}
