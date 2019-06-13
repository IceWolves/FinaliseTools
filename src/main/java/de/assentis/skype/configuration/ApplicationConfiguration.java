package de.assentis.skype.configuration;

import de.assentis.skype.datasource.ApplicationDatasource;
import de.assentis.skype.excel.ExcelWriterService;
import de.assentis.skype.excel.impl.ExcelWriterServiceImpl;
import de.assentis.skype.message.MessageExtractorService;
import de.assentis.skype.message.impl.MessageExtractorServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Value("${skype.groupname}")
    private String skypeGroupName;

    @Value("${message.filepath}")
    private String messageFilePath;

    @Value("${excel.filepath}")
    private String excelFilePath;

    @Value("${excel.worksheet}")
    private String excelWorksheet;

    @Value("${excel.worksheet.column.name}")
    private int excelColumnName;

    @Value("${excel.worksheet.column.bemerkung}")
    private int excelColumnBemerkung;

    @Value("${excel.worksheet.column.labels_nachgefuehrt}")
    private int excelColumnLabelsNachgefuehrt;

    @Value("${excel.worksheet.column.xml_vorhanden}")
    private int excelColumnXmlVorhanden;

    @Value("${excel.worksheet.column.layout_qs}")
    private int excelColumnLayoutQs;

    @Value("${excel.worksheet.column.finalisiert}")
    private int excelColumnFinalisiert;

    @Value("${excel.worksheet.column.id}")
    private int excelColumnId;

    @Bean
    public ApplicationDatasource applicationDatasource() {
        return ApplicationDatasource.builder()
                .skypeGroupName(this.skypeGroupName)
                .messageFilePath(this.messageFilePath)
                .excelFilePath(this.excelFilePath)
                .excelWorksheetName(excelWorksheet)
                .excelColumnName(this.excelColumnName)
                .excelColumnBemerkung(this.excelColumnBemerkung)
                .excelColumnLablesNachgefuehrt(this.excelColumnLabelsNachgefuehrt)
                .excelColumnXmlVorhanden(this.excelColumnXmlVorhanden)
                .excelColumnLayoutQs(this.excelColumnLayoutQs)
                .excelColumnFinalisiert(this.excelColumnFinalisiert)
                .excelColumnId(this.excelColumnId)
                .build();
    }

    @Bean
    public ExcelWriterService excelWriterService() {
        return new ExcelWriterServiceImpl();
    }

    @Bean
    public MessageExtractorService messageExtractorService() {
        return new MessageExtractorServiceImpl();
    }

}
