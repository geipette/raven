package no.guttab.raven.search.response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.springframework.util.Assert;

public class UrlFragment {
   private static final String ENCODING = Charset.forName("ISO_8859-1").name();

   private String requestFieldName;
   private String fqCriteria;

   public UrlFragment(String requestFieldName, String fqCriteria) {
      Assert.notNull(requestFieldName, "requestFieldName can not be null");
      Assert.notNull(fqCriteria, "fqCriteria can not be null");
      this.requestFieldName = requestFieldName;
      this.fqCriteria = fqCriteria;
   }

   public String getRequestFieldName() {
      return requestFieldName;
   }

   public String getFqCriteria() {
      return fqCriteria;
   }

   @Override
   public String toString() {
      return encode(requestFieldName) + '=' + encode(fqCriteria);
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

      if (requestFieldName != null ? !requestFieldName.equals(that.requestFieldName) : that.requestFieldName != null) return false;
      if (fqCriteria != null ? !fqCriteria.equals(that.fqCriteria) : that.fqCriteria != null) return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = requestFieldName != null ? requestFieldName.hashCode() : 0;
      result = 31 * result + (fqCriteria != null ? fqCriteria.hashCode() : 0);
      return result;
   }


}
