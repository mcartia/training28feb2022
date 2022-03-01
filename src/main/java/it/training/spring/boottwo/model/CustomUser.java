package it.training.spring.boottwo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CustomUser {

    @Id String username;
    String password;
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> roles;
    Boolean enabled;

    public CustomUser() {
        super();
        this.roles = new ArrayList<>();
    }

    public CustomUser(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        this.roles.add(role);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {

        String roleStr = "";
        for (String role : roles) {
            roleStr+=role+" ";
        }

        return "CustomUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled='" + enabled + '\'' +
                ", roles=" + roleStr +
                '}';
    }
}
