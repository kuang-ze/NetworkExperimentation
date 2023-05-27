package com.edu.networkexperimentation.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestLoginUser implements Serializable {
    private static final long serialVersionUID = 2L;

    private Long id;
    private String password;
}
