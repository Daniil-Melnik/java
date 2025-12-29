import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RecorceTest {

    public static void readJsonFile() throws IOException {
        try (InputStream inputStream = RecorceTest.class.getClassLoader()
                .getResourceAsStream("recources/reco.json")) {

            if (inputStream != null) {
                String jsonContent = readFromInputStream(inputStream);

                System.out.println("=== JSON File ===");
                System.out.println(jsonContent);
                System.out.println();
            } else {
                System.out.println("JSON file not found!");
            }
        }
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
