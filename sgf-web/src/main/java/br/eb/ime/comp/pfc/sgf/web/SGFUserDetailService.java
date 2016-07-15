package br.eb.ime.comp.pfc.sgf.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.eb.ime.comp.pfc.sgf.models.Client;
import br.eb.ime.comp.pfc.sgf.web.service.LoginService;

public class SGFUserDetailService implements UserDetailsService {

	@Autowired
	private LoginService service;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

		Client client = service.getClient(id);
		if (client == null)
			throw new UsernameNotFoundException(String.format("User %s was not found", id));
		
		List<GrantedAuthority> roles = new ArrayList<>();
		for (String role : client.getRoles()) {
			roles.add(new SimpleGrantedAuthority(role));
		}
		
		return new User(client.getUsername(), client.getPassword(), roles);
	}

}
