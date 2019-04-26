package com.gykj.rollcall.entity;

/**
 * desc   : Token返回实体类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2414:28
 * version: 1.0
 */
public class TokenEntity {

    /**
     * access_token : db3d413d-3414-439a-a6a8-aca2c46d377f
     * token_type : bearer
     * refresh_token : deaa34f1-78af-4ea9-84ab-5b46fe844e36
     * expires_in : 24050
     * scope : server
     * tenant_id : 1
     * license : made by pigx
     * user_id : 1
     * username : admin
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int tenant_id;
    private String license;
    private int user_id;
    private String username;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(int tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
