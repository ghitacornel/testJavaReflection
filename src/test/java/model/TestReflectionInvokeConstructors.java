package model;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;

public class TestReflectionInvokeConstructors {

    @Test
    public void testInvokeConstructor() throws Exception {

        Assert.assertEquals(1, SimpleClass.class.getConstructors().length);
        Constructor<SimpleClass> constructor = SimpleClass.class.getConstructor();

        SimpleClass instance = constructor.newInstance();
        Assert.assertNotNull(instance);
        Assert.assertNull(instance.constructorField);

    }

    @Test
    public void testInvokePrivateConstructor() throws Exception {

        Assert.assertEquals(2, SimpleClass.class.getDeclaredConstructors().length);
        Constructor<SimpleClass> constructor = SimpleClass.class.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        SimpleClass instance = constructor.newInstance("some constructor set value");
        Assert.assertNotNull(instance);
        Assert.assertEquals("some constructor set value", instance.constructorField);

    }
}
