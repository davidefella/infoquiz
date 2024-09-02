package com.davidefella.infoquiz.service.reportgenerators;

import com.davidefella.infoquiz.model.persistence.EvaluationStudent;
import com.davidefella.infoquiz.service.EvaluationStudentService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ExcelReportGenerator {

    EvaluationStudentService evaluationStudentService;

    @Autowired
    public ExcelReportGenerator(EvaluationStudentService evaluationStudentService) {
        this.evaluationStudentService = evaluationStudentService;
    }

    public byte[] generateResultsByEvaluationID(Long evaluationId) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Risultati");

        writeHeader(sheet);
        writeStudent(sheet, evaluationId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }

    private void writeHeader(Sheet sheet){
        Row row = sheet.createRow(0);

        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Cognome");

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Nome");

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Punteggio");

    }

    private void writeStudent(Sheet sheet, Long evaluationId){
        int rowCounter = 1;
        Row rowStudent = null;
        for (EvaluationStudent evaluationStudent : evaluationStudentService.findByEvaluationId(evaluationId)){
            rowStudent = sheet.createRow(rowCounter++);

            rowStudent.createCell(0).setCellValue(evaluationStudent.getStudent().getLastName());
            rowStudent.createCell(1).setCellValue(evaluationStudent.getStudent().getFirstName());
            rowStudent.createCell(2).setCellValue(evaluationStudent.getScore());

        }
    }

}
