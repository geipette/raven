package no.guttab.raven.search.response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class UrlFragment {
   private static final String ENCODING = Charset.forName("ISO_8859-1").name();

   private String key;
   private String value;

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
         return URLEncoder.encode(unEncoded, ENCODING);
      } catch (UnsupportedEncodingException e) {
         throw new IllegalStateException(ENCODING + " not supported?", e);
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      UrlFragment that = (UrlFragment) o;

      if (key != null ? !key.equals(that.key) : that.key != null) return false;
      if (value != null ? !value.equals(that.value) : that.value != null) return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = key != null ? key.hashCode() : 0;
      result = 31 * result + (value != null ? value.hashCode() : 0);
      return result;
   }
}
