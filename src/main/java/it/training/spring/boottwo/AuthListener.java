package it.training.spring.boottwo;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthListener {

    @EventListener
    public void authOk(AuthenticationSuccessEvent authOK) {
        System.out.println("+++ Auth ok for: "+authOK.getAuthentication().getName());
    }

    @EventListener
    public void authKO(AuthenticationFailureBadCredentialsEvent authKO) {
        System.out.println("--- Auth KO for: "+authKO.getAuthentication().getName());
    }

}
