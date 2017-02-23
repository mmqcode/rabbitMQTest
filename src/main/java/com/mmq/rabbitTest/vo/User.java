package com.mmq.rabbitTest.vo;

import java.io.Serializable;

/**
 * Created by Derry on 2017/2/22.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1l;
    private String name;
    private String account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
