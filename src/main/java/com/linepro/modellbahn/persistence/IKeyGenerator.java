package com.linepro.modellbahn.persistence;

public interface IKeyGenerator {

  IKey getKey(Object value) throws Exception;
}
