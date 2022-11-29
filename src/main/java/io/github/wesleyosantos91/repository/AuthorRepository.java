package io.github.wesleyosantos91.repository;

import io.github.wesleyosantos91.entity.AuthorEntity;
import io.github.wesleyosantos91.repository.custom.author.AuthorRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long>, AuthorRepositoryCustom {
}
