package br.com.caio.integrationtests.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;
import java.io.Serializable;
import java.util.Objects;

public class PeopleDTO extends RepresentationModel<br.com.caio.data.dto.PeopleDTO> implements Serializable {

    private static final long serialVersionUID = 1;

    private Long id;

    @JsonProperty("first_Name")
    private String firstName;

    private String lastName;

    private String address;

    private String gender;

    private Boolean enabled;

    public PeopleDTO(){}

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
        if (!(o instanceof PeopleDTO peopleDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), peopleDTO.getId()) && Objects.equals(getFirstName(), peopleDTO.getFirstName()) && Objects.equals(getLastName(), peopleDTO.getLastName()) && Objects.equals(getAddress(), peopleDTO.getAddress()) && Objects.equals(getGender(), peopleDTO.getGender()) && Objects.equals(getEnabled(), peopleDTO.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getFirstName(), getLastName(), getAddress(), getGender(), getEnabled());
    }
}
