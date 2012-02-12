package no.guttab.raven.reflection;

public class ArrayUtils {

   public static boolean equals(Class<?>[] a, Class<?>[] a2) {
      if (a == a2)
         return true;
      if (a == null || a2 == null)
         return false;

      int length = a.length;
      if (a2.length != length)
         return false;

      for (int i = 0; i < length; i++)
         if (a[i] != a2[i])
            return false;

      return true;
   }

}
