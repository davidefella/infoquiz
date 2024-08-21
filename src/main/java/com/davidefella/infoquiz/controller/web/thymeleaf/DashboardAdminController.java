package com.davidefella.infoquiz.controller.web.thymeleaf;

import com.davidefella.infoquiz.service.EvaluationService;
import com.davidefella.infoquiz.service.reportgenerators.ExcelReportGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

@Controller
public class DashboardAdminController {


    private EvaluationService evaluationService;
    private ExcelReportGenerator excelReportGenerator;

    @Autowired
    public DashboardAdminController(EvaluationService evaluationService, ExcelReportGenerator excelReportGenerator) {
        this.evaluationService = evaluationService;
        this.excelReportGenerator = excelReportGenerator;
    }

    @GetMapping("/dashboard-admin")
    public String showAdminDashboard(Model model) {
        model.addAttribute("evaluations", evaluationService.findAllActiveEvaluations());
        return "dashboard-admin";
    }

    @GetMapping("/download-results")
    public ResponseEntity<byte[]> downloadResults(@RequestParam String evaluationId) throws IOException {

        byte[] excelReport = excelReportGenerator.generateResultsByEvaluationID(Long.valueOf(evaluationId));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=results.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelReport);
    }

}
