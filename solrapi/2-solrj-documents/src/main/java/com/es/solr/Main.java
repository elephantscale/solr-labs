package com.es.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.handler.extraction.ExtractingParams;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String []args) {
        System.out.println("Main program for com.es.solr.Main");
        try {
           SolrCellRequestDemo.main(null);
        }
        catch (Exception e) {
           System.out.println(e.toString());
        }
    }

    public static class SolrCellRequestDemo {
        public static void main(String[] args) throws IOException, SolrServerException {
            String urlString = "http://localhost:8983/solr/techproducts";
            SolrClient server = new HttpSolrClient.Builder(urlString).build();
            ContentStreamUpdateRequest req = new
                    ContentStreamUpdateRequest("/update/extract");
            req.addFile(new File("my-file.pdf"),"UTF8");

            req.setParam(ExtractingParams.EXTRACT_ONLY, "true");
            NamedList<Object> result = server.request(req);
            System.out.println("Result: " + result);
        }
    }
}
