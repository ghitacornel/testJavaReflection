package model;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 4 OBTAIN AND CHANGE A FIELD VALUE
 */
public class TestModifyFieldsValues {

    private final Object object = new SimpleClass();

    @Test
    public void testModifyPrivateFinalFieldValue() throws Exception {

        // get field
        Field field = object.getClass().getDeclaredField("privateField");
        // see getDeclaredFields()
        // see getFields()
        // see getField()

        // make field accessible
        field.setAccessible(true);

        // get field value
        Assert.assertEquals("private final field value", field.get(object));

        // set field value
        field.set(object, "new private field value");
        Assert.assertEquals("new private field value", field.get(object));

    }

    @Test
    public void testModifyPrivateStaticFieldValue() throws Exception {

        // get field
        Field field = object.getClass().getDeclaredField("privateStaticField");

        // make field accessible
        field.setAccessible(true);

        // get field value
        Assert.assertEquals("private static field value", field.get(object));
        Assert.assertEquals("private static field value", field.get(null));// static works on null also

        // set field value
        field.set(object, "new private static field value 1");
        Assert.assertEquals("new private static field value 1", field.get(object));

        // set field value
        field.set(null, "new private static field value 2");// static works on null also
        Assert.assertEquals("new private static field value 2", field.get(null));// static works on null also

    }

    // STILL FAILS
    @Test(expected = java.lang.NoSuchFieldException.class)
    public void testModifyPrivateStaticFinalFieldValue() throws Exception {

        Field field = object.getClass().getDeclaredField("privateStaticFinalField");

        field.setAccessible(true);

        {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        }

        Assert.assertEquals("private static final field value", field.get(object));

//        {// reflection over reflection
//            Field modifiersField = Field.class.getDeclaredField("modifiers");
//            modifiersField.setAccessible(true);
//            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
//        }

        field.set(object, "new private static field value");
        Assert.assertEquals("new private static final field value", field.get(object));
    }

}
