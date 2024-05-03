package com.uautso.sovs.config;




import com.uautso.sovs.model.BaseEntity;

import java.time.LocalDateTime;


public class FailedLogin extends BaseEntity {
    private int count;
    private LocalDateTime date;

    public FailedLogin() {
        this.count = 0;
        this.date = LocalDateTime.now();
    }

    public FailedLogin(int count, LocalDateTime date) {
        this.count = count;
        this.date = date;
    }

    @Override
    public String toString() {
        return "FailedLogin{" +
                "count=" + count +
                ", date=" + date +
                '}';
    }
}
