package qna.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.deletehistory.DeleteHistoryRepository;
import qna.exception.NotFoundException;
import qna.question.Question;
import qna.question.QuestionRepository;
import qna.user.User;

@Service
public class QnaService {
    private static final Logger log = LoggerFactory.getLogger(QnaService.class);

    private QuestionRepository questionRepository;
    private DeleteHistoryRepository deleteHistoryRepository;

    public QnaService(QuestionRepository questionRepository, DeleteHistoryRepository deleteHistoryRepository) {
        this.questionRepository = questionRepository;
        this.deleteHistoryRepository = deleteHistoryRepository;
    }

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Question question) {
        deleteHistoryRepository.saveAll(question.delete(loginUser));
    }
}
