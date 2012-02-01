package no.guttab.raven.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AnnotationsWithCallback {

   private List<Class<? extends Annotation>> annotations;

   protected AnnotationsWithCallback(Class<? extends Annotation>... annotations) {
      this.annotations = Arrays.asList(annotations);
   }

   public List<Class<? extends Annotation>> getAnnotationTypes() {
      return annotations;
   }

   public abstract void doFor(Field field, Map<Class<? extends Annotation>,? extends Annotation> annotations);
}
