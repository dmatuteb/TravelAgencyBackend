package io.github.dmatuteb.TravelAgency.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.dmatuteb.TravelAgency.report.CountriesListReport;
import io.github.dmatuteb.TravelAgency.report.ReportGenerator;
import com.itextpdf.text.DocumentException;

@Controller
@RequestMapping("/reports")
public class ReportController {

	@Autowired
	private ReportGenerator reportGenerator;

	@Autowired
	private EntityManager entityManager;

	@GetMapping("/countries-list")
	public ResponseEntity<InputStreamResource> generateCountryListReport()
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, MalformedURLException, DocumentException, IOException {
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(this.reportGenerator
				.setReportType(CountriesListReport.class).useEntityManager(this.entityManager).createReport());
	}
}
