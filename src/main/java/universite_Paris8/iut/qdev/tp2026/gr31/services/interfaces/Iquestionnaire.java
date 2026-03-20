package universite_Paris8.iut.qdev.tp2026.gr31.services.interfaces;

import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionDTO;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionnaireDTO;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.enums.Langue;

import java.io.File;
import java.util.ArrayList;

public interface Iquestionnaire {
        public ArrayList<QuestionnaireDTO> fournirListeQuestionnaire ();
        public boolean chargerFichier(File file);
        public QuestionnaireDTO fournirUnQuestionnaire(int dificulte, Langue langue,String libelle);
        public void majStatQuestions();
        public QuestionDTO fournirStatQuestion();

}
