package qais.omari.document_access_control_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class DocumentAccessControlSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentAccessControlSystemApplication.class, args);
    }
}