package no.guttab.raven.webapp.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface AnnotatedFieldExecutor<T extends Annotation> {
   void execute(Field field, T annotation);
}
