package com.project.shelf.book.BookResponseRecord;

import lombok.Builder;

@Builder
public record BookCategorySearchDTO(
        String AuthorName,
        String BookTitle,
        String BookPath,
        Integer BookCount
) {

}
