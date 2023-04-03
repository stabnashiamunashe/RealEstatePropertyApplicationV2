package zw.co.rapiddata.Security.Service;

import zw.co.rapiddata.Security.Models.SecurityUser;
import zw.co.rapiddata.Security.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(email)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("Username " + email + " not found!"));
    }
}
