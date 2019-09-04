package edu.neu.neumall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "ProductComment")
public class ProductComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long ID;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private CommentType type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "commenter")
//    @JsonBackReference
    private User commenter;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product")
//    @JsonBackReference
    private Product target;

    @CreationTimestamp
    @Column(name = "create_time")
    private Date createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Date updateTime;

    public CommentType getType() {
        return type;
    }

    public void setType(CommentType type) {
        this.type = type;
    }

    public void setTypeByName(String typeName) {
        try {
            this.type = CommentType.valueOf(typeName);
        } catch (IllegalArgumentException e) {
            this.type = CommentType.AVERAGE;
        }
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Product getTarget() {
        return target;
    }

    public void setTarget(Product target) {
        this.target = target;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static enum CommentType {
        GOOD, BAD, AVERAGE
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ProductComment) {
            return this.commenter.equals(((ProductComment) o).commenter)
                    && this.target.equals(((ProductComment) o).target);
        }
        return false;
    }
}
