package org.example.demo1.reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para generar informes utilizando JasperReports.
 * Los informes incluyen:
 * - Listado completo de películas.
 * - Películas en mal estado.
 * - Películas con más de una copia.
 * - Información detallada de una copia específica.
 */

public class ReportGenerator {

    // Configuración de conexión a la base de datos
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jasper";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    /**
     * Método principal que ejecuta los informes definidos.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */

    public static void main(String[] args) {
        try {
            // Generar los informes según el caso de uso
            generateAllMoviesReport();
            generateDamagedMoviesReport();
            generateMoviesWithMultipleCopiesReport();
            generateSpecificCopyReport(5); // Cambiar el ID según sea necesario
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera un informe que incluye el listado completo de películas.
     *
     * @throws Exception Si ocurre un error durante la generación del informe.
     */

    public static void generateAllMoviesReport() throws Exception {
        generateReport("all_movies.jrxml", null, "all_movies.pdf");
    }

    /**
     * Genera un informe que incluye las películas en mal estado.
     *
     * @throws Exception Si ocurre un error durante la generación del informe.
     */

    public static void generateDamagedMoviesReport() throws Exception {
        generateReport("damaged_movies.jrxml", null, "damaged_movies.pdf");
    }

    /**
     * Genera un informe que lista las películas con más de una copia disponible.
     *
     * @throws Exception Si ocurre un error durante la generación del informe.
     */

    public static void generateMoviesWithMultipleCopiesReport() throws Exception {
        generateReport("multiple_copies.jrxml", null, "multiple_copies.pdf");
    }

    /**
     * Genera un informe que muestra la información detallada de una copia específica.
     *
     * @param idCopia El ID de la copia específica para la cual se generará el informe.
     * @throws Exception Si ocurre un error durante la generación del informe.
     */

    public static void generateSpecificCopyReport(int idCopia) throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id_copia", idCopia); // Parámetro dinámico
        generateReport("specific_copy.jrxml", parameters, "specific_copy_" + idCopia + ".pdf");
    }

    /**
     * Método genérico para generar informes en base a un archivo JRXML, parámetros
     * y un archivo de salida.
     *
     * @param reportFile El nombre del archivo JRXML que contiene el diseño del informe.
     * @param parameters Los parámetros dinámicos necesarios para el informe (puede ser null).
     * @param outputFile La ruta del archivo de salida donde se exportará el informe en formato PDF.
     * @throws Exception Si ocurre un error durante la generación o exportación del informe.
     */

    private static void generateReport(String reportFile, Map<String, Object> parameters, String outputFile) throws Exception {
        // Cargar el archivo JRXML
        String jrxmlPath = "src/main/resources/reports/" + reportFile;
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);

        // Conexión a la base de datos
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Llenar el informe con los datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Exportar el informe a un archivo PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
            System.out.println("Informe generado: " + outputFile);
        }
    }
}
