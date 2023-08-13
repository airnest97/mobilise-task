package com.mobilise.task.specifications;


import com.mobilise.task.models.Book;

public class BookSpecs extends QueryToCriteria<Book> {
    public BookSpecs(String query) {
        super(query);
    }
}
