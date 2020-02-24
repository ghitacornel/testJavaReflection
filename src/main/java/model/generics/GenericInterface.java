package model.generics;

public interface GenericInterface<A, B> {

    A f();

    default void g(B b) {
        System.out.println(b);
    }

}
