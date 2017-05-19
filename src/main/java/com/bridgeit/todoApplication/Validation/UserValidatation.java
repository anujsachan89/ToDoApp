package com.bridgeit.todoApplication.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bridgeit.todoApplication.model.User;

public class UserValidatation implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	String STRING_PATTERN = "[a-zA-Z]+";
	String MOBILE_PATTERN = "[0-9]{10}";

	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object target, Errors errors) {
		User user = (User) target;

		ValidationUtils.rejectIfEmpty(errors, "fullName", "required.fullName", " Name is required.");

		// input string conatains characters only
		if ( user.getFullName() != null && !user.getFullName().isEmpty() )
		{
			pattern = Pattern.compile(STRING_PATTERN);
			matcher = pattern.matcher(user.getFullName());
			
		}

		

		// email validation in spring
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.email", "Email is required.");

		if ( user.getEmail() != null && !user.getEmail().isEmpty() ) 
		{
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(user.getEmail());
			if (!matcher.matches()) {
				errors.rejectValue("email", "email.incorrect", "Enter a correct email");
			}
		}

		// phone number validation
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "required.mobile", "Mobile Number is required.");

		if ( user.getMobile() != null && !user.getMobile().isEmpty() )
		{
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(user.getMobile());
			if (!matcher.matches()) {
				errors.rejectValue("mobile", "mobile.incorrect", "Enter a correct Mobile number");
			}
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password", "Password is required.");


	}

}