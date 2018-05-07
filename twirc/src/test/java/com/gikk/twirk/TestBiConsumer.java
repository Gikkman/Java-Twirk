package com.gikk.twirk;

import java.util.function.BiFunction;

/**
 *
 * @author Gikkman
 */
public class TestBiConsumer<T, U> {
    private BiFunction<T,U, Boolean> test;
    private TestResult res;

    public TestResult assign(BiFunction<T, U, Boolean> test){
        this.test = test;
        return res = new TestResult();
    }

    public void consume(T t, U u){
        if(test != null) {
            res.complete(test.apply(t, u));
        }
    }
}
