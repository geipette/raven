package no.guttab.raven.annotations;

public enum InterestingTerms {
   LIST("list"),
   DETAILS("details"),
   NONE("none"),
   DEFAULT(null);
   private String interestingTermsParameterValue;

   InterestingTerms(String interestingTermsParameterValue) {
      this.interestingTermsParameterValue = interestingTermsParameterValue;
   }

   public String getInterestingTermsParameterValue() {
      return interestingTermsParameterValue;
   }
}
