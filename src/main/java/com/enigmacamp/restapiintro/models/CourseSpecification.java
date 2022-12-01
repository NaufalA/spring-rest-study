package com.enigmacamp.restapiintro.models;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

public class CourseSpecification implements Specification<Course> {
    private String field;
    private String value;
    private String matchType;

    public CourseSpecification(String field, String value, String matchType) {
        this.field = field;
        this.value = value;
        this.matchType = matchType;
    }

    @Override
    public Specification<Course> and(Specification<Course> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Course> or(Specification<Course> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        switch (matchType) {
            case "equals":
                return cb.equal(cb.lower(root.get(field)), "%" + value + "%");
            case "contains":
            default:
                return cb.like(cb.lower(root.get(field)), "%" + value + "%");
        }

    }
}
