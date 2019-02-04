# Clustering

In this lab we will install Carrot clustering.


Lab Goals:

* Install Carrot as a standalone component
* Integrate Carrot with Solr


### STEP 1) Install dependencies

```bash
sudo apt-get install libwebkitgtk-1.0-0
```


### STEP 2) Stop Solr and Restart

```bash
cd $HOME/solr
bin/solr stop -all
```

### STEP 2) Install Carrot

Download Carrot [here](https://github.com/carrot2/carrot2/releases/download/release%2F3.16.1/carrot2-workbench-linux.gtk.x86_64-3.16.1.zip)

```bash
wget https://github.com/carrot2/carrot2/releases/download/release%2F3.16.1/carrot2-workbench-linux.gtk.x86_64-3.16.1.zip
```
 
Follow the instructions [here](https://project.carrot2.org/download.html)

### STEP 2) Integrate Carrot with Solr

Follow [these instructions](https://cwiki.apache.org/confluence/display/solr/Result+Clustering)
