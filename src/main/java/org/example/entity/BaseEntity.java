package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.Date;

@MappedSuperclass
@FilterDef(name = "softDeleteFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
public abstract class BaseEntity {

    @Column(name = "deleted_at",updatable = false)
    private Date deletedAt;
}
