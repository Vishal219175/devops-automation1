package com.example.OktaDemo.models;

import java.io.Serializable;

public class OktaChangePassword implements Serializable {
    public Item oldPassword ;
    public Item newPassword;
    public boolean revokeSessions;
}
