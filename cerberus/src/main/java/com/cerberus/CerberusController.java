package com.cerberus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import Decoder.BASE64Encoder;

@RestController
public class CerberusController {
	
	@RequestMapping(value="/showData", method=RequestMethod.GET)
	public String showData(){
		return "welcome to rest serbvice";
	}
	
	
	@RequestMapping(value="/getChildren", method=RequestMethod.GET)
	public String getChildren(){
//		String output = null;
//		 try {
//	            String fldUuid = "1436b532-ed7d-4570-8dab-11755b5be496";
//	            URL url = new URL("http://localhost:8080/OpenKM/services/rest/folder/getChildren?fldId=" + fldUuid);
//	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	            conn.setRequestMethod("GET");
//	            conn.setRequestProperty("Accept", "application/json");
//	 
//	            Authenticator.setDefault(new Authenticator() {
//	                protected PasswordAuthentication getPasswordAuthentication() {
//	                    return new PasswordAuthentication("okmAdmin", "admin".toCharArray());
//	                }
//	            });
//	 
//	            if (conn.getResponseCode() == 200) {
//	                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//	                System.out.println("Output from Server .... \n");
//	               
//	 
//	                while ((output = br.readLine()) != null) {
//	                    System.out.println(output);
//	                }
//	            } else {
//	                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
//	            }
//	 
//	            conn.disconnect();
//	        } catch (MalformedURLException e) {
//	            e.printStackTrace();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//		return output;
		
		/***********************/
		
		String url = "http://localhost:8080/OpenKM/services/rest/folder/getChildren?fldId=1436b532-ed7d-4570-8dab-11755b5be496";
        String name = "okmAdmin";
        String password = "admin";
        String authString = name + ":" + password;
        String authStringEnc = new BASE64Encoder().encode(authString.getBytes());
        System.out.println("Base64 encoded auth string: " + authStringEnc);
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("application/json")
                                         .header("Authorization", "Basic " + authStringEnc)
                                         .get(ClientResponse.class);
        if(resp.getStatus() != 200){
            System.err.println("Unable to connect to the server");
        }
        String output = resp.getEntity(String.class);
        System.out.println("response: "+output);
		return output;
		
		
	    }
	
	@RequestMapping(value="/deleteDocument", method=RequestMethod.DELETE)
	public String deleteDocument(/*@RequestParam MultipartFile file*/){
		
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost("http://localhost:8080/OpenKM/services/rest/document/createSimple");
//
//		FileBody uploadFilePart = new FileBody(file);
//		MultipartEntity reqEntity = new MultipartEntity();
//		reqEntity.addPart("upload-file", uploadFilePart);
//		httpPost.setEntity(reqEntity);
//
//		HttpResponse response = httpclient.execute(httpPost);
		
		
		final String uri = "http://localhost:8080/OpenKM/services/rest/document/delete";
	     
	    RestTemplate restTemplate = new RestTemplate();
	     
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    //headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
	    HttpEntity<String> entity = new HttpEntity<String>("0ce8d4c9-b95f-471a-b970-3f320b3c8312", headers);
	    
	    restTemplate.getMessageConverters().add(
				new ByteArrayHttpMessageConverter());
		
	     
	    ResponseEntity<byte[]> response1 = restTemplate.exchange(uri, HttpMethod.DELETE, entity, byte[].class);
		return "success";
	     
		
		
	}

}
