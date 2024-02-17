package org.example;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class ChromaAddDocuments {
    public static void main(String[] args) throws IOException, URISyntaxException {
        EmbeddingStore<TextSegment> embeddingStore = ChromaEmbeddingStore.builder()
                .baseUrl("https://chroma-6mxzvcwm2a-uc.a.run.app")
                .collectionName("cookgptrag").build();

        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        Path path  = Paths.get(
                Objects
                        .requireNonNull(
                                ChromaAddDocuments.class.getResource("/recipe.txt")
                        )
                        .toURI()
        );
        String content = Files.readString(path);
        TextSegment segment1 = TextSegment.from(content);
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);

        Path filePdfath =  Paths.get(
                Objects
                        .requireNonNull(
                                ChromaAddDocuments.class.getResource("/CookGPT_Linkedin.pdf")
                        )
                        .toURI()
        );
        Document document = FileSystemDocumentLoader.loadDocument(filePdfath, new ApachePdfBoxDocumentParser());
        Embedding embedding2 = embeddingModel.embed(document.toTextSegment()).content();
        embeddingStore.add(embedding2, document.toTextSegment());

        Embedding queryEmbedding = embeddingModel.embed("What is cookgpt?").content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        System.out.println(embeddingMatch.score());
        System.out.println(embeddingMatch.embedded().text());
    }
}
