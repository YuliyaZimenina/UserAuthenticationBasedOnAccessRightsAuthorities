package net.juliya.zimenina.UserAuthenticationBasedOnAccessRightsAuthorities.config;


import net.juliya.zimenina.UserAuthenticationBasedOnAccessRightsAuthorities.model.Permission;
import net.juliya.zimenina.UserAuthenticationBasedOnAccessRightsAuthorities.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.EMPLOYEES_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(Permission.EMPLOYEES_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Permission.EMPLOYEES_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("Admin")
                        .password(passwordEncoder().encode("admin"))
                        .authorities(Role.ADMIN.getAuthorities())
                        .build(),
                User.builder()
                        .username("User")
                        .password(passwordEncoder().encode("user"))
                        .authorities(Role.USER.getAuthorities())
                        .build()
        );
    }

    // Password encoding method
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
