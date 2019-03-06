package model;

import model.annotations.SimpleAnnotation;
import model.interfaces.SimpleInterface;
import model.interfaces.SimpleTypedInterface;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestReflectionIntrospection {

    final private Object model = new SimpleClass();

    @Test
    public void testGetClass() {
        Assert.assertEquals(SimpleClass.class, model.getClass());
    }

    @Test
    public void testGetClassAnnotations() {
        Assert.assertEquals(1, model.getClass().getAnnotations().length);
        Annotation annotation = model.getClass().getAnnotations()[0];
        Assert.assertEquals(SimpleAnnotation.class, annotation.annotationType());
        SimpleAnnotation simpleAnnotation = (SimpleAnnotation) annotation;
        Assert.assertEquals(2, simpleAnnotation.specialValue());
    }

    @Test
    public void testGetClassInterfaces() {

        Assert.assertEquals(2, model.getClass().getInterfaces().length);
        Assert.assertEquals(SimpleInterface.class, model.getClass().getInterfaces()[0]);
        Assert.assertEquals(SimpleTypedInterface.class, model.getClass().getInterfaces()[1]);

        Assert.assertEquals(2, model.getClass().getGenericInterfaces().length);
        Assert.assertEquals(SimpleInterface.class, model.getClass().getGenericInterfaces()[0]);
        Assert.assertEquals("model.interfaces.SimpleTypedInterface<java.lang.Integer>", model.getClass().getGenericInterfaces()[1].getTypeName());

    }

    @Test
    public void testGetClassInterfaceType() {

        Type type = model.getClass().getGenericInterfaces()[1];
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Assert.assertEquals("model.interfaces.SimpleTypedInterface<java.lang.Integer>", parameterizedType.getTypeName());
        Assert.assertEquals("java.lang.Integer", parameterizedType.getActualTypeArguments()[0].getTypeName());
        Assert.assertEquals(Integer.class, parameterizedType.getActualTypeArguments()[0]);

    }

    @Test
    public void testGetClassDeclaredConstructors() {
        Assert.assertEquals(2, model.getClass().getDeclaredConstructors().length);

        // no argument constructor
        Assert.assertEquals(0, model.getClass().getDeclaredConstructors()[0].getParameterCount());

        // with argument private and annotated constructor
        Assert.assertEquals(1, model.getClass().getDeclaredConstructors()[1].getParameterCount());
        Assert.assertEquals("java.lang.String", model.getClass().getDeclaredConstructors()[1].getParameterTypes()[0].getTypeName());
        Assert.assertEquals(1, model.getClass().getDeclaredConstructors()[1].getAnnotations().length);
        Assert.assertEquals(SimpleAnnotation.class, model.getClass().getDeclaredConstructors()[1].getAnnotations()[0].annotationType());
        Assert.assertEquals(4, ((SimpleAnnotation) model.getClass().getDeclaredConstructors()[1].getAnnotations()[0]).specialValue());

    }

    @Test
    public void testGetClassDeclaredMethods() {

        Assert.assertEquals(6, model.getClass().getDeclaredMethods().length);

        List<Method> methods = Arrays.stream(model.getClass().getDeclaredMethods()).sorted((o1, o2) -> {
            int firstComparison = o1.getName().compareTo(o2.getName());
            if (firstComparison != 0) return firstComparison;
            return o1.getReturnType().getCanonicalName().compareTo(o2.getReturnType().getCanonicalName());
        }).collect(Collectors.toList());

        Assert.assertEquals("executeTypedBusiness", methods.get(0).getName());
        Assert.assertEquals(Integer.class, methods.get(0).getReturnType());

        Assert.assertEquals("executeTypedBusiness", methods.get(1).getName());
        Assert.assertEquals(Object.class, methods.get(1).getReturnType());

        Assert.assertEquals("getName", methods.get(2).getName());
        Assert.assertEquals(String.class, methods.get(2).getReturnType());

        Assert.assertEquals("privateMethod", methods.get(3).getName());
        Assert.assertEquals(Integer.class, methods.get(3).getReturnType());

        Assert.assertEquals("privateStaticMethod", methods.get(4).getName());
        Assert.assertEquals(Integer.class, methods.get(4).getReturnType());

        Assert.assertEquals("publicMethod", methods.get(5).getName());
        Assert.assertEquals(String.class, methods.get(5).getReturnType());
        Assert.assertEquals(1, methods.get(5).getAnnotations().length);
        Assert.assertEquals(SimpleAnnotation.class, methods.get(5).getAnnotations()[0].annotationType());
        Assert.assertEquals(5, ((SimpleAnnotation) methods.get(5).getAnnotations()[0]).specialValue());

    }

    @Test
    public void testGetClassDeclaredFieldsAndValues() throws Exception {

        Assert.assertEquals(4, model.getClass().getDeclaredFields().length);

        List<Field> fields = Arrays.stream(model.getClass().getDeclaredFields()).sorted(Comparator.comparing(Field::getName)).collect(Collectors.toList());

        Assert.assertEquals("constructorField", fields.get(0).getName());
        Assert.assertNull(fields.get(0).get(model));

        Assert.assertEquals("privateField", fields.get(1).getName());
        fields.get(1).setAccessible(true);
        Assert.assertEquals("private final field value", fields.get(1).get(model));
        Assert.assertEquals(1, fields.get(1).getAnnotations().length);
        Assert.assertEquals(SimpleAnnotation.class, fields.get(1).getAnnotations()[0].annotationType());
        Assert.assertEquals(3, ((SimpleAnnotation) fields.get(1).getAnnotations()[0]).specialValue());

        Assert.assertEquals("privateStaticField", fields.get(2).getName());
        fields.get(2).setAccessible(true);
        Assert.assertEquals("private static field value", fields.get(2).get(model));

        Assert.assertEquals("privateStaticFinalField", fields.get(3).getName());
        fields.get(3).setAccessible(true);
        Assert.assertEquals("private static final field value", fields.get(3).get(model));

    }

    @Test
    public void testGetClassParent() {
        Assert.assertEquals(SimpleClassParent.class, model.getClass().getSuperclass());
    }

    @Test
    public void testGetClassModifiers() {
        Assert.assertTrue(Modifier.isFinal(model.getClass().getModifiers()));
        Assert.assertTrue(Modifier.isAbstract(model.getClass().getSuperclass().getModifiers()));
        Assert.assertTrue(Modifier.isPublic(model.getClass().getModifiers()));
    }

    @Test
    public void testGetClassJarFile(){
        System.out.println(Test.class.getProtectionDomain().getCodeSource().getLocation());
    }

}
