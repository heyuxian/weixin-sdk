package me.javaroad.sdk.wechat.mp.store;

import me.javaroad.sdk.wechat.mp.model.AccessToken;

/**
 * @author heyx
 */
public class InMemoryTokenStore implements TokenStore {

    private AccessToken accessToken;

    @Override
    public void storeAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public AccessToken readAccessToken() {
        return accessToken;
    }
}
