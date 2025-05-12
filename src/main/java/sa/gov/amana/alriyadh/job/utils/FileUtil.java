package sa.gov.amana.alriyadh.job.utils;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Base64;

public class FileUtil {

    public static String getSystemSeparator() {
        return File.separator;
    }

    public static boolean createFile(String filePath, String base64) {
        boolean created = false;
        OutputStream outputStream = null;
        try {
            File targetFile = new File(filePath);
            if (!targetFile.getParentFile().exists()) {
                created = targetFile.getParentFile().mkdirs();
            }
            if (!targetFile.exists()) {
                System.out.println("file not exsists");
                targetFile.createNewFile();
                byte[] data = DatatypeConverter.parseBase64Binary(base64);
                outputStream = new BufferedOutputStream(new FileOutputStream(targetFile));
                outputStream.write(data);
                outputStream.close();
                created = true;
            } else {
                System.err.println("file exsists");
                byte[] data = DatatypeConverter.parseBase64Binary(base64);
                outputStream = new BufferedOutputStream(new FileOutputStream(targetFile));
                outputStream.write(data);
                created = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return created;
    }

    public static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.getEncoder().encodeToString(bytes).toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encodedfile;
    }

    public static String convertPdfUrlToBase64(String pdfUrl) {
        String base64EncodedPdf = "";
        try {
            URL url = new URL(pdfUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            try (InputStream inputStream = connection.getInputStream();
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                // Read the input stream into the byte array output stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                // Convert the ByteArrayOutputStream to byte array
                byte[] pdfBytes = byteArrayOutputStream.toByteArray();
                // Encode the byte array to Base64
                base64EncodedPdf = Base64.getEncoder().encodeToString(pdfBytes);
//				System.out.println(base64EncodedPdf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64EncodedPdf;
    }

    public static String generateCurrentDatePath(Boolean isFileName) {

        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int date = localDate.getDayOfMonth();

        if (isFileName) {
            return "_" + year + "_" + month + "_" + date;
        } else {
            return year + "/" + month + "/" + date + "/";
        }

    }

    public static boolean createFilePath(String filePath) {
        File targetFile = new File(filePath);

        // If filePath is a directory, ensure it exists
        if (targetFile.isDirectory()) {
            if (!targetFile.exists()) {
                return targetFile.mkdirs(); // Create directory if it doesn't exist
            }
            return true;
        }

        // If filePath is a file, ensure parent directories exist
        File parentDir = targetFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                System.err.println("Failed to create directories: " + parentDir.getAbsolutePath());
                return false;
            }
        }

        // Ensure the file exists
        try {
            if (!targetFile.exists()) {
                boolean fileCreated = targetFile.createNewFile();
                if (fileCreated) {
                    System.out.println("File created: " + targetFile.getAbsolutePath());
                }
                return fileCreated;
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error creating file: " + targetFile.getAbsolutePath());
            e.printStackTrace();
            return false;
        }
    }


}
