package org.acme.boundary;

import org.acme.control.CustomerService;
import org.acme.entity.Customer;
import org.acme.entity.validation.ConstraintViolation;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final CustomerService customerService;

    @Inject
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @POST
    public Response setCustomer(@RequestBody Customer customer, @Context UriInfo uriInfo) {
        List<ConstraintViolation> violations = customer.validation();
        if (violations.isEmpty()) {
            UUID uuid = customerService.setCustomer(customer);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            return Response.created(uriBuilder.path(String.valueOf(uuid)).build()).build();
        }
        return Response.status(BAD_REQUEST).entity(violations).build();
    }

    @GET
    @Path("/{id}")
    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform", unit = MetricUnits.MILLISECONDS)
    public Response getCustomerById(@PathParam("id") UUID id) {
        Customer customer = customerService.findCustomerById(id);
        return Response.ok(customer).build();
    }

    @GET
    public Response getCustomers(@Context UriInfo uriInfo){
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
        return Response.ok(customerService.findCustomers(queryParameters)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomerById(@PathParam("id") UUID id) {
        customerService.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@RequestBody Customer updateCustomer, @PathParam("id") UUID id) {
        List<ConstraintViolation> violations = updateCustomer.validation();
        if (violations.isEmpty()) {
            customerService.updateCustomer(updateCustomer, id);
            return Response.noContent().build();
        }
        return Response.status(BAD_REQUEST).entity(violations.toString()).build();

    }

}