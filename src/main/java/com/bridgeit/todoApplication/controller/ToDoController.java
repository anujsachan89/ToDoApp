package com.bridgeit.todoApplication.controller;
	
import java.util.List;

	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpSession;

	import org.apache.log4j.Logger;
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.bind.annotation.ResponseBody;
	import com.bridgeit.todoApplication.Json.ErrorResponse;
import com.bridgeit.todoApplication.Json.Response;
import com.bridgeit.todoApplication.Json.TaskResponse;
import com.bridgeit.todoApplication.model.ToDoTask;
import com.bridgeit.todoApplication.model.User;
import com.bridgeit.todoApplication.service.ToDoService;

	@Controller
	public class ToDoController {

		@Autowired
		private ToDoService toDoService;

		static final Logger logger = Logger.getLogger(ToDoController.class);

		@RequestMapping(value = "/createtask", method = RequestMethod.POST)
		public @ResponseBody Response addTask(@RequestBody ToDoTask todo, HttpServletRequest request) {

			ErrorResponse er = null;
			TaskResponse tr = null;
			HttpSession sess = request.getSession();
			User user = (User) sess.getAttribute("user");
			todo.setUser(user);
			logger.info("Task Created"+user);
			try {

				toDoService.addToDoTask(todo);
				tr = new TaskResponse();
				tr.setDoTask(todo);
				tr.setStatus(1);
				tr.setMessage("Task added successfully");
				logger.info("task created with name: "+todo);
				return tr;
				
			} catch (Exception e) {
				logger.error("ToDoTask Add exception", e);
				er = new ErrorResponse();
				er.setStatus(-1);
				er.setMessage("Internal server error, please try again.");
				return er;
			}
		}

		@RequestMapping(value = "/todoList" , method = RequestMethod.GET)
		public @ResponseBody Response getToDoList(HttpServletRequest request) {
			System.out.println("getToDOList");
			HttpSession sess = request.getSession();
			User user = (User) sess.getAttribute("user");
			System.out.println(user);
			ErrorResponse er = null;
			List<ToDoTask> toDoList;
			try {
				toDoList = toDoService.getToDoList(user.getId());

				TaskResponse tr = new TaskResponse();
				tr.setStatus(1);
				tr.setMessage("Data fetched sccessfully");
				tr.setList(toDoList);
				return tr;
			} catch (Exception e) {
				e.printStackTrace();
				er = new ErrorResponse();
				er.setStatus(-1);
				er.setMessage("Internal server error, please try again.");
				return er;
			}
		}

		@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
		public @ResponseBody Response deleteTask(@PathVariable("id") int taskId) {
			System.out.println(taskId);
			Response res = null;
			ErrorResponse er = null;
			try {
				toDoService.deleteTaskByToDoId(taskId);
				res = new Response();
				res.setStatus(1);
				res.setMessage("data delete scuessfully");
				return res;
			} catch (Exception e) {
				e.printStackTrace();
				er.setStatus(-1);
				er.setMessage("Internal server error, please try again");
				return er;
			}
		}

		@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
		public @ResponseBody Response updateTask(@RequestBody ToDoTask todo, @PathVariable("id") int taskId, HttpServletRequest request) {

			ErrorResponse er = null;
			TaskResponse tr = null;
			HttpSession sess = request.getSession();
			User user = (User) sess.getAttribute("user");
			todo.setUser(user);
			todo.setId(taskId);
		
			
			System.out.println("inside update");

			try {
				toDoService.addToDoTask(todo);
				tr = new TaskResponse();
				tr.setDoTask(todo);
				tr.setStatus(1);
				tr.setMessage("Task updated successfully");
				return tr;
			} catch (Exception e) {
				//logger.error("ToDoTask update exception", e);
				er = new ErrorResponse();
				er.setStatus(-1);
				er.setMessage("Internal server error, please try again.");
				return er;
			}
		}
		@RequestMapping(value = "/getUser", method = RequestMethod.GET)
		   public ResponseEntity<User> findUser(HttpServletRequest request) {
		   	HttpSession session=request.getSession();
		   	User user=(User) session.getAttribute("user");
		       
		       if(user==null){
		           return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		       }
		       return new ResponseEntity<User>(user, HttpStatus.OK);
		   }
	
}
