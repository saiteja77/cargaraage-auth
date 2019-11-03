package com.bitbyte.cargaraag.auth.controllers;

import com.bitbyte.cargaraag.auth.entities.User;
import com.bitbyte.cargaraag.auth.exceptionhandlers.WrongCredentialsException;
import com.bitbyte.cargaraag.auth.models.AuthorizationResponse;
import com.bitbyte.cargaraag.auth.models.Credentials;
import com.bitbyte.cargaraag.auth.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping(value = "/login/authorize", produces = "application/json")
    public ResponseEntity<AuthorizationResponse> authorizeUser(@RequestBody Credentials userCreds){
        try{
            String token = authenticationService.authenticateUser(userCreds);
            LOG.info("Successfully Authenticated the the user {}", userCreds.getUserName());
            return new ResponseEntity<>(new AuthorizationResponse("Log in Successful!,", token), HttpStatus.OK);
        } catch (WrongCredentialsException wce){
            LOG.info("{}: {}", wce.getMessage(), userCreds.getUserName());
            return new ResponseEntity<>(new AuthorizationResponse(wce.getMessage(), null), HttpStatus.UNAUTHORIZED);
        } catch (NoSuchAlgorithmException nsae){
            LOG.info("{}: {}", nsae.getMessage(), userCreds.getUserName());
            return new ResponseEntity<>(new AuthorizationResponse("It's not you. It's. We're trying to fix some things up. Please try later", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sign_up")
    public ResponseEntity<AuthorizationResponse> signUpUser(@RequestBody User user){
        try{
            String token = authenticationService.signUpNewUser(user);
            LOG.info("Successfully created the user: {}", user.getUserName());
            return new ResponseEntity<>(new AuthorizationResponse("Sign up successful. Please use your user name: " + user.getUserName() + " to Log In for the next time", token), HttpStatus.CREATED);
        } catch (NoSuchAlgorithmException nsae){
            LOG.info("{}: {}", nsae.getMessage(), user.getUserName());
            return new ResponseEntity<>(new AuthorizationResponse("It's not you. It's. We're trying to fix some things up. Please try later", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
