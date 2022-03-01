package it.training.spring.boottwo;

import it.training.spring.boottwo.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    CustomUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("--- CustomUserDetailService.loadUserByUsername ---");
        System.out.println("Attempting to find user: "+username);
        if (repo.existsById(username)) {
            System.out.println("User "+username+" found!");
            CustomUser foundUser = repo.findById(username).get();


        List<GrantedAuthority> authList = new ArrayList<>();
        for (String role : foundUser.getRoles()) {
            authList.add(new SimpleGrantedAuthority(role));
        }
            return User
                    .builder()
                    .username(foundUser.getUsername())
                    .password(foundUser.getPassword())
                    .disabled(!foundUser.getEnabled())
                    .authorities(authList).build();
        } else throw new UsernameNotFoundException(username);
    }

}
