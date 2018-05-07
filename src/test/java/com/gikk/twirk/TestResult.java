package com.gikk.twirk;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Gikkman
 */
public class TestResult {
    CompletableFuture<Boolean> res = new CompletableFuture<>();

    TestResult(){ }

    void complete(boolean result){
        res.complete(result);
    }

    public boolean await() throws Exception {
        return res.get(TestSuit.WAIT_TIME, TestSuit.UNIT);
    }
}
