package com.org.checkr.service;

import com.org.checkr.dto.response.QuestionnaireResponseDTO;
import com.org.checkr.entity.Questionnaire;
import com.org.checkr.entity.User;
import com.org.checkr.exception.NotFoundException;
import com.org.checkr.repository.QuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;

    @Autowired
    public QuestionnaireService(QuestionnaireRepository questionnaireRepository) {
        this.questionnaireRepository = questionnaireRepository;
    }

    public Questionnaire findQuestionnaireById(Long id) {
        Questionnaire questionnaire = questionnaireRepository.findById(id).orElse(null);
        if(questionnaire == null) {
            throw new NotFoundException("Questionnaire with ID " + id + " not found.");
        } else {
            return questionnaire;
        }
    }

    public List<Questionnaire> getAllQuestionnaires() {
        return questionnaireRepository.findAll();
    }

    public QuestionnaireResponseDTO mapQuestionnaireToDto(Questionnaire questionnaire) {
        return new QuestionnaireResponseDTO(
                questionnaire.getId(),
                questionnaire.getQuestion()
        );
    }

}
