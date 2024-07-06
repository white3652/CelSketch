package com.celsketch.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPageable implements Pageable {

    private int page;
    private int size;
    private Sort sort;

    public CustomPageable(int page, int size, Sort sort) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        return (long) page * size;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new CustomPageable(page + 1, size, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? new CustomPageable(page - 1, size, sort) : this;
    }

    @Override
    public Pageable first() {
        return new CustomPageable(0, size, sort);
    }

    @Override
    public boolean hasPrevious() {
        return page > 0;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new CustomPageable(pageNumber, this.size, this.sort);
    }
}