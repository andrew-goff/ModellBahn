package com.linepro.modellbahn.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.linepro.modellbahn.model.IArtikel;
import com.linepro.modellbahn.model.IZug;
import com.linepro.modellbahn.model.IZugConsist;
import com.linepro.modellbahn.model.util.AbstractItem;
import com.linepro.modellbahn.util.ToStringBuilder;

/**
 * ZugConsist.
 * A component of a train
 * @author  $Author:$
 * @version $Id:$
 */
@Entity(name = "ZugConsist")
@Table(name = "zug_consist", indexes = { @Index(columnList = "zug_id"),
        @Index(columnList = "artikel_id", unique = true) }, uniqueConstraints = {
                @UniqueConstraint(columnNames = { "zug_id", "position" }) })
public class ZugConsist extends AbstractItem implements IZugConsist {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3941436184732408563L;

    /** The zug. */
    private IZug zug;

    /** The position. */
    private Integer position;

    /** The artikel. */
    private IArtikel artikel;

    /**
     * Instantiates a new zug consist.
     */
    public ZugConsist() {
        super();
    }

    /**
     * Instantiates a new zug consist.
     *
     * @param id the id
     * @param zug the zug
     * @param position the position
     * @param artikel the artikel
     * @param deleted the deleted
     */
    public ZugConsist(Long id, IZug zug, Integer position, IArtikel artikel, Boolean deleted) {
        super(id, deleted);

        setZug(zug);
        setPosition(position);
        setArtikel(artikel);
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Zug.class)
    @JoinColumn(name = "zug_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "consist_fk1"))
    @JsonGetter("zug")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    public IZug getZug() {
        return zug;
    }

    @Override
    @JsonSetter("zug")
    public void setZug(IZug zug) {
        this.zug = zug;
    }

    @Override
    @OrderColumn
    @Column(name = "position", nullable=false)
    @JsonGetter("position")
    public Integer getPosition() {
        return position;
    }

    @Override
    @JsonSetter("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Artikel.class)
    @JoinColumn(name = "artikel_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "consist_fk2"))
    @JsonGetter("artikel")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    public IArtikel getArtikel() {
        return artikel;
    }

    @Override
    @JsonSetter("artikelId")
    public void setArtikel(IArtikel artikel) {
        this.artikel = artikel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("Zug", getZug())
                .append("Position", getPosition())
                .append("artikel", getArtikel())
                .toString();
    }
}
