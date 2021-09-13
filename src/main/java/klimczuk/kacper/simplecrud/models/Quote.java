package klimczuk.kacper.simplecrud.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Quote content cannot be empty")
    private String content;
    @NotBlank(message = "Quote author name cannot be empty")
    private String authorName;
    @NotBlank(message = "Quote author surname cannot be empty")
    private String authorSurname;

    public Quote(Long id, String content, String authorName, String authorSurname) {
        this.id = id;
        this.authorName = authorName;
        this.authorSurname = authorSurname;
        this.content = content;
    }

    public Quote() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
