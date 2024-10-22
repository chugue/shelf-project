package com.project.shelf.admin.AdminResponseRecord;

import lombok.Builder;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BookListRespDTO(
    Integer count,
    List<BookDTO> bookDTOList
){
    @Builder
    public record BookDTO(
        Integer no,
        Integer bookId,
        String title,
        String author,
        String publisher,
        String registrationDate,
        String path
    ){}
}
