package br.com.filmes.spring.filmesrest.omdbclient;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFile {

    public Path getFilePath(String s) {
        URL url = this.getClass().getClassLoader().getResource("cache.csv");
        File file = new File(url.getFile());
        return Path.of(file.getPath());
    }

    public List<String> readFile() {
        try (Stream<String> file = Files.lines(getFilePath("cache.csv"))) {
            return file.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int comparator(String title) {
        try (Stream<String> file = Files.lines(getFilePath("cache.csv"))) {
            List<String> list = file.collect(Collectors.toList());
            for (int i = 0; i < list.size(); i++) {
                String[] line = list.get(i).split(";");
                if (line[0].equalsIgnoreCase(title)) {
                    return i;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return -1;
    }

    public ResultSearch resultClass(int b) {
        List<String> lineCsv = readFile();
        List<ResultSearch> resultSearches = new ArrayList<>();
        for (String line : lineCsv) {
            List<MovieMinimal> moviesCsv = new ArrayList<>();

            String[] split = line.split(";");

            String search = split[0];
            int total = Integer.parseInt(split[2]);
            boolean response = Boolean.valueOf(split[3]);


            String[] list = split[1].split("/");

            for (int i = 0; i < list.length; i += 3) {
                String imdbID = list[i];
                String title = list[i + 1];
                int year = Integer.parseInt(list[i + 2]);
                moviesCsv.add(new MovieMinimal(imdbID, title, year));
                resultSearches.add(new ResultSearch(moviesCsv, total, response));
            }

        }
        return resultSearches.get(b);
    }

    public void writeFile(String line) {
        Path filePath = getFilePath("cache.csv");
        try {
            Files.writeString(Path.of(String.valueOf(filePath)), line, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public String listCsv(ResultSearch resultSearch, String search) {
        String str = search + ";";
        List<MovieMinimal> movieMinimals = resultSearch.getResultList();
        if (movieMinimals == null) {
            return null;
        }
        for (int i = 0; i < movieMinimals.size(); i++) {
            MovieMinimal mm = movieMinimals.get(i);
            String imdbId = mm.getImdbId();
            String title = mm.getTitle();
            Integer year = mm.getYear();
            str += imdbId + "/";
            str += title + "/";
            str += year;
            if (i == movieMinimals.size() - 1) {
                str += ";";
            } else {
                str += "/";
            }
        }

        int total = resultSearch.getTotal();
        str += total + ";";
        str += "false\n";
        return str;
    }
}












