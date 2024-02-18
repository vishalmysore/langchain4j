package com.vishal.web.backend;

import com.vishal.web.rails.NERRail;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import java.util.List;
@Component

public class ChromaDbConnector implements VectorDB{
    private final static Logger LOG = LoggerFactory.getLogger(ChromaDbConnector.class);
    private EmbeddingModel embeddingModel;
    private EmbeddingStore<TextSegment> embeddingStore;
    private String baseUrl;
    private String dbName;
    private ContentRetriever contentRetriever;
    public ChromaDbConnector(@Value("${chromadb-base-url}") String baseUrl,
                             @Value("${chromadb-db-name}") String dbName) {
        this.baseUrl = baseUrl;
        this.dbName = dbName;
        embeddingStore = ChromaEmbeddingStore.builder()
                .baseUrl(baseUrl)
                .collectionName(dbName).build();

        embeddingModel = new AllMiniLmL6V2EmbeddingModel();
         contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(2) // on each interaction we will retrieve the 2 most relevant segments
                .minScore(0.5) // we want to retrieve segments at least somewhat similar to user query
                .build();

    }

    public ContentRetriever getContentRetriever() {
        return contentRetriever;
    }

    public EmbeddingModel getEmbeddingModel() {
        return embeddingModel;
    }

    public EmbeddingStore<TextSegment> getEmbeddingStore() {
        return embeddingStore;
    }

    public String getData(String freeTextQuery){


        Embedding queryEmbedding = embeddingModel.embed(freeTextQuery).content();
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
        EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        System.out.println(embeddingMatch.score()); // 0.8144288493114709
        System.out.println(embeddingMatch.embedded().text());

        return embeddingMatch.embedded().text();
    }

    public void addData(String data) {
        LOG.info("adding data "+data);
        TextSegment segment1 = TextSegment.from(data);
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(embedding1, segment1);
    }
}
