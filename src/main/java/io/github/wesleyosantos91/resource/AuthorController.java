package io.github.wesleyosantos91.resource;

import io.github.wesleyosantos91.entity.AuthorEntity;
import io.github.wesleyosantos91.repository.AuthorRepository;
import io.github.wesleyosantos91.repository.custom.author.filter.AuthorFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository repository;

    @GetMapping
    public Page<AuthorEntity> search(AuthorFilter filter, Pageable page) {
        return repository.search(filter, page);
    }
 }
