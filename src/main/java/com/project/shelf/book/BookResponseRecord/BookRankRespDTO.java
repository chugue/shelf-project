package com.project.shelf.book.BookResponseRecord;

import lombok.Builder;

@Builder
public record BookRankRespDTO(

        Integer bookId,
        String bookTitle,
        String bookPath,
        Author author
) {
    @Builder
    public record Author(
            Integer authorId,
            String authorName
    ) {
    }
}
