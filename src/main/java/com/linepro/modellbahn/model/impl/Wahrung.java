package com.linepro.modellbahn.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.linepro.modellbahn.model.IWahrung;

import com.linepro.modellbahn.model.util.AbstractNamedItem;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * Wahrung.
 * Currency for a price.
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = DBNames.WAHRUNG)
@Table(name = DBNames.WAHRUNG, indexes = { @Index(columnList = DBNames.NAME, unique = true) }, 
       uniqueConstraints = { @UniqueConstraint(columnNames = { DBNames.NAME }) })
public class Wahrung extends AbstractNamedItem implements IWahrung {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 9168067747478159138L;

    /** The decimals. */
    @NotNull(message = "{com.linepro.modellbahn.validator.constraints.decimals.notnull}")
    @Range(min=0,max=2, message = "{com.linepro.modellbahn.validator.constraints.decimals.range}")
    private Integer decimals;
	
	/**
	 * Instantiates a new wahrung.
	 */
	public Wahrung() {
		super();
	}

    public Wahrung(String name) {
        super(name);
    }

    /**
	 * Instantiates a new wahrung.
	 *
	 * @param id the id
	 * @param name the name
	 * @param bezeichnung the bezeichnung
	 * @param decimals the decimals
	 * @param deleted the deleted
	 */
	public Wahrung(Long id, String name, String bezeichnung, Integer decimals, Boolean deleted) {
		super(id, name, bezeichnung, deleted);

		setDecimals(decimals);
	}

	@Override
    @Column(name=DBNames.DECIMALS)
	public Integer getDecimals() {
		return decimals;
	}

	@Override
    public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .appendSuper(super.toString())
		        .append(ApiNames.DECIMALS, getDecimals())
		        .toString();
	}
}
