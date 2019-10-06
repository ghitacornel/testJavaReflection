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
        Class<?> aClass = object.getClass();
        Assert.assertSame(SimpleClass.class, aClass);
    }

    @Test
    public void testGetClassParent() {
        Class<?> aClass = object.getClass();
        Class<?> aClassSuperclass = aClass.getSuperclass();
        Assert.assertSame(SimpleClassParent.class, aClassSuperclass);
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
        Annotation[] annotations = aClass.getAnnotations();

        Assert.assertEquals(1, annotations.length);
        Annotation annotation = annotations[0];
        Assert.assertEquals(SimpleAnnotation.class, annotation.annotationType());
        SimpleAnnotation simpleAnnotation = (SimpleAnnotation) annotation;
        Assert.assertEquals(2, simpleAnnotation.specialValue());
    }

    @Test
    public void testGetClassInterfaces() {

        Class<?> aClass = object.getClass();
        Class<?>[] interfaces = aClass.getInterfaces();

        Assert.assertEquals(2, interfaces.length);
        Assert.assertEquals(SimpleInterface.class, interfaces[0]);
        Assert.assertEquals(SimpleTypedInterface.class, interfaces[1]);

        Assert.assertEquals(2, aClass.getGenericInterfaces().length);
        Assert.assertEquals(SimpleInterface.class, aClass.getGenericInterfaces()[0]);
        Assert.assertEquals("model.interfaces.SimpleTypedInterface<java.lang.Integer>", aClass.getGenericInterfaces()[1].getTypeName());

    }

    @Test
    public void testGetClassInterfaceType() {

        Class<?> aClass = object.getClass();
        Type[] genericInterfaces = aClass.getGenericInterfaces();

        Type type = genericInterfaces[1];
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Assert.assertEquals("model.interfaces.SimpleTypedInterface<java.lang.Integer>", parameterizedType.getTypeName());
        Assert.assertEquals("java.lang.Integer", parameterizedType.getActualTypeArguments()[0].getTypeName());
        Assert.assertEquals(Integer.class, parameterizedType.getActualTypeArguments()[0]);

    }

    @Test
    public void testGetClassDeclaredConstructors() {

        Class<?> aClass = object.getClass();
        Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();

        Assert.assertEquals(2, declaredConstructors.length);

        // no argument constructor
        Assert.assertEquals(0, declaredConstructors[0].getParameterCount());

        // with argument private and annotated constructor
        Assert.assertEquals(1, declaredConstructors[1].getParameterCount());
        Assert.assertEquals("java.lang.String", declaredConstructors[1].getParameterTypes()[0].getTypeName());
        Assert.assertEquals(1, declaredConstructors[1].getAnnotations().length);
        Assert.assertEquals(SimpleAnnotation.class, declaredConstructors[1].getAnnotations()[0].annotationType());
        Assert.assertEquals(4, ((SimpleAnnotation) declaredConstructors[1].getAnnotations()[0]).specialValue());

    }

    @Test
    public void testGetClassDeclaredMethods() {

        Class<?> aClass = object.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();

        Assert.assertEquals(6, declaredMethods.length);

        // sort methods
        List<Method> methods = Arrays.stream(declaredMethods).sorted(Comparator.comparing(Method::getName).thenComparing(o -> o.getReturnType().getCanonicalName())).collect(Collectors.toList());

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
        Field[] declaredFields = aClass.getDeclaredFields();

        Assert.assertEquals(4, declaredFields.length);

        // sort fields
        List<Field> fields = Arrays.stream(declaredFields).sorted(Comparator.comparing(Field::getName)).collect(Collectors.toList());

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
