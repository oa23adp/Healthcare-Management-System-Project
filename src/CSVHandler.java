import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {

    private CSVHandler() {}

    //Creates an empty file if it doesn't exist (and ensures parent directories exist).
    public static void createFileIfNotExists(String filename) {
        try {
            Path path = Paths.get(filename);

            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            if (Files.notExists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + filename);
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readLines(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        Path path = Paths.get(filename);

        if (Files.notExists(path)) {
            return lines;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeLines(String filename, List<String> lines) {
        createFileIfNotExists(filename);

        Path path = Paths.get(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {
            for (String line : lines) {
                writer.write(line == null ? "" : line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename);
            e.printStackTrace();
        }
    }

    public static List<String> parseLine(String line) {
        List<String> fields = new ArrayList<>();
        if (line == null) return fields;

        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (inQuotes) {
                if (c == '"') {
                    // Escaped quote inside a quoted field: ""
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        sb.append('"');
                        i++;
                    } else {
                        inQuotes = false; // closing quote
                    }
                } else {
                    sb.append(c);
                }
            } else {
                if (c == ',') {
                    fields.add(sb.toString());
                    sb.setLength(0);
                } else if (c == '"') {
                    inQuotes = true; // opening quote
                } else {
                    sb.append(c);
                }
            }
        }

        fields.add(sb.toString());
        return fields;
    }

    public static List<String> parseLineStrict(String line) {
        List<String> fields = new ArrayList<>();
        if (line == null) return fields;

        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        sb.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    sb.append(c);
                }
            } else {
                if (c == ',') {
                    fields.add(sb.toString());
                    sb.setLength(0);
                } else if (c == '"') {
                    inQuotes = true;
                } else {
                    sb.append(c);
                }
            }
        }

        if (inQuotes) {
            throw new IllegalArgumentException("Malformed CSV line (unclosed quotes): " + line);
        }

        fields.add(sb.toString());
        return fields;
    }

    public static String toLine(List<String> fields) {
        StringBuilder out = new StringBuilder();
        if (fields == null) return "";

        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) out.append(',');

            String value = fields.get(i);
            if (value == null) value = "";

            boolean mustQuote =
                    value.contains(",") ||
                            value.contains("\"") ||
                            value.contains("\n") ||
                            value.contains("\r");

            if (mustQuote) {
                out.append('"');
                out.append(value.replace("\"", "\"\""));
                out.append('"');
            } else {
                out.append(value);
            }
        }
        return out.toString();
    }

    public static List<List<String>> readRecords(String filename) {
        ArrayList<String> lines = readLines(filename);
        List<List<String>> records = new ArrayList<>();
        for (String line : lines) {
            records.add(parseLine(line));
        }
        return records;
    }

    public static void writeRecords(String filename, List<List<String>> records) {
        List<String> lines = new ArrayList<>();
        if (records != null) {
            for (List<String> rec : records) {
                lines.add(toLine(rec));
            }
        }
        writeLines(filename, lines);
    }
}