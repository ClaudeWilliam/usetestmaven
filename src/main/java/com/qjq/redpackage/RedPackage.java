package com.qjq.redpackage;

public class RedPackage {
    /**
     * id
     */
    private Long id;

    /**
     * 金额
     */
    private Long money;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public RedPackage(Long id, Long money) {
        this.id = id;
        this.money = money;
    }
}
