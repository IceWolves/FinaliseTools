package de.assentis.skype.message;

import de.assentis.skype.domain.Document;

import java.io.InputStream;
import java.util.List;

public interface MessageExtractorService {

    List<Document> extractFinalizedDocuments(final InputStream messages);
}
