package no.guttab.raven.reflection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DefaultConstructorInstantiator.class, DefaultConstructorInstantiatorTest.TestObject.class})
public class DefaultConstructorInstantiatorTest {

   @Test
   public void newInstance_should_create_a_new_instance_of_type_using_the_default_constructor() throws Exception {
      TestObject expectedResponse = new TestObject();
      whenNewInstanceOnSearchResponse().thenReturn(expectedResponse);

      DefaultConstructorInstantiator<TestObject> instantiator =
            new DefaultConstructorInstantiator<TestObject>(TestObject.class);
      TestObject actual = instantiator.newInstance();

      assertThat(actual, sameInstance(expectedResponse));
   }

   @Test(expected = DefaultConstructorInstantiator.CouldNotInstantiateTypeException.class)
   public void newInstance_should_throw_expected_exception_when_newInstance_throws_InstantiationException() throws Exception {
      whenNewInstanceOnSearchResponse().thenThrow(new InstantiationException());

      DefaultConstructorInstantiator<TestObject> responseFactory =
            new DefaultConstructorInstantiator<TestObject>(TestObject.class);
      responseFactory.newInstance();
   }

   @Test(expected = DefaultConstructorInstantiator.CouldNotInstantiateTypeException.class)
   public void newInstance_should_throw_expected_exception_when_newInstance_throws_IllegalAccessException() throws Exception {
      whenNewInstanceOnSearchResponse().thenThrow(new IllegalAccessException());

      DefaultConstructorInstantiator<TestObject> responseFactory =
            new DefaultConstructorInstantiator<TestObject>(TestObject.class);
      responseFactory.newInstance();
   }


   private OngoingStubbing<TestObject> whenNewInstanceOnSearchResponse() throws InstantiationException, IllegalAccessException {
      PowerMockito.mockStatic(DefaultConstructorInstantiatorTest.TestObject.class);
      return when(TestObject.class.newInstance());
   }

   class TestObject {
   }

}
