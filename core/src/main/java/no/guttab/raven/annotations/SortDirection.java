package no.guttab.raven.annotations;

public enum SortDirection {
   ASCENDING("asc"),
   DESCENDING("desc");
   private String parameterValue;

   SortDirection(String parameterValue) {
      this.parameterValue = parameterValue;
   }

   public String parameterValue() {
      return parameterValue;
   }
}
