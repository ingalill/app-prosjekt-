/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asd.ntnu.no.restapiv3;

import asd.ntnu.no.restapiv3.service.UUIDPrimaryKey;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author martin
 */
@Entity
@XmlRootElement
public class Picture implements Serializable {
    @EmbeddedId UUIDPrimaryKey id;
    
    @Lob
    byte[] image;

    Timestamp created;

    @Version
    Timestamp version;        

    public Picture() {
        long time = System.currentTimeMillis();
        created = new Timestamp(time);
        version = new Timestamp(time);
    }
    
    public Picture(UUID id) {
        this();
        this.id = new UUIDPrimaryKey(id);
    }

    public UUID getUUID() {
        return id != null ? id.getUUID() : null;
    }
    
    public void setUUID(UUID id) {
        this.id = id != null ? new UUIDPrimaryKey(id) : null;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @XmlTransient
    public Timestamp getVersion() {
        return version;
    }

    @XmlTransient
    public void setVersion(Timestamp version) {
        this.version = version;
    }
}