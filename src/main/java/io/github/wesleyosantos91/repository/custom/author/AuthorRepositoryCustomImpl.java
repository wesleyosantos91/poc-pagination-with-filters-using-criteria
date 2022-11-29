package io.github.wesleyosantos91.repository.custom.author;

import io.github.wesleyosantos91.entity.AuthorEntity;
import io.github.wesleyosantos91.entity.AuthorEntity_;
import io.github.wesleyosantos91.repository.custom.author.filter.AuthorFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<AuthorEntity> search(AuthorFilter filter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<AuthorEntity> criteria = builder.createQuery(AuthorEntity.class);
        Root<AuthorEntity> root = criteria.from(AuthorEntity.class);

        Predicate[] predicates = createRestrictions(filter, builder, root);

        existRestrictions(criteria, predicates);

        TypedQuery<AuthorEntity> query = manager.createQuery(criteria);
        createPaginationRestrictions(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(filter));
    }

    private Predicate[] createRestrictions(AuthorFilter filter, CriteriaBuilder builder, Root<AuthorEntity> root) {

        List<Predicate> predicates = new ArrayList<>();

        if(!ObjectUtils.isEmpty(filter.getName())) {
            predicates.add(builder.like(
                    builder.lower(root.get(AuthorEntity_.NAME)), "%" + filter.getName().toLowerCase() + "%"));
        }

        if (filter.getId() != null) {
            predicates.add(
                    builder.equal(root.get(AuthorEntity_.ID), filter.getId()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void createPaginationRestrictions(TypedQuery<AuthorEntity> query, Pageable pageable) {
        int currentPage = pageable.getPageNumber();
        int totalRecordsPerPage = pageable.getPageSize();
        int firstPageRegistration = currentPage * totalRecordsPerPage;

        query.setFirstResult(firstPageRegistration);
        query.setMaxResults(totalRecordsPerPage);
    }

    private Long total(AuthorFilter filter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<AuthorEntity> root = criteria.from(AuthorEntity.class);

        Predicate[] predicates = createRestrictions(filter, builder, root);

        existRestrictions(criteria, predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }

    private static void existRestrictions(CriteriaQuery<?> criteria, Predicate[] predicates) {
        if (predicates.length > ZERO.intValue()) {
            criteria.where(predicates);
        }
    }
}
