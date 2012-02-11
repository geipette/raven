package no.guttab.raven.search.query;

import java.lang.reflect.Field;

import no.guttab.raven.annotations.AnnotatedFieldCallback;
import no.guttab.raven.annotations.AnnotationUtils;
import no.guttab.raven.annotations.InterestingTerms;
import no.guttab.raven.annotations.MoreLikeThis;
import no.guttab.raven.annotations.MoreLikeThisStream;
import org.apache.solr.client.solrj.SolrQuery;

import static no.guttab.raven.reflection.FieldUtils.getFieldValue;

public class MoreLikeThisQueryProcessor implements QueryProcessor {
   @Override
   public void buildQuery(Object queryInput, SolrQuery solrQuery) {
      MoreLikeThis moreLikeThis = getMoreLikeThisAnnotationFor(queryInput);
      if (moreLikeThis != null) {
         solrQuery.setQueryType("mlt");
         solrQuery.set("mlt.fl", moreLikeThis.fields());
         setInterestingTerms(moreLikeThis, solrQuery);
         setContentStream(queryInput, solrQuery);
      }
   }

   private void setContentStream(final Object queryInput, final SolrQuery solrQuery) {
      AnnotationUtils.doForFirstAnnotatedFieldOn(
            queryInput, MoreLikeThisStream.class,
            new MoreLikeThisStreamAnnotatedFieldCallback(queryInput, solrQuery));
   }

   private void setInterestingTerms(MoreLikeThis moreLikeThis, SolrQuery solrQuery) {
      InterestingTerms interestingTerms = moreLikeThis.interestingTerms();
      if (interestingTerms != InterestingTerms.DEFAULT) {
         solrQuery.set("mlt.interestingTerms", interestingTerms.getInterestingTermsParameterValue());
      }
   }


   private MoreLikeThis getMoreLikeThisAnnotationFor(Object queryInput) {
      return queryInput.getClass().getAnnotation(MoreLikeThis.class);
   }

   private static class MoreLikeThisStreamAnnotatedFieldCallback implements AnnotatedFieldCallback<MoreLikeThisStream> {
      private Object queryInput;
      private SolrQuery solrQuery;

      public MoreLikeThisStreamAnnotatedFieldCallback(Object queryInput, SolrQuery solrQuery) {
         this.queryInput = queryInput;
         this.solrQuery = solrQuery;
      }

      @Override
      public void doFor(Field field, MoreLikeThisStream annotation) {
         solrQuery.set(annotation.type().getStreamParameterKey(), (String) getFieldValue(field, queryInput));
      }
   }
}
