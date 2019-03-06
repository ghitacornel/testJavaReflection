package model;

import org.junit.Assert;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class TestReflectionInvokeMethods {

    private SimpleClass model = new SimpleClass();

    @Test
    public void testInvokePublicMethod() throws Exception {

        Method method = SimpleClass.class.getDeclaredMethod("publicMethod", String.class);
        Assert.assertEquals(model.publicMethod("argument value"), method.invoke(model, "argument value"));

    }

    @Test
    public void testInvokePrivateMethod() throws Exception {

        Method method = SimpleClass.class.getDeclaredMethod("privateMethod", Integer.class);
        method.setAccessible(true);
        Assert.assertEquals(4, method.invoke(model, 2));

    }

    @Test
    public void testInvokePrivateStaticMethod() throws Exception {

        Method method = SimpleClass.class.getDeclaredMethod("privateStaticMethod", Integer.class);
        method.setAccessible(true);
        Assert.assertEquals(27, method.invoke(model, 3));// static methods can be invoked over an object
        Assert.assertEquals(27, method.invoke(null, 3));// static methods can be invoked over null

    }

    @Test
    public void testInvokePublicMethodUsingMethodHandler() throws Throwable {

        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(SimpleClass.class, "publicMethod", MethodType.methodType(String.class, String.class));

        Assert.assertEquals(model.publicMethod("argument value"), methodHandle.invoke(model, "argument value"));

    }
}
