package com.project.shelf.author.AuthorResponseRecord;

import lombok.Builder;

@Builder
public record SearchPageRespDTO(
        Integer AuthorId,
        String AuthorName
) {
}