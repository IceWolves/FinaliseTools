package de.assentis.skype.message.impl;

import de.assentis.skype.domain.Document;
import de.assentis.skype.message.MessageExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MessageExtractorServiceImpl implements MessageExtractorService {

    @Override
    public List<Document> extractFinalizedDocuments(final InputStream messages) {
        try {
            final byte[] messagesComntent = new byte[messages.available()];
            messages.read(messagesComntent);

            messages.close();
            return convertMessagesContentToDocuments(splitMessages(messagesComntent));
        } catch (IOException e) {
            log.error("Error while trying to parse messages stream");
        }

        return Collections.emptyList();
    }

    private String[] splitMessages(final byte[] messagesContent) {
        if(messagesContent != null && messagesContent.length > 0) {
            return new String(messagesContent).split("Update:");
        }

        return new String[0];
    }

    private List<Document> convertMessagesContentToDocuments(final String[] messagesContent) {
        return Arrays.stream(messagesContent).filter(StringUtils::isNotBlank).map(mappDocument()).collect(Collectors.toList());
    }

    private Function<String, Document> mappDocument() {
        return messageContent -> {
            if (StringUtils.isBlank(messageContent)) {
                log.error("Found Skype message with no content but 'Update:'-keyword");
                return null;
            }

            final String[] messageParts = messageContent.startsWith("\n") ? messageContent.substring(1).split("\n")
                    : messageContent.split("\n");
            if (messageParts.length < 2) {
                log.error("Found Skype message that doesnt consist of min two rows!");
                return null;
            }

            final String row1 = messageParts[0];
            final String row2 = messageParts[1];

            if (StringUtils.isBlank(row1)) {
                log.error("Row one is empty!");
                return null;
            }

            if (StringUtils.isBlank(row2)) {
                log.error("Row two is empty!");
                return null;
            }

            final String[] row1Parts = Arrays.stream(row1.split(" ")).filter(StringUtils::isNotBlank).toArray(String[]::new);

            if (row1Parts.length < 5) {
                log.error("Row one is missing some parts. Can't map!");
                return null;
            }

            final String finalisiert = row1Parts[row1Parts.length - 1];
            final String layoutQs = row1Parts[row1Parts.length - 2];
            final String xmlVorhanden = row1Parts[row1Parts.length - 3];
            final String labelsNachgefuehrt = row1Parts[row1Parts.length - 4];
            final String name = String.join(" ", Arrays.copyOf(row1Parts, row1Parts.length - 4));
            final String bemerkung = messageParts.length == 3 ? messageParts[2] : "";

            return Document.builder()
                    .id(row2)
                    .layoutQSFunktionsTest(layoutQs)
                    .labelsRdeNachgefuehrt(labelsNachgefuehrt)
                    .xmlVorhanden(xmlVorhanden)
                    .bemerkung(bemerkung)
                    .finalisiert(finalisiert)
                    .name(name)
                    .build();
        };
    }
}
