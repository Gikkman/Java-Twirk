package com.gikk.twirk;

import java.util.function.Function;

/**
 *
 * @author Gikkman
 */
public class TestConsumer<T> {
    private Function<T, Boolean> test;
    private TestResult res;

    public TestResult assign(Function<T, Boolean> test){
        this.test = test;
        return res = new TestResult();
    }

    public void consume(T input){
        if(test != null) {
            res.complete(test.apply(input));
        }
    }
}
