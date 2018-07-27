package com.linepro.modellbahn.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileFinder implements IFileFinder {

    protected Set<String> contentPaths = Collections.synchronizedSet(new HashSet<String>());

    protected final Logger logger;
    
    public FileFinder() {
        logger = LoggerFactory.getILoggerFactory().getLogger(getClass().getName());
    }

    @Override
    public void addPath(String path) {
        contentPaths.add(path);
    }

    @Override
    public void addPaths(Collection<String> paths) {
        contentPaths.addAll(paths);
    }

    @Override
    public Set<String> getPaths() {
        return contentPaths;
    }

    @Override
    public File findFile(String path) {
        for (String contentPath : getPaths()) {
            File file = Paths.get(contentPath).resolve(path).toFile();
            
            if (file.exists() && file.isFile() && file.canRead()) {
                logger.info("Found " + file);
    
                return file;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("contentPaths", contentPaths)
                .toString();
    }
}