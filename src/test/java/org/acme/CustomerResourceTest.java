package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.control.CustomerService;
import org.acme.entity.Customer;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.anything;

@QuarkusTest
@DisplayName("Customer REST-API")
public class CustomerResourceTest {
    @Inject
    CustomerService customerService;

    private UUID id;
    private Customer customer;

    @BeforeEach
    void setUp() {
        id = customerService.findCustomers(new MultivaluedMapImpl<>()).get(0).getId();

        customer = new Customer();
        customer.setPrename("Joachim");
        customer.setName("Hans");
        customer.setAddress("Karlsstraße 23");
    }

    @Test
    public void testGetCustomers_ok() {
        given()
          .when().get("/customers")
          .then().statusCode(200);
    }

    @Test
    public void testGetCustomersWithFilter_ok() {
        given().queryParam("name", "Hans")
                .when().get("/customers")
                .then().statusCode(200);
    }

    @Test
    public void testGetCustomerById_ok() {
        given().pathParam("id", id)
                .when().get("/customers/{id}")
                .then().statusCode(200);
    }

    @Test
    public void testGetCustomerById_notFound() {
        given().pathParam("id", "213")
                .when().get("/customers/{id}")
                .then().statusCode(404);
    }

    @Test
    public void testPostCustomer_created() {
        given().body(customer).contentType(MediaType.APPLICATION_JSON)
                .when().post("/customers")
                .then()
                .statusCode(201).header("location", anything());
    }

    @Test
    public void testPostCustomer_badRequest() {
        customer.setAddress(null);

        given().body(customer).contentType(MediaType.APPLICATION_JSON)
                .when().post("/customers")
                .then()
                .statusCode(400);
    }

    @Test
    public void testPutCustomer_noContent() {
        customer.setAddress("Kriegstraße 23");

        given().pathParam("id", id).body(customer).contentType(MediaType.APPLICATION_JSON)
                .when().put("/customers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testPutCustomer_notFound() {
        customer.setAddress("Kriegstraße 23");

        given().pathParam("id", 34234).body(customer).contentType(MediaType.APPLICATION_JSON)
                .when().put("/customers/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDeleteCustomer_noContent() {
        given().pathParam("id", id)
                .when().delete("/customers/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteCustomer_notFound() {
        given().pathParam("id", "213")
                .when().delete("/customers/{id}")
                .then()
                .statusCode(404);
    }
}