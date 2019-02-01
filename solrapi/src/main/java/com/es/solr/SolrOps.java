package com.es.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public class SolrOps {
    public void addDocument(File fileToAdd) {
//        try {
            String urlString = "http://localhost:8983/solr/collection1";
            SolrClient solr = new HttpSolrClient(urlString);
            SolrInputDocument document = new SolrInputDocument();
            // TODO parse the input document, form fields, add them to the document
//            document.addField("id", "552199");
//            document.addField("name", "Gouda cheese wheel");
//            document.addField("price", "49.99");
//            UpdateResponse response = solr.add(document);
//            solr.commit();
//        } catch (SolrServerException | IOException e) {
//            e.printStackTrace();
//        }
    }
    public void addFields() {
        try {
            String urlString = "http://localhost:8983/solr/collection1";
            SolrClient solr = new HttpSolrClient(urlString);
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", "552199");
            document.addField("name", "Gouda cheese wheel");
            document.addField("price", "49.99");
            UpdateResponse response = solr.add(document);
            solr.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }
    public void query(String queryStr) {
        try {
            String urlString = "http://localhost:8983/solr/collection1";
            SolrClient solr = new HttpSolrClient(urlString);

            SolrQuery parameters = new SolrQuery();
            parameters.set("q", queryStr);
            // optionally, set request handler
            // parameters.set("qt", "/spellCheckCompRH");
            QueryResponse response = solr.query(parameters);

            SolrDocumentList list = response.getResults();
            Iterator<SolrDocument> iter  = list.iterator();
            while (iter.hasNext()) {
                SolrDocument solrDocument = iter.next();
                System.out.println(solrDocument.toString());
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void main (String[] args) {
        SolrOps instance = new SolrOps();
        instance.addFields();
        instance.query("Gouda");
    }
}
