/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asd.ntnu.no.restapiv3.service;

import asd.ntnu.no.restapiv3.Picture;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import net.coobird.thumbnailator.Thumbnails;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author martin
 */
@Path("picture")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class PictureService {
    private static final String INSERT_PICTURE =
        "INSERT INTO PICTURE (MOSTSIGNIFICANT,LEASTSIGNIFICANT,IMAGE,CREATED,VERSION) " +
        "VALUES (?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
    
    private static final String SELECT_IMAGE = 
        "SELECT image FROM picture WHERE MOSTSIGNIFICANT = ? AND LEASTSIGNIFICANT = ?";

    @Resource
    DataSource ds;
    
    @PersistenceContext
    EntityManager em;

    @POST
    @Path("create")
    public Response storePicture(@FormParam("url") URL url) {
        Picture result = null;
        
        try {
            result = storePicture(url.openStream());
        } catch (IOException ex) {
            Logger.getLogger(PictureService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result != null ? Response.ok(result).build() : Response.serverError().build();
    }
    
    public Picture storePicture(InputStream is) {
        UUID id = UUID.randomUUID();

        try(Connection con = ds.getConnection();
            PreparedStatement ps = con.prepareCall(INSERT_PICTURE);
            InputStream bis = new BufferedInputStream(is)) {
            ps.setLong(1, id.getMostSignificantBits());
            ps.setLong(2, id.getLeastSignificantBits());
            ps.setBlob(3, bis);
            ps.executeUpdate();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(PictureService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return em.find(Picture.class, new UUIDPrimaryKey(id));
    }
    
    @POST
    @Path("upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response storePicture(
        @FormDataParam("file")InputStream is,
        @FormDataParam("file") FormDataContentDisposition fileDetail) {
        Picture result = storePicture(is);
        return result != null ? Response.ok(result).build() : Response.serverError().build();
    }
    
    @GET
    @Path("{uuid}")
    @Produces("image/*")
    public Response getImage(@PathParam("uuid")final String uuid, @QueryParam("width")int width) {
        StreamingOutput so = (OutputStream output) -> {
            try(Connection c = ds.getConnection();
                PreparedStatement ps = c.prepareStatement(SELECT_IMAGE)) {
                UUID id = UUID.fromString(uuid);
                ps.setLong(1, id.getMostSignificantBits());
                ps.setLong(2, id.getLeastSignificantBits());
                ResultSet rs = ps.executeQuery();
                
                if(rs.next()) {
                    try(InputStream is = rs.getBlob(1).getBinaryStream()) {
                        if(width == 0) {
                            byte[] buffer = new byte[2048];
                            int length;
                            while((length = is.read(buffer)) != -1) {
                                output.write(buffer,0,length);
                            }
                        } else {
                            Thumbnails.of(is)
                                      .size(width,width)
                                      .outputFormat("jpeg")
                                      .toOutputStream(output);
                        }
                    } 
                }
    
                output.flush();
            } catch (SQLException | IOException ex) {
                Logger.getLogger(PictureService.class.getName()).log(Level.SEVERE, null, ex);
            }            
        };
        return Response.ok(so).build();
    }
        
    
    @GET
    public List<Picture> getPictures() {
        return em.createQuery("SELECT p FROM Picture p ORDER BY p.created DESC", Picture.class).getResultList();
    }
    
    @GET
    @Path("dummy")
    public Response loadDummy() throws MalformedURLException {
        storePicture(new URL("https://www.thegnomonworkshop.com/news/wp-content/uploads/2013/04/empireonline.jpg"));
        return Response.ok().build();
    }   
}
 