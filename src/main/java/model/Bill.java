package model;

import java.math.BigDecimal;

public class Bill {
    private long bill_id;
    private long user_id;
    private BigDecimal money;

    public Bill(long bill_id, long user_id, BigDecimal money) {
        this.bill_id = bill_id;
        this.user_id = user_id;
        this.money = money;
    }

    public long getBill_id() {
        return bill_id;
    }

    public void setBill_id(long bill_id) {
        this.bill_id = bill_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "bill_id=" + bill_id +
                ", user_id=" + user_id +
                ", money=" + money +
                '}';
    }
}
