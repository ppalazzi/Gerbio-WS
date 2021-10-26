package com.palazzisoft.gerbio.integrator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Error")
public class IntegratorError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avatar")
    @Enumerated(EnumType.STRING)
    private ErrorType type;

    private LocalDateTime timestamp;
    private String errorMessage;
    private String className;

    @Column(columnDefinition = "TEXT")
    private String stackTrace;


    public enum ErrorType {
        BRAND, PRODUCT, ORDER, CATEGORY, IMPORT;
    }
}