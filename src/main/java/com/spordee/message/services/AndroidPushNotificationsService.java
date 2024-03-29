package com.spordee.message.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
 
@Service
public class AndroidPushNotificationsService {
	private final Logger log = LoggerFactory.getLogger(AndroidPushNotificationsService.class);
 
  @Value("${spordee.app.firebaseServerKey}")
  private String FIREBASE_SERVER_KEY;
  private String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
  
  @Async
  public CompletableFuture<String> send(HttpEntity<String> entity) {
 
    RestTemplate restTemplate = new RestTemplate();
 
    /**
    https://fcm.googleapis.com/fcm/send
    Content-Type:application/json
    Authorization:key=FIREBASE_SERVER_KEY*/
 
    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
    restTemplate.setInterceptors(interceptors);
 
    String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
 
    return CompletableFuture.completedFuture(firebaseResponse);
  }
}