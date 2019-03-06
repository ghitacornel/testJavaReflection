package model;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestReflectionModifyFields {

    private SimpleClass model = new SimpleClass();

    @Test
    public void testModifyPrivateFinalFieldValue() throws Exception {

        Field field = model.getClass().getDeclaredField("privateField");

        field.setAccessible(true);
        Assert.assertEquals("private final field value", field.get(model));

        field.set(model, "new private field value");
        Assert.assertEquals("new private field value", field.get(model));
    }

    @Test
    public void testModifyPrivateStaticFieldValue() throws Exception {

        Field field = model.getClass().getDeclaredField("privateStaticField");

        field.setAccessible(true);
        Assert.assertEquals("private static field value", field.get(model));

        field.set(model, "new private static field value");
        Assert.assertEquals("new private static field value", field.get(model));
    }

    // STILL FAILS
    @Test(expected = java.lang.IllegalAccessException.class)
    public void testModifyPrivateStaticFinalFieldValue() throws Exception {

        Field field = model.getClass().getDeclaredField("privateStaticFinalField");

        field.setAccessible(true);
        Assert.assertEquals("private static final field value", field.get(model));

        {// reflection over reflection
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        }

        field.set(model, "new private static field value");
        Assert.assertEquals("new private static final field value", field.get(model));
    }

}
