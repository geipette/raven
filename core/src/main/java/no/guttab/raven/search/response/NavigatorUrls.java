package no.guttab.raven.search.response;

import no.guttab.raven.search.config.SearchRequestConfig;

public class NavigatorUrls {
   private final UrlFragments urlFragments;

   public NavigatorUrls(SearchRequestConfig searchRequestConfig) {
      urlFragments = new UrlFragments(searchRequestConfig);
   }

   public void addUrlFragment(String indexFieldName, String fqCriteria) {
      urlFragments.addFragment(indexFieldName, fqCriteria);
   }

   public String resetUrlFor(String indexFieldName) {
      final UrlFragments significantFragments = urlFragments.withoutIndexField(indexFieldName);
      return buildUrlFor(significantFragments);
   }

   public String resetUrlFor(String indexFieldName, String fqCriteria) {
      final UrlFragments significantFragments = urlFragments.withoutFragment(indexFieldName, fqCriteria);
      return buildUrlFor(significantFragments);
   }

   public String buildUrlFor(String indexFieldName, String fqCriteria) {
      final UrlFragments significantFragments = urlFragments.withAddedFragment(indexFieldName, fqCriteria);
      return buildUrlFor(significantFragments);
   }

   private String buildUrlFor(UrlFragments significantFragments) {
      final StringBuilder urlBuilder = new StringBuilder();
      for (UrlFragments.Entry entry : significantFragments) {
         appendAmpersandIfNeeded(urlBuilder);
         appendFragment(urlBuilder, entry);
      }
      prependUrlQueryString(urlBuilder);
      return urlBuilder.toString();
   }

   private void prependUrlQueryString(StringBuilder urlBuilder) {
      urlBuilder.insert(0, '?');
   }

   private void appendFragment(StringBuilder urlBuilder, UrlFragments.Entry entry) {
      urlBuilder.append(entry.getFragment());
   }

   private void appendAmpersandIfNeeded(StringBuilder urlBuilder) {
      if (isNotFirstIncludedFragment(urlBuilder)) {
         urlBuilder.append('&');
      }
   }

   private boolean isNotFirstIncludedFragment(StringBuilder urlBuilder) {
      return urlBuilder.length() > 0;
   }

}
