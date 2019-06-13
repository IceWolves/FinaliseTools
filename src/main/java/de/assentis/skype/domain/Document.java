package de.assentis.skype.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {

    private String id;

    private String name;

    private String bemerkung;

    private String labelsRdeNachgefuehrt;

    private String xmlVorhanden;

    private String layoutQSFunktionsTest;

    private String finalisiert;

}
