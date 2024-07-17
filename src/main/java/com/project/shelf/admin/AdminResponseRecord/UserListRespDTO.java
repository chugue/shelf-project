package com.project.shelf.admin.AdminResponseRecord;

import lombok.Builder;
import java.util.List;

@Builder
public record UserListRespDTO(
    Integer userCount,
    Integer subCount,
    List<UserList> userList
){
    @Builder
    public record UserList(
        Integer userId,
        String email,
        String createdAt,
        Boolean isSub,
        Integer subMonths
    ){}
}
