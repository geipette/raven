package no.guttab.raven.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface AnnotatedFieldCallback {
   void doFor(Field field, Annotation annotation);
}
