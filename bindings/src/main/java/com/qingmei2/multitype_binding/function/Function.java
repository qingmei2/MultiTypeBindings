package com.qingmei2.multitype_binding.function;

/**
 * Represents a function which produces result from input arguments.
 *
 * @param <T> the type of the input of the function
 * @param <R> the type of the result of the function
 */
public interface Function<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t an argument
     * @return the function result
     */
    R apply(T t);
}
