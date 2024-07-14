package com.project.shelf.book.BookResponseRecord;

import lombok.Builder;

@Builder
public record BookCategorySearchDTO(
        Integer categoryId,
        String authorName,
        String bookTitle,
        String bookPath,
        Integer bookCount
) {

}
