package com.linepro.modellbahn.persistence;

public interface IProduktTeilKey extends IProduktKey {
  String getTeilHersteller();

  String getTeilBestellNr();
}
