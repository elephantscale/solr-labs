# Solr Clustering 

In this lab we will practice doing a simple cluster


Lab Goals:

* Do some simple cluster



### STEP 1) Restart Solr


We will stop any solr we have runnign and then restart, with solr clustering enabled.

```bash
cd $SOLR_HOME
bin/solr stop -all
bin/solr start -e techproducts -Dsolr.clustering.enabled=true
```


### Step 2) Test Clustering in Browser

open the following url in your browser:

```text
http://YOURIPADDRESS:8983/solr/techproducts/clustering?q=*:*&rows=100&wt=json
```


You will get a *very* long output in json. But here's the meat of what we're trying to see:


```json
{
  "responseHeader":{
    "status":0,
    "QTime":40},
  "response":{"numFound":32,"start":0,"maxScore":1.0,"docs":[
          .... results snipped..... 
        ]
  },
  "clusters":[{
      "labels":["DDR"],
      "score":5.114085862823866,
      "docs":["TWINX2048-3200PRO",
        "VS1GB400C3",
        "VDBDB1A16"]},
    {
      "labels":["iPod"],
      "score":12.40457968342098,
      "docs":["MA147LL/A",
        "F8V7067-APL-KIT",
        "IW-02"]},
    {
      "labels":["CORSAIR"],
      "score":1.2564734139366827,
      "docs":["TWINX2048-3200PRO",
        "VS1GB400C3"]},
    {
      "labels":["Canon"],
      "score":8.214731320333584,
      "docs":["9885A004",
        "0579B002"]},
    {
      "labels":["Encoded Characters"],
      "score":9.057806653671927,
      "docs":["UTF8TEST",
        "GB18030TEST"]},
    {
      "labels":["Hard Drive"],
      "score":14.559444822760689,
      "docs":["SP2514N",
        "6H500F0"]},
    {
      "labels":["Video"],
      "score":11.933133936463285,
      "docs":["MA147LL/A",
        "100-435805"]},
    {
      "labels":["Other Topics"],
      "score":0.0,
      "other-topics":true,
      "docs":["VA902B",
        "3007WFP",
        "EN7800GTX/2DHTV/256M",
        "adata",
        "apple",
        "asus",
        "ati",
        "belkin",
        "canon",
        "corsair",
        "dell",
        "maxtor",
        "samsung",
        "viewsonic",
        "SOLR1000",
        "USD",
        "EUR",
        "GBP",
        "NOK"]}]}
```

Notice we have some clusters.


You can also try doing the same query in curl:

```bash
curl "http://localhost:8983/solr/techproducts/clustering?q=*:*&rows=100&wt=json"
```

Answer the following questions:

 * Which cluster has the largest number of documents?
 * Do the clusters seem to make sense? Are the coherent?
 * If you could change anything, what would you change?
 * What do the scores tell you? Relate the score to the cluster?
   - Do you think higher is better?  Or lower is better?
   - Take the highest and lowest scored cluster.  examine the documents to see if you agree with the scoring.



