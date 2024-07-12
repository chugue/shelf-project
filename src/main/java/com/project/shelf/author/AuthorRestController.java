package com.project.shelf.author;


import com.project.shelf._core.util.ApiUtil;
import com.project.shelf.author.AuthorResponseRecord.SearchPageRespDTO;
import com.project.shelf.user.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorRestController {

    private final AuthorService authorService;


    @GetMapping("/search")
    public ResponseEntity<?> search() {
        List<SearchPageRespDTO> respDTO =  authorService.searchPage();
        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }
}
