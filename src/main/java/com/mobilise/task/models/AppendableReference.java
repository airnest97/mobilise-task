package com.mobilise.task.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import static com.mobilise.task.utils.Constants.APPENDABLE_SEPARATOR;

@Getter
@Setter
@MappedSuperclass
public class AppendableReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(length = 20, nullable = false, updatable = false, unique = true)
    private String reference;

    @PrePersist
    public void appendReference() {
        if (!StringUtils.hasText(this.reference)) {
            this.reference = RandomStringUtils.randomAlphanumeric(6, 10);
        }
    }

    public String getReference() {
        return String.format("%s%s%s", this.id, APPENDABLE_SEPARATOR, this.reference);
    }

}
