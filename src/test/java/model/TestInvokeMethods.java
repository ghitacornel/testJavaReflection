package model;

import org.junit.Assert;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * 3 METHOD INVOCATION
 */
public class TestInvokeMethods {

    private SimpleClass model = new SimpleClass();

    @Test
    public void testInvokePublicMethod() throws Exception {

        // get method
        Method method = SimpleClass.class.getDeclaredMethod("publicMethod", String.class);
        // see getDeclaredMethods()
        // see getMethods()
        // see getMethod()

        // invoke method
        Assert.assertEquals(model.publicMethod("argument value"), method.invoke(model, "argument value"));

    }

    @Test
    public void testInvokePrivateMethod() throws Exception {

        // get method
        Method method = SimpleClass.class.getDeclaredMethod("privateMethod", Integer.class);

        // make method accessible
        method.setAccessible(true);

        // invoke method
        Object methodInvocationResult = method.invoke(model, 2);
        Assert.assertEquals(4, methodInvocationResult);

    }

    @Test
    public void testInvokePrivateStaticMethod() throws Exception {

        // get method
        Method method = SimpleClass.class.getDeclaredMethod("privateStaticMethod", Integer.class);

        // make method accessible
        method.setAccessible(true);

        // invoke method
        Object methodInvocationResultWithNotNullTargetObject = method.invoke(model, 3);
        Assert.assertEquals(27, methodInvocationResultWithNotNullTargetObject);// static methods can be invoked over an object

        Object methodInvocationResultWithNullTargetObject = method.invoke(null, 3);
        Assert.assertEquals(27, methodInvocationResultWithNullTargetObject);// static methods can be invoked over null

    }

    @Test
    public void testInvokePublicMethodUsingMethodHandler() throws Throwable {

        // get method handle
        MethodHandle methodHandle = MethodHandles.lookup().findVirtual(SimpleClass.class, "publicMethod", MethodType.methodType(String.class, String.class));

        // invoke method using method handle
        Assert.assertEquals(model.publicMethod("argument value"), methodHandle.invoke(model, "argument value"));

    }
}
