package com.linepro.modellbahn.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderCV;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.keys.DecoderCVKey;
import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.model.validation.CVValue;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.rest.json.resolver.DecoderResolver;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * DecoderCV. Holds the CV values for a Decoder (when Konfiguration.CV is in
 * force)
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.DECODER_CV)
@Table(name = DBNames.DECODER_CV, indexes = {
        @Index(columnList = DBNames.DECODER_ID + "," + DBNames.CV_ID, unique = true) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { DBNames.DECODER_ID, DBNames.CV_ID }) })
@JsonRootName(value = ApiNames.CV)
@JsonPropertyOrder({ ApiNames.ID, ApiNames.DECODER, ApiNames.CV, ApiNames.WERT, ApiNames.DELETED, ApiNames.LINKS })
public class DecoderCV extends AbstractItem<DecoderCVKey> implements IDecoderCV {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2660599652146536110L;

    /** The decoder. */
    @NotNull
    private IDecoder decoder;

    /** The cv. */
    @NotNull
    private IDecoderTypCV cv;

    /** The wert. */
    @CVValue
    private Integer wert;

    private Integer cvValue;

    /**
     * Instantiates a new decoder CV.
     */
    public DecoderCV() {
        super();
    }

    public DecoderCV(Long id, IDecoder decoder, IDecoderTypCV cv, Integer wert, Boolean deleted) {
        super(id, deleted);

        setDecoder(decoder);
        setCv(cv);
        setWert(wert);
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Decoder.class)
    @JoinColumn(name = DBNames.DECODER_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name =  DBNames.DECODER_CV + "_fk1"))
    @JsonGetter(ApiNames.DECODER)
    @JsonView(Views.DropDown.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = ApiNames.NAMEN, resolver=DecoderResolver.class)
    public IDecoder getDecoder() {
        return decoder;
    }

    @Override
    @JsonSetter(ApiNames.DECODER)
    @JsonDeserialize(as=Decoder.class)
    public void setDecoder(IDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DecoderTypCV.class)
    @JoinColumn(name = DBNames.CV_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER_CV + "_fk2"))
    public IDecoderTypCV getCv() {
        if (cv != null) {
            setCvValue(cv.getCv());
        }

        return cv;
    }

    @Override
    @JsonIgnore
    public void setCv(IDecoderTypCV cv) {
        this.cv = cv;

        if (cv != null) {
            setCvValue(cv.getCv());
        }
    }

    @Override
    @JsonGetter(ApiNames.CV)
    @JsonView(Views.DropDown.class)
    public Integer getCvValue() {
        return cvValue;
    }

    @Override
    @JsonSetter(ApiNames.CV)
    public void setCvValue(Integer cv) {
        this.cvValue = cv;
    }

    @Override
    @Column(name = DBNames.WERT)
    @JsonGetter(ApiNames.WERT)
    @JsonView(Views.DropDown.class)
    public Integer getWert() {
        return wert;
    }

    @Override
    @JsonSetter(ApiNames.WERT)
    public void setWert(Integer wert) {
        this.wert = wert;
    }

    @Override
    @Transient
    @JsonIgnore
    public String getParentId() {
        return decoder.getLinkId();
    }

    @Override
    @Transient
    @JsonIgnore
    public String getLinkId() {
        return String.format(ApiPaths.DECODER_CV_LINK, getParentId(), getCv().getCv());
    }

    @Override
    public int compareTo(IItem<?> other) {
        if (other instanceof DecoderCV) {
            return new CompareToBuilder()
                    .append(getDecoder(), ((DecoderCV) other).getDecoder())
                    .append(getCv(), ((DecoderCV) other).getCv())
                    .toComparison();
        }
        
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getDecoder())
                .append(getCv())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DecoderCV)) {
            return false;
        }

        DecoderCV other = (DecoderCV) obj;

        return new EqualsBuilder()
                .append(getDecoder(), other.getDecoder())
                .append(getCv(), other.getCv())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append(ApiNames.DECODER, getDecoder())
                .append(ApiNames.CV, getCv())
                .append("wert", getWert())
                .toString();
    }
}
