package com.zodiaczen.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base mode for changes dates.
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public abstract class DateAudit implements Serializable {
    //todo: add class documentation

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "updated_date", updatable = false)
    private LocalDateTime updatedDate;
}
