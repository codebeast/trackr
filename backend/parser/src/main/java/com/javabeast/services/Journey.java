package com.javabeast.services;


import com.javabeast.TrackerMessage;
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
public class Journey {
    private List<TrackerMessage> trackerMessageList = new ArrayList<>();
}
