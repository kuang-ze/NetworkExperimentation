package com.edu.networkexperimentation.model.response;

import com.edu.networkexperimentation.model.domain.User;
import lombok.Data;

@Data
public class ResponseUser {
    private Long id;
    private String username;
    private int userIdentity;
    private Long gradeID;

    public ResponseUser(User user) {
        id = user.getId();
        username = user.getUsername();
        userIdentity = user.getUserIdentity();
        gradeID = user.getGradeID();
    }
}
