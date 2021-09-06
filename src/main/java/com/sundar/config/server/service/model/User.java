package com.sundar.config.server.service.model;

import lombok.*;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;
}
