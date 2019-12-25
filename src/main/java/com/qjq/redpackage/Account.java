package com.qjq.redpackage;

public class Account {
    /**
     *  id
     */
    private Long id;
    /**
     * 金额
     */
    private Long amount;


    public Account(Long id, Long amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
