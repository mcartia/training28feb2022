package it.training.spring.boottwo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomUserDetailService customUserDetailService;

    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String providedUser = authentication.getName();
        String providedPassword = authentication.getCredentials().toString();

        System.out.println("--- CustomAuthenticationProvider.authenticate ---");

        UserDetails foundUser = customUserDetailService.loadUserByUsername(providedUser);
        if (passwordEncoder.matches(providedPassword, foundUser.getPassword())) {
            return new UsernamePasswordAuthenticationToken(foundUser,foundUser.getPassword(),foundUser.getAuthorities());
        } else {
            throw new BadCredentialsException(providedUser);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
