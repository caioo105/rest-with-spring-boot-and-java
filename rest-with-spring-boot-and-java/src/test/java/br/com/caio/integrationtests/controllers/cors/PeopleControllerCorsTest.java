package br.com.caio.integrationtests.controllers.cors;

import br.com.caio.config.TestConfigs;
import br.com.caio.integrationtests.dto.PeopleDTO;
import br.com.caio.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PeopleControllerCorsTest extends AbstractIntegrationTest {

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
    void create() throws JsonProcessingException {
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

        assertEquals("Richard", createdPeople.getFirstName());
        assertEquals("Stallman", createdPeople.getLastName());
        assertEquals("New York City - New York - USA", createdPeople.getAddress());
        assertEquals("M", createdPeople.getGender());

    }

    @Test
    @Order(2)
    void createWithWrongOrigin() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
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
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/people")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

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

        assertEquals("Richard", createdPeople.getFirstName());
        assertEquals("Stallman", createdPeople.getLastName());
        assertEquals("New York City - New York - USA", createdPeople.getAddress());
        assertEquals("M", createdPeople.getGender());
    }

    @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
                .setBasePath("/people")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", people.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    private void mockPerson() {
        people.setFirstName("Richard");
        people.setLastName("Stallman");
        people.setAddress("New York City - New York - USA");
        people.setGender("Male");
        people.setEnabled(true);
    }
}