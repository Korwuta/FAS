package com.example.FAS.mapper;

import com.example.FAS.dto.response.NavigationResponse;
import com.example.FAS.model.Navigation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NavigationMapper {
    public NavigationResponse toNavigationResponse(Navigation navigation){
        return NavigationResponse.builder()
                .id(navigation.getId())
                .displayName(navigation.getDisplayName())
                .icon(navigation.getIcon())
                .url(navigation.getUrl())
                .build();
    }
    public List<NavigationResponse> toNavigationResponseList(List<Navigation> navigations){
        List<NavigationResponse> navigationResponse = new ArrayList<>();
        navigations = navigations.stream()
                .sorted((a,b)-> a.getLinkPosition() - b.getLinkPosition()).toList();
        Map<Navigation,NavigationResponse> navMap = new HashMap<>();
        for (Navigation navigation: navigations){
            if (navMap.containsKey(navigation.getParentNav())){
                navMap.get(navigation.getParentNav()).getChildLinks()
                        .add(toNavigationResponse(navigation));
            }
            navMap.put(navigation,toNavigationResponse(navigation));
            if (navigation.getParentNav() == null){
                navigationResponse.add(navMap.get(navigation));
            }
        }
        return navigationResponse;
    }
}
