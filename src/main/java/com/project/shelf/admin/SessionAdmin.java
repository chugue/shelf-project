package com.project.shelf.admin;

import lombok.Builder;
import lombok.Data;

@Data
public class SessionAdmin {
    private Integer id;
    private String email;

    @Builder
    public SessionAdmin(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public SessionAdmin(Admin admin) {
        this.id = admin.getId();
        this.email = admin.getEmail();
    }
}
