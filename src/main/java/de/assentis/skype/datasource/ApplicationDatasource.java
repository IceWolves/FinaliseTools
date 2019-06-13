package de.assentis.skype.datasource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationDatasource {

    private String skypeGroupName;

    private String excelFilePath;

    private String excelWorksheetName;

    private int excelColumnName;

    private int excelColumnBemerkung;

    private int excelColumnLablesNachgefuehrt;

    private int excelColumnXmlVorhanden;

    private int excelColumnLayoutQs;

    private int excelColumnFinalisiert;

    private int excelColumnId;

}
