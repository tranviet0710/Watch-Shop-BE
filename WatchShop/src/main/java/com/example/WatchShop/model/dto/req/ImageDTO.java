package com.example.WatchShop.model.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ImageDTO {
    private long id;
    private String source;
    private Date createDate;
    private Date updateDate;
}
