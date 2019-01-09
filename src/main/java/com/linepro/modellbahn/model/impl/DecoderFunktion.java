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

import com.linepro.modellbahn.model.IDecoder;
import com.linepro.modellbahn.model.IDecoderFunktion;
import com.linepro.modellbahn.model.IDecoderTypFunktion;
import com.linepro.modellbahn.model.IItem;

import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.persistence.util.BusinessKey;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.rest.util.ApiPaths;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * DecoderFunktion.
 * The functions supported by a Decoder (Key : description) 
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.DECODER_FUNKTION)
@Table(name = DBNames.DECODER_FUNKTION, indexes = { @Index(columnList = DBNames.DECODER_ID + "," + DBNames.FUNKTION_ID, unique = true) },
       uniqueConstraints = { @UniqueConstraint(columnNames = { DBNames.DECODER_ID, DBNames.FUNKTION_ID })})
public class DecoderFunktion extends AbstractItem implements IDecoderFunktion {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3254516717556070251L;

    /** The decoder. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.decoder.notnull}")
    private IDecoder decoder;
    
    /** The funktion. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.funktion.notnull}")
    private IDecoderTypFunktion funktion;

	/** The wert. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.wert.notnull}")
	private String bezeichnung;

	/**
	 * Instantiates a new decoder funktion.
	 */
	public DecoderFunktion() {
		super();
	}

	/**
	 * Instantiates a new decoder funktion.
	 *
	 * @param decoder the decoder
	 * @param funktion the funktion
	 * @param bezeichnung the bezeichnung
	 */
	public DecoderFunktion(Long id, IDecoder decoder, IDecoderTypFunktion funktion, String bezeichnung, Boolean deleted) {
	    super(id, deleted);

        setDecoder(decoder);
        setFunktion(funktion);		
        setBezeichnung(bezeichnung);
	}

	@Override
    @BusinessKey
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=Decoder.class)
    @JoinColumn(name=DBNames.DECODER_ID, nullable = false, referencedColumnName=DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER_FUNKTION + "_fk1"))
    public IDecoder getDecoder() {
        return decoder;
    }

    @Override
    public void setDecoder(IDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    @BusinessKey
    @ManyToOne(fetch=FetchType.LAZY, targetEntity=DecoderTypFunktion.class)
    @JoinColumn(name = DBNames.FUNKTION_ID, nullable = false, referencedColumnName=DBNames.ID, foreignKey = @ForeignKey(name = DBNames.DECODER_FUNKTION + "_fk2"))
    public IDecoderTypFunktion getFunktion() {
        return funktion;
    }

    @Override
    public void setFunktion(IDecoderTypFunktion funktion) {
        this.funktion = funktion;
    }

    @Override
	@Column(name=DBNames.BEZEICHNUNG, length=100)
	public String getBezeichnung() {
		return bezeichnung;
	}

    @Override
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

    @Override
    @Transient
    public String getLinkId() {
        return String.format(ApiPaths.DECODER_FUNKTION_LINK, getParentId(), getFunktion().getReihe(), getFunktion().getFunktion());
    }

    @Override
    @Transient
    public String getParentId() {
        return getDecoder().getLinkId();
    }

    @Override
    public int compareTo(IItem other) {
        if (other instanceof DecoderFunktion) {
            return new CompareToBuilder()
                    .append(getDecoder(), ((DecoderFunktion) other).getDecoder())
                    .append(getFunktion(), ((DecoderFunktion) other).getFunktion())
                    .toComparison();
        }
        
        return super.compareTo(other);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getDecoder())
                .append(getFunktion())
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof DecoderFunktion)) {
            return false;
        }

        DecoderFunktion other = (DecoderFunktion) obj;

        return new EqualsBuilder()
                .append(getDecoder(), other.getDecoder())
                .append(getFunktion(), other.getFunktion())
                .isEquals();
    }

	@Override
	public String toString() {
		return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(ApiNames.DECODER, getDecoder())
				.append(ApiNames.FUNKTION, getFunktion())
				.append(ApiNames.BEZEICHNUNG, getBezeichnung())
				.toString();
	}
}
