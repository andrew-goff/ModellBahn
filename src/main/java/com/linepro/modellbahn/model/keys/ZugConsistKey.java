package com.linepro.modellbahn.model.keys;

import javax.persistence.Query;

import com.linepro.modellbahn.model.IZug;
import com.linepro.modellbahn.persistence.DBNames;
import com.linepro.modellbahn.rest.util.ApiNames;
import com.linepro.modellbahn.util.ToStringBuilder;

public class ZugConsistKey extends BaseKey {

    protected final IZug zug;

    protected final Integer position;

    public ZugConsistKey(final IZug zug, final Integer position) {
        this.zug = zug;
        this.position = position;
    }

    public IZug getZug() {
        return zug;
    }

    public Integer getPosition() {
        return position;
    }

    @Override
    public void addCriteria(Query query) {
        query.setParameter(DBNames.ZUG, getZug());
        query.setParameter(DBNames.POSITION, getPosition());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(ApiNames.ZUG, getZug())
                .append(ApiNames.POSITION, getPosition())
                .toString();
    }
}
