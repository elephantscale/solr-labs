package com.es.solr;

import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class SolrOpsTest extends TestCase {
    private static final Logger logger = LoggerFactory.getLogger(SolrOpsTest.class);

    public void testAddDocument() {
        logger.info("testAddDocument - start");
        SolrOps instance = new SolrOps();
        instance.addDocument(new File("test-data/docToAdd.txt"));
    }
    public void testQuery() {
        logger.info("testQuery - start");
        SolrOps instance = new SolrOps();
        String query = "Belkin";
        instance.query(query);
    }
    public void testAddFields() {
        logger.info("testAddDocument - start");
        SolrOps instance = new SolrOps();
        instance.addFields();
    }
    // TODO note that the test is not guaranteed to run, because the tests execute in any sequence
    // rather, create a test that will first add a field, then query for it

    // TODO bonus - modify the code to test remote server, not localhost
}