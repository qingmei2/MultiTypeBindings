package com.qingmei2.multitype_binding.function;

/**
 * Represents a function which produces result from two input arguments.
 *
 * @param <T> the type of the first argument
 * @param <U> the type of the second argument
 * @param <R> the type of the result of the function
 */
public interface BiFunction<T, U, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param value1 the first argument
     * @param value2 the second argument
     * @return the function result
     */
    R apply(T value1, U value2);
}
