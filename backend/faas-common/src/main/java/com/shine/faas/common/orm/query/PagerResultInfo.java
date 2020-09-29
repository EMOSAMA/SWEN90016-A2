package com.shine.faas.common.orm.query;

import java.util.ArrayList;
import java.util.List;

public class PagerResultInfo<T> {
    private int pageIndex;
    private int pageSize;
    private long totalNumbers;
    private int totalPages;
    private List<T> results;

    public PagerResultInfo() {
        this.results = new ArrayList<>();
    }

    public PagerResultInfo(List<T> data) {
        this.results = data;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalNumbers() {
        return this.totalNumbers;
    }

    public void setTotalNumbers(long totalNumbers) {
        this.totalNumbers = totalNumbers;
        this.totalPages = getTotalPages();
    }

    public List<T> getResults() {
        return this.results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getTotalPages() {
        if (this.totalNumbers < 1 || this.pageSize < 1)
            return 0;

        return (int) (this.totalNumbers % this.pageSize == 0 ?
                (this.totalNumbers / this.pageSize) :
                (this.totalNumbers / this.pageSize + 1));
    }
}
