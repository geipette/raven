package no.guttab.raven.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface AnnotatedFieldCallback<T extends Annotation> {
   void doFor(Field field, T annotation);
}
