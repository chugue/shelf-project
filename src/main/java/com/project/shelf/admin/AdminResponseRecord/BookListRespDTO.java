package com.project.shelf.admin.AdminResponseRecord;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BookListRespDTO(
    Integer count,
    List<BookDTO> bookDTOList
){
    @Builder
    public record BookDTO(
        Integer bookId,
        String title,
        String author,
        String publisher,
        String createdAt
    ){}
}
