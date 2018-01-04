package me.javaroad.sdk.wechat.mp.store;

import me.javaroad.sdk.wechat.mp.model.AccessToken;

/**
 * @author heyx
 */
public interface TokenStore {

    void storeAccessToken(AccessToken accessToken);

    AccessToken readAccessToken();
}
