package de.assentis.skype.message;

import de.assentis.skype.domain.Document;
import de.assentis.skype.message.impl.MessageExtractorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageExtractorServiceTest {

    private static final String VALID_SAMPLE_MESSAGES = "/de/assentis/skype/message/valid_sample_messages.txt";

    @Test
    public void given_message_content_when_messages_are_valid_the_are_returned_as_Document() throws IOException {
        final MessageExtractorService messageExtractorService = new MessageExtractorServiceImpl();

        final InputStream inputStream = MessageExtractorServiceTest.class.getResourceAsStream(VALID_SAMPLE_MESSAGES);
        final List<Document> documents = messageExtractorService.extractFinalizedDocuments(inputStream);

        assertEquals(4, documents.size());
    }

}
