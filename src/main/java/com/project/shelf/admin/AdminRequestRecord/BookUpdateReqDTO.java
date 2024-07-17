package com.project.shelf.admin.AdminRequestRecord;

public record BookUpdateReqDTO(
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
