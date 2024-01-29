package com.spordee.message.security.services;

import com.spordee.message.security.model.User;
import com.spordee.message.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl  {
	@Autowired
	UserRepository userRepository;

//	@Override
//	@Transactional
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepository.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//
//		return UserDetailsImpl.build(user);
//	}

}
