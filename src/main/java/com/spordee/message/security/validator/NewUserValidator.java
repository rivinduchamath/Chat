package com.spordee.message.security.validator;

import com.spordee.message.security.model.User;
import com.spordee.message.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NewUserValidator implements Validator {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User newUser = (User) target;
		if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
			errors.rejectValue("username", "new.account.username.already.exists");
		}
	}
}
