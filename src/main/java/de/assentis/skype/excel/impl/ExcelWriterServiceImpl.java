package de.assentis.skype.excel.impl;

import de.assentis.skype.datasource.ApplicationDatasource;
import de.assentis.skype.domain.Document;
import de.assentis.skype.excel.ExcelWriterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Slf4j
@Service
public class ExcelWriterServiceImpl implements ExcelWriterService {

    @Override
    public boolean writeDocumentInfoToExcel(final ApplicationDatasource applicationDatasource, final List<Document> documents) {
        try {
            final String excelFilePath = applicationDatasource.getExcelFilePath();
            if(StringUtils.isBlank(excelFilePath)) {
                throw new IOException("Excel file path must not be null or empty!");
            }

            if(documents == null || documents.isEmpty()) {
                throw new IOException("No documents given!");
            }

            final FileInputStream excelFile = new FileInputStream(new File(excelFilePath));
            final Workbook workbook = new XSSFWorkbook(excelFile);

            final Sheet datatypeSheet = workbook.getSheet(applicationDatasource.getExcelWorksheetName());

            documents.forEach(document -> {
                for(final Row row : datatypeSheet) {
                    if(row.getCell(applicationDatasource.getExcelColumnName(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().equalsIgnoreCase(document.getName())) {
                        row.getCell(applicationDatasource.getExcelColumnBemerkung(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(StringUtils.defaultString(document.getBemerkung()));
                        row.getCell(applicationDatasource.getExcelColumnId(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(StringUtils.defaultString(document.getId()));
                        row.createCell(applicationDatasource.getExcelColumnLablesNachgefuehrt()).setCellValue(StringUtils.defaultString(document.getLabelsRdeNachgefuehrt()));
                        row.getCell(applicationDatasource.getExcelColumnXmlVorhanden(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(StringUtils.defaultString(document.getXmlVorhanden()));
                        row.getCell(applicationDatasource.getExcelColumnLayoutQs(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(StringUtils.defaultString(document.getLayoutQSFunktionsTest()));
                        row.getCell(applicationDatasource.getExcelColumnFinalisiert(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(StringUtils.defaultString(document.getFinalisiert()));
                    }
                }
            });

            final FileOutputStream outputStream = new FileOutputStream(applicationDatasource.getExcelFilePath());
            workbook.write(outputStream);
            excelFile.close();
            workbook.close();
            outputStream.close();
            return true;
        } catch (IOException e) {
            log.error("Error while trying to open excel file! See stacktrace for more information", e);
        }

        return false;
    }

}
