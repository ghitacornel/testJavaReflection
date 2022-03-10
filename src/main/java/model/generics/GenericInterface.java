package model.generics;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public interface GenericInterface<A, B> {

    A f();

    default String g() {
        Type type = this.getClass().getGenericInterfaces()[0];
        ParameterizedType parameterizedType= (ParameterizedType) type;
        return Arrays.toString(parameterizedType.getActualTypeArguments());
    }

}
