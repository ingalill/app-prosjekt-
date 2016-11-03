/*
 *Må ha en søke funksjon.
 */
package asd.ntnu.no.restapiv2.service;

import asd.ntnu.no.restapiv2.Userprofile;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @version 2
 * @author ingalill
 */
@Stateless
@Path("userprofile")
public class UserprofileFacadeREST extends AbstractFacade<Userprofile> {

    @PersistenceContext(unitName = "asd.ntnu.no_RESTapiv2_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UserprofileFacadeREST() {
        super(Userprofile.class);
    }

    @POST
    @Override
    //@Path("add")
    @Consumes({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.APPLICATION_JSON})
    public void create(Userprofile entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Userprofile entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Userprofile find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Userprofile> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Userprofile> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
