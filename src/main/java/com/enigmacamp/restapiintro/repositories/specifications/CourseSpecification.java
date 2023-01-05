package com.enigmacamp.restapiintro.repositories.specifications;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.CourseInfo;
import com.enigmacamp.restapiintro.models.CourseType;
import com.enigmacamp.restapiintro.shared.utils.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class CourseSpecification implements Specification<Course> {
    SearchCriteria searchCriteria;

    public CourseSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<String> path;
        if (searchCriteria.getField().equals("typeName")) {
            path = typeJoin(root).get(searchCriteria.getField());
        } else if (searchCriteria.getField().equals("duration") || searchCriteria.getField().equals("level")) {
            path = infoJoin(root).get(searchCriteria.getField());
        } else {
            path = root.get(searchCriteria.getField());
        }
        switch (searchCriteria.getQueryOperator()) {
            default:
            case EQUALS:
                if (searchCriteria.getValue() instanceof String) {
                    return cb.equal(path, "%" + searchCriteria.getValue() + "%");
                }
                return cb.equal(path, searchCriteria.getValue());
            case DOES_NOT_EQUAL:
                if (searchCriteria.getValue() instanceof String) {
                    return cb.notEqual(path, "%" + searchCriteria.getValue() + "%");
                }
                return cb.notEqual(path, searchCriteria.getValue());
            case CONTAINS:
                if (searchCriteria.getValue() instanceof String) {
                    return cb.equal(cb.lower(path), "%" + ((String) searchCriteria.getValue()).toLowerCase() + "%");
                }
                return cb.like(path, "%" + searchCriteria.getValue() + "%");
            case DOES_NOT_CONTAIN:
                if (searchCriteria.getValue() instanceof String) {
                    return cb.equal(cb.lower(path), "%" + ((String) searchCriteria.getValue()).toLowerCase() + "%");
                }
                return cb.notLike(path, "%" + searchCriteria.getValue() + "%");
            case LESS_THAN:
                return cb.lessThan(path, searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL:
                return cb.lessThanOrEqualTo(path, searchCriteria.getValue().toString());
            case GREATER_THAN:
                return cb.greaterThan(path, searchCriteria.getValue().toString());
            case GREATER_THAN_EQUAL:
                return cb.greaterThanOrEqualTo(path, searchCriteria.getValue().toString());
        }
    }

    private Join<Course, CourseInfo> infoJoin(Root<Course> root) {
        return root.join("courseInfo");
    }

    private Join<Course, CourseType> typeJoin(Root<Course> root) {
        return root.join("courseType");
    }
}
