package universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos;

public class QuestionDTO {
        private int id;
        private int difficulte;
        private String libelle;
        private String reponse;
        private String explication;
        private int taux_reussite;
        private String reference;
        private static int idActuel = 0;


        public QuestionDTO(int difficulte, String libelle, String reponse, String explication, String reference) {
                this.id = idActuel;
                idActuel++;
                this.difficulte = difficulte;
                this.libelle = libelle;
                this.reponse = reponse;
                this.explication = explication;
                this.reference = reference;
                this.taux_reussite = 0;
        }

        public void setDifficulte(int difficulte)      { this.difficulte = difficulte; }
        public void setLibelle(String libelle)          { this.libelle = libelle; }
        public void setReponse(String reponse)          { this.reponse = reponse; }
        public void setExplication(String explication)  { this.explication = explication; }
        public void setTaux_reussite(int taux_reussite){ this.taux_reussite = taux_reussite; }
        public void setReference(String reference)      { this.reference = reference; }

        public int    getId()            { return id; }
        public int    getDifficulte()    { return difficulte; }
        public int    getTaux_reussite() { return taux_reussite; }
        public String getLibelle()       { return libelle; }
        public String getReponse()       { return reponse; }
        public String getExplication()   { return explication; }
        public String getReference()     { return reference; }

        @Override
        public String toString() {
                return "QuestionDTO{" +
                        "id=" + id +
                        ", libelle='" + libelle + '\'' +
                        ", difficulte=" + difficulte +
                        ", taux_reussite=" + taux_reussite +
                        '}';
        }

}
