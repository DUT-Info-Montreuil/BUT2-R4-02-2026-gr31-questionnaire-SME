package universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.enums.Langue;

import java.util.ArrayList;

public class QuestionnaireDTO {
private ArrayList<QuestionDTO> listeQuestion;
private int idQuestionnaire;
private String libelleQuestionnaire;
private int numeroQuActuelle;
private Langue langue;
private static int idQuestionnaireActuel = 0;


public QuestionnaireDTO(String libelleQuestionnaire, Langue langue){
        this.idQuestionnaire = idQuestionnaireActuel;
        idQuestionnaireActuel +=1;
        this.langue = langue;
        this.libelleQuestionnaire = libelleQuestionnaire;

}

        public void setLangue(Langue langue) {
                this.langue = langue;
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

        public Langue getLangue() {
                return langue;
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
