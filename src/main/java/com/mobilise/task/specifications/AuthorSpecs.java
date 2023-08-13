package com.mobilise.task.specifications;

import com.mobilise.task.models.Author;

public class AuthorSpecs extends QueryToCriteria<Author>{
    public AuthorSpecs(String query) {
        super(query);
    }
}
