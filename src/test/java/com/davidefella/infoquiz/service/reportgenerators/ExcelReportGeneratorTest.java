package com.davidefella.infoquiz.service.reportgenerators;

import com.davidefella.infoquiz.model.persistence.Evaluation;
import com.davidefella.infoquiz.model.persistence.EvaluationPlayer;
import com.davidefella.infoquiz.model.persistence.Player;
import com.davidefella.infoquiz.service.EvaluationPlayerService;
import com.davidefella.infoquiz.service.EvaluationService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExcelReportGeneratorTest {

    @Mock
    private EvaluationPlayerService evaluationPlayerService;

    @Mock
    private EvaluationService evaluationService;

    @InjectMocks
    private ExcelReportGenerator excelReportGenerator;

    @BeforeEach
    void setup() {
        openMocks(this);
    }



    /*
    @Test
    void testGenerateResultsByEvaluationCode() throws IOException {
        // Arrange: Configura un oggetto Evaluation e assicurati che non sia null
        Evaluation evaluation = new Evaluation();
        evaluation.setId(1L);  // Imposta un ID fittizio
        evaluation.setCode("E1");  // Imposta il codice

        when(evaluationService.findByCode("E1")).thenReturn(evaluation);

        // Lista di EvaluationPlayer fittizia
        List<EvaluationPlayer> evaluationPlayers = Arrays.asList(
                new EvaluationPlayer(evaluation, new Player("John", "Smith"), 85.0),
                new EvaluationPlayer(evaluation, new Player("Jane", "Doe"), 90.0)
        );

        when(evaluationPlayerService.findByEvaluationId(evaluation.getId())).thenReturn(evaluationPlayers);

        // Act: Genera il report Excel
        byte[] excelData = excelReportGenerator.generateResultsByEvaluationID(evaluation.getId());

        // Assert: Verifica il contenuto del file Excel generato
        assertNotNull(excelData, "Il file Excel generato non dovrebbe essere nullo");

        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelData))) {
            Sheet sheet = workbook.getSheetAt(0);
            assertNotNull(sheet, "Il foglio di lavoro non dovrebbe essere nullo");

            // Verifica la riga dell'intestazione
            Row headerRow = sheet.getRow(0);
            assertEquals("Cognome", headerRow.getCell(0).getStringCellValue());
            assertEquals("Nome", headerRow.getCell(1).getStringCellValue());
            assertEquals("Punteggio", headerRow.getCell(2).getStringCellValue());

            // Verifica le righe dei giocatori
            for (int i = 0; i < evaluationPlayers.size(); i++) {
                EvaluationPlayer player = evaluationPlayers.get(i);
                Row playerRow = sheet.getRow(i + 1);

                assertEquals(player.getPlayer().getLastName(), playerRow.getCell(0).getStringCellValue());
                assertEquals(player.getPlayer().getFirstName(), playerRow.getCell(1).getStringCellValue());
                assertEquals(player.getScore(), playerRow.getCell(2).getNumericCellValue());
            }
        }
    }

    @Test
    void testEmptyExcelGeneration() throws IOException {
        // Arrange: Crea un oggetto Evaluation mockato
        Evaluation evaluation = new Evaluation();
        evaluation.setId(1L);  // Imposta un ID fittizio
        evaluation.setCode("E1");  // Imposta il codice

        // Configura il mock per restituire l'oggetto Evaluation
        when(evaluationService.findByCode("E1")).thenReturn(evaluation);

        // Mock: Restituisce una lista vuota di EvaluationPlayer per l'Evaluation specificata
        when(evaluationPlayerService.findByEvaluationId(evaluation.getId()))
                .thenReturn(Arrays.asList());

        // Act: Genera il report Excel
        byte[] excelData = excelReportGenerator.generateResultsByEvaluationID(evaluation.getId());

        // Assert: Verifica che il file Excel abbia solo la riga di intestazione
        assertNotNull(excelData, "I dati generati dall'Excel non dovrebbero essere nulli");

        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(excelData))) {
            Sheet sheet = workbook.getSheetAt(0);
            assertNotNull(sheet, "Il foglio di lavoro non dovrebbe essere nullo");

            // Verifica che solo l'intestazione sia presente
            assertEquals(1, sheet.getPhysicalNumberOfRows(), "Il foglio dovrebbe contenere solo una riga (l'intestazione)");
        }
    }*/

}
