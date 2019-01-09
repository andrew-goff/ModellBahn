package com.linepro.modellbahn.util.impexp;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import com.google.inject.assistedinject.Assisted;
import com.linepro.modellbahn.model.IItem;
import com.linepro.modellbahn.persistence.IPersister;
import com.linepro.modellbahn.persistence.impl.StaticPersisterFactory;
import com.linepro.modellbahn.util.Selector;

public class Importer<E extends IItem> {

    /** The logger. */
    private final Logger logger;

    /** The persister. */
    private final IPersister<E> persister;

    @Inject
    public Importer(final ILoggerFactory logManager, @Assisted final Class<E> entityClass) {
        this.logger = logManager.getLogger(entityClass.getName());
        
        this.persister = StaticPersisterFactory.get().createPersister(entityClass);
    }
    
    public void read(Path path) {
        try ( Reader in = Files.newBufferedReader(path)) {
            try {
                Iterable<CSVRecord> records = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(in);
    
                for (CSVRecord record : records) {
                    E item = persister.create();
                    
                    for (Selector selector : persister.getSelectors().values()) {
                        String value = record.get(selector.getName());
                        
                        if (!selector.isCollection() && (value != null)) {
                            selector.getSetter().invoke(item, value);
                        }
                    }
                    
                    persister.save(item);
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }
}
