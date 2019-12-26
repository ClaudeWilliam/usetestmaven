package com.qjq.redpackage;

import java.util.concurrent.atomic.AtomicLong;

public class Account {
    /**
     *  id
     */
    private Long id;
    /**
     * 金额
     */
    private AtomicLong amount;

    public Account(Long id, AtomicLong amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AtomicLong getAmount() {
        return amount;
    }

    public void setAmount(AtomicLong amount) {
        this.amount = amount;
    }
}
