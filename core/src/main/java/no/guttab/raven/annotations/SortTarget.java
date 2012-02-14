package no.guttab.raven.annotations;

import static no.guttab.raven.annotations.SortDirection.ASCENDING;

public @interface SortTarget {
   String displayName() default "";

   SortDirection direction() default ASCENDING;
}
