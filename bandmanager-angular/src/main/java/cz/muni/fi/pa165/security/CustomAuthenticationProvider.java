package cz.muni.fi.pa165.security;

import cz.fi.muni.pa165.dto.UserAuthDTO;
import cz.fi.muni.pa165.facade.ManagerFacade;
import cz.fi.muni.pa165.facade.MemberFacade;
import cz.muni.fi.pa165.enums.SecurityRole;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Iurii xkuznetc Kuznetcov
 */

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private ManagerFacade managerFacade;

    @Inject
    private MemberFacade memberFacade;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserAuthDTO transportDTO = new UserAuthDTO();
        transportDTO.setEmail(email);
        transportDTO.setPassword(password);

        boolean memberAuthenticatedSuccefully = memberFacade.authenticate(transportDTO);
        boolean managerAuthenticatedSuccefully = managerFacade.authenticate(transportDTO);

        if (managerAuthenticatedSuccefully) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_" + SecurityRole.MANAGER.toString()));
            return new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
        } else if (memberAuthenticatedSuccefully) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_" + SecurityRole.MEMBER.toString()));
            return new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
        } else {
            throw new BadCredentialsException("Invalid credentials.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
