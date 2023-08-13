/*
 * Copyright (c) 2023.
 * @author Ernest
 */

package com.mobilise.task.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class IQueryToCriteria<T> implements Specification<T> {
    private final List<Criteria> criteriaList;
    private final String fullTextValue;
    private int page;
    private int size;

    public IQueryToCriteria(String query) {
        criteriaList = CriteriaConverter.queryToCriteria(query);
        fullTextValue = CriteriaConverter.getFullTextValue(query);
    }

    public IQueryToCriteria(String query, Criteria... first) {
        criteriaList = new ArrayList<>();
        criteriaList.addAll(Arrays.asList(first));
        criteriaList.addAll(CriteriaConverter.queryToCriteria(query));
        fullTextValue = CriteriaConverter.getFullTextValue(query);
    }

    public IQueryToCriteria(String query, String[] ignoreProperties, Criteria... first) {
        criteriaList = new ArrayList<>();
        criteriaList.addAll(Arrays.asList(first));
        criteriaList.addAll(CriteriaConverter.queryToCriteria(query, ignoreProperties));
        fullTextValue = CriteriaConverter.getFullTextValue(query);
    }

    public void addCriteria(Criteria criteria) {
        if (this.criteriaList != null)
            this.criteriaList.add(criteria);
    }

    public List<Criteria> getCriteriaList() {
        return criteriaList;
    }

    public String getFullTextValue() {
        return fullTextValue;
    }

    // criteria pagination // access modifiers
    public void setSize(int size) {
        if (size > 50) {
            this.size = 50;
            return;
        }
        this.size = size;
    }

    public void setSizeIgnoreLimit(int size) {
        this.size = size;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
    // end pagination // access modifiers

    @Data
    @AllArgsConstructor
    public static class Criteria {
        private String key;
        private String condition;
        private Object value;
        private Operator operator;
        private JoinType joinType = JoinType.INNER;

        public Criteria(String key, String condition, Object value) {
            this.key = key;
            this.condition = condition;
            this.value = value;
            this.operator = Operator.AND;
        }

        public Criteria(String key, String condition, Object value, Operator operator) {
            this.key = key;
            this.condition = condition;
            this.value = value;
            this.operator = operator;
        }

        public Predicate nextPredicate(CriteriaBuilder criteriaBuilder) {
            if (operator == Operator.OR) return criteriaBuilder.or();
            return criteriaBuilder.and();
        }
    }

    public enum Operator {
        AND, OR
    }
}
