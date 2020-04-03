package RSP.security;

import RSP.security.model.AuthenticationToken;
import RSP.security.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationProvider implements AuthenticationProvider {
    //private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthenticationProvider.class);

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultAuthenticationProvider(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(
            Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password,passwordEncoder.encode(userDetails.getPassword())))
            throw new BadCredentialsException("Bad Credentials.");
        userDetails.eraseCredentials();
        return SecurityUtils.setCurrentUser(userDetails);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass) ||
                AuthenticationToken.class.isAssignableFrom(aClass);
    }
}
