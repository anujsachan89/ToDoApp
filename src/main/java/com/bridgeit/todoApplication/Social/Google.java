package com.bridgeit.todoApplication.Social;

import java.io.IOException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.bridgeit.todoApplication.model.GmailProfile;
import com.bridgeit.todoApplication.model.GmailToken;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Google {
	
	public String scope = "profile email";
	public String Gmail_CLIENT_ID="82595694685-30kh318g6nmp9i5vimpcf708bol21b2o.apps.googleusercontent.com";
	public String Gmail_SECRET_KEY = "1DuabOSLDE30meh5hNWn9I0B";
	public String Gmail_RERDIRECT_URI = "/postgmailLogin";
	//https://accounts.google.com/o/oauth2/v2/auth
	//OLD:  https://accounts.google.com/o/oauth2/auth
	public String Gmail_URL = "https://accounts.google.com/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&state=%s&"
			+ "response_type=code&scope=%s&approval_prompt=force&access_type=offline";
	
	//OLD: https://accounts.google.com/o/oauth2/token
	//NEW: 
	//THIS is post url
	public String Gmail_ACCESS_TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
			
	
	//OLD : https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=%s";
	
	//Access token in URL
	//public String Gmail_GET_USER_URL = "https://www.googleapis.com/plus/v1/people/me?access_token=%s";
	
	// Access token in header
	public String Gmail_GET_USER_URL = "https://www.googleapis.com/plus/v1/people/me";
	
	public Google() {
		System.out.println(getClass().getSimpleName()+" created");
	}
	public 	String getGmailUrl(String appUrl, String pState) {
		System.out.println("Inside gmaIL FOR GET GMAIL URL");

		appUrl = appUrl + Gmail_RERDIRECT_URI;
		return String.format(Gmail_URL, new String[]{ Gmail_CLIENT_ID, appUrl, pState, scope });
	}


	public GmailProfile authUser(String authCode, String appUrl) throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println("Inside gmaIL FOR GET GMAIL profile");
		String accessToken = getAccessToken(authCode, appUrl);
		
		return getUserProfile(accessToken);
		
	}


	private GmailProfile getUserProfile(String accessToken) throws JsonParseException, JsonMappingException, IOException {

		System.out.println("Inside gmaIL FOR GET GMAIL getuserprofile");
		//String accProfileUrl = String.format(Gmail_GET_USER_URL, new String[]{ accessToken });
		String accProfileUrl = Gmail_GET_USER_URL;
		  
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target( accProfileUrl );
		 
		String headerAuth = "Bearer "+accessToken;
		Response response =  target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON).get();
		System.out.println( response );
		GmailProfile profile = response.readEntity(GmailProfile.class);
		
		
		//String profile = response.readEntity(String.class);
		System.out.println( profile);
		
		client.close();
		return profile;
	}


	private String getAccessToken(String authCode, String appUrl) {
		
		System.out.println("Inside gmaIL FOR GET GMAIL getAccess token");
		appUrl = appUrl + Gmail_RERDIRECT_URI;
		String accTokenUrl = Gmail_ACCESS_TOKEN_URL; // String.format(Gmail_ACCESS_TOKEN_URL, new String[]{ Gmail_CLIENT_ID, Gmail_SECRET_KEY, appUrl, authCode });

		System.out.println( accTokenUrl );
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target( accTokenUrl );
		Form f = new Form();
		f.param("client_id", Gmail_CLIENT_ID);
		f.param("client_secret", Gmail_SECRET_KEY);
		f.param("redirect_uri", appUrl);
		f.param("code", authCode);
		f.param("grant_type", "authorization_code");
		//?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s&grant_type=authorization_code";
		
		Response response =  target.request().accept(MediaType.APPLICATION_JSON).post( Entity.form(f) ); //method(HttpMethod.POST);
		GmailToken gmailToken = response.readEntity(GmailToken.class);
		//String gmailToken = response.readEntity(String.class);
		//System.out.println( gmailToken );
		client.close();
		return gmailToken.getAccess_token();
	}

}