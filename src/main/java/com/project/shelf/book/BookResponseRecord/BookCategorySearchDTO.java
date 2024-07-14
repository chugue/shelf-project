package com.project.shelf.book.BookResponseRecord;

import com.project.shelf.author.Author;
import lombok.Builder;

import java.util.List;

@Builder
public record BookCategorySearchDTO(
        Integer bookCount,
        List<Book> book


) {
    @Builder
    public record Book(
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
}
