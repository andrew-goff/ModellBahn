/*
 * 
 */
package com.linepro.modellbahn.util.impexp;

import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.google.inject.assistedinject.Assisted;
import com.linepro.modellbahn.model.IDecoderTyp;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.model.IProdukt;
import com.linepro.modellbahn.model.IUnterKategorie;
import com.linepro.modellbahn.model.refs.INamedItemRef;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.impl.StaticPersisterFactory;
import com.linepro.modellbahn.util.Selector;

// TODO: Auto-generated Javadoc
/**
 * The Class Exporter.
 * @param <E> the element type
 */
public class Exporter<E extends IItem> {

    /** The Constant DATE_FORMAT. */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    /** The logger. */
    private final Logger logger;

    /** The persister. */
    private final IPersister<E> persister;
    
    /** The headers. */
    private final List<String> headers;

    /**
     * Instantiates a new exporter.
     * @param logManager the log manager
     * @param entityClass the entity class
     */
    @Inject
    public Exporter(final ILoggerFactory logManager, @Assisted final Class<E> entityClass) {
        this.logger = logManager.getLogger(entityClass.getName());
        
        this.persister = StaticPersisterFactory.get().createPersister(entityClass);
        
        headers = persister.getSelectors().values().stream()
                .filter(s -> !s.isCollection() && s.getSetter() != null)
                .map(Selector::getName)
                .collect(Collectors.toList());
    }
    
    /**
     * Write.
     * @param path the path
     */
    public void write(Path path) {
        try ( Writer out = Files.newBufferedWriter(path);
              CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers.toArray(new String[0])))) {
            for (E item : persister.findAll()) {
                try {
                    List<Object> values = new ArrayList<>(headers.size());
                    
                    for (String column : headers) {
                        Selector selector = persister.getSelectors().get(column);
                        
                        values.add(convert(selector.getGetter().invoke(item)));
                        
                        printer.printRecord(values);
                    }
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
    
    /**
     * Convert.
     * @param value the value
     * @return the string
     */
    private String convert(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof IProdukt) {
            return ((IProdukt) value).getBestellNr();
        } else if (value instanceof IDecoderTyp) {
            return ((IDecoderTyp) value).getBestellNr();
        } else if (value instanceof IUnterKategorie) {
            return ((IUnterKategorie) value).getName();
        } else if (value instanceof INamedItemRef) {
            return ((INamedItemRef) value).getName();
        } else if (value instanceof LocalDate) {
            return DATE_FORMAT.format(value);
        } else if (value instanceof BigDecimal) {
            return ((BigDecimal) value).toPlainString();
        }
        
        return value.toString();
    }
}
