package com.example.FAS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NavigationResponse {
    private Long id;
    private String displayName;
    private String url;
    private String icon;
    @Builder.Default
    private List<NavigationResponse> childLinks = new ArrayList<>();
}
