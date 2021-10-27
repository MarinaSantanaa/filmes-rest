package br.com.filmes.spring.filmesrest.rest;

import br.com.filmes.spring.filmesrest.omdbclient.CsvFile;
import br.com.filmes.spring.filmesrest.omdbclient.MovieMinimalRestRepository;
import br.com.filmes.spring.filmesrest.omdbclient.ResultSearch;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchRestController {
    CsvFile fileCsv = new CsvFile();
    private final MovieMinimalRestRepository restRepository;

    public SearchRestController(MovieMinimalRestRepository restRepository) {
        this.restRepository = restRepository;
    }

    @GetMapping("/search")
    public ResultSearch search(@RequestParam String title) {
        ResultSearch resultSearch = new ResultSearch();
        CsvFile csv = new CsvFile();
        int b = csv.comparator(title);

        if (b != -1) {
            return csv.resultClass(b);
        } else if (resultSearch.getResultList() == null) {
            resultSearch = this.restRepository.search(title);
            String line = csv.listCsv(resultSearch, title);
            fileCsv.writeFile(line);
        }
        return resultSearch;
    }

}


