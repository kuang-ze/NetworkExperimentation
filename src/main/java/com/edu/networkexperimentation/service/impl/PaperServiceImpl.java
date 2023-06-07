package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.contant.PaperConstant;
import com.edu.networkexperimentation.mapper.AnswerMapper;
import com.edu.networkexperimentation.mapper.QuestionMapper;
import com.edu.networkexperimentation.model.domain.Answer;
import com.edu.networkexperimentation.model.domain.Paper;
import com.edu.networkexperimentation.model.domain.Question;
import com.edu.networkexperimentation.model.request.RequestAnswer;
import com.edu.networkexperimentation.model.request.RequestPaper;
import com.edu.networkexperimentation.model.response.ResponseHistoryAnswer;
import com.edu.networkexperimentation.model.response.ResponseHistoryPaper;
import com.edu.networkexperimentation.model.response.ResponsePaper;
import com.edu.networkexperimentation.service.PaperService;
import com.edu.networkexperimentation.mapper.PaperMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 29764
 * @description 针对表【paper】的数据库操作Service实现
 * @createDate 2023-06-05 20:47:20
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper>
        implements PaperService {

    @Resource
    private AnswerMapper answerMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public ResponsePaper preparePaper(RequestPaper requestPaper) {
        Paper paper = new Paper();
        paper.setTitle(requestPaper.getTitle());
        paper.setUserID(requestPaper.getUserID());
        this.save(paper);
        ResponsePaper responsePaper = new ResponsePaper(paper);

        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("type", PaperConstant.XZ_TYPE);
        wrapper.orderByAsc("RAND()").last("LIMIT " + requestPaper.getXzSum());
        List<Question> questions = questionMapper.selectList(wrapper);
        questions.forEach(responsePaper::addXzQuestion);

        wrapper.clear();
        wrapper.eq("type", PaperConstant.PD_TYPE);
        wrapper.orderByAsc("RAND()").last("LIMIT " + requestPaper.getPdSum());
        questions = questionMapper.selectList(wrapper);
        questions.forEach(responsePaper::addPdQuestion);

        wrapper.clear();
        wrapper.eq("type", PaperConstant.TK_TYPE);
        wrapper.orderByAsc("RAND()").last("LIMIT " + requestPaper.getTkSum());
        questions = questionMapper.selectList(wrapper);
        questions.forEach(responsePaper::addTkQuestion);

        responsePaper.getXzQuestions().forEach(item -> {
            Answer answer = new Answer();
            answer.setContent("");
            answer.setType(item.getType());
            answer.setPaperID(paper.getId());
            answer.setQuestionID(item.getId());
            answer.setIsTrue(0);
            answerMapper.insert(answer);
        });
        responsePaper.getPdQuestions().forEach(item -> {
            Answer answer = new Answer();
            answer.setContent("");
            answer.setType(item.getType());
            answer.setPaperID(paper.getId());
            answer.setQuestionID(item.getId());
            answer.setIsTrue(0);
            answerMapper.insert(answer);
        });
        responsePaper.getTkQuestions().forEach(item -> {
            Answer answer = new Answer();
            answer.setContent("");
            answer.setType(item.getType());
            answer.setPaperID(paper.getId());
            answer.setQuestionID(item.getId());
            answer.setIsTrue(0);
            answerMapper.insert(answer);
        });

        return responsePaper;
    }

    @Override
    public void submitPaper(List<RequestAnswer> requestAnswers) {
        RequestAnswer t = requestAnswers.get(0);
        Paper paper = this.getById(t.getPaperID());
        paper.setIsAnswered(1);
        this.updateById(paper);
        requestAnswers.forEach(item -> {
            QueryWrapper<Answer> wrapper = new QueryWrapper<>();
            wrapper.eq("questionID", item.getQuestionID());
            wrapper.eq("paperID", item.getPaperID());
            Answer answer = answerMapper.selectOne(wrapper);
            if (answer != null) {
                answer.setContent(item.getContent());
                Question question = questionMapper.selectById(item.getQuestionID());
                switch (question.getType()) {
                    case PaperConstant.XZ_TYPE:
                        answer.setIsTrue(isTrue(answer.getContent(), question.getCorrectChoice().toString()));
                        break;
                    case PaperConstant.PD_TYPE:
                        answer.setIsTrue(isTrue(answer.getContent(), question.getIsTrue().toString()));
                        break;
                    case PaperConstant.TK_TYPE:
                        answer.setIsTrue(isTrue(answer.getContent(), question.getCorrectContent()));
                        break;
                }
                answerMapper.updateById(answer);
            }
        });
    }

    @Override
    public ResponseHistoryPaper getPaperByID(Long id) {
        ResponseHistoryPaper paper = new ResponseHistoryPaper(this.getById(id));
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("paperID", paper.getId());
        List<Answer> answers = answerMapper.selectList(wrapper);

        answers.forEach(item -> {
            Question question = questionMapper.selectById(item.getQuestionID());
            switch (question.getType()) {
                case PaperConstant.XZ_TYPE:
                    paper.addXzAnswer(new ResponseHistoryAnswer(question, item));
                    break;
                case PaperConstant.PD_TYPE:
                    paper.addPdAnswer(new ResponseHistoryAnswer(question, item));
                    break;
                case PaperConstant.TK_TYPE:
                    paper.addTkAnswer(new ResponseHistoryAnswer(question, item));
                    break;
            }
        });

        return paper;
    }

    @Override
    public List<ResponseHistoryPaper> getAllPaperByUserID(Long id) {
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("userID", id);
        List<Paper> papers = this.list(wrapper);
        List<ResponseHistoryPaper> historyPapers = new ArrayList<>();
        papers.forEach(item -> {
            historyPapers.add(new ResponseHistoryPaper(item));
        });
        return historyPapers;
    }

    private int isTrue(String answer, String correctAnswer) {
        return correctAnswer.equals(answer) ? 1 : 0;
    }

}




