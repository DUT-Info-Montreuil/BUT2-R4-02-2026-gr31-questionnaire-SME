package universite_Paris8.iut.qdev.tp2026.gr31.services.impls;

import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionDTO;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.dtos.QuestionnaireDTO;
import universite_Paris8.iut.qdev.tp2026.gr31.commons.enums.LangueEnum;
import universite_Paris8.iut.qdev.tp2026.gr31.services.interfaces.Iquestionnaire;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.AccesRefuseException;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.FichierIncoherentException;
import universite_Paris8.iut.qdev.tp2026.gr31.utils.exceptions.FichierIntrouvableException;

import java.io.*;
import java.util.*;

public class ServiceQuestionnaireImplementation implements Iquestionnaire {

    private static final String SEPARATEUR  = ";";
    private static final int    NB_COLONNES = 9;

    private static final int COL_ID_QUESTIONNAIRE      = 0;
    private static final int COL_LIBELLE_QUESTIONNAIRE = 1;
    private static final int COL_LANGUE                = 3;
    private static final int COL_QUESTION              = 4;
    private static final int COL_REPONSE               = 5;
    private static final int COL_NIV_DIFFICULTE        = 6;
    private static final int COL_EXPLICATION           = 7;
    private static final int COL_SOURCE                = 8;

    private ArrayList<QuestionnaireDTO> listeQuestionnaires = new ArrayList<>();


    @Override
    public ArrayList<QuestionnaireDTO> fournirListeQuestionnaire() {
        return listeQuestionnaires;
    }


    @Override
    public boolean chargerFichier(File file) throws FichierIntrouvableException, AccesRefuseException, FichierIncoherentException {

        if (file == null || !file.exists()) {
            throw new FichierIntrouvableException();
        }

        if (!file.canRead()) {
            throw new AccesRefuseException();
        }

        Map<Integer, QuestionnaireDTO> questionnairesMap = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            reader.readLine();
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                if (ligne.trim().isEmpty()) continue;
                String[] colonnes = ligne.split(SEPARATEUR, -1);

                if (colonnes.length < NB_COLONNES) {
                    System.out.println("e");
                    throw new FichierIncoherentException();
                }

                try {
                    int    idQuestionnaire      = Integer.parseInt(colonnes[COL_ID_QUESTIONNAIRE].trim());
                    String libelleQuestionnaire = colonnes[COL_LIBELLE_QUESTIONNAIRE].trim();
                    LangueEnum langue = LangueEnum.valueOf(colonnes[COL_LANGUE].trim());
                    String texteQuestion        = colonnes[COL_QUESTION].trim();
                    String reponse              = colonnes[COL_REPONSE].trim();
                    int    difficulte           = Integer.parseInt(colonnes[COL_NIV_DIFFICULTE].trim());
                    String explication          = colonnes[COL_EXPLICATION].trim();
                    String source               = colonnes[COL_SOURCE].trim();

                    if (difficulte < 1 || difficulte > 3) {
                        throw new FichierIncoherentException();
                    }

                    questionnairesMap.putIfAbsent(
                            idQuestionnaire,
                            new QuestionnaireDTO(libelleQuestionnaire, idQuestionnaire, langue)
                    );

                    questionnairesMap.get(idQuestionnaire).ajouterQuestion(
                            new QuestionDTO(difficulte, texteQuestion, reponse, explication, source)
                    );

                } catch (NumberFormatException e) {
                    throw new FichierIncoherentException();
                }
            }

        } catch (IOException e) {
            throw new AccesRefuseException();
        }

        listeQuestionnaires = new ArrayList<>(questionnairesMap.values());
        return true;
    }


    @Override
    public QuestionnaireDTO fournirUnQuestionnaire(int difficulte, LangueEnum langueEnum, String libelle) {
        for (QuestionnaireDTO questionnaire : listeQuestionnaires) {

            if (!questionnaire.getLibelleQuestionnaire().equalsIgnoreCase(libelle)) continue;

            if (langueEnum != null && questionnaire.getLangue() != langueEnum) continue;

            ArrayList<QuestionDTO> questionsFiltrees = new ArrayList<>();
            for (QuestionDTO question : questionnaire.getListeQuestion()) {
                if (question.getDifficulte() == difficulte) {
                    questionsFiltrees.add(question);
                }
            }

            if (!questionsFiltrees.isEmpty()) {
                QuestionnaireDTO resultat = new QuestionnaireDTO(
                        questionnaire.getLibelleQuestionnaire(),
                        questionnaire.getNumeroQuestionnaire(),
                        questionnaire.getLangue()
                );
                for (QuestionDTO q : questionsFiltrees) {
                    resultat.ajouterQuestion(q);
                }
                return resultat;
            }
        }
        return null;
    }

    @Override
    public void majStatQuestions() {
        // À compléter
        for (QuestionnaireDTO questionnaire : listeQuestionnaires) {
            for (QuestionDTO question : questionnaire.getListeQuestion()) {
            }
        }
    }


    @Override
    public QuestionDTO fournirStatQuestion() {
        QuestionDTO questionLaPlusDifficile = null;
        int tauxMin = Integer.MAX_VALUE;

        for (QuestionnaireDTO questionnaire : listeQuestionnaires) {
            for (QuestionDTO question : questionnaire.getListeQuestion()) {
                if (question.getTaux_reussite() < tauxMin) {
                    tauxMin = question.getTaux_reussite();
                    questionLaPlusDifficile = question;
                }
            }
        }
        return questionLaPlusDifficile;
    }
}