package it.training.spring.boottwo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomUserDetailService cuService;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    public void configure(HttpSecurity httpSec) throws Exception {
        httpSec.authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                /* h2 settings */
                .antMatchers("/h2-console/**")
                .permitAll()
                .and().csrf().disable()
                .headers().frameOptions().disable()
                /* end h2 settings */
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().httpBasic()
                .and().logout().permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("admin")
                .password(passwordEncoder.encode("12345678"))
                .roles("USER","ADMIN")
                .and()
                .withUser("user")
                .password(passwordEncoder.encode("12345678"))
                .roles("USER");

        /*auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource)
                .usersByUsernameQuery("select USERNAME, PASSWORD, ENABLED FROM CUSTOM_USER WHERE USERNAME=?")
                .authoritiesByUsernameQuery("select CUSTOM_USER_USERNAME, ROLES FROM CUSTOM_USER_ROLES WHERE CUSTOM_USER_USERNAME=?");*/
                /*.withDefaultSchema().withUser("db_admin")
                .password(passwordEncoder.encode("12345678"))
                .roles("USER","ADMIN")
                .and()
                .withUser("db_user")
                .password(passwordEncoder.encode("12345678"))
                .roles("USER");*/

        //auth.userDetailsService(cuService);
        customAuthenticationProvider.passwordEncoder = passwordEncoder;
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        String idForEncode = "bcrypt";
        Map<String,PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
        return passwordEncoder;
    }

}
