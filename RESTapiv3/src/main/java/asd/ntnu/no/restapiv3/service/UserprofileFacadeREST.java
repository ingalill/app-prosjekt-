/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asd.ntnu.no.restapiv3.service;

import asd.ntnu.no.restapiv3.Userprofile;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
 * @version 03.11.2014
 * @author inga lill
 */
@Stateless
@Path("userprofile")
@Produces(MediaType.APPLICATION_JSON)
public class UserprofileFacadeREST extends AbstractFacade<Userprofile> {

    // @Resource(mappedName="jdbc/Profile")
    //DataSource dataSource;
    @PersistenceContext(unitName = "asd.ntnu.no_RESTapiv3_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UserprofileFacadeREST() {
        super(Userprofile.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Userprofile entity) {
        //    System.out.println("Hello from create " + entity);
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
    public Userprofile find(@PathParam("id") Long id) { // find metoden fungerer kun på primary key. 
        return super.find(id);
    }

    @GET
    @Path("search/{query}")
    @Produces({MediaType.APPLICATION_JSON})
    public Userprofile findByName(@PathParam("query") String query) {
        Query q = getEntityManager().createNamedQuery("Userprofile.findByFirstname");
        q.setParameter("firstname", query);
        q.setMaxResults(1);
        Object result = q.getSingleResult();
        if (result == null) {
            return null;
        }
        return (Userprofile) result;
        //return em.createQuery("select u from Userprofile u", Userprofile.class).getResultList();
        //return q;//super.findByName(query);
        // return null;
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
