package com.example.nagoyameshi.service;

 import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;
 
 @Service
public class StripeService {
	 @Value("${stripe.api-key}")
     private String stripeApiKey;
   
     public String createStripeSession(HttpServletRequest httpServletRequest) {
    	 Stripe.apiKey = stripeApiKey;
         String requestUrl = new String(httpServletRequest.getRequestURL());
         String priceId = "{price_1OiVjnIhYmnFrNDSUfqsSy0g}";
 
         SessionCreateParams params =
             SessionCreateParams.builder()
                 
                 .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                 .setSuccessUrl(requestUrl.replaceAll("/subscription/register", "") + "/")
                 .addLineItem(
           		      SessionCreateParams.LineItem.builder()
           		        .setPrice("price_1Oqwg7LVD5FyumV87uuv7x4s")
           		        .setQuantity(1L)
           		        .build()
           		    )
           		    
           		 
           		    .build();
         try {
             Session session = Session.create(params);
             return session.getId();
         } catch (StripeException e) {
             e.printStackTrace();
             return "";
         }
     } 
    
}