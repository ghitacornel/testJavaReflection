package model.generics;

/**
 * in order to get the type, the types must be present on the declared class
 */
public class GenericClass implements GenericInterface<Integer, String> {

    @Override
    public Integer f() {
        return 1;
    }

}
