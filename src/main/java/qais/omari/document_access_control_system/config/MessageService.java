package qais.omari.document_access_control_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String key, Locale locale, Object... args) {
        try {
            String message = messageSource.getMessage(key, args, locale);
            System.out.println("DEBUG: Found message for key '" + key + "' in locale '" + locale + "': " + message);
            return message;
        } catch (NoSuchMessageException e) {
            System.out.println("DEBUG: No message found for key '" + key + "' in locale '" + locale + "'");
            if (!locale.equals(Locale.ENGLISH)) {
                try {
                    String message = messageSource.getMessage(key, args, Locale.ENGLISH);
                    System.out.println("DEBUG: Found fallback message for key '" + key + "' in English: " + message);
                    return message;
                } catch (NoSuchMessageException e2) {
                    System.out.println("DEBUG: No fallback message found for key '" + key + "' in English either");
                }
            }

            return "Missing translation for: " + key;
        }
    }

    public String getMessage(String key, String acceptLanguage, Object... args) {
        Locale locale = parseLocale(acceptLanguage);
        System.out.println("DEBUG: Parsed locale '" + locale + "' from Accept-Language: '" + acceptLanguage + "'");
        return getMessage(key, locale, args);
    }

    private Locale parseLocale(String acceptLanguage) {
        if (acceptLanguage == null || acceptLanguage.isEmpty()) {
            return Locale.ENGLISH;
        }
        
        String language = acceptLanguage.toLowerCase();
        if (language.startsWith("ar")) {
            return new Locale("ar");
        }
        
        return Locale.ENGLISH;
    }
}