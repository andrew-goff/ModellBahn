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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.json.DecoderTypSerializer;
import com.linepro.modellbahn.rest.json.Views;
import com.linepro.modellbahn.util.ToStringBuilder;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;

/**
 * DecoderTypCV.
 * The CVs available for a given DecoderTyp
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = "DecoderTypCV")
@Table(name = "DecoderTypCV", indexes = { @Index(columnList = "decoder_typ_id,cv", unique = true),
        @Index(columnList = "decoder_typ_id"), @Index(columnList = ApiNames.CV) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { "decoder_typ_id", ApiNames.CV }) })
@JsonRootName(value = ApiNames.CV)
@JsonPropertyOrder({ApiNames.ID, ApiNames.DECODER_TYP,  ApiNames.CV,  ApiNames.DESCRIPTION,  ApiNames.MINIMAL,  ApiNames.MAXIMAL,  ApiNames.WERKSEINSTELLUNG, ApiNames.DELETED, ApiNames.LINKS}) 
public class DecoderTypCV extends AbstractItem implements IDecoderTypCV {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5202372019371973750L;

    /** The decoder typ. */
    private IDecoderTyp decoderTyp;

    /** The cv. */
    private Integer cv;

    /** The bezeichnung. */
    private String bezeichnung;

    /** The minimal. */
    private Integer minimal;

    /** The maximal. */
    private Integer maximal;

    /** The werkseinstellung. */
    private Integer werkseinstellung;

    /**
     * Instantiates a new decoder typ CV.
     */
    public DecoderTypCV() {
        super();
    }

    /**
     * Instantiates a new decoder typ CV.
     *
     * @param id the id
     * @param decoderTyp the decoder typ
     * @param cv the cv
     * @param bezeichnung the bezeichnung
     * @param minimal the minimal
     * @param maximal the maximal
     * @param werkseinstellung the werkseinstellung
     * @param deleted the deleted
     */
    public DecoderTypCV(Long id, IDecoderTyp decoderTyp, Integer cv, String bezeichnung, Integer minimal, Integer maximal,
            Integer werkseinstellung, Boolean deleted) {
        super(id, deleted);

        setDecoderTyp(decoderTyp);
        setCV(cv);
        setBezeichnung(bezeichnung);
        setMinimal(minimal);
        setMaximal(maximal);
        setWerkseinstellung(werkseinstellung);
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DecoderTyp.class)
    @JoinColumn(name = "decoder_typ_id", nullable = false, referencedColumnName = ApiNames.ID, foreignKey = @ForeignKey(name = "decoder_typ_cv_fk1"))
    @JsonGetter(ApiNames.DECODER_TYP)
    @JsonView(Views.DropDown.class)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = ApiNames.NAME)
    public IDecoderTyp getDecoderTyp() {
        return decoderTyp;
    }

    @Override
    @JsonSetter(ApiNames.DECODER_TYP)
    public void setDecoderTyp(IDecoderTyp decoderTyp) {
        this.decoderTyp = decoderTyp;
    }

    @Override
    @BusinessKey
    @Column(name = ApiNames.CV, nullable = false)
    @JsonGetter(ApiNames.CV)
    @JsonSerialize(contentUsing=DecoderTypSerializer.class)
    public Integer getCV() {
        return cv;
    }

    @Override
    @JsonSetter(ApiNames.CV)
    public void setCV(Integer cv) {
        this.cv = cv;
    }

    @Override
    @Column(name = ApiNames.DESCRIPTION, nullable = true, length = 100)
    @JsonGetter(ApiNames.DESCRIPTION)
    @JsonView(Views.DropDown.class)
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    @JsonSetter(ApiNames.DESCRIPTION)
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    @Column(name = ApiNames.MINIMAL, nullable = true)
    @JsonGetter(ApiNames.MINIMAL)
    @JsonView(Views.Public.class)
    public Integer getMinimal() {
        return minimal;
    }

    @Override
    @JsonSetter(ApiNames.MINIMAL)
    public void setMinimal(Integer minimal) {
        this.minimal = minimal;
    }

    @Override
    @Column(name = ApiNames.MAXIMAL, nullable = true)
    @JsonGetter(ApiNames.MAXIMAL)
    @JsonView(Views.Public.class)
    public Integer getMaximal() {
        return maximal;
    }

    @Override
    @JsonSetter(ApiNames.MAXIMAL)
    public void setMaximal(Integer maximal) {
        this.maximal = maximal;
    }

    @Override
    @Column(name = ApiNames.WERKSEINSTELLUNG, nullable = true)
    @JsonGetter(ApiNames.WERKSEINSTELLUNG)
    @JsonView(Views.Public.class)
    public Integer getWerkseinstellung() {
        return werkseinstellung;
    }

    @Override
    @JsonSetter(ApiNames.WERKSEINSTELLUNG)
    public void setWerkseinstellung(Integer werkseinstellung) {
        this.werkseinstellung = werkseinstellung;
    }

    @Override
    @Transient
    @JsonIgnore
    public String getParentId() {
        return getDecoderTyp().getLinkId();
    }

    @Override
    @Transient
    @JsonIgnore
    public String getLinkId() {
        return String.format(ApiPaths.DECODER_TYP_CV_LINK, getParentId(), getCV());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getDecoderTyp())
                .append(getCV())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DecoderTypCV)) {
            return false;
        }

        DecoderTypCV other = (DecoderTypCV) obj;

        return new EqualsBuilder()
                .append(getDecoderTyp(), other.getDecoderTyp())
                .append(getCV(), other.getCV())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append(ApiNames.DECODER_TYP, getDecoderTyp())
                .append(ApiNames.CV, getCV())
                .append(ApiNames.DESCRIPTION, getBezeichnung())
                .append(ApiNames.MINIMAL, getMinimal())
                .append(ApiNames.MAXIMAL, getMaximal())
                .append(ApiNames.WERKSEINSTELLUNG, getWerkseinstellung())
                .toString();
    }
}