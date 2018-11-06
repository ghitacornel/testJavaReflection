package model;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestReflectionModifyFields {

    private SimpleClass model = new SimpleClass();

    @Test
    public void testModifyFieldValue() throws Exception {

        Field field = model.getClass().getDeclaredField("privateField");

        field.setAccessible(true);
        Assert.assertEquals("private field value", field.get(model));

        field.set(model, "new private field value");
        Assert.assertEquals("new private field value", field.get(model));
    }

    @Test(expected = java.lang.IllegalAccessException.class)
    public void testModifyStaticFieldValue() throws Exception {

        Field field = model.getClass().getDeclaredField("privateStaticField");

        field.setAccessible(true);
        Assert.assertEquals("private static field value", field.get(model));

        field.set(model, "new private static field value");
//        Assert.assertEquals("new private static field value", field.get(model));
    }

}
