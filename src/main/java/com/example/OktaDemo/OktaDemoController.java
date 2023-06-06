package com.example.OktaDemo;


import com.example.OktaDemo.models.*;
import com.nimbusds.jose.shaded.gson.Gson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/okta")
public class OktaDemoController {


    @GetMapping("/secured")
    public ResponseEntity<String> getStringByPassingToken() {
        return new ResponseEntity<>("Congratulations!! Your Token Is Valid !", HttpStatus.OK);
    }

    @GetMapping("/demo")
    public String welcome2User() {
        return "Hi Welcome to the SSO application Using JWT Token";
    }


    @PostMapping("/create")
    public ResponseEntity<String> CreateUser(@RequestBody NewUser user) {
        var oktaUser = new OktaUser();
        oktaUser.profile = new OktaProfile();
        oktaUser.profile.firstName = user.FirstName;
        oktaUser.profile.lastName = user.LastName;
        oktaUser.profile.email = user.Email;
        oktaUser.profile.login = user.Email;
        oktaUser.profile.mobilePhone = user.Mobile;
        oktaUser.credentials = new OktaCredentials();
        oktaUser.credentials.password = new Item();
        oktaUser.credentials.password.value = user.Password;

        // Save to Okta
        ResponseEntity<String> oktaResponse = callApi(oktaUser);

        return oktaResponse;
    }

    private ResponseEntity<String> callApi(OktaUser oktaUser) {
        String jsonBody = new Gson().toJson(oktaUser);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dev-75134855.okta.com/api/v1/users"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "SSWS 00XVhPr54Qo6ZSSkDOzXSo9_weovc5ig-NPJoj4h-h")
                .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new ResponseEntity<>("User Created", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("User not Created", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<String> ChangePassword(@RequestBody OktaChangePassword oktaChangePassword) {
        String jsonBody = new Gson().toJson(oktaChangePassword);

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "https://dev-75134855.okta.com/api/v1/users/00u9014aalQl7k1ON5d7/credentials/change_password"))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Authorization", "SSWS 00XVhPr54Qo6ZSSkDOzXSo9_weovc5ig-NPJoj4h-h")
                        .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new ResponseEntity<String>("Password changed successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<String>("Failed to change password", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*@PostMapping("/recovery")
    public ResponseEntity<String> changeRecoveryPassword(@RequestBody OktaRecoveryModel oktaRecoveryModel) {
        String jsonBody = new Gson().toJson(oktaRecoveryModel);

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        "https://dev-75134855.okta.com/api/v1/users/00u9014aalQl7k1ON5d7/credentials/change_password"))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Authorization", "SSWS 00XVhPr54Qo6ZSSkDOzXSo9_weovc5ig-NPJoj4h-h")
                        .method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new ResponseEntity<String>("Password Recovery in Progress", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<String>("Failed to recover password", HttpStatus.INTERNAL_SERVER_ERROR);
    }*/


    @PostMapping("/recovery")
    public ResponseEntity<String> Recovery(@RequestBody OktaRecoveryModel recovery){
        return calledApi("https://dev-75134855.okta.com/api/v1/users/00u9014aalQl7k1ON5d7/credentials/change_recovery_question","POST",recovery);
    }
    private ResponseEntity<String> calledApi(String URL,String methodType,Object body) {
        String jsonBody = new Gson().toJson(body);



        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "SSWS 00XVhPr54Qo6ZSSkDOzXSo9_weovc5ig-NPJoj4h-h")
                .method(methodType, HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();



        HttpResponse<String> response = null;
        try
        {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new ResponseEntity<>("Recovery Question Done",HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();



        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        return new ResponseEntity<>("User not Created",HttpStatus.NOT_FOUND);
    }
}