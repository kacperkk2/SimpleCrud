package klimczuk.kacper.simplecrud.controllers;

import klimczuk.kacper.simplecrud.exceptions.QuoteNotFoundException;
import klimczuk.kacper.simplecrud.models.Quote;
import klimczuk.kacper.simplecrud.repositories.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    @GetMapping("/quotes")
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    @GetMapping("/quotes/{id}")
    public Quote getQuoteById(@PathVariable long id) {
        return quoteRepository.findById(id).orElseThrow(() -> new QuoteNotFoundException(id));
    }

    @PostMapping("/quotes")
    public ResponseEntity<Quote> addQuote(@Valid @RequestBody Quote quote) {
        Quote savedQuote = quoteRepository.save(quote);
        return new ResponseEntity<>(savedQuote, HttpStatus.CREATED);
    }

    @PutMapping("/quotes/{id}")
    public Quote replaceQuote(@Valid @RequestBody Quote newQuote, @PathVariable long id) {
        return quoteRepository.findById(id)
                .map(quote -> {
                    quote.setContent(newQuote.getContent());
                    quote.setAuthorName(newQuote.getAuthorName());
                    quote.setAuthorSurname(newQuote.getAuthorSurname());
                    return quoteRepository.save(quote);
                }).orElseGet(() -> {
                    newQuote.setId(id);
                    return quoteRepository.save(newQuote);
                });
    }

    @DeleteMapping("/quotes/{id}")
    public void deleteQuote(@PathVariable long id) {
        if (!quoteRepository.existsById(id)) {
            throw new QuoteNotFoundException(id);
        }
        quoteRepository.deleteById(id);
    }
}
