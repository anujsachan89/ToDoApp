package com.bridgeit.todoApplication.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bridgeit.todoApplication.Json.ErrorResponse;
import com.bridgeit.todoApplication.Json.Response;
import com.bridgeit.todoApplication.Json.SignupErrorResponse;
import com.bridgeit.todoApplication.Validation.UserValidatation;
import com.bridgeit.todoApplication.model.Status;
import com.bridgeit.todoApplication.model.User;
import com.bridgeit.todoApplication.service.UserService;

@RestController
public class RegistrationController {

	@Autowired
    UserService userService;  
	@Autowired
	private UserValidatation userValidatation;

	static final Logger logger = Logger.getLogger(RegistrationController.class);
	
     
    //-------------------Create a User--------------------------------------------------------
     
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response addUser(@RequestBody User user, BindingResult bindingResult)
    
    {
		userValidatation.validate(user, bindingResult);
		SignupErrorResponse serror = null;
		if (bindingResult.hasErrors()) 
		{
			List<FieldError> list = bindingResult.getFieldErrors();
			serror = new SignupErrorResponse();
			serror.setStatus(-1);
			serror.setErrorlist(list);
			logger.error("Binding result has error"+user);
			return serror;
		}

		try 
		{
			System.out.println(user.toString());
			userService.addEntity(user);
			serror= new SignupErrorResponse();
			serror.setStatus(1);
			serror.setMessage("User added successfully");
			logger.info("User Saved Successfully"+user);
			return serror;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("signUp exception", e);
			ErrorResponse eerror = new ErrorResponse();
			eerror.setStatus(-1);
			eerror.setMessage("Internal server error, please try again.");
			logger.info("Server Error",e);
			return eerror;
		}
	}

    
     
    
    
    
    //------------------- Delete a User --------------------------------------------------------
     
   
        @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    	public @ResponseBody Status deleteEmployee(@PathVariable("id") int id) {

    		try {
    			userService.deleteEntity(id);
    			return new Status(1, "Employee deleted Successfully !");
    		} catch (Exception e) {
    			return new Status(0, e.toString());
    		}
    	}
 
     
    
 
}
