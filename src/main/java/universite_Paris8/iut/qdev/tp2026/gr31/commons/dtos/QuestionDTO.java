package universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos;

public class QuestionDTO {
        private int id;
        private int difficulte;
        private String libelle;
        private String reponse;
        private int taux_reussite;
        private String reference;
        private static int idActuel=0;

        public QuestionDTO(int difficulte, String libelle, String reponse, String reference){
                this.id = idActuel;
                this.idActuel ++;
                this.libelle = libelle;
                this.difficulte = difficulte;
                this.reponse = reponse;
                this.reference = reference;
        }

        public void setDifficulte(int difficulte) {
                this.difficulte = difficulte;
        }

        public void setLibelle(String libelle) {
                this.libelle = libelle;
        }

        public void setReponse(String reponse) {
                this.reponse = reponse;
        }

        public void setTaux_reussite(int taux_reussite) {
                this.taux_reussite = taux_reussite;
        }

        public void setReference(String reference) {
                this.reference = reference;
        }

        public int getId() {
                return id;
        }

        public int getDifficulte() {
                return difficulte;
        }

        public int getTaux_reussite() {
                return taux_reussite;
        }

        public String getLibelle() {
                return libelle;
        }

        public String getReference() {
                return reference;
        }

        public String getReponse() {
                return reponse;
        }
}
