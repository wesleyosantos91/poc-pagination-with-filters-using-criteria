package io.github.wesleyosantos91.repository.custom.author;

import io.github.wesleyosantos91.entity.AuthorEntity;
import io.github.wesleyosantos91.repository.custom.author.filter.AuthorFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorRepositoryCustom {

    Page<AuthorEntity> search(AuthorFilter filter, Pageable pageable);
}
