package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.service.GeneticService;
import com.edu.networkexperimentation.model.domain.Question;
import com.edu.networkexperimentation.model.domain.Times;
import com.edu.networkexperimentation.mapper.QuestionMapper;
import com.edu.networkexperimentation.mapper.TimesMapper;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeneticImpl extends MppServiceImpl<QuestionMapper, Question> implements GeneticService {
    private long stuid;
    private double T0;
    private double alpha = 0.98;
    private double diff;
    private double dist;
    private int loops = 2000;
    private int chapters = 3;
    private int questionTypes = 3;
    private int questionsnums = 10;
    private List<Question> paper;
    private double currennt_fitness;
    private double[] interest;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private TimesMapper timesMapper;
    //获取初始解 proportion(i) 为i章节所占比例; nums(i) 为i题型数量
    public void init_paper(double[] proportion,int[] nums){
        //计算题量
        questionsnums = 0;
        for(int i = 0; i < chapters; i++){
            questionsnums += nums[i];
        }
        //设置章节轮盘
        double [] q = new double[chapters];
        for(int i = 0; i < chapters; i++){
            if(i == 0){
                q[i] = proportion[i];
            }else{
                q[i] = q[i - 1] + proportion[i];
            }
        }
        for(int j = 0; j < questionTypes; j++){
            int pre_index = -1;
            for(int k = 0; k < nums[j]; k++){
                SecureRandom secureRandom = new SecureRandom();
                //随机选择章节
                double p = secureRandom.nextDouble();
                int choose_chapter = 1;
                for(int h = chapters - 1 - 1; h >= 0; h--){
                    if(p > q[h]){
                        choose_chapter = h + 1 + 1;
                        break;
                    }
                }
                //首先选择出难度和区分度合适的题型
                List<Question> questionsList = questionMapper.selectList(new QueryWrapper<Question>()
                        .eq("type", j)
                        .eq("chapter",choose_chapter)
                        .le("difficulty", diff)
                        .le("distinguish",dist));
                if(questionsList == null){
                    throw new RuntimeException("类型为"+j+"的题目数量过少，生成试卷失败");
                }
                //随机选择题目
                int randomInRange = secureRandom.nextInt(questionsList.size());
                //题目不重复且章节符合则选择该题目
                int backtimes = 20;
                while(pre_index == randomInRange){
                    if(backtimes == 0){
                        throw new RuntimeException("类型为"+j+"的题目数量过少，生成试卷失败");
                    }
                    randomInRange = secureRandom.nextInt(questionsList.size());
                    backtimes--;
                }
                if(pre_index != randomInRange){
                    pre_index = randomInRange;
                    paper.add(questionsList.get(randomInRange));
                }
            }
        }
    }

    //计算试卷的适应度 diff和dist 分别为用户指定的难度和区分度
    public double get_Fitness(List<Question> paper){

        double sums = 0,fitness = 0,interest = 0;
        for(int i = 0; i < paper.size(); i++){
            sums += paper.get(i).getDifficulty();
            interest += this.interest[paper.get(i).getChapter()];
        }
        fitness = sums * 0.5 / questionsnums - diff + interest * 0.5;
        return fitness;
    }
    //选择
    public void select(){

        List<Question> new_paper;

        double nfitness = 0;

        for(int l = 0; l < loops; l++){
            currennt_fitness = get_Fitness(paper);
            new_paper = change(paper);
            nfitness = get_Fitness(new_paper);

            double p = nfitness - currennt_fitness;

            if(p > 0){
                paper = new_paper;
            }else{
                //计算接受概率
                SecureRandom secureRandom = new SecureRandom();
                double q = secureRandom.nextDouble();
                if(Math.exp(-p/T0) > q){
                    paper = new_paper;
                }
            }
            if(T0 <= Math.exp(-8)){
                break;
            }
            T0 = T0 * alpha;
        }
    }
    //随机替换
    public List<Question> change(List<Question> paper){
        SecureRandom secureRandom = new SecureRandom();
        //生成替换位置
        int index = secureRandom.nextInt(questionsnums);
        //获取替换类型
        Question q = paper.get(index);
        //获取可能的替换，限定题型
        List<Question> questionsList = questionMapper.selectList(new QueryWrapper<Question>()
                .eq("type", q.getType()));
        int new_index = secureRandom.nextInt(questionsList.size());
        //替换
        paper.set(index,questionsList.get(new_index));
        return paper;
    }
    @Override
    public List<Question> get_Paper(int stuid, int difficulty,int distinguish,double[] proportion,int[] nums){
        //初始化卷子
        paper = new ArrayList<>();
        //初始化温度
        T0 = Math.exp(10);
        //初始化参数
        interest = new double[chapters];
        for(int i = 0; i < chapters; i++) {
            List<Times> timesList = timesMapper.selectList(new QueryWrapper<Times>()
                    .eq("stuid", stuid)
                    .eq("chapterid", i));
            interest[i] = 0;
            for(int j = 0; j < timesList.size(); j++){
                interest[i] += timesList.get(j).getScan() + timesList.get(j).getTime_length();
            }
        }
        this.stuid = stuid;
        this.diff = difficulty;
        this.dist = distinguish;
        //初始化解
        init_paper(proportion,nums);
        select();
        return paper;
    }
}
