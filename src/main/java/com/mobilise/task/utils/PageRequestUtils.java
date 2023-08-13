package com.mobilise.task.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequestUtils {

    public static Pageable normalize(int page, int size){
        return normalize(page, size, Sort.unsorted());
    }

    public static Pageable normalize(int page, int size, Sort sort){
        if(size > 50) size = 50;
        return PageRequest.of(page, size, sort);
    }
}
