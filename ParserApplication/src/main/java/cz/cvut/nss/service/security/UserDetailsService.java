package cz.cvut.nss.service.security;

import cz.cvut.nss.model.User;
import cz.cvut.nss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
{

    private final UserService userService;

    @Autowired
    public UserDetailsService(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        final User user = userService.getByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
        return new cz.cvut.nss.security.model.UserDetails(user);
    }
}
