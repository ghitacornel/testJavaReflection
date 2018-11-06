package model;

import model.annotations.SimpleAnnotation;
import model.interfaces.SimpleInterface;
import model.interfaces.SimpleTypedInterface;

@SimpleAnnotation(specialValue = 2)
final public class SimpleClass extends SimpleClassParent implements SimpleInterface, SimpleTypedInterface<Integer> {

    @SimpleAnnotation(specialValue = 3)
    final private String privateField = "private field value";
    public static final String privateStaticField = "private static field value";

    public String constructorField;

    public SimpleClass() {
        constructorField = null;
    }

    @SimpleAnnotation(specialValue = 4)
    private SimpleClass(String constructorField) {
        this.constructorField = constructorField;
    }

    @SimpleAnnotation(specialValue = 5)
    public String publicMethod(String argument) {
        return privateField + " " + argument;
    }

    public String getName() {
        return privateField;
    }

    @Override
    public Integer executeTypedBusiness(Integer argument) {
        return null;
    }

    private Integer privateMethod(Integer argument) {
        return argument * argument;
    }

    static private Integer privateStaticMethod(Integer argument) {
        return argument * argument * argument;
    }
}
