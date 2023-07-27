package com.edu.networkexperimentation.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPaperGenetic {
    private int stuid;
    private String title;
    private int difficulty;
    private int distinguish;
    private double[] proportion;
    private int[] nums;
}
