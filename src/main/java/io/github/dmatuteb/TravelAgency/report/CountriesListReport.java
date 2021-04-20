package io.github.dmatuteb.TravelAgency.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class CountriesListReport extends Report {

	public CountriesListReport() {
	}

	public CountriesListReport(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public ByteArrayInputStream getReportData() throws DocumentException, IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		Document pdfDocument = new Document();
		PdfWriter.getInstance(pdfDocument, byteArrayOutputStream);

		pdfDocument.open();

		Font fontTitle = new Font(FontFamily.UNDEFINED, 18, Font.BOLD);

		fontTitle.setColor(36, 115, 217);

		Font fontLabel = new Font(FontFamily.UNDEFINED, 11, Font.BOLD);

		String title = "Countries list";

		pdfDocument.addTitle(title);
		pdfDocument.addSubject("It's a pdf report that prints the quantity of amusement parks per country");

		Chunk titleChunk = new Chunk(title, fontTitle);
		Paragraph titleParagraph = new Paragraph();

		titleParagraph.setAlignment(Element.ALIGN_CENTER);
		titleParagraph.add(titleChunk);

		pdfDocument.add(titleParagraph);

		Image image = Image.getInstance("src/main/resources/static/img/CountriesList.png");

		image.setAlignment(Element.ALIGN_CENTER);

		pdfDocument.add(image);

		pdfDocument.add(Chunk.NEWLINE);
		pdfDocument.add(Chunk.NEWLINE);

		PdfPTable headerTable = new PdfPTable(2);

		PdfPCell countryNameLabelCell = new PdfPCell(new Phrase("Country", fontLabel));

		countryNameLabelCell.setBorder(Rectangle.NO_BORDER);

		headerTable.addCell(countryNameLabelCell);

		PdfPCell amusementParkQuantityLabelCell = new PdfPCell(new Phrase("Quantity", fontLabel));

		amusementParkQuantityLabelCell.setBorder(Rectangle.NO_BORDER);

		headerTable.addCell(amusementParkQuantityLabelCell);

		pdfDocument.add(headerTable);

		LineSeparator headerTableSeparator = new LineSeparator();

		pdfDocument.add(new Chunk(headerTableSeparator));

		PdfPTable bodyTable = new PdfPTable(2);

		Query amusementParksQuantityPerCountryQuery = super.entityManager.createQuery(
				"SELECT c.name, count(ap.id) FROM AmusementPark ap LEFT JOIN Country c ON ap.amusementParkCountry.id = c.id GROUP BY ap.amusementParkCountry.id");

		List<Object[]> amusementParksQuantityPerCountry = amusementParksQuantityPerCountryQuery.getResultList();

		amusementParksQuantityPerCountry.stream().forEach(result -> {
			PdfPCell countryNameCell = new PdfPCell(new Phrase(String.valueOf(result[0])));

			countryNameCell.setBorder(Rectangle.NO_BORDER);

			bodyTable.addCell(countryNameCell);

			PdfPCell amusementParkQuantityCell = new PdfPCell(new Phrase(String.valueOf(result[1])));

			amusementParkQuantityCell.setBorder(Rectangle.NO_BORDER);

			bodyTable.addCell(amusementParkQuantityCell);
		});

		pdfDocument.add(bodyTable);

		pdfDocument.close();

		return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}
}
