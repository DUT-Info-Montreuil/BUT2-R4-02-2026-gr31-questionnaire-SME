package universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos;

import universite_Paris8.iut.qdev.tp2026.gr31.commons.enums.LangueEnum;
import java.util.ArrayList;

public class QuestionnaireDTO {

        private ArrayList<QuestionDTO> listeQuestion;
        private int idQuestionnaire;
        private String libelleQuestionnaire;
        private int numeroQuestionnaire;
        private int numeroQuActuelle;
        private LangueEnum langueEnum;
        private static int idQuestionnaireActuel = 0;

        public QuestionnaireDTO(String libelleQuestionnaire, int numeroQuestionnaire, LangueEnum langueEnum) {
                this.idQuestionnaire = idQuestionnaireActuel;
                idQuestionnaireActuel++;
                this.libelleQuestionnaire = libelleQuestionnaire;
                this.numeroQuestionnaire = numeroQuestionnaire;
                this.langueEnum = langueEnum;
                this.listeQuestion = new ArrayList<>();
                this.numeroQuActuelle = 0;
        }
        public void setLangue(LangueEnum langueEnum){
                this.langueEnum = langueEnum;
        }
        public void setLibelleQuestionnaire(String libelleQuestionnaire){
                this.libelleQuestionnaire = libelleQuestionnaire;
        }
        public void setNumeroQuestionnaire(int numeroQuestionnaire){
                this.numeroQuestionnaire = numeroQuestionnaire;
        }
        public void setNumeroQuActuelle(int numeroQuActuelle){
                this.numeroQuActuelle = numeroQuActuelle;
        }

        public int getIdQuestionnaire(){
                return idQuestionnaire;
        }
        public String getLibelleQuestionnaire(){
                return libelleQuestionnaire;
        }
        public int getNumeroQuestionnaire(){
                return numeroQuestionnaire;
        }
        public int getNumeroQuActuelle(){
                return numeroQuActuelle;
        }
        public LangueEnum getLangue(){
                return langueEnum;
        }
        public ArrayList<QuestionDTO> getListeQuestion(){
                return listeQuestion;
        }

        public void ajouterQuestion(QuestionDTO question) {
                listeQuestion.add(question);
        }

        public QuestionDTO getQuestionActuelle(){
                if (numeroQuActuelle < listeQuestion.size()) {
                        return listeQuestion.get(numeroQuActuelle);
                }
                return null;
        }

        public boolean questionSuivante(){
                if (numeroQuActuelle < listeQuestion.size() - 1) {
                        numeroQuActuelle++;
                        return true;
                }
                return false;
        }

        public boolean estTermine(){
                return numeroQuActuelle >= listeQuestion.size();
        }

        public int getTailleQuestionnaire(){
                return listeQuestion.size();
        }

        @Override
        public String toString() {
                return "QuestionnaireDTO{" +
                        "idQuestionnaire=" + idQuestionnaire +
                        ", libelle='" + libelleQuestionnaire + '\'' +
                        ", langue=" + langueEnum +
                        ", nbQuestions=" + listeQuestion.size() +
                        ", questionActuelle=" + numeroQuActuelle +
                        '}';
        }
}