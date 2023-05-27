package com.edu.networkexperimentation.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestRegisterUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String checkPassword;
}
