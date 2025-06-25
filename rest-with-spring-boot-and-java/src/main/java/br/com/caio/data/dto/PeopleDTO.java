package br.com.caio.data.dto;

import br.com.caio.serializer.GenderSeriazable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "fistName", "lastName", "address", "gender"})
public class PeopleDTO extends RepresentationModel<PeopleDTO> implements Serializable {

    private static final long serialVersionUID = 1;

    private Long id;

    @JsonProperty("first_Name")
    private String firstName;

    private String lastName;

    private String address;

    //@JsonIgnore
    @JsonSerialize(using = GenderSeriazable.class)
    private String gender;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeopleDTO people)) return false;
        return Objects.equals(getId(), people.getId()) && Objects.equals(getFirstName(), people.getFirstName()) && Objects.equals(getLastName(), people.getLastName()) && Objects.equals(getAddress(), people.getAddress()) && Objects.equals(getGender(), people.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAddress(), getGender());
    }
}
