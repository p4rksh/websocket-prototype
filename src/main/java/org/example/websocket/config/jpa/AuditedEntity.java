package org.example.websocket.config.jpa;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Getter
@Setter
public abstract class AuditedEntity implements Serializable {
    public static final String DEFAULT_AUDITOR_VALUE = "SYSTEM";

    @Builder.Default
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy = DEFAULT_AUDITOR_VALUE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @LastModifiedBy
    @Column(nullable = false)
    private String updatedBy = DEFAULT_AUDITOR_VALUE;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
