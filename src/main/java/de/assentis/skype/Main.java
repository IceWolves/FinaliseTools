package de.assentis.skype;

import de.assentis.skype.configuration.ApplicationConfiguration;
import de.assentis.skype.datasource.ApplicationDatasource;
import de.assentis.skype.domain.Document;
import de.assentis.skype.excel.ExcelWriterService;
import de.assentis.skype.message.MessageExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        final ApplicationDatasource applicationDatasource = annotationConfigApplicationContext.getBean(ApplicationDatasource.class);
        final MessageExtractorService messageExtractorService = annotationConfigApplicationContext.getBean(MessageExtractorService.class);
        final ExcelWriterService excelWriterService = annotationConfigApplicationContext.getBean(ExcelWriterService.class);

        final String messageFilePath = applicationDatasource.getMessageFilePath();
        log.debug("Read messages from {}...", messageFilePath);
        try {
            final FileInputStream fileInputStream = new FileInputStream(new File(messageFilePath));
            final List<Document> documents = messageExtractorService.extractFinalizedDocuments(fileInputStream);

            log.debug("Document infos read succesfully!");
            log.debug("Write infos to Excel file at path {}", applicationDatasource.getExcelFilePath());

            final boolean writeResultStatus = excelWriterService.writeDocumentInfoToExcel(applicationDatasource, documents);
            if(writeResultStatus) {
                log.debug("Excel file updated successfully");
            } else {
                throw new Exception("Somthing went wrong while trying to update Excel file. See logger output for any information");
            }
        } catch (Exception e) {
            log.error("Something went wrong! {}", e.getLocalizedMessage());
        }
    }
}
