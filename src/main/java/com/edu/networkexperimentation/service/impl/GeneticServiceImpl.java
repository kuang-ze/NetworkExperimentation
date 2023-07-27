package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.mapper.QuestionMapper;
import com.edu.networkexperimentation.mapper.TimesMapper;
import com.edu.networkexperimentation.model.domain.Question;
import com.edu.networkexperimentation.model.domain.Times;
import com.edu.networkexperimentation.service.GeneticService;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class GeneticServiceImpl extends MppServiceImpl<QuestionMapper, Question>
        implements GeneticService {
    private int stuid;
    private int loops = 50;
    private int chapters = 3;
    private int questionTypes = 3;
    private int papernums = 6;//种群大小，即随机选择几套题来构建初始种群进行迭代

    private int questionsnums = 10;
    private HashMap<Integer, List<Question>> papers = new HashMap<Integer, List<Question>>();

    private double[] y = new double[papernums];
    private double[] difficulty = new double[papernums];
    private double[] distinguish = new double[papernums];

    private double[] interest = new double[papernums];

    private double[] fitness = new double[papernums];
    @Resource
    private QuestionMapper questionsMapper;

    @Resource
    private TimesMapper timesMapper;

    //获得初始种群 proportion(i) 为i章节所占比例; nums(i) 为i题型数量
    public void init_population(int difficulty, int distinguish, double[] proportion, int[] nums) {
        //计算题量
        questionsnums = 0;
        for (int i = 0; i < chapters; i++) {
            questionsnums += nums[i];
        }
        //设置章节轮盘
        double[] q = new double[chapters];
        for (int i = 0; i < chapters; i++) {
            if (i == 0) {
                q[i] = proportion[i];
            } else {
                q[i] = q[i - 1] + proportion[i];
            }
        }
        for (int i = 0; i < papernums; i++) {
            for (int j = 0; j < questionTypes; j++) {
                int pre_index = -1;
                for (int k = 0; k < nums[j]; k++) {
                    SecureRandom secureRandom = new SecureRandom();
                    //随机选择章节
                    double p = secureRandom.nextDouble();
                    int choose_chapter = 1;
                    for (int h = chapters - 1 - 1; h >= 0; h--) {
                        if (p > q[h]) {
                            choose_chapter = h + 1 + 1;
                            break;
                        }
                    }
                    //首先选择出难度和区分度合适的题型
                    List<Question> questionsList = questionsMapper.selectList(new QueryWrapper<Question>()
                            .eq("type", j)
                            .eq("chapter", choose_chapter)
                            .le("difficulty", difficulty)
                            .le("distinguish", distinguish));
                    if (questionsList == null) {
                        throw new RuntimeException("类型为" + j + "的题目数量过少，生成试卷失败");
                    }
                    //随机选择题目
                    int randomInRange = secureRandom.nextInt(questionsList.size());
                    //题目不重复且章节符合则选择该题目
                    int backtimes = 20;
                    while (pre_index == randomInRange) {
                        if (backtimes == 0) {
                            throw new RuntimeException("类型为" + j + "的题目数量过少，生成试卷失败");
                        }
                        randomInRange = secureRandom.nextInt(questionsList.size());
                        backtimes--;
                    }
                    if (pre_index != randomInRange) {
                        pre_index = randomInRange;
                        if (papers.get(i) == null) {
                            papers.put(i, new ArrayList<Question>() {
                            });
                        }
                        papers.get(i).add(questionsList.get(randomInRange));
                    }
                }
            }
        }
    }

    //计算适应度 diff和dist 分别为用户指定的难度和区分度
    public void get_Fitness(double diff, double dist) {
        //获取用户记录表

        //计算误差 y(i) = difficulty(i) * 0.6 + distinguish(i) * 0.4
        double sums = 0;

        for (int i = 0; i < papernums; i++) {
            //计算误差
            for (int j = 0; j < papers.get(i).size(); j++) {
                Question question = papers.get(i).get(j);
                Times times = new Times();
                times.setStuid(stuid);
                times.setChapterid(question.getChapter());
                times.setInterid(question.getInter());

                difficulty[i] += Math.abs(question.getDifficulty() - diff);
                distinguish[i] += Math.abs(question.getDistinguish() - dist);

//                log.error("times变化前:\t" + times);
//                log.error("question:\t" +question);
                times = timesMapper.selectByMultiId(times);
//                log.info("times:\t" + times);
                //若用户曾经浏览过相关知识点
                if (times != null) {
                    interest[i] += times.getScan() + times.getTime_length();//interest(i) = 点击次数 + 浏览时间
                }
            }
            y[i] = difficulty[i] * 0.6 + distinguish[i] * 0.4;
            sums += y[i];
        }
        //计算适应度
        for (int i = 0; i < papernums; i++) {
            fitness[i] = (1 - y[i] / sums) * 0.5 + interest[i] * 0.5;
        }
    }

    //选择 运用轮盘赌选择优选出下一代
    public void select() {
        HashMap<Integer, List<Question>> new_papers = new HashMap<Integer, List<Question>>();
        double[] s = new double[papernums]; //存储i套试题的选择概率
        double[] q = new double[papernums]; //存储i套试题的累计概率
        double fitnesses = 0;
        //获取当前种群适应度和
        for (int i = 0; i < papernums; i++) {
            fitnesses += fitness[i];
        }
        //计算选择概率
        for (int i = 0; i < papernums; i++) {
            s[i] = fitness[i] / fitnesses;
        }
        //计算累计概率
        for (int i = 0; i < papernums; i++) {
            if (i == 0) {
                q[i] = s[i];
            } else {
                q[i] = q[i - 1] + s[i];
            }
        }
        //轮盘选择 生成新种群
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < papernums; i++) {
            double randomInRange = secureRandom.nextDouble();
            int choose = 0;
            for (int j = papernums - 1 - 1; j >= 0; j--) {
                if (randomInRange > q[j]) {
                    choose = j + 1;
                    break;
                }
            }
            new_papers.put(i, papers.get(choose));
        }
        papers = new_papers;
    }

    //随机选择两个个体单点交叉 交叉概率为0.7
    public void onePoint_CrossOver() {
        double alpha = 0.7;
        SecureRandom secureRandom = new SecureRandom();
        int x = secureRandom.nextInt(papernums);
        int y = secureRandom.nextInt(papernums);
        int cross = secureRandom.nextInt(questionsnums);
        double p = secureRandom.nextDouble();

        if (p >= alpha) {
            List<Question> tempx = papers.get(x);
            List<Question> tempy = papers.get(y);
            Question temp;
            for (int i = 0; i < questionsnums; i++) {
                if (i >= cross) {
                    temp = tempx.get(i);
                    tempx.set(i, tempy.get(i));
                    tempy.set(i, temp);
                }
            }
            papers.put(x, tempx);
            papers.put(y, tempy);
        }
    }

    //变异 变异概率为0.001
    public void mutation() {
        double alpha = 0.001;
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < papernums; i++) {
            double p = secureRandom.nextDouble();
            if (p >= 1 - alpha) {
                //生成变异位置
                int index = secureRandom.nextInt(questionsnums);
                //获取变异类型
                Question q = papers.get(i).get(index);
                //获取可能的变异，限定题型
                List<Question> questionsList = questionsMapper.selectList(new QueryWrapper<Question>()
                        .eq("type", q.getType()));
                int new_index = secureRandom.nextInt(questionsList.size());
                //变异
                papers.get(i).set(index, questionsList.get(new_index));
            }
        }
    }

    @Override
    public List<Question> get_Paper(int stuid, int difficulty, int distinguish, double[] proportion, int[] nums) {
        //初始化用户
        this.stuid = stuid;
        //初始化种群
        init_population(difficulty, distinguish, proportion, nums);

        for (int i = 0; i < loops; i++) {
            get_Fitness(difficulty, distinguish);
            select();
            onePoint_CrossOver();
            mutation();
        }
        int index = 0;
        double max_fitness = 0;
        for (int i = 0; i < papernums; i++) {
            if (fitness[i] > max_fitness) {
                max_fitness = fitness[i];
                index = i;
            }
        }
//        if (index != -1) {
            return papers.get(index);
//        }
//        throw new BusinessException(ErrorCode.NULL_ERROR, "生成试卷失败");
    }

}

