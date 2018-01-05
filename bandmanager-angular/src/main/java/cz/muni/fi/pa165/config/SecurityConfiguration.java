package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.enums.SecurityRole;
import cz.muni.fi.pa165.security.CustomAuthenticationProvider;
import cz.muni.fi.pa165.security.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.inject.Inject;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = {CustomAuthenticationProvider.class})
@Import(RestConfiguration.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Inject
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/rest/**").permitAll()

                .antMatchers(HttpMethod.GET, "/rest/songs").hasRole(SecurityRole.MEMBER.name())
                .antMatchers(HttpMethod.GET, "/rest/songs").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/rest/songs/create").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/rest/songs").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/rest/songs").hasRole(SecurityRole.MANAGER.name())

                .antMatchers(HttpMethod.GET, "/rest/albums").hasRole(SecurityRole.MEMBER.name())
                .antMatchers(HttpMethod.GET, "/rest/albums").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/rest/albums/create").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/rest/albums").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/rest/albums").hasRole(SecurityRole.MANAGER.name())

                .antMatchers(HttpMethod.GET, "/rest/bands").hasRole(SecurityRole.MEMBER.name())
                .antMatchers(HttpMethod.GET, "/rest/bands").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/rest/bands/create").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/rest/bands").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/rest/bands").hasRole(SecurityRole.MANAGER.name())

                .antMatchers(HttpMethod.GET, "/rest/bandinvites").hasRole(SecurityRole.MEMBER.name())
                .antMatchers(HttpMethod.GET, "/rest/bandinvites").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/rest/bandinvites").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/rest/bandinvites").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/rest/bandinvites").hasRole(SecurityRole.MANAGER.name())

                .antMatchers(HttpMethod.GET, "/rest/tours").hasRole(SecurityRole.MEMBER.name())
                .antMatchers(HttpMethod.GET, "/rest/tours").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/rest/tours/create").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/rest/tours").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/rest/tours").hasRole(SecurityRole.MANAGER.name())

                .antMatchers(HttpMethod.GET, "/rest/members").hasRole(SecurityRole.MEMBER.name())
                .antMatchers(HttpMethod.GET, "/rest/members").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/rest/members").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/rest/members").hasRole(SecurityRole.MEMBER.name())
                .antMatchers(HttpMethod.PUT, "/rest/members").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/rest/members").hasRole(SecurityRole.MEMBER.name())
                .antMatchers(HttpMethod.DELETE, "/rest/members").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/rest/members").hasRole(SecurityRole.MEMBER.name())

                .antMatchers(HttpMethod.GET, "/rest/managers").hasRole(SecurityRole.MEMBER.name())
                .antMatchers(HttpMethod.GET, "/rest/managers").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.POST, "/rest/managers").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.PUT, "/rest/managers").hasRole(SecurityRole.MANAGER.name())
                .antMatchers(HttpMethod.DELETE, "/rest/managers").hasRole(SecurityRole.MANAGER.name())

                .antMatchers("/js/**").permitAll()
                .antMatchers("/partials/**").permitAll()
                .antMatchers("/index.html").permitAll()

                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login.html").permitAll().and()
                .logout().logoutUrl("/logout.html").logoutSuccessUrl("/index.html?logout").permitAll().and()
                .csrf().disable();
    }
}
