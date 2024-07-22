package com.project.shelf.book.BookResponseRecord;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record BrandNewRespDTO (
    String brandNewWeekly,
    List<brandNewList> brandNewList

){
    @Builder
    public record brandNewList(
            Integer bookId,
            String title,
            String author,
            String path,
            LocalDate registrationDate
    ){}
}
