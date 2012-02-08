package no.guttab.raven.annotations;

public enum CombineOperator {
   AND("AND"),
   OR("OR");

   private String queryCriteriaSeparator;

   CombineOperator(String queryCriteriaSeparator) {
      this.queryCriteriaSeparator = queryCriteriaSeparator;
   }

   public String getQueryCriteriaSeparator() {
      return queryCriteriaSeparator;
   }
}
