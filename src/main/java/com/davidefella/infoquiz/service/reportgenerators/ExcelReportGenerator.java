package com.davidefella.infoquiz.service.reportgenerators;

import com.davidefella.infoquiz.model.persistence.EvaluationPlayer;
import com.davidefella.infoquiz.service.EvaluationPlayerService;
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

    EvaluationPlayerService evaluationPlayerService;

    @Autowired
    public ExcelReportGenerator(EvaluationPlayerService evaluationPlayerService) {
        this.evaluationPlayerService = evaluationPlayerService;
    }

    public byte[] generateResultsByEvaluationID(Long evaluationId) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Risultati");

        writeHeader(sheet);
        writePlayers(sheet, evaluationId);

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

    private void writePlayers(Sheet sheet, Long evaluationId){
        int rowCounter = 1;
        Row rowPlayer = null;
        for (EvaluationPlayer  evaluationPlayer: evaluationPlayerService.findByEvaluationId(evaluationId)){
            rowPlayer = sheet.createRow(rowCounter++);

            rowPlayer.createCell(0).setCellValue(evaluationPlayer.getPlayer().getLastName());
            rowPlayer.createCell(1).setCellValue(evaluationPlayer.getPlayer().getFirstName());
            rowPlayer.createCell(2).setCellValue(evaluationPlayer.getScore());

        }
    }

}
