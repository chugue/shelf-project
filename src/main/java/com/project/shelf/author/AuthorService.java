package com.project.shelf.author;


import com.project.shelf.author.AuthorResponseRecord.SearchPageRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    //검색페이지
    public List<SearchPageRespDTO> searchPage() {
        List<SearchPageRespDTO> authors = authorRepository.findAll().stream()
                .map(author -> SearchPageRespDTO.builder()
                        .AuthorName(author.getName()).AuthorId(author.getId()).build())
                .toList();
        return authors;
    }
}
