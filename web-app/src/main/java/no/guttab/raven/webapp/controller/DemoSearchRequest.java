package no.guttab.raven.webapp.controller;

import no.guttab.raven.annotations.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@SuppressWarnings({"FieldCanBeLocal"})
@SearchRequest
public class DemoSearchRequest {
    @IndexFieldName("cat")
    @FacetField
    private List<String> category;

    @FacetField
    private String inStock;

    @Sort
    private String sort;

    @Page(resultsPerPage = 5)
    private int page;

    @Query
    @NotNull
    private String keyword = "*:*";

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
