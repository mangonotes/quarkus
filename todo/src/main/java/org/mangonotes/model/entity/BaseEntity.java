package org.mangonotes.model.entity;


import javax.persistence.*;
import java.time.LocalDateTime;
@MappedSuperclass
public class BaseEntity<PK extends java.io.Serializable> {
    @Transient
    private boolean isNew =true;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private PK id;
    @Column(name="created_date")
    private LocalDateTime createdDate;
    @Column(name="modified_date")
    private LocalDateTime modifiedDate;

    public boolean isNew() {
        return isNew;
    }

    public PK getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
    @PrePersist
    @PostPersist
    void markNotNew(){
        this.isNew=false;
        this.createdDate= LocalDateTime.now();
    }
    @PreUpdate
    void onUpdate(){
        modifiedDate =LocalDateTime.now();
    }
}
