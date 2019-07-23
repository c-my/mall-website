package edu.neu.neumall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Access(AccessType.FIELD)
public class User implements UserDetails {
    //User id, primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long ID;

    //User's nickname, need not unique
    @NotNull
    @Column(name = "nickname")
    private String nickName;

    /**
     * User's avatar
     */
    @NotNull
    @Column(name = "avatar", columnDefinition = "varchar(50) default '/img/default_avatar.jpg'")
    private String avatar = "/img/default_avatar.jpg";
    // TODO: 2019/7/23 add a default_avatar file in static file folder

    /**
     * User's password, after encrypted
     * Will not shown in json
     */
    @NotNull
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * User's phone, require unique
     * and as the identity when login
     */
    @Column(name = "phone", unique = true)
    @NotNull
    private String phone;


    /**
     * User's email, unique required
     */
    @Column(name = "email", unique = true)
    private String email;


    /**
     * User's reset-password question
     */
    @NotNull
    @Column(name = "question")
    private String question;

    /**
     * User's reset-password answer
     */
    @NotNull
    @Column(name = "answer")
    private String answer;

    @CreationTimestamp
    private Date create_time;

    @UpdateTimestamp
    private Date update_time;

    /**
     * User's shopping cart
     */
    @OneToOne(mappedBy = "owner")
    @JsonBackReference
    private ShoppingCart shoppingCart;

    /**
     * User's shipping address list
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<ShippingAddr> shippingAddrAddrList = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "owner")
    private Set<Order> order;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public List<ShippingAddr> getShippingAddrAddrList() {
        return shippingAddrAddrList;
    }

    public void setShippingAddrAddrList(List<ShippingAddr> shippingAddrAddrList) {
        this.shippingAddrAddrList = shippingAddrAddrList;
    }

    public void addShipping(ShippingAddr shippingAddr) {
        this.shippingAddrAddrList.add(shippingAddr);
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public enum UserRole {
        CUSTOMER, SHOPKEEPER, ADMIN, NONE
    }


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
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
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
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
            return this.getID() == ((User) o).ID;
        }
        return false;
    }
}
