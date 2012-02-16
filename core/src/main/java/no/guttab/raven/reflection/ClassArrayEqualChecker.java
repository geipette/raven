package no.guttab.raven.reflection;

class ClassArrayEqualChecker {
   private Class<?>[] a;
   private Class<?>[] a2;

   public ClassArrayEqualChecker(Class<?>[] a, Class<?>... a2) {
      this.a = a;
      this.a2 = a2;
   }

   public boolean check() {
      if (isSame(a, a2))
         return true;

      if (isNull(a, a2)) {
         return false;
      }

      if (hasDifferentLength(a, a2))
         return false;

      return eachElementIsSame(a, a2);
   }

   private static boolean isSame(Class<?>[] a, Class<?>[] a2) {
      return a == a2;
   }

   private static boolean isNull(Class<?>[] a, Class<?>[] a2) {
      return a == null || a2 == null;
   }

   private static boolean hasDifferentLength(Class<?>[] a, Class<?>[] a2) {
      return a.length != a2.length;
   }

   private static boolean eachElementIsSame(Class<?>[] a, Class<?>[] a2) {
      for (int i = 0; i < a.length; i++) {
         if (a[i] != a2[i]) return false;
      }
      return true;
   }
}
