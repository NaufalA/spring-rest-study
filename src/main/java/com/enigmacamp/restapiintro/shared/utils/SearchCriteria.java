package com.enigmacamp.restapiintro.shared.utils;

public class SearchCriteria {
    private String field;
    private Object value;
    private QueryOperator queryOperator;
    private SearchOperation searchOperation;

    public SearchCriteria() {}

    public SearchCriteria(String field, Object value, QueryOperator queryOperator, SearchOperation searchOperation) {
        this.field = field;
        this.value = value;
        this.queryOperator = queryOperator;
        this.searchOperation = searchOperation;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public QueryOperator getQueryOperator() {
        return queryOperator;
    }

    public SearchOperation getSearchOperation() {
        return searchOperation;
    }
}
