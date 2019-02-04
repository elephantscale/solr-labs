[\<\< back to main index](../README.md)

# Indexing Lab 1:  

### Overview

This will get us familiar with indexing and faceting

### Builds on
[Install](../install/README.md)

### Run time
approx. 20-30 minutes

### Notes


## STEP 1: Restart Solr
If solr is already started, there is no need to restart.

```bash
cd $SOLR_INSTALL
./bin/solr start -c -p 8983 -s example/cloud/node1/solr
```

This starts the first node. When it’s done start the second node, and tell it how to connect to to ZooKeeper:

```bash
./bin/solr start -c -p 7574 -s example/cloud/node2/solr -z localhost:9983
```


## STEP 2:  Create a New Collection 

We’re going to use a whole new data set in this exercise, so it would be better to have a new collection instead of trying to reuse the one we had before.

One reason for this is we’re going to use a feature in Solr called "field guessing", where Solr attempts to guess what type of data is in a field while it’s indexing it. It also automatically creates new fields in the schema for new fields that appear in incoming documents. This mode is called "Schemaless". We’ll see the benefits and limitations of this approach to help you decide how and where to use it in your real application.


The data you’re going to index is related to movies, so start by creating a collection named "films" that uses the `_default` configSet:

```bash
bin/solr create -c films -s 2 -rf 2
```

Console output will look like

```console
WARNING: Using _default configset. Data driven schema functionality is enabled by default, which is
         NOT RECOMMENDED for production use.

         To turn it off:
            bin/solr config -c films -p 7574 -action set-user-property -property update.autoCreateFields -value false

Connecting to ZooKeeper at localhost:9983 ...
INFO  - 2017-07-27 15:07:46.191; org.apache.solr.client.solrj.impl.ZkClientClusterStateProvider; Cluster at localhost:9983 ready
Uploading /7.6.0/server/solr/configsets/_default/conf for config films to ZooKeeper at localhost:9983

Creating new collection 'films' using command:
http://localhost:7574/solr/admin/collections?action=CREATE&name=films&numShards=2&replicationFactor=2&maxShardsPerNode=2&collection.configName=films

{
  "responseHeader":{
    "status":0,
    "QTime":3830},
  "success":{
    "192.168.0.110:8983_solr":{
      "responseHeader":{
        "status":0,
        "QTime":2076},
      "core":"films_shard2_replica_n1"},
    "192.168.0.110:7574_solr":{
      "responseHeader":{
        "status":0,
        "QTime":2494},
      "core":"films_shard1_replica_n2"}}}
```



## STEP 3: Create the names field

The films data we are going to index has a small number of fields for each movie: an ID, director name(s), film name, release date, and genre(s).

If you look at one of the files in example/films, you’ll see the first film is named .45, released in 2006. As the first document in the dataset, Solr is going to guess the field type based on the data in the record. If we go ahead and index this data, that first film name is going to indicate to Solr that that field type is a "float" numeric field, and will create a "name" field with a type FloatPointField. All data after this record will be expected to be a float. **This is clearly NOT what we want!**`

We will set up the `names` field like this:

```bash
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-field": {"name":"name", "type":"text_general", "multiValued":false, "stored":true}}' http://localhost:8983/solr/films/schema
```


## Step 4: Create a "catch all" copy field

In the first exercise when we queried the documents we had indexed, we didn’t have to specify a field to search because the configuration we used was set up to copy fields into a text field, and that field was the default when no other field was defined in the query. We do not have that in our current configuration.

We can, however, set up a "catchall field" by defining a copy field that will take all data from all fields and index it into a field named _text_. Let’s do that now.

You can use either the Admin UI or the Schema API for this.

```bash
curl -X POST -H 'Content-type:application/json' --data-binary '{"add-copy-field" : {"source":"*","dest":"_text_"}}' http://localhost:8983/solr/films/schema
```

In the Admin UI, choose `Add Copy Field`, then fill out the source and destination for your field, as in this screenshot.


## Step 5: Index Data

We will build the index of the data like this:

```bash
bin/post -c films example/films/films.json
```

Each command includes these main parameters:

* `-c films`: this is the Solr collection to index data to.
* `example/films/films.json`: this is the path to the data file to index. You could simply supply the directory where this file resides, but since you know the format you want to index, specifying the exact file for that format is more efficient.

Here is the expected output:

```console
/bin/java -classpath /solr-{solr-docs-version}.0/dist/solr-core-{solr-docs-version}.0.jar -Dauto=yes -Dc=films -Ddata=files org.apache.solr.util.SimplePostTool example/films/films.json
SimplePostTool version 5.0.0
Posting files to [base] url http://localhost:8983/solr/films/update...
Entering auto mode. File endings considered are xml,json,jsonl,csv,pdf,doc,docx,ppt,pptx,xls,xlsx,odt,odp,ods,ott,otp,ots,rtf,htm,html,txt,log
POSTing file films.json (application/json) to [base]/json/docs
1 files indexed.
COMMITting Solr index changes to http://localhost:8983/solr/films/update...
Time spent: 0:00:00.878

```

Navigate in your browser to the following:

```text
http://YOURIPADDRESS:8983/solr/#/films/query
```

Hit `Execute Query` with an empty field.  It should give you 1100 total responses, with the first 10 printed.

Now, type `comedy` in the search box, and hit `Execute Query` again.  You should get 417 responses. This is because it is using the catchall field we just created.



### Step 6: Experiment With Faceting

One of Solr’s most popular features is faceting. Faceting allows the search results to be arranged into subsets (or buckets, or categories), providing a count for each subset. There are several types of faceting: field values, numeric and date ranges, pivots (decision tree), and arbitrary query faceting.


#### 6.1: Field Facets

Navigate in your browser to the following:

```text
http://YOURIPADDRESS:8983/solr/#/films/query
```

You should see a `facet` checkbox.  Check the checkbox and you will see the following options:

* facet.query
* facet.field
* facet.prefix

To see facet counts from all documents (`q=*:*`): turn on faceting (`facet=true`), and specify the field to facet on via the facet.field param. If you only want facets, and no document contents, specify `rows=0`. 

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



#### 6.2 Range Facets

For numerics or dates, it’s often desirable to partition the facet counts into ranges rather than discrete values. A prime example of numeric range faceting, using the example techproducts data from our previous exercise, is price. In the `/browse` UI, it looks like this:

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

The films data includes the release date for films, and we could use that to create date range facets, which are another common use for range facets.

The Solr Admin UI doesn’t yet support range facet options, so you will need to use curl or similar command line tool for the following examples.

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


#### 6.3 Pivot Facets

Another faceting type is pivot facets, also known as "decision trees", allowing two or more fields to be nested for all the various possible combinations. Using the films data, pivot facets can be used to see how many of the films in the "Drama" category (the `genre_str` field) are directed by a director. Here’s how to get at the raw data for this scenario:



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

