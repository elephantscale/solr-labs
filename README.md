# Solr labs

The preparation steps in this 'README' apply to all subsequent labs.

## Lab Goals:

1. Prepare the working environment for the Solr labs.
2. Make the necessary steps, so that the students won't have to repeat them in each and every lab.

### STEP 1) Lab environment

The instructor will provide a server with SSH access

### STEP 2) Login 

Login with the credentials provided by the instructor

### STEP 3) To view the labs 

Markdown (`*.md`) files are plain text files and can be viewed in any text editor.

For best viewing experience we recommend the following setup:

* Get Chrome browser from [here](https://www.google.com/chrome/browser/desktop/)
* Install Markdown [preview plus plugin](https://chrome.google.com/webstore/detail/markdown-preview-plus/febilkbfcbhebfnokafefeacimjdckgl?hl=en-US)
* Once installed, go to 'Window --> Extensions' menu of Chrome ;   Find 'Markdown Preview Plus' plugin ;  set 'Allow access to file urls'
* Open any `*.md` file using Chrome (File --> Open)

### If you need to edit a file

We recommend using a 'programmer's editor to view these files. These editors have integrated file browser and allows navigating through files quickly.

* For Windows: [Sublime](http://www.sublimetext.com/), [NodePad++](http://notepad-plus-plus.org/), [Textpad](http://www.textpad.com/)
* For Mac: [Sublime](http://www.sublimetext.com/),  [TextWrangler](http://www.barebones.com/products/textwrangler/)
* For Linux: [Sublime](http://www.sublimetext.com/), GEdit, Vim


### Labs

* Introduction
  - [Inverted Index](./inverted-index/README.md)
  - [IR Intro](./IR/README.md)
* Solr Intro
  - [Install Solr](./solr-intro/1-install.md)
  - [Query Solr](./solr-intro/2-query.md)
* Searching
  - [Searching with Solritas](./search/1-solritas-search.md)
  - [Facets](./search/2-facets.md)
  - [Geospatial Search](./search/3-geospatial.md)
* Indexing
  - [Indexing Films Example](./indexing/1-films.md)
  - [Indexing Tweets Example](./indexing/2-tweets.md)
* Schema Updating
  - [Schema](./schema/README.md)
  - [Text Analysis](./textanalysis/README.mD)
* Solr API
  - [SolrAPI](./solrapi/README.md)
* Bookstore App
  - [Bookstore App](./bookstore/README.md)
* Extended Features
  - [Clustering](./clustering/README.md)
* SolrCloud
  - [SolrCloud](./solrcloud/README.md)
* Docker and Kubernetes
 - [Docker Single Instance](./kubernetes/docker-single-instance.md)
 - [Install Kubernetes](./kubernetes/ 1.2-install-kubernetes-gcp.md)
 - [Solr Operator](./kubernetes/solr-operator.md)
