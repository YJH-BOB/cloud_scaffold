package com.wdt.minio.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectItem {
    private String objectName;
    private Long size;
    private Boolean isDir ;
    private String bucketName ;
}
