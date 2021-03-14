package org.acme.boundary;

import org.acme.control.ContractService;
import org.acme.entity.Contract;
import org.acme.entity.validation.ConstraintViolation;
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
import javax.ws.rs.core.*;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NOT_IMPLEMENTED;

@Path("/contracts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContractResource {

    private final ContractService contractService;

    @Inject
    public ContractResource(ContractService contractService){
        this.contractService = contractService;
    }

    @POST
    public Response setContract(@RequestBody Contract contract, @Context UriInfo uriInfo){
        List<ConstraintViolation> violations = contract.validation();
        if (violations.isEmpty()) {
            UUID uuid = contractService.setContract(contract);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            return Response.created(uriBuilder.path(String.valueOf(uuid)).build()).build();
        }
        return Response.status(BAD_REQUEST).entity(violations).build();
    }

    @GET
    @Path("/{id}")
    public Response getContractById(@PathParam("id") UUID id){
        Contract contract = contractService.findContractById(id);
        return contract != null ?
                Response.ok(contract).build() : Response.status(NOT_FOUND).build();

    }

    @GET
    public Response getContracts(){
        return Response.ok(contractService.findAllContract()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContractById(@PathParam("id") UUID id){
        contractService.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Response updateContract(@RequestBody Contract contract){
        List<ConstraintViolation> violations = contract.validation();
        if (violations.isEmpty()) {
            contractService.updateContract(contract);
            return Response.status(NOT_IMPLEMENTED).build();
        }
        return Response.status(BAD_REQUEST).entity(violations).build();
    }

}
