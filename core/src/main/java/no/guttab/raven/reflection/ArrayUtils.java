package no.guttab.raven.reflection;

public class ArrayUtils {

   private ArrayUtils() {
   }

   public static boolean equals(Class<?>[] a, Class<?>[] a2) {
      return new ClassArrayEqualChecker(a, a2).check();
   }

}
