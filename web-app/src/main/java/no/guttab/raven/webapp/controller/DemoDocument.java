package no.guttab.raven.webapp.controller;

import java.util.List;

import no.guttab.raven.annotations.IndexFieldName;
import no.guttab.raven.annotations.SortDirection;
import no.guttab.raven.annotations.SortTarget;
import no.guttab.raven.annotations.SortVariant;
import org.joda.time.DateTime;

public class DemoDocument {
   @IndexFieldName("cat")
   private List<String> categories;
   private String[] features;
   @SortTarget
   private String id;
   private boolean inStock;
   @IndexFieldName("manu")
   private String manufacturer;
   @IndexFieldName("manufacturedate_dt")
   private DateTime manufacturedDate;
   private String name;
   @SortTarget(variants = @SortVariant(SortDirection.DESCENDING))
   private Integer popularity;
   private Float price;
   private String store;


   public List<String> getCategories() {
      return categories;
   }

   public String[] getFeatures() {
      return features;
   }

   public String getId() {
      return id;
   }

   public boolean isInStock() {
      return inStock;
   }

   public String getManufacturer() {
      return manufacturer;
   }

   public DateTime getManufacturedDate() {
      return manufacturedDate;
   }

   public String getName() {
      return name;
   }

   public Integer getPopularity() {
      return popularity;
   }

   public Float getPrice() {
      return price;
   }

   public String getStore() {
      return store;
   }
}
