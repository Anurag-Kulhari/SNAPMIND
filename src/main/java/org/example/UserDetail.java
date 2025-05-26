package org.example;


import jakarta.persistence.*;

@Entity
@Table(name = "user_detail")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String phone;

    @Column
    @Lob
    private String userWebData;

    public UserDetail(){

    }
    public UserDetail(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserWebData() {
        return userWebData;
    }

    public void setUserWebData(String userWebData) {
        this.userWebData = userWebData;
    }
}
