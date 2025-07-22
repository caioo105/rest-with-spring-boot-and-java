package br.com.caio.integrationtests.controllers;

import br.com.caio.config.TestConfigs;
import br.com.caio.integrationtests.dto.PeopleDTO;
import br.com.caio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.caio.model.People;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PeopleControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static PeopleDTO people;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        people = new PeopleDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_CAIO)
                .setBasePath("/people")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(people)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PeopleDTO createdPeople = objectMapper.readValue(content, PeopleDTO.class);
        people = createdPeople;

        assertNotNull(createdPeople.getId());
        assertNotNull(createdPeople.getFirstName());
        assertNotNull(createdPeople.getLastName());
        assertNotNull(createdPeople.getAddress());
        assertNotNull(createdPeople.getGender());

        assertTrue(createdPeople.getId() > 0);

        assertEquals("Caio", createdPeople.getFirstName());
        assertEquals("Martins", createdPeople.getLastName());
        assertEquals("SBC - SP - Brazil", createdPeople.getAddress());
        assertEquals("Male", createdPeople.getGender());

    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        people.setLastName("Costa Martins");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(people)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PeopleDTO createdPeople = objectMapper.readValue(content, PeopleDTO.class);
        people = createdPeople;

        assertNotNull(createdPeople.getId());
        assertNotNull(createdPeople.getFirstName());
        assertNotNull(createdPeople.getLastName());
        assertNotNull(createdPeople.getAddress());
        assertNotNull(createdPeople.getGender());

        assertTrue(createdPeople.getId() > 0);

        assertEquals("Caio", createdPeople.getFirstName());
        assertEquals("Costa Martins", createdPeople.getLastName());
        assertEquals("SBC - SP - Brazil", createdPeople.getAddress());
        assertEquals("Male", createdPeople.getGender());

    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", people.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PeopleDTO createdPeople = objectMapper.readValue(content, PeopleDTO.class);
        people = createdPeople;

        assertNotNull(createdPeople.getId());
        assertNotNull(createdPeople.getFirstName());
        assertNotNull(createdPeople.getLastName());
        assertNotNull(createdPeople.getAddress());
        assertNotNull(createdPeople.getGender());

        assertTrue(createdPeople.getId() > 0);

        assertEquals("Caio", createdPeople.getFirstName());
        assertEquals("Costa Martins", createdPeople.getLastName());
        assertEquals("SBC - SP - Brazil", createdPeople.getAddress());
        assertEquals("Male", createdPeople.getGender());
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", people.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PeopleDTO createdPeople = objectMapper.readValue(content, PeopleDTO.class);
        people = createdPeople;

        assertNotNull(createdPeople.getId());
        assertTrue(createdPeople.getId() > 0);

        assertEquals("Caio", createdPeople.getFirstName());
        assertEquals("Costa Martins", createdPeople.getLastName());
        assertEquals("SBC - SP - Brazil", createdPeople.getAddress());
        assertEquals("Male", createdPeople.getGender());
        assertFalse(createdPeople.getEnabled());
    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", people.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {
        mockPerson();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(people)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<PeopleDTO> people = objectMapper.readValue(content, new TypeReference<List<PeopleDTO>>() {});

        PeopleDTO peopleOne = people.get(0);

        assertNotNull(peopleOne.getId());
        assertTrue(peopleOne.getId() > 0);

        assertEquals("Ayrton", peopleOne.getFirstName());
        assertEquals("Senna", peopleOne.getLastName());
        assertEquals("São Paulo - Brasil", peopleOne.getAddress());
        assertEquals("Male", peopleOne.getGender());
        assertTrue(peopleOne.getEnabled());

        PeopleDTO peopleFour = people.get(4);

        assertNotNull(peopleFour.getId());
        assertTrue(peopleFour.getId() > 0);

        assertEquals("Muhamamd", peopleFour.getFirstName());
        assertEquals("Ali", peopleFour.getLastName());
        assertEquals("Kentucky - US", peopleFour.getAddress());
        assertEquals("Male", peopleFour.getGender());
        assertTrue(peopleFour.getEnabled());
    }

    private void mockPerson() {
        people.setFirstName("Caio");
        people.setLastName("Martins");
        people.setAddress("SBC - SP - Brazil");
        people.setGender("Male");
        people.setEnabled(true);
    }
}