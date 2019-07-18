package edu.neu.neumall.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Access(AccessType.FIELD)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userID;

    @NotNull
    @Column(name = "nick_name")
    private String nickName;

    @NotNull
    @Column(name = "user_password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "user_phone", unique = true)
    @NotNull
    private String phone;

    @Column(name = "user_email", unique = true)
    private String email;

    @NotNull
    @Column(name = "user_question")
    private String question;

    @NotNull
    @Column(name = "user_answer")
    private String answer;

    @CreationTimestamp
    private Date create_time;

    @UpdateTimestamp
    private Date update_time;

    //sells relation between user and products
    @OneToMany(mappedBy = "owner")
    private List<Product> productList;

    @OneToMany(mappedBy = "owner")
    private List<Shipping> shippingList;

    @ManyToMany
    private Set<Product> purchaseList;

    @OneToMany(mappedBy = "user_id")
    private Set<Order> user_order;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public List<Shipping> getShippingList() {
        return shippingList;
    }

    /**
     * @param shippingList
     */
    public void setShippingList(List<Shipping> shippingList) {
        this.shippingList = shippingList;
    }

    public void addShipping(Shipping shipping) {
        this.shippingList.add(shipping);
    }

    public static enum UserRole {
        CUSTOMER, SHOPKEEPER, ADMIN, NONE
    }


    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            return this.getUserID() == ((User) o).userID;
        }
        return false;
    }
}
