package br.com.caio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "people")
public class People implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 6)
    private String gender;

    @Column(nullable = false)
    private Boolean enabled;

    public People(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof People people)) return false;
        return Objects.equals(getId(), people.getId()) && Objects.equals(getFirstName(), people.getFirstName()) && Objects.equals(getLastName(), people.getLastName()) && Objects.equals(getAddress(), people.getAddress()) && Objects.equals(getGender(), people.getGender()) && Objects.equals(getEnabled(), people.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender(), getEnabled());
    }
}
