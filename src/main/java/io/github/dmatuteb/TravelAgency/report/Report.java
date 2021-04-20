package io.github.dmatuteb.TravelAgency.report;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.persistence.EntityManager;

import com.itextpdf.text.DocumentException;

public abstract class Report {

	protected EntityManager entityManager;

	public Report() {
	}

	public Report(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public abstract ByteArrayInputStream getReportData() throws DocumentException, IOException;

}
