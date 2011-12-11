package no.guttab.raven.webapp.search.response;

import java.util.HashMap;
import java.util.Map;

public class NavigatorUrls {

   private final Map<String, UrlFragment> urlFragmentMap = new HashMap<String, UrlFragment>();

   public NavigatorUrls() {
   }

   public void addUrlFragment(String indexFieldName, String urlFragmentKey, String urlFragmentValue) {
      addUrlFragment(indexFieldName, new UrlFragment(urlFragmentKey, urlFragmentValue));
   }

   public void addUrlFragment(String indexFieldName, UrlFragment urlFragment) {
      urlFragmentMap.put(indexFieldName, urlFragment);
   }

   public String buildUrlFor(String indexFieldName, String value) {
      final StringBuilder urlBuilder = new StringBuilder();
      for (Map.Entry<String, UrlFragment> entry : urlFragmentMap.entrySet()) {
         appendAmpersandIfNeeded(urlBuilder);
         if (entryIsTheActiveFragment(indexFieldName, entry)) {
            appendActiveFragment(urlBuilder, entry, value);
         } else {
            appendOtherFragment(urlBuilder, entry);
         }
      }
      urlBuilder.insert(0, '?');
      return urlBuilder.toString();
   }

   private boolean entryIsTheActiveFragment(String indexFieldName, Map.Entry<String, UrlFragment> entry) {
      return entry.getKey().equals(indexFieldName);
   }

   private void appendActiveFragment(StringBuilder urlBuilder, Map.Entry<String, UrlFragment> entry, String value) {
      urlBuilder.append(new UrlFragment(entry.getValue().getKey(), value));
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
