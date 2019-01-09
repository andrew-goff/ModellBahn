/*
 * Adress
 * Author:  JohnG
 * Version: $id$
 */
package com.linepro.modellbahn.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Range;

import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderAdress;
import com.linepro.modellbahn.model.IItem;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.model.util.AdressTyp;
import com.linepro.modellbahn.model.validation.Adress;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * DecoderAdress. An address for a decoder (several have more than one)
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.DECODER_ADRESS)
@Table(name =  DBNames.DECODER_ADRESS, indexes = { @Index(columnList = DBNames.DECODER_ID + "," + DBNames.INDEX, unique = true) },
        uniqueConstraints = { @UniqueConstraint(columnNames = { DBNames.DECODER_ID, DBNames.INDEX }) })
@Adress
public class DecoderAdress extends AbstractItem implements IDecoderAdress {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1845658968133212205L;

    /** The decoder. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.decoder.notnull}")
    private IDecoder decoder;

    /** The adress. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.index.notnull}")
    @Range(min=0,max=5, message = "{index.range}")
    private Integer index;

    /** The typ. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.adressTyp.notnull}")
    private AdressTyp adressTyp;

    /** The adress. */
    private Integer adress;

    public DecoderAdress() {
    }

    public DecoderAdress(Long id, IDecoder decoder, Integer index, AdressTyp typ, Integer adress, Boolean deleted) {
        super(id, deleted);
        setDecoder(decoder);
        setIndex(index);
        setAdressTyp(typ);
        setAdress(adress);
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Decoder.class)
    @JoinColumn(name = DBNames.DECODER_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name =  DBNames.DECODER_ADRESS + "_fk1"))
    public IDecoder getDecoder() {
        return decoder;
    }

    @Override
    public void setDecoder(IDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    @Column(name = DBNames.INDEX, nullable = false)
    public Integer getIndex() {
        return index;
    }

    @Override
    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    @BusinessKey
    @Enumerated(EnumType.STRING)
    @Column(name = DBNames.ADRESS_TYP, nullable = false, length = 10)
    public AdressTyp getAdressTyp() {
        return adressTyp;
    }

    @Override
    public void setAdressTyp(AdressTyp adressTyp) {
        this.adressTyp = adressTyp;
    }

    @Override
    @Column(name = DBNames.ADRESS, nullable = false)
    public Integer getAdress() {
        return adress;
    }

    @Override
    public void setAdress(Integer adress) {
        this.adress = adress;
    }

    @Override
    @Transient
    public String getParentId() {
        return decoder.getLinkId();
     }

     @Override
     @Transient
     public String getLinkId() {
         return String.format(ApiPaths.DECODER_ADRESS_LINK, getParentId(), getIndex());
     }

     @Override
     public int compareTo(IItem other) {
         if (other instanceof DecoderAdress) {
             return new CompareToBuilder()
                     .append(getDecoder(), ((DecoderAdress) other).getDecoder())
                     .append(getIndex(), ((DecoderAdress) other).getIndex())
                     .toComparison();
         }
         
         return super.compareTo(other);
     }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getDecoder())
                .append(getIndex())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DecoderAdress)) {
            return false;
        }

        DecoderAdress other = (DecoderAdress) obj;

        return new EqualsBuilder()
                .append(getDecoder(), other.getDecoder())
                .append(getIndex(), other.getIndex())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.DECODER, getDecoder())
                .append(ApiNames.INDEX, getIndex())
                .append(ApiNames.ADRESS_TYP, getAdressTyp())
                .append(ApiNames.ADRESS, getAdress())
                .toString();
    }
}