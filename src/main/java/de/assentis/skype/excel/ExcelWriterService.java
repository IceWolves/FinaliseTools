package de.assentis.skype.excel;

import de.assentis.skype.datasource.ApplicationDatasource;
import de.assentis.skype.domain.Document;

import java.util.List;

public interface ExcelWriterService {

    boolean writeDocumentInfoToExcel(final ApplicationDatasource applicationDatasource, final List<Document> document);

}
