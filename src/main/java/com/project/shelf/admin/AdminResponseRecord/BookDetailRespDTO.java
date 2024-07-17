package com.project.shelf.admin.AdminResponseRecord;

import lombok.Builder;

@Builder
public record BookDetailRespDTO(
        String bookPath,
        String bookTitle,
        String author,
        String publisher,
        String category,
        String bookIntro,
        String contentIntro,
        String authorIntro,
        String pageCount
) {
}
