[\<\< back to main index](../README.md)

# Indexing Lab 1:  

### Overview

This will get us familiar with faceting.

### Builds on
[Install](../install/README.md)

### Run time
approx. 20-30 minutes

### Notes

One of Solr’s most popular features is faceting. Faceting allows the search results to be arranged into 
subsets (or buckets, or categories), providing a count for each subset. There are several types of 
faceting: field values, numeric and date ranges, pivots (decision tree), and arbitrary query faceting.

## STEP 1: Start Solr if necessary in SolrCloud mode.
If solr is already started, there is no need to restart.

```bash
cd $SOLR_INSTALL
./bin/solr start -c -p 8983 -s example/cloud/node1/solr
```

This starts the first node. When it’s done start the second node, and tell it how to connect to to ZooKeeper:

```bash
./bin/solr start -c -p 7574 -s example/cloud/node2/solr -z localhost:9983
```

## STEP 2:  Verify that the Films collection exists.

Go to the Solr web API and verify that the films collection still exists. It should have been created in
[Indexing Lab](../indexing/1-films.md).  If it does not exist, go back to that lab and recreate it.


### Step 3: Do a field facet

Navigate in your browser to the following:

```text
http://YOURIPADDRESS:8983/solr/#/films/query
```

You should see a `facet` checkbox.  Check the checkbox and you will see the following options:

* facet.query
* facet.field
* facet.prefix

To see facet counts from all documents (`q=*:*`): turn on faceting (`facet=true`), and specify the 
field to facet on via the facet.field param. If you only want facets, and no document contents, specify `rows=0`. 

The `curl` command below will return facet counts for the `genre_str` field:

```bash
curl "http://localhost:8983/solr/films/select?q=*:*&rows=0&facet=true&facet.field=genre_str"
```

With the response loooking like this.

```json
{
  "responseHeader":{
    "zkConnected":true,
    "status":0,
    "QTime":11,
    "params":{
      "q":"*:*",
      "facet.field":"genre_str",
      "rows":"0",
      "facet":"true"}},
  "response":{"numFound":1100,"start":0,"maxScore":1.0,"docs":[]
  },
  "facet_counts":{
    "facet_queries":{},
    "facet_fields":{
      "genre_str":[
        "Drama",552,
        "Comedy",389,
        "Romance Film",270,
        "Thriller",259,
        "Action Film",196,
        "Crime Fiction",170,
        "World cinema",167]},
        "facet_ranges":{},
        "facet_intervals":{},
        "facet_heatmaps":{}}}

```



#### Step 4) Do a range facet 

For numerics or dates, it’s often desirable to partition the facet counts into ranges rather than 
discrete values. A prime example of numeric range faceting, using the example techproducts data from our 
previous exercise, is price. In the `/browse` UI, it looks like this:

```text
price
  0.0 - 50.0 (19)
  50.0 - 100.0 (1)
  150.0 - 200.0 (2)
  250.0 - 300.0 (1)
  350.0 - 400.0 (2)
  450.0 - 500.0 (1)
  More than 600.0 (2)
```

The films data includes the release date for films, and we could use that to create date range facets, 
which are another common use for range facets.

The Solr Admin UI doesn’t yet support range facet options, so you will need to use curl or similar 
command line tool for the following examples.

```bash
curl 'http://localhost:8983/solr/films/select?q=*:*&rows=0'\
    '&facet=true'\
    '&facet.range=initial_release_date'\
    '&facet.range.start=NOW-20YEAR'\
    '&facet.range.end=NOW'\
    '&facet.range.gap=%2B1YEAR'
```

You should see a result like this:

```json 
{
  "responseHeader":{
    "zkConnected":true,
    "status":0,
    "QTime":8,
    "params":{
      "facet.range":"initial_release_date",
      "facet.limit":"300",
      "q":"*:*",
      "facet.range.gap":"+1YEAR",
      "rows":"0",
      "facet":"on",
      "facet.range.start":"NOW-20YEAR",
      "facet.range.end":"NOW"}},
  "response":{"numFound":1100,"start":0,"maxScore":1.0,"docs":[]
  },
  "facet_counts":{
    "facet_queries":{},
    "facet_fields":{},
    "facet_ranges":{
      "initial_release_date":{
        "counts":[
          "1997-07-28T17:12:06.919Z",0,
          "1998-07-28T17:12:06.919Z",0,
          "1999-07-28T17:12:06.919Z",48,
          "2000-07-28T17:12:06.919Z",82,
          "2001-07-28T17:12:06.919Z",103,
          "2002-07-28T17:12:06.919Z",131,
          "2003-07-28T17:12:06.919Z",137,
          "2004-07-28T17:12:06.919Z",163,
          "2005-07-28T17:12:06.919Z",189,
          "2006-07-28T17:12:06.919Z",92,
          "2007-07-28T17:12:06.919Z",26,
          "2008-07-28T17:12:06.919Z",7,
          "2009-07-28T17:12:06.919Z",3,
          "2010-07-28T17:12:06.919Z",0,
          "2011-07-28T17:12:06.919Z",0,
          "2012-07-28T17:12:06.919Z",1,
          "2013-07-28T17:12:06.919Z",1,
          "2014-07-28T17:12:06.919Z",1,
          "2015-07-28T17:12:06.919Z",0,
          "2016-07-28T17:12:06.919Z",0],
        "gap":"+1YEAR",
        "start":"1997-07-28T17:12:06.919Z",
        "end":"2017-07-28T17:12:06.919Z"}},
    "facet_intervals":{},
    "facet_heatmaps":{}}}
```


####  Step 5) Do a pivot facet

Another faceting type is pivot facets, also known as "decision trees", allowing two or more fields to be nested for all 
the various possible combinations. Using the films data, pivot facets can be used to see how many of the films in the 
"Drama" category (the `genre_str` field) are directed by a director. Here’s how to get at the raw data for this scenario:



```bash
curl "http://localhost:8983/solr/films/select?q=*:*&rows=0&facet=on&facet.pivot=genre_str,directed_by_str"
```

This results in the following response, which shows a facet for each category and director combination:

```json
{"responseHeader":{
    "zkConnected":true,
    "status":0,
    "QTime":1147,
    "params":{
      "q":"*:*",
      "facet.pivot":"genre_str,directed_by_str",
      "rows":"0",
      "facet":"on"}},
  "response":{"numFound":1100,"start":0,"maxScore":1.0,"docs":[]
  },
  "facet_counts":{
    "facet_queries":{},
    "facet_fields":{},
    "facet_ranges":{},
    "facet_intervals":{},
    "facet_heatmaps":{},
    "facet_pivot":{
      "genre_str,directed_by_str":[{
          "field":"genre_str",
          "value":"Drama",
          "count":552,
          "pivot":[{
              "field":"directed_by_str",
              "value":"Ridley Scott",
              "count":5},
            {
              "field":"directed_by_str",
              "value":"Steven Soderbergh",
              "count":5},
            {
              "field":"directed_by_str",
              "value":"Michael Winterbottom",
              "count":4}}]}]}}}
```

