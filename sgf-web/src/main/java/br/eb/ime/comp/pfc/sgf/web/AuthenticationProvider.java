package br.eb.ime.comp.pfc.sgf.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import br.eb.ime.comp.pfc.sgf.models.Client;
import br.eb.ime.comp.pfc.sgf.web.service.LoginService;

public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private LoginService service;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String id, UsernamePasswordAuthenticationToken auth)
			throws AuthenticationException {
		UserDetails loadedUser;
		
		try{
			Client client = service.getClient(id);
			List<GrantedAuthority> roles = new ArrayList<>();
			for(String role : client.getRoles()){
				roles.add(new SimpleGrantedAuthority(role));
			}
			loadedUser = new User(client.getUsername(), client.getPassword(), roles);
		}catch (Exception serviceProblem) {
            throw new InternalAuthenticationServiceException(serviceProblem.getMessage(), serviceProblem);
        }
		
        return loadedUser;
	}

}
