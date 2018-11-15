package com.cygrove.libcore.register.moudule;

public class LoginMoudule {
    /**
     * token : 2dbd84ce7f7b435da962432fc8c2e464
     * registerStore : true
     * usePwd : true
     * employee : false
     */
    private String token;
    private boolean registerStore;
    private boolean usePwd;
    private boolean employee;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRegisterStore() {
        return registerStore;
    }

    public void setRegisterStore(boolean registerStore) {
        this.registerStore = registerStore;
    }

    public boolean isUsePwd() {
        return usePwd;
    }

    public void setUsePwd(boolean usePwd) {
        this.usePwd = usePwd;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }
}