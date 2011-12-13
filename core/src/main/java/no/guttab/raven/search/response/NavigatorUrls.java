package no.guttab.raven.search.response;

import java.util.HashMap;
import java.util.Map;

import no.guttab.raven.search.config.SearchRequestConfig;

public class NavigatorUrls {

   private final Map<String, UrlFragment> urlFragmentMap = new HashMap<String, UrlFragment>();
   private SearchRequestConfig searchRequestConfig;

   public NavigatorUrls(SearchRequestConfig searchRequestConfig) {
      this.searchRequestConfig = searchRequestConfig;
   }

   public void addUrlFragment(String indexFieldName, String fqCriteria) {
      addUrlFragment(indexFieldName,
            new UrlFragment(searchRequestConfig.requestFieldNameFor(indexFieldName), fqCriteria));
   }

   public void addUrlFragment(String indexFieldName, UrlFragment urlFragment) {
      urlFragmentMap.put(indexFieldName, urlFragment);
   }

   public String buildUrlFor(String indexFieldName) {
      return buildUrlFor(indexFieldName, null);
   }

   public String buildUrlFor(String indexFieldName, String value) {
      final StringBuilder urlBuilder = new StringBuilder();

      if (value != null) {
         appendActiveFragment(urlBuilder, indexFieldName, value);
      }
      appendOtherFragments(indexFieldName, urlBuilder);
      prependUrlQueryString(urlBuilder);

      return urlBuilder.toString();
   }

   private void prependUrlQueryString(StringBuilder urlBuilder) {
      urlBuilder.insert(0, '?');
   }

   private void appendOtherFragments(String indexFieldName, StringBuilder urlBuilder) {
      for (Map.Entry<String, UrlFragment> entry : urlFragmentMap.entrySet()) {
         if (!entryIsTheActiveFragment(indexFieldName, entry)) {
            appendAmpersandIfNeeded(urlBuilder);
            appendOtherFragment(urlBuilder, entry);
         }
      }
   }

   private boolean entryIsTheActiveFragment(String indexFieldName, Map.Entry<String, UrlFragment> entry) {
      return entry.getKey().equals(indexFieldName);
   }

   private void appendActiveFragment(StringBuilder urlBuilder, String indexFieldName, String value) {
      UrlFragment activeFragment = new UrlFragment(searchRequestConfig.requestFieldNameFor(indexFieldName), value);
      urlBuilder.append(activeFragment);
   }

   private void appendOtherFragment(StringBuilder urlBuilder, Map.Entry<String, UrlFragment> entry) {
      urlBuilder.append(entry.getValue());
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
