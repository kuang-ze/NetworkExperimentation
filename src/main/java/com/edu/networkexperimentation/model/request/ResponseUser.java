package com.edu.networkexperimentation.model.request;

import com.edu.networkexperimentation.model.domain.User;
import lombok.Data;

@Data
public class ResponseUser {
    private Long id;
    private String username;
    private int userIdentity;

    public ResponseUser(User user) {
        id = user.getId();
        username = user.getUsername();
        userIdentity = user.getUserIdentity();
    }
}
