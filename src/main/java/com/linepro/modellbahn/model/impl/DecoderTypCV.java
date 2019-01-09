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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Range;

import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypCV;
import com.linepro.modellbahn.model.IItem;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.model.validation.CVValue;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * DecoderTypCV.
 * The CVs available for a given DecoderTyp
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.DECODER_TYP_CV)
@Table(name = DBNames.DECODER_TYP_CV, indexes = { @Index(columnList = DBNames.DECODER_TYP_ID +"," + DBNames.CV, unique = true),
        @Index(columnList = DBNames.DECODER_TYP_ID), @Index(columnList = DBNames.CV) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { DBNames.DECODER_TYP_ID, DBNames.CV }) })
public class DecoderTypCV extends AbstractItem implements IDecoderTypCV {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5202372019371973750L;

    /** The decoder typ. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.decoderTyp.notnull}")
    private IDecoderTyp decoderTyp;

    /** The cv. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.cv.notnull}")
    @Range(min=1,max=255,message = "{cv.range}")
    private Integer cv;

    /** The bezeichnung. */
    @NotEmpty
    private String bezeichnung;

    /** The minimal. */
    @CVValue
    private Integer minimal;

    /** The maximal. */
    @CVValue
    private Integer maximal;

    /** The werkseinstellung. */
    @CVValue
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
        setCv(cv);
        setBezeichnung(bezeichnung);
        setMinimal(minimal);
        setMaximal(maximal);
        setWerkseinstellung(werkseinstellung);
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DecoderTyp.class)
    @JoinColumn(name = DBNames.DECODER_TYP_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER_TYP_CV + "_fk1"))
    public IDecoderTyp getDecoderTyp() {
        return decoderTyp;
    }

    @Override
    public void setDecoderTyp(IDecoderTyp decoderTyp) {
        this.decoderTyp = decoderTyp;
    }

    @Override
    @BusinessKey
    @Column(name = DBNames.CV, nullable = false)
    public Integer getCv() {
        return cv;
    }

    @Override
    public void setCv(Integer cv) {
        this.cv = cv;
    }

    @Override
    @Column(name = DBNames.BEZEICHNUNG, length = 100)
    public String getBezeichnung() {
        return bezeichnung;
    }

    @Override
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    @Override
    @Column(name = DBNames.MINIMAL)
    public Integer getMinimal() {
        return minimal;
    }

    @Override
    public void setMinimal(Integer minimal) {
        this.minimal = minimal;
    }

    @Override
    @Column(name = DBNames.MAXIMAL)
    public Integer getMaximal() {
        return maximal;
    }

    @Override
    public void setMaximal(Integer maximal) {
        this.maximal = maximal;
    }

    @Override
    @Column(name = DBNames.WERKSEINSTELLUNG)
    public Integer getWerkseinstellung() {
        return werkseinstellung;
    }

    @Override
    public void setWerkseinstellung(Integer werkseinstellung) {
        this.werkseinstellung = werkseinstellung;
    }

    @Override
    @Transient
    public String getParentId() {
        return getDecoderTyp().getLinkId();
    }

    @Override
    @Transient
    public String getLinkId() {
        return String.format(ApiPaths.DECODER_TYP_CV_LINK, getParentId(), getCv());
    }

    @Override
    public int compareTo(IItem other) {
        if (other instanceof DecoderTypCV) {
            return new CompareToBuilder()
                    .append(getDecoderTyp(), ((DecoderTypCV) other).getDecoderTyp())
                    .append(getCv(), ((DecoderTypCV) other).getCv())
                    .toComparison();
        }
        
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getDecoderTyp())
                .append(getCv())
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
                .append(getCv(), other.getCv())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.DECODER_TYP, getDecoderTyp())
                .append(ApiNames.CV, getCv())
                .append(ApiNames.BEZEICHNUNG, getBezeichnung())
                .append(ApiNames.MINIMAL, getMinimal())
                .append(ApiNames.MAXIMAL, getMaximal())
                .append(ApiNames.WERKSEINSTELLUNG, getWerkseinstellung())
                .toString();
    }
}