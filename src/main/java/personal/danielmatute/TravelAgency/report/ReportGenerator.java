package personal.danielmatute.TravelAgency.report;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.persistence.EntityManager;

import org.springframework.core.io.InputStreamResource;

import com.itextpdf.text.DocumentException;

public class ReportGenerator {

    private Class<?> reportType;
    private EntityManager entityManager;

    public <T extends Report> ReportGenerator setReportType(Class<T> reportType) {
        this.reportType = reportType;
        return this;
    }

    public ReportGenerator useEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        return this;
    }

    public InputStreamResource createReport()
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, MalformedURLException, DocumentException, IOException {
        Report report;

        if (this.entityManager != null) {
            report = (Report) reportType.getDeclaredConstructor(EntityManager.class).newInstance(this.entityManager);
        } else {
            report = (Report) reportType.getDeclaredConstructor().newInstance();
        }

        this.reportType = null;
        this.entityManager = null;

        return new InputStreamResource(report.getReportData());
    }

}
