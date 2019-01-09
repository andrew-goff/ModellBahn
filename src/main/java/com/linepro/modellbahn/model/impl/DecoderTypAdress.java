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
import org.hibernate.validator.constraints.Range;

import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IDecoderTypAdress;
import com.linepro.modellbahn.model.IItem;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.model.util.AdressTyp;
import com.linepro.modellbahn.model.validation.Adress;
import com.linepro.modellbahn.model.validation.CVValue;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * DecoderTypAdress.
 * The Adresss available for a given DecoderTyp
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.DECODER_TYP_ADRESS)
@Table(name = DBNames.DECODER_TYP_ADRESS, indexes = { @Index(columnList = DBNames.DECODER_TYP_ID +"," + DBNames.INDEX, unique = true),
        @Index(columnList = DBNames.DECODER_TYP_ID), @Index(columnList = DBNames.INDEX) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { DBNames.DECODER_TYP_ID, DBNames.INDEX }) })
@Adress
public class DecoderTypAdress extends AbstractItem implements IDecoderTypAdress {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5202372019371973750L;

    /** The decoder typ. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.decoderTyp.notnull}")
    private IDecoderTyp decoderTyp;

    /** The index. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.index.notnull}")
    @Range(min=1,max=10, message = "{index.range}")
    private Integer index;

    /** The adressTyp. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.decoderTyp.notnull}")
    private AdressTyp adressTyp;

    /** The span. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.span.notnull}")
    @Range(min=1,max=32,message = "{span.range}")
    private Integer span;

    /** The werkseinstellung. */
    @CVValue
    private Integer werkseinstellung;

    /**
     * Instantiates a new decoder typ Adress.
     */
    public DecoderTypAdress() {
        super();
    }

    /**
     * Instantiates a new decoder typ Adress.
     *
     * @param id the id
     * @param decoderTyp the decoder typ
     * @param index the index
     * @param adressTyp the adressTyp
     * @param span the span
     * @param werkseinstellung the werkseinstellung
     * @param deleted the deleted
     */
    public DecoderTypAdress(Long id, IDecoderTyp decoderTyp, Integer index, AdressTyp adressTyp, Integer span,
            Integer werkseinstellung, Boolean deleted) {
        super(id, deleted);

        setDecoderTyp(decoderTyp);
        setIndex(index);
        setAdressTyp(adressTyp);
        setSpan(span);
        setAdress(werkseinstellung);
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DecoderTyp.class)
    @JoinColumn(name = DBNames.DECODER_TYP_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER_TYP_ADRESS + "_fk1"))
    public IDecoderTyp getDecoderTyp() {
        return decoderTyp;
    }

    @Override
    public void setDecoderTyp(IDecoderTyp decoderTyp) {
        this.decoderTyp = decoderTyp;
    }

    @Override
    @BusinessKey
    @Column(name = DBNames.INDEX, nullable = false)
    public Integer getIndex() {
        return index;
    }

    @Override
    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    @Column(name = DBNames.ADRESS_TYP, length = 100)
    public AdressTyp getAdressTyp() {
        return adressTyp;
    }

    @Override
    public void setAdressTyp(AdressTyp adressTyp) {
        this.adressTyp = adressTyp;
    }

    @Override
    @Column(name = DBNames.SPAN)
    public Integer getSpan() {
        return span;
    }

    @Override
    public void setSpan(Integer span) {
        this.span = span;
    }

    @Override
    @Column(name = DBNames.WERKSEINSTELLUNG)
    public Integer getAdress() {
        return werkseinstellung;
    }

    @Override
    public void setAdress(Integer werkseinstellung) {
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
        return String.format(ApiPaths.DECODER_TYP_ADRESS_LINK, getParentId(), getIndex());
    }

    @Override
    public int compareTo(IItem other) {
        if (other instanceof DecoderTypAdress) {
            return new CompareToBuilder()
                    .append(getDecoderTyp(), ((DecoderTypAdress) other).getDecoderTyp())
                    .append(getIndex(), ((DecoderTypAdress) other).getIndex())
                    .toComparison();
        }
        
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getDecoderTyp())
                .append(getIndex())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DecoderTypAdress)) {
            return false;
        }

        DecoderTypAdress other = (DecoderTypAdress) obj;

        return new EqualsBuilder()
                .append(getDecoderTyp(), other.getDecoderTyp())
                .append(getIndex(), other.getIndex())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.DECODER_TYP, getDecoderTyp())
                .append(ApiNames.ADRESS_TYP, getAdressTyp())
                .append(ApiNames.INDEX, getIndex())
                .append(ApiNames.SPAN, getSpan())
                .toString();
    }
}