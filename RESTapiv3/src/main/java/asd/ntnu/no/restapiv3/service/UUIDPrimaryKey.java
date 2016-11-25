/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asd.ntnu.no.restapiv3.service;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Embeddable;

/**
 *
 * @author martin
 */
@Embeddable
public class UUIDPrimaryKey implements Serializable {
    Long mostSignificant;
    Long leastSignificant;

    public UUIDPrimaryKey() {
    }

    public UUIDPrimaryKey(long mostSignificant, long leastSignificant) {
        this.mostSignificant = mostSignificant;
        this.leastSignificant = leastSignificant;
    }
    
    public UUIDPrimaryKey(UUID uuid) {
        setUUID(uuid);
    }
    
    public UUID getUUID() {
        return new UUID(mostSignificant, leastSignificant);
    }

    public final void setUUID(UUID uuid) {
        if(uuid == null) {
            throw new IllegalArgumentException("uuid cannot be null");
        }
        this.mostSignificant = uuid.getMostSignificantBits();
        this.leastSignificant = uuid.getLeastSignificantBits();
    }    

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UUIDPrimaryKey other = (UUIDPrimaryKey) obj;
        if (!Objects.equals(this.mostSignificant, other.mostSignificant)) {
            return false;
        }
        if (!Objects.equals(this.leastSignificant, other.leastSignificant)) {
            return false;
        }
        return true;
    }
}