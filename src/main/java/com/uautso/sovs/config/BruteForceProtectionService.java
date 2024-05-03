package com.uautso.sovs.config;

public interface BruteForceProtectionService {

    void registerLoginFailure(final String username);
    void resetBruteForceCounter(final String username);
    boolean isBruteForceAttack(final String username);

}
