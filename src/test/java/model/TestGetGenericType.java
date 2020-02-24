package model;

import model.generics.GenericClass;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TestGetGenericType {

    @Test
    public void testObtainGeneric() throws Exception {
        GenericClass object = new GenericClass();

        Type[] types = object.getClass().getGenericInterfaces();
        Assert.assertEquals(1, types.length);

        Type type = types[0];
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Assert.assertEquals(2, actualTypeArguments.length);
        Assert.assertEquals(Integer.class.getCanonicalName(), actualTypeArguments[0].getTypeName());
        Assert.assertEquals(String.class.getCanonicalName(), actualTypeArguments[1].getTypeName());

    }
}
