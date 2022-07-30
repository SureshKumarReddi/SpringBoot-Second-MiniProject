package com.suresh.unittestingcontrollers;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;//STATIC IMPORT
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suresh.bindings.User;
import com.suresh.constants.AppConstants;
import com.suresh.controllers.RegistrationController;
import com.suresh.service.RegistrationService;

@WebMvcTest(controllers = RegistrationController.class)
public class RegistrationControllerTest {

	@MockBean //dependent mock object is created 
	 private RegistrationService mockService;
	
	@Autowired  //for sending http requests
	private MockMvc mvc;

	
	@Test
	public void emailVerificationTestTrue() throws Exception {
		//1.setting the behaviour for mockobject instead of calling actual service method to  stop the changes to the Database
		when(mockService.uniqueEmail("suresh.y@hcl.com")).thenReturn(true);
		//2.creating the http request using MockMvcRequestBuilders based on the request method in controller class
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/checkemail/suresh.y@hcl.com");
	
		//for sending http request..
		 MvcResult result = mvc.perform(builder).andReturn();
	    MockHttpServletResponse response = result.getResponse(); //returns the response of checkEmail method
		String actualresult = response.getContentAsString();
		assertEquals(AppConstants.UNIQUE, actualresult);;
		//assertEquals suggestion is not showing for this
		//we need to do STATIC IMPORT for this.
	}
	
	@Test
	public void emailVerificationTestFalse() throws Exception {
	
		when(mockService.uniqueEmail("Welcome@123.com")).thenReturn(false);
		
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/checkemail/Welcome@123.com");
		
		MvcResult result = mvc.perform(builder).andReturn();
		
		String response = result.getResponse().getContentAsString();
		
		assertEquals(AppConstants.DUPLICATE,response);
		
	}
	
	@Test
	public void getCountriesTest() throws Exception{
		
		Map<Integer,String> map = new HashMap<>();
		map.put(1,"India");
		map.put(2,"USA");
		
		when(mockService.getCountries()).thenReturn(map);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/allCountries");
		
		MvcResult result = mvc.perform(builder).andReturn();
		
		int actualResult = result.getResponse().getStatus();
		
		assertEquals(200,actualResult);
		
	}
	
	@Test
	public void getStatesTest() throws Exception{
		
		Map<Integer,String> map = new HashMap<>();
		map.put(1,"AP");
		map.put(2,"TG");
		
		when(mockService.getCountries()).thenReturn(map);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/states/1");
		
		MvcResult result = mvc.perform(builder).andReturn();
		
		int actualResult = result.getResponse().getStatus();
		
		assertEquals(200,actualResult);
		
	}
	
	
	@Test
	public void getCitiesTest() throws Exception{
		
		Map<Integer,String> map = new HashMap<>();
		map.put(1,"Hindupur");
		map.put(2,"Anatapur");
		
		when(mockService.getCountries()).thenReturn(map);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/cities/1");
		
		MvcResult result = mvc.perform(builder).andReturn();
		
		int actualResult = result.getResponse().getStatus();
		
		assertEquals(200,actualResult);
		
	}
	
	@Test
	public void getregisterUserTestTrue() throws Exception{
		
		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("Kumar");
		user.setUserEmail("sureshkumar@gmail.com");
		user.setUserGender("Male");
		user.setUserId(1);
		user.setUserPassword("passwordnotsharable");
		user.setUserPhno(12132312);
	
		//we are passing json data convert user object into json format using ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		String writeValueAsString = mapper.writeValueAsString(user);
		
		when(mockService.registerUser(user)).thenReturn(true);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/saveUser")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(writeValueAsString);
		
		MvcResult result = mvc.perform(builder).andReturn();
		
		String actualResult = result.getResponse().getContentAsString();
		
		assertEquals(AppConstants.USER_SAVED,actualResult);
		
	}
	
	@Test
	public void getregisterUserTestFalse() throws Exception{
		
		User user = new User();
		user.setFirstName("suresh");
		user.setLastName("Kumar");
		user.setUserEmail("sureshkumar@gmail.com");
		user.setUserGender("Male");
		user.setUserId(1);
		user.setUserPassword("passwordnotsharable");
		user.setUserPhno(12132312);
	
		//we are passing json data convert user object into json format using ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		String writeValueAsString = mapper.writeValueAsString(user);
		
		when(mockService.registerUser(user)).thenReturn(false);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/saveUser")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(writeValueAsString);
		
		MvcResult result = mvc.perform(builder).andReturn();
		
		String actualResult = result.getResponse().getContentAsString();
		
		assertEquals(AppConstants.USER_NOT_SAVED,actualResult);
		
	}
}
