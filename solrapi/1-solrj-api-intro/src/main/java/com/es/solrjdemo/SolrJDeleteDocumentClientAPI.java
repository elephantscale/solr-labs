package com.es.solrjdemo;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrJDeleteDocumentClientAPI {
  public static Logger _log = LoggerFactory.getLogger(SolrJDeleteDocumentClientAPI.class);

  public static void main(String[] args){
    
    String hostURL = "http://localhost:8983/solr/techproducts";
    HttpSolrClient solr = new HttpSolrClient.Builder(hostURL).build();
    
    try {
    // TODO: Delete By Id "HPPR0445"
    // 	UpdateResponse response = solr.???(???);
        solr.commit();
    //    _log.info(response.toString());
    } catch (SolrServerException e) {
      _log.error(e.getMessage());
    } catch (IOException e) {
      _log.error(e.getMessage());
    }
  }
}
