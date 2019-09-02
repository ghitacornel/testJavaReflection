package model;

import model.annotations.SimpleAnnotation;
import model.interfaces.SimpleInterface;
import model.interfaces.SimpleTypedInterface;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestIntrospection {

    final private Object object = new SimpleClass();

    @Test
    public void testGetClass() {
        Assert.assertEquals(SimpleClass.class, object.getClass());
    }

    @Test
    public void testGetClassParent() {
        Assert.assertEquals(SimpleClassParent.class, object.getClass().getSuperclass());
    }

    @Test
    public void testGetClassModifiers() {

        Class<?> aClass = object.getClass();

        Assert.assertTrue(Modifier.isFinal(aClass.getModifiers()));
        Assert.assertTrue(Modifier.isAbstract(aClass.getSuperclass().getModifiers()));
        Assert.assertTrue(Modifier.isPublic(aClass.getModifiers()));
    }

    @Test
    public void testGetClassJarFile() {
        System.out.println(Test.class.getProtectionDomain().getCodeSource().getLocation());
    }

    @Test
    public void testGetClassAnnotations() {

        Class<?> aClass = object.getClass();

        Assert.assertEquals(1, aClass.getAnnotations().length);
        Annotation annotation = aClass.getAnnotations()[0];
        Assert.assertEquals(SimpleAnnotation.class, annotation.annotationType());
        SimpleAnnotation simpleAnnotation = (SimpleAnnotation) annotation;
        Assert.assertEquals(2, simpleAnnotation.specialValue());
    }

    @Test
    public void testGetClassInterfaces() {

        Class<?> aClass = object.getClass();

        Assert.assertEquals(2, aClass.getInterfaces().length);
        Assert.assertEquals(SimpleInterface.class, aClass.getInterfaces()[0]);
        Assert.assertEquals(SimpleTypedInterface.class, aClass.getInterfaces()[1]);

        Assert.assertEquals(2, aClass.getGenericInterfaces().length);
        Assert.assertEquals(SimpleInterface.class, aClass.getGenericInterfaces()[0]);
        Assert.assertEquals("model.interfaces.SimpleTypedInterface<java.lang.Integer>", aClass.getGenericInterfaces()[1].getTypeName());

    }

    @Test
    public void testGetClassInterfaceType() {

        Type type = object.getClass().getGenericInterfaces()[1];
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Assert.assertEquals("model.interfaces.SimpleTypedInterface<java.lang.Integer>", parameterizedType.getTypeName());
        Assert.assertEquals("java.lang.Integer", parameterizedType.getActualTypeArguments()[0].getTypeName());
        Assert.assertEquals(Integer.class, parameterizedType.getActualTypeArguments()[0]);

    }

    @Test
    public void testGetClassDeclaredConstructors() {

        Class<?> aClass = object.getClass();

        Assert.assertEquals(2, aClass.getDeclaredConstructors().length);

        // no argument constructor
        Assert.assertEquals(0, aClass.getDeclaredConstructors()[0].getParameterCount());

        // with argument private and annotated constructor
        Assert.assertEquals(1, aClass.getDeclaredConstructors()[1].getParameterCount());
        Assert.assertEquals("java.lang.String", aClass.getDeclaredConstructors()[1].getParameterTypes()[0].getTypeName());
        Assert.assertEquals(1, aClass.getDeclaredConstructors()[1].getAnnotations().length);
        Assert.assertEquals(SimpleAnnotation.class, aClass.getDeclaredConstructors()[1].getAnnotations()[0].annotationType());
        Assert.assertEquals(4, ((SimpleAnnotation) aClass.getDeclaredConstructors()[1].getAnnotations()[0]).specialValue());

    }

    @Test
    public void testGetClassDeclaredMethods() {

        Class<?> aClass = object.getClass();

        Assert.assertEquals(6, aClass.getDeclaredMethods().length);

        List<Method> methods = Arrays.stream(aClass.getDeclaredMethods()).sorted((o1, o2) -> {
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

        Class<?> aClass = object.getClass();
        
        Assert.assertEquals(4, aClass.getDeclaredFields().length);

        List<Field> fields = Arrays.stream(aClass.getDeclaredFields()).sorted(Comparator.comparing(Field::getName)).collect(Collectors.toList());

        Assert.assertEquals("constructorField", fields.get(0).getName());
        Assert.assertNull(fields.get(0).get(object));

        Assert.assertEquals("privateField", fields.get(1).getName());
        fields.get(1).setAccessible(true);
        Assert.assertEquals("private final field value", fields.get(1).get(object));
        Assert.assertEquals(1, fields.get(1).getAnnotations().length);
        Assert.assertEquals(SimpleAnnotation.class, fields.get(1).getAnnotations()[0].annotationType());
        Assert.assertEquals(3, ((SimpleAnnotation) fields.get(1).getAnnotations()[0]).specialValue());

        Assert.assertEquals("privateStaticField", fields.get(2).getName());
        fields.get(2).setAccessible(true);
        Assert.assertEquals("private static field value", fields.get(2).get(object));

        Assert.assertEquals("privateStaticFinalField", fields.get(3).getName());
        fields.get(3).setAccessible(true);
        Assert.assertEquals("private static final field value", fields.get(3).get(object));

    }

}
