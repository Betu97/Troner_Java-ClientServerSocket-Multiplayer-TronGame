package model;

import model.exceptions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * classe dedicada per validar els camps a nivell de servidor
 * @author Team Troner (grupC3)
 * @version v_final
 */
public class FormValidator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{6,}$");
    private static final Pattern USERNAME_PATTERN =  Pattern.compile("^[A-Za-z0-9_-]");

    /**
     * Mètode per validar el username
     * @return booleà
     * @throws IncorrectUsernameFormatException
     */
    public static boolean validateUsername(String txt) throws IncorrectUsernameFormatException {
        Matcher matcher;
        try{
            matcher = USERNAME_PATTERN.matcher(txt);
        }
        catch (NumberFormatException e){
            throw new IncorrectUsernameFormatException("ERROR: El format del nom d'usuari és incorrecte");
        }

        return matcher.find();
    }

    /**
     * Mètode per validar el email
     * @return booleà
     * @throws IncorrectEmailFormatException
     */
    public static boolean validateEmail(String emailStr) throws IncorrectEmailFormatException {
        Matcher matcher;

        try{
            matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        }catch (NumberFormatException e){
           throw new IncorrectEmailFormatException("ERROR: El format de l'email és incorrecte");
        }
        return matcher.find();
    }

    /**
     * Mètode per validar la password
     * @return booleà
     * @throws IncorrectPasswordFormatException
     */
    public static boolean validatePassword(String password) throws IncorrectPasswordFormatException {
        Matcher matcherPass;
        try{
            matcherPass = PASSWORD_PATTERN.matcher(password);
        }catch (NumberFormatException e){
            throw new IncorrectPasswordFormatException("ERROR: La contrasenya ha de contenir almenys una majúscula, una minúscula, <un número i ha de ser de com a mínim 6 caràcters");
        }
        return matcherPass.matches();
    }
}