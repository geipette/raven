package no.guttab.raven.webapp.search.response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class UrlFragment {

   private String key;
   private String value;
   private String encoding = Charset.forName("ISO_8859-1").name();

   public UrlFragment(String key, String value) {
      this.key = key;
      this.value = value;
   }

   public String getKey() {
      return key;
   }

   public String getValue() {
      return value;
   }

   @Override
   public String toString() {
      return encode(key) + '=' + encode(value);
   }

   private String encode(String unEncoded) {
      try {
         return URLEncoder.encode(unEncoded, encoding);
      } catch (UnsupportedEncodingException e) {
         throw new IllegalStateException(encoding + " not supported?", e);
      }
   }
}
