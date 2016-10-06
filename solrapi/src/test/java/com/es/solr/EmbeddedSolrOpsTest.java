package com.es.solr;

import junit.framework.TestCase;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mark on 6/18/15.
 */
public class EmbeddedSolrOpsTest extends TestCase {
    private static final Logger logger = LoggerFactory.getLogger(EmbeddedSolrOpsTest.class);
    //private EmbeddedSolrServer server = new EmbeddedSolrServer();

    public void testAddDocument() {
        logger.info("testAddDocument - start");
        SolrOps instance = new SolrOps();
        //instance.addDocument(new File("test-data/docToAdd.txt"));
    }
    public void testQuery() {
        logger.info("testQuery - start");
        SolrOps instance = new SolrOps();
        String query = "Belkin";
        //instance.query(query);
    }

}