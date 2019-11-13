# Add Collection

## Step 1: Add collection

Normally, Solr will add a default collection called `techproducts`.  However, if we run in production mode it will not do so. Let us restore this so we can us it for test.

```bash
sudo -u zk ./solr create -c techproducts -s 2 -rf 2
```


# STEP 2: Post file to collection


We will now do a post to add the techproducts data.

```bash
sudo -u zk ./solr post ../example/techproducts/solr/solr.xml
```
