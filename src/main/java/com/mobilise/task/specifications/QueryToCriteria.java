package com.mobilise.task.specifications;

import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class QueryToCriteria<T> extends QueryToCriteriaImpl<T> {

    private String orderBy = "id";

    private String order = "desc";

    public QueryToCriteria(String query) {
        super(query);
    }

    public QueryToCriteria(String query, Criteria... criteria) {
        super(query, criteria);
    }

    public QueryToCriteria(String query, String[] ignoreParameters, Criteria... criteria) {
        super(query, ignoreParameters, criteria);
    }

    @NonNull
    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder criteriaBuilder) {
        List<Criteria> criteriaList = getCriteriaList();
        ArrayList<Predicate> predicates = new ArrayList<>();
        int predicateSize = criteriaList.size();
        for (int i = 0; i < predicateSize; i++) {
            boolean orOperation = false;
            Criteria criteria = criteriaList.get(i);
            ArrayList<Predicate> orPredicates = new ArrayList<>();
            while (criteria.getOperator() == Operator.OR) {
                orPredicates.add(CriteriaConverter.asPredicate(criteria, root, criteriaBuilder));
                i += 1; // provide next criteria to be joined
                if (i == predicateSize) break;
                criteria = criteriaList.get(i);
                orPredicates.add(CriteriaConverter.asPredicate(criteria, root, criteriaBuilder));
                orOperation = true;
            }

            if(orOperation) {
                predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[]{})));
                continue;
            }

            predicates.add(CriteriaConverter.asPredicate(criteria, root, criteriaBuilder));
        }
        CriteriaConverter.fullTextAsPredicate(this, predicates, criteriaBuilder, root);
        query.where(predicates.toArray(new Predicate[]{}));
        query.distinct(true);
        if ("asc".equalsIgnoreCase(order)){
            query.orderBy(criteriaBuilder.asc(root.get(getOrderBy())));
        } else {
            query.orderBy(criteriaBuilder.desc(root.get(getOrderBy())));
        }
        return query.getGroupRestriction();
    }

    /**
     * An overridable function provides a mechanism to specify which columns or attributes of an entity should
     * be considered for a full search in the criteria builder
     *
     * @return A unique set of columns/attribute names needed for full text searching
     */
    public Set<String> getFullTextAttributes() {
        return null;
    }

    /**
     * An overridable function to specify which columns/attribute of an entity can be used for ordering
     *
     * @return A list of attributes that can be considered for ordering
     */
    public String[] getSupportedOrderAttributes() {
        return new String[]{};
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        // the orderBy column provided by the client is not a supported one, don't use it
        String[] supportedAttr = getSupportedOrderAttributes();
        if (orderBy == null || orderBy.isEmpty() || supportedAttr == null || supportedAttr.length < 1 ||
                Arrays.stream(supportedAttr).noneMatch(s -> s.equals(orderBy))) return;
        this.orderBy = orderBy;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder(String order) {
        return this.order;
    }
}
