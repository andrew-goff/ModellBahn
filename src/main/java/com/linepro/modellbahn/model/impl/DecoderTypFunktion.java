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
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.IItem;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * DecoderTypFunktion. The Functions available for a DecoderTyp
 * 
 * @author $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.DECODER_TYP_FUNKTION)
@Table(name = DBNames.DECODER_TYP_FUNKTION, indexes = { @Index(columnList = DBNames.DECODER_TYP_ID + "," + DBNames.REIHE + "," + DBNames.FUNKTION, unique = true),
        @Index(columnList = DBNames.DECODER_TYP_ID) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { DBNames.DECODER_TYP_ID, DBNames.REIHE, DBNames.FUNKTION }) })
public class DecoderTypFunktion extends AbstractItem implements IDecoderTypFunktion {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -9194895557054214626L;

    /** The decoder typ. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.decoderTyp.notnull}")
    private IDecoderTyp decoderTyp;

    /** The reihe. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.reihe.notnull}")
    @Range(min=0, max=1, message = "{com.linepro.modellbahn.validator.constraints.reihe.range}")
    private Integer reihe;

    @NotEmpty(message = "{com.linepro.modellbahn.validator.constraints.funktion.notempty}")
    private String funktion;

    @NotEmpty(message = "{com.linepro.modellbahn.validator.constraints.bezeichnung.notempty}")
    private String bezeichnung;
    
    /** The programmable. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.programmable.notnull}")
    private Boolean programmable;

    public DecoderTypFunktion() {
        super();
    }

    public DecoderTypFunktion(IDecoderTyp decoderTyp, Integer reihe, String funktionNr) {
        this(null, decoderTyp, reihe, funktionNr, "", false, false);
    }

    public DecoderTypFunktion(Long id, IDecoderTyp decoderTyp, Integer reihe, String funktionNr, String bezeichnung,
            Boolean programmable, Boolean deleted) {
        super(id, deleted);

        setDecoderTyp(decoderTyp);
        setReihe(reihe);
        setFunktion(funktionNr);
        setBezeichnung(bezeichnung);
        setProgrammable(programmable);
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DecoderTyp.class)
    @JoinColumn(name = DBNames.DECODER_TYP_ID, nullable = false, referencedColumnName = DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER_TYP_FUNKTION + "_fk1"))
    public IDecoderTyp getDecoderTyp() {
        return decoderTyp;
    }

    @Override
    public void setDecoderTyp(IDecoderTyp decoderTyp) {
        this.decoderTyp = decoderTyp;
    }

    @Override
    @BusinessKey
    @Column(name = DBNames.REIHE, nullable = false)
    public Integer getReihe() {
        return reihe;
    }

    @Override
    public void setReihe(Integer reihe) {
        this.reihe = reihe;
    }

    @Column(name = DBNames.FUNKTION, length = 4)
    public String getFunktion() {
      return funktion;
    }

    @Override
    public void setFunktion(String funktion) {
      this.funktion = funktion;
    }

    @Column(name = DBNames.BEZEICHNUNG, length = 100)
    public String getBezeichnung() {
      return bezeichnung;
    }

    @Override
    public void setBezeichnung(String bezeichnung) {
      this.bezeichnung = bezeichnung;
    }

    @Override
    @Column(name = DBNames.PROGRAMMABLE, nullable = false)
    public Boolean getProgrammable() {
        return programmable;
    }

    @Override
    public void setProgrammable(Boolean programmable) {
        this.programmable = programmable;
    }

    @Override
    @Transient
    public String getParentId() {
        return getDecoderTyp().getLinkId();
    }

    @Override
    @Transient
    public String getLinkId() {
        return String.format(ApiPaths.DECODER_TYP_FUNKTION_LINK, getParentId(), getReihe(), super.getLinkId());
    }

    @Override
    public int compareTo(IItem other) {
        if (other instanceof DecoderTypFunktion) {
            return new CompareToBuilder()
                    .append(getDecoderTyp(), ((DecoderTypFunktion) other).getDecoderTyp())
                    .append(getReihe(), ((DecoderTypFunktion) other).getReihe())
                    .append(Integer.valueOf(getFunktion().substring(1)), Integer.valueOf(((DecoderTypFunktion) other).getFunktion().substring(1)))
                    .toComparison();
        }
        
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getDecoderTyp())
                .append(getReihe())
                .append(getFunktion())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DecoderTypFunktion)) {
            return false;
        }

        DecoderTypFunktion other = (DecoderTypFunktion) obj;

        return new EqualsBuilder()
                .append(getDecoderTyp(), other.getDecoderTyp())
                .append(getReihe(), other.getReihe())
                .append(getFunktion(), other.getFunktion())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.DECODER_TYP, getDecoderTyp())
                .append(ApiNames.REIHE, getReihe())
                .append(ApiNames.PROGRAMMABLE, getProgrammable())
                .toString();
    }
}