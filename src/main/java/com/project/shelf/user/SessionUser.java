package com.project.shelf.user;

import lombok.Builder;
import lombok.Data;

@Data
public class SessionUser {
    private Integer id;
    private String nickName;
    private String email;

    @Builder
    public SessionUser(Integer id, String nickName, String email) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
    }

    public SessionUser(User user) {
        this.id = user.getId();
        this.nickName = getNickName();
        this.email = getEmail();
    }
}
