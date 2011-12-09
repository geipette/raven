package no.guttab.raven.webapp.controller;

import javax.validation.constraints.NotNull;

import no.guttab.raven.webapp.annotations.FilterQuery;
import no.guttab.raven.webapp.annotations.IndexFieldName;
import no.guttab.raven.webapp.annotations.Query;

public class SearchRequest {
   @IndexFieldName("cat")
   @FilterQuery(isFacetField = true)
   private String category;

   @Query
   @NotNull
   private String keyword = "*:*";
}
