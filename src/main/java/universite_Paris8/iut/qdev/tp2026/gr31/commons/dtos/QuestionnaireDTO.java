package universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.enums.LangueEnum;

import java.util.ArrayList;

public class QuestionnaireDTO {
private ArrayList<QuestionDTO> listeQuestion;
private int idQuestionnaire;
private String libelleQuestionnaire;
private int numeroQuActuelle;
private LangueEnum langueEnum;
private static int idQuestionnaireActuel = 0;


public QuestionnaireDTO(String libelleQuestionnaire, LangueEnum langueEnum){
        this.idQuestionnaire = idQuestionnaireActuel;
        idQuestionnaireActuel +=1;
        this.langueEnum = langueEnum;
        this.libelleQuestionnaire = libelleQuestionnaire;

}

        public void setLangue(LangueEnum langueEnum) {
                this.langueEnum = langueEnum;
        }

        public void setLibelleQuestionnaire(String libelleQuestionnaire) {
                this.libelleQuestionnaire = libelleQuestionnaire;
        }

        public void setNumeroQuActuelle(int numeroQuActuelle) {
                this.numeroQuActuelle = numeroQuActuelle;
        }

        public int getIdQuestionnaire() {
                return idQuestionnaire;
        }

        public LangueEnum getLangue() {
                return langueEnum;
        }

        public ArrayList<QuestionDTO> getListeQuestion() {
                return listeQuestion;
        }

        public int getNumeroQuActuelle() {
                return numeroQuActuelle;
        }

        public String getLibelleQuestionnaire() {
                return libelleQuestionnaire;
        }

        public void ajouterQuestion(QuestionDTO question){
        listeQuestion.add(question);
        }

        public int getTailleQuestionnaire(){
        return listeQuestion.size();
        }
}
