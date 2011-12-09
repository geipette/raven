package no.guttab.raven.webapp.controller;

import no.guttab.raven.webapp.annotations.FilterQuery;
import no.guttab.raven.webapp.annotations.IndexFieldName;

public class SearchRequest {
   @IndexFieldName("cat")
   @FilterQuery(isFacetField = true)
   private String category;
}
