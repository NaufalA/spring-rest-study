package com.enigmacamp.restapiintro.models;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CourseTypeSpecification implements Specification<CourseType> {
    @Override
    public Predicate toPredicate(Root<CourseType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return null;
    }
}
