package de.assentis.skype.excel;

import de.assentis.skype.datasource.ApplicationDatasource;
import de.assentis.skype.domain.Document;
import de.assentis.skype.excel.impl.ExcelWriterServiceImpl;
import de.assentis.skype.message.MessageExtractorService;
import de.assentis.skype.message.MessageExtractorServiceTest;
import de.assentis.skype.message.impl.MessageExtractorServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExcelWriterServiceTest {

    private static final String VALID_SAMPLE_MESSAGES = "/de/assentis/skype/message/valid_sample_messages.txt";
    private static final String VALID_SAMPLE_EXCEL = "/de/assentis/skype/excel/sample_sheet.xlsx";

    @Test
    public void given_no_excel_file_path_then_dont_modify_excel() {
        assertFalse(executeExcelWriterService(null));
    }

    @Test
    public void given_empty_excel_file_path_then_dont_modify_excel() {
        assertFalse(executeExcelWriterService(" "));
    }

    @Test
    public void given_wrong_excel_file_path_then_dont_modify_excel() {
        assertFalse(executeExcelWriterService("sdas"));
    }

    @Test
    public void given_no_documents_dont_modifiy_excel() {
        assertFalse(executeExcelWriterService(MessageExtractorServiceTest.class.getResource(VALID_SAMPLE_EXCEL).getPath(), null));
    }

    @Test
    public void given_empty_documents_dont_modifiy_excel() {
        assertFalse(executeExcelWriterService(MessageExtractorServiceTest.class.getResource(VALID_SAMPLE_EXCEL).getPath(), new ArrayList<>()));
    }

    @Test
    public void given_documents_then_modify_excel_when_docuemnts_are_relevant() {
        assertTrue(executeExcelWriterService(MessageExtractorServiceTest.class.getResource(VALID_SAMPLE_EXCEL).getPath()));
    }

    private boolean executeExcelWriterService(final String excelFilePath) {
        final MessageExtractorService messageExtractorService = new MessageExtractorServiceImpl();
        final InputStream inputStream = MessageExtractorServiceTest.class.getResourceAsStream(VALID_SAMPLE_MESSAGES);

        return executeExcelWriterService(excelFilePath, messageExtractorService.extractFinalizedDocuments(inputStream));
    }

    private boolean executeExcelWriterService(final String excelFilePath, final List<Document> documents) {
        final ExcelWriterService excelWriterService = new ExcelWriterServiceImpl();
        return excelWriterService.writeDocumentInfoToExcel(createApplicationDatasource(excelFilePath), documents);
    }

    private ApplicationDatasource createApplicationDatasource(final String excelFilePath) {
        return ApplicationDatasource.builder()
                .excelColumnBemerkung(15)
                .excelColumnId(27)
                .excelColumnFinalisiert(26)
                .excelColumnLayoutQs(25)
                .excelColumnXmlVorhanden(24)
                .excelColumnLablesNachgefuehrt(23)
                .excelColumnName(0)
                .excelFilePath(excelFilePath)
                .excelWorksheetName("Übersicht")
                .build();
    }

}
