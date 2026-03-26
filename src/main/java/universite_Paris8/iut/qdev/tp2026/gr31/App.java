package universite_Paris8.iut.qdev.tp2026.gr31;

import universite_Paris8.iut.qdev.tp2026.gr31.commons.enums.LangueEnum;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionnaireDTO;
import universite_Paris8.iut.qdev.tp2026.gr31.services.impls.ServiceQuestionnaireImplementation;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.AccesRefuseException;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.FichierIncoherentException;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.FichierIntrouvableException;

import java.io.File;

public class App {
    public static void main(String[] args) {
        ServiceQuestionnaireImplementation s = new ServiceQuestionnaireImplementation();

        try {
            // Chargement du fichier
            File fichier = new File("ressources/questionsQuizz_2025_V1.csv");
            s.chargerFichier(fichier);
            System.out.println("Fichier chargé avec succès !");
            System.out.println("Nombre de questionnaires : " + s.fournirListeQuestionnaire().size());

            // Récupération d'un questionnaire
            QuestionnaireDTO questionnaire = s.fournirUnQuestionnaire(1, LangueEnum.fr, "Sport");
            if (questionnaire != null) {
                System.out.println("Questionnaire trouvé : " + questionnaire);
            } else {
                System.out.println("Aucun questionnaire trouvé pour ces critères.");
            }

        } catch (FichierIntrouvableException e) {
            System.out.println("Erreur : fichier introuvable.");
        } catch (AccesRefuseException e) {
            System.out.println("Erreur : accès refusé au fichier.");
        } catch (FichierIncoherentException e) {
            System.out.println("Erreur : le fichier CSV est mal formaté.");
        }
    }
}
