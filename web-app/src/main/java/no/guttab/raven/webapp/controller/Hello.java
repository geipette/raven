package no.guttab.raven.webapp.controller;

import javax.validation.constraints.NotNull;

public class Hello {
   @NotNull(message = "Greeting must be set")
   private String greeting;

   public void setGreeting(String greeting) {
      this.greeting = greeting;
   }

   public String getGreeting() {
      return greeting;
   }
}
