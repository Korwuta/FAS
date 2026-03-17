package com.example.FAS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Navigation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String displayName;
    private String url;
    private String icon;
    private int linkPosition;
    @ManyToOne
    @JoinColumn(name = "parentNavId")
    private Navigation parentNav;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "navigation_permission",joinColumns = @JoinColumn(name="navigation_link_id"),
//            inverseJoinColumns = @JoinColumn(name="permission_id"),uniqueConstraints = {})
    @PrePersist
    protected void onCreated(){
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

//    @Transient
//    public NavigationType getType(){
//        if(parentNav==null&&childNav.isEmpty()){
//            return NavigationType.PARENT_LINK;
//        }else if(childNav!=null&&!childNav.isEmpty()){
//            return NavigationType.COLLAPSIBLE_GROUP;
//        }else{
//            return NavigationType.CHILD_LINK;
//        }
//    }
}
