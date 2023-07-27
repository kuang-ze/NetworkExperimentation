package com.edu.networkexperimentation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.networkexperimentation.model.domain.Question;
import com.github.jeffreyning.mybatisplus.service.IMppService;

import java.util.List;

public interface GeneticService extends IMppService<Question> {
    List<Question> get_Paper(int stuid, int difficulty, int distinguish, double[] proportion, int[] nums);
}
