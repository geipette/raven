package no.guttab.raven.webapp.controller;

import java.util.List;
import javax.validation.constraints.NotNull;

import no.guttab.raven.annotations.FacetField;
import no.guttab.raven.annotations.FacetFieldType;
import no.guttab.raven.annotations.FilterQuery;
import no.guttab.raven.annotations.IndexFieldName;
import no.guttab.raven.annotations.Query;


public class SearchRequest {
   @IndexFieldName("cat")
   @FacetField(type = FacetFieldType.MULTI_SELECT)
   @FilterQuery
   private List<String> category;

   @FacetField
   @FilterQuery
   private String inStock;

   @Query
   @NotNull
   private String keyword = "*:*";

   public void setCategory(List<String> category) {
      this.category = category;
   }

   public void setKeyword(String keyword) {
      this.keyword = keyword;
   }
}
