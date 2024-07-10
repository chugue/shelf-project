package com.project.shelf.user;

import lombok.Builder;
import lombok.Data;

@Data
public class SessionUser {
    private Integer id;

    @Builder
    public SessionUser(Integer id) {
        this.id = id;
    }

    public SessionUser(User user) {
        this.id = user.getId();
    }
}
