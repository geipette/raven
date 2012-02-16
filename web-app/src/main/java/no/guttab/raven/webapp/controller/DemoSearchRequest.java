package no.guttab.raven.webapp.controller;

import java.util.List;
import javax.validation.constraints.NotNull;

import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.IndexFieldName;
import no.guttab.raven.annotations.Query;
import no.guttab.raven.annotations.SearchRequest;
import no.guttab.raven.annotations.Sort;


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
}
