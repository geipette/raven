package no.guttab.raven.webapp.controller;

import no.guttab.raven.webapp.annotations.FilterQuery;
import no.guttab.raven.webapp.annotations.IndexFieldName;
import no.guttab.raven.webapp.annotations.Query;

public class SearchRequest {
   @IndexFieldName("cat")
   @FilterQuery(isFacetField = true)
   private String category;

   @Query
   private String keyword = "*:*";
}
