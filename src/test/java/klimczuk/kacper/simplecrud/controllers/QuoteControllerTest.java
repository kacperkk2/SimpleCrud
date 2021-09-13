package klimczuk.kacper.simplecrud.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import klimczuk.kacper.simplecrud.exceptions.QuoteNotFoundException;
import klimczuk.kacper.simplecrud.models.Quote;
import klimczuk.kacper.simplecrud.repositories.QuoteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(QuoteController.class)
class QuoteControllerTest {
    private static final long TEST_ID = 1;
    private static final String QUOTES_ENDPOINT = "/quotes/";
    private static Quote TEST_QUOTE;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${api.url.prefix}")
    private String urlPrefix;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteRepository quoteRepository;

    @BeforeAll
    private static void init() {
        TEST_QUOTE = new Quote(1L, "test", "test", "test");
    }

    @Test
    public void whenCollectionEmptyGetAllQuotesShouldReturnEmptyList() throws Exception {
        mockMvc.perform(get(urlPrefix + QUOTES_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void whenCollectionNotEmptyGetAllQuotesShouldReturnNotEmptyList() throws Exception {
        when(quoteRepository.findAll()).thenReturn(Arrays.asList(TEST_QUOTE, TEST_QUOTE));
        MvcResult result = mockMvc.perform(get(urlPrefix + QUOTES_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains(objectMapper.writeValueAsString(TEST_QUOTE)));
    }

    @Test
    public void whenThereIsNoSelectedQuoteGetQuoteByIdShouldThrowException() throws Exception {
        when(quoteRepository.findById(TEST_ID)).thenThrow(new QuoteNotFoundException(TEST_ID));
        mockMvc.perform(get(urlPrefix + QUOTES_ENDPOINT + TEST_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof QuoteNotFoundException));
    }

    @Test
    public void whenThereIsSelectedQuoteGetQuoteByIdShouldReturnSelectedQuote() throws Exception {
    when(quoteRepository.findById(anyLong())).thenReturn(java.util.Optional.of(TEST_QUOTE));
        MvcResult result = mockMvc.perform(get(urlPrefix + QUOTES_ENDPOINT + TEST_ID))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals(content, objectMapper.writeValueAsString(TEST_QUOTE));
    }

    @Test
    public void whenAddingNewQuoteAddQuoteShouldReturnAddedQuote() throws Exception {
        when(quoteRepository.save(Mockito.any(Quote.class))).thenReturn(TEST_QUOTE);
        MvcResult result = mockMvc.perform(post(urlPrefix + QUOTES_ENDPOINT)
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(TEST_QUOTE)))
                .andExpect(status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals(content, objectMapper.writeValueAsString(TEST_QUOTE));
    }

    @Test
    public void whenReplacingQuoteReplaceQuoteShouldReplaceAndReturnQuote() throws Exception {
        when(quoteRepository.save(Mockito.any(Quote.class))).thenReturn(TEST_QUOTE);
        MvcResult result = mockMvc.perform(put(urlPrefix + QUOTES_ENDPOINT + TEST_ID)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(TEST_QUOTE)))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals(content, objectMapper.writeValueAsString(TEST_QUOTE));
    }

    @Test
    public void whenDeletingNotExistingQuoteDeleteQuoteShouldThrowException() throws Exception {
        when(quoteRepository.existsById(TEST_ID)).thenReturn(false);
        mockMvc.perform(delete(urlPrefix + QUOTES_ENDPOINT + TEST_ID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof QuoteNotFoundException));
    }

    @Test
    public void whenDeletingExistingQuoteDeleteQuoteShouldDeleteQuote() throws Exception {
        when(quoteRepository.existsById(TEST_ID)).thenReturn(true);
        mockMvc.perform(delete(urlPrefix + QUOTES_ENDPOINT + TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        verify(quoteRepository).deleteById(TEST_ID);
    }
}