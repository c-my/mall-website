package edu.neu.neumall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer shippingID;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @NotNull
    @Column(name = "receiver_name")
    private String receiverName;

    @NotNull
    @Column(name = "receiver_phone")
    private String receiverPhone;

    @Column(name = "receiver_zip")
    private String receiverZip;

    @NotNull
    @Column(name = "receiver_province")
    private String receiverProvince;

    @NotNull
    @Column(name = "receiver_city")
    private String receiverCity;

    @NotNull
    @Column(name = "receiver_address")
    private String receiverAddress;

    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    public Integer getShippingID() {
        return shippingID;
    }

    public void setShippingID(Integer shippingID) {
        this.shippingID = shippingID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
