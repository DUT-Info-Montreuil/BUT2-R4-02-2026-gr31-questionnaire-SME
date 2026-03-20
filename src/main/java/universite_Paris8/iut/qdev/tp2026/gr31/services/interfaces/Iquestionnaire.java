package universite_Paris8.iut.qdev.tp2026.gr31.services.interfaces;

import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionDTO;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionnaireDTO;

import java.util.ArrayList;

public interface Iquestionnaire {
        public ArrayList<QuestionnaireDTO> fournirListeQuestionnaire ();
        public void chargerFichier();
        public QuestionnaireDTO fournirUnQuestionnaire();
        public void majStatQuestions();
        public QuestionDTO fournirStatQuestion();

}
