package com.rex.my.dao.entity.primary;

import com.rex.my.dao.entity.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

public class Trade extends BaseEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trade.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trade.money
     *
     * @mbg.generated
     */
    private BigDecimal money;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trade.trade_type
     *
     * @mbg.generated
     */
    private String tradeType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trade.trade_date
     *
     * @mbg.generated
     */
    private Date tradeDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trade.create_date
     *
     * @mbg.generated
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trade.update_date
     *
     * @mbg.generated
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trade.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trade.account_id
     *
     * @mbg.generated
     */
    private String accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trade.item_id
     *
     * @mbg.generated
     */
    private String itemId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trade.id
     *
     * @return the value of trade.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trade.id
     *
     * @param id the value for trade.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trade.money
     *
     * @return the value of trade.money
     *
     * @mbg.generated
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trade.money
     *
     * @param money the value for trade.money
     *
     * @mbg.generated
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trade.trade_type
     *
     * @return the value of trade.trade_type
     *
     * @mbg.generated
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trade.trade_type
     *
     * @param tradeType the value for trade.trade_type
     *
     * @mbg.generated
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trade.trade_date
     *
     * @return the value of trade.trade_date
     *
     * @mbg.generated
     */
    public Date getTradeDate() {
        return tradeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trade.trade_date
     *
     * @param tradeDate the value for trade.trade_date
     *
     * @mbg.generated
     */
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trade.create_date
     *
     * @return the value of trade.create_date
     *
     * @mbg.generated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trade.create_date
     *
     * @param createDate the value for trade.create_date
     *
     * @mbg.generated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trade.update_date
     *
     * @return the value of trade.update_date
     *
     * @mbg.generated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trade.update_date
     *
     * @param updateDate the value for trade.update_date
     *
     * @mbg.generated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trade.user_id
     *
     * @return the value of trade.user_id
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trade.user_id
     *
     * @param userId the value for trade.user_id
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trade.account_id
     *
     * @return the value of trade.account_id
     *
     * @mbg.generated
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trade.account_id
     *
     * @param accountId the value for trade.account_id
     *
     * @mbg.generated
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trade.item_id
     *
     * @return the value of trade.item_id
     *
     * @mbg.generated
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trade.item_id
     *
     * @param itemId the value for trade.item_id
     *
     * @mbg.generated
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}