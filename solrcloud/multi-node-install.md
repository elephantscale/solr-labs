# Installing SolrCloud

In this lab we will install SolrCloud cluster with 3 shards

Lab Goals

1. Installing and configuring Zookeeper
2. Installing and configuring SolrCloud

### Prerequisite

We will use 3  Ubuntu 18.04.3 LTS machines for this guid with the following resources

    CPU : 2 cores
    RAM: 8 GB
    
To verify your resources use the following commands

```bash
grep -c ^processor /proc/cpuinfo
```
         
This should return your cpu core counts.
```bash
free -g
```
    
This should return your ram amount.

After verifying that you have the minimum resource that is needed, make sure your Ubuntu installation is up-to-date

```bash
sudo apt-get update
```

    
Install Java
 
```bash 
sudo apt-get install default-jdk
```
    
Note: you must have root access.              
    
Once you're done with prerequisite we should start by installing and configuring ZooKeeper

Apache ZooKeeper is open-source software that enables resilient and highly reliable distributed coordination. It is commonly used in distributed systems to manage configuration information, naming services, distributed synchronization, quorum, and state. In addition, distributed systems rely on ZooKeeper to implement consensus, leader election, and group management.

### Retrieve IP Address

```bash
export HOST1=YOURIPADDRESS1
export HOST2=YOURIPADDRESS2
export HOST3=YOURIPADDRESS3
```

### Check ulimit

If you start solr now, you will likely get this nasty warning:


```console
*** [WARN] *** Your open file limit is currently 1024.
 It should be set to 65000 to avoid operational disruption.
 If you no longer wish to see this warning, set SOLR_ULIMIT_CHECKS to false in your profile or solr.in.sh
*** [WARN] ***  Your Max Processes Limit is currently 31748.
 It should be set to 65000 to avoid operational disruption.
 If you no longer wish to see this warning, set SOLR_ULIMIT_CHECKS to false in your profile or solr.in.sh
```

So, let us avoid this problem by properly setting our ulimits. First, let's check to see our current ulimit:

```console
$ ulimit -n
1024
```

Let see our max ulmit:

```console
$ cat /proc/sys/fs/file-max
810574
```

Plenty of room for improvement, right?

Let's change it to 65000, as solr recommends:

```console
$ sudo  nano /etc/sysctl.conf
```

add the following:

```console
fs.file-max = 65000
```

Run this:

```bash
sudo sysctl -p
```

Edit this file:

```console
$ sudo nano /etc/security/limits.conf
```

Add this:

```console
* soft     nproc          65000
* hard     nproc          65000
* soft     nofile         65000
* hard     nofile         65000
root soft     nproc          65000
root hard     nproc          65000
root soft     nofile         65000
root hard     nofile         65000
```

Edit this file:

```console
$ sudo nano /etc/pam.d/common-session
```

Add this:

```console
session required pam_limits.so
```

Logout:

```console
$ exit
```



# Installing ZooKeeper

### STEP 1 - Creating user for ZooKeeper ) 

Never use root for you installation, create a user to run Zookeeper

```bash
sudo useradd zk -m
```            
   
Passing the -m flag to the useradd command will create a home directory for this user. The home directory for zk will be /home/zk by default.
   
Set bash as the default shell for the zk user: 

```bash   
sudo usermod --shell /bin/bash zk
```
    
Set a password for this user:

```bash
sudo passwd zk
```
   
Next, you will add the zk user to the sudo group so it can run commands in a privileged mode:
   
```bash
sudo usermod -aG sudo zk
```
   
   
Switch to the zk user:

```bash   
su -l zk        
```
   

   
### STEP 2 - Creating a Data Directory for ZooKeeper 

ZooKeeper persists all configuration and state data to disk so it can survive a reboot. In this step, you will create a data directory that ZooKeeper will use to read and write data. 
   
   
Create a directory for ZooKeeper to use:

```bash
    sudo mkdir -p /data/zookeeper
```
   
       
Grant your zk user ownership to the directory:

```bash
    sudo chown zk:zk /data/zookeeper
```
      
You have successfully created and configured the data directory. When you move on to configure ZooKeeper, you will specify this path as the data directory that ZooKeeper will use to store its files.
   
### STEP 3 - Downloading and Extracting the ZooKeeper Binaries ) 
   
We are going to download and extract Zookeeper directly ro /opt directory. You may use apt but it may install an old version with different features. Installing ZooKeeper manually will give you full control to choose which version you would like to use.

start by changing to the /opt directory:
   
```bash   
cd /opt
``` 

We are going to use the latest stable version which is 3.5.6   

```bash
sudo wget http://apache.mirror.anlx.net/zookeeper/stable/apache-zookeeper-3.5.6-bin.tar.gz
```

Extract the binaries from the compressed archive:

```bash
    sudo tar -xvf apache-zookeeper-3.5.6-bin.tar.gz
```


Next, give the zk user ownership of the extracted binaries so that it can run the executables. You can change ownership like so:
   
```bash
    sudo chown zk:zk -R  apache-zookeeper-3.5.6-bin
```
   
Next, you will configure a symbolic link to ensure that your ZooKeeper directory will remain relevant across updates.

```bash
    sudo ln -s apache-zookeeper-3.5.6-bin zookeeper
```
   
Change the ownership of that link to zk:zk.

```bash
    sudo chown -h zk:zk zookeeper
```
   
### STEP 3 - Configuring ZooKeeper )    
   
   The configuration file will live in the /opt/zookeeper/conf directory. This directory contains a sample configuration file that comes with the ZooKeeper distribution. This sample file, named zoo_sample.cfg, contains the most common configuration parameter definitions and sample values for these parameters. Some of the common parameters are as follows:
   
   
   - **tickTime**: Sets the length of a tick in milliseconds. A tick is a time unit used by ZooKeeper to measure the length between heartbeats. Minimum session timeouts are twice the tickTime.
   
   - **dataDir**: Specifies the directory used to store snapshots of the in-memory database and the transaction log for updates. You could choose to specify a separate directory for transaction logs.
   
   - **clientPort**: The port used to listen for client connections.
   - **maxClientCnxns**: Limits the maximum number of client connections.
   
   Create a configuration file named `zoo.cfg` at `/opt/zookeeper/conf`. You can create and open a file using nano or your favorite editor:
   
   
```bash
    nano /opt/zookeeper/conf/zoo.cfg
```   

   Add the following set of properties and values to that file:
   
```text   
    tickTime=2000
    dataDir=/data/zookeeper
    clientPort=2181
    maxClientCnxns=60
    4lw.commands.whitelist=mntr,conf,ruok
```
   
  
   A tickTime of 2000 milliseconds is the suggested interval between heartbeats. A shorter interval could lead to system overhead with limited benefits. The dataDir parameter points to the path defined by the symbolic link you created in the previous section. Conventionally, ZooKeeper uses port 2181 to listen for client connections. In most situations, 60 allowed client connections are plenty for development and testing.
   
   Save the file and exit the editor.
   
   
   You have configured ZooKeeper and are ready to start the server.
   
### STEP 4 -  Starting ZooKeeper - the standalone installation )    
   
   You’ve configured all the components needed to run ZooKeeper. In this step, you will start the ZooKeeper service and test your configuration by connecting to the service locally.

```bash   
    cd /opt/zookeeper
```   
   Start ZooKeeper with the **`zkServer.sh`** command.
   
   
```bash
    bin/zkServer.sh start
```
   
   You will see the following on your standard output:
   
```console
    ZooKeeper JMX enabled by default
    Using config: /opt/zookeeper/bin/../conf/zoo.cfg
    Starting zookeeper ... STARTED
```   
  
   Connect to the local ZooKeeper server with the following command:
   
```bash
    bin/zkCli.sh -server 127.0.0.1:2181
```
   
   You will get a prompt with the label CONNECTED. This confirms that you have a successful local, standalone ZooKeeper installation. 
   
   Note : If you encounter errors, you will want to verify that the configuration is correct.
   
   
   
   After you’ve done some testing, you will close the client session by typing quit on the prompt. The ZooKeeper service will continue running after you closed the client session. Shut down the ZooKeeper service, as you’ll configure it as a systemd service in the next step:
   
```bash   
    bin/zkServer.sh stop
```   
   
   
### STEP 6 -  Creating and Using a Systemd  )  
   
   The systemd, system and service manager, is an init system used to bootstrap the user space and to manage system processes after boot. You can create a daemon for starting and checking the status of ZooKeeper using systemd.
   
   Use your editor to create a .service file named zk.service at /etc/systemd/system/.
   
```bash
    sudo nano /etc/systemd/system/zk.service
```
   
   Add the following lines to the file to define the ZooKeeper Service:
   
```text 
       [Unit]
       Description=Zookeeper Daemon
       Documentation=http://zookeeper.apache.org
       Requires=network.target
       After=network.target
       
       [Service]    
       Type=forking
       WorkingDirectory=/opt/zookeeper
       User=zk
       Group=zk
       ExecStart=/opt/zookeeper/bin/zkServer.sh start /opt/zookeeper/conf/zoo.cfg
       ExecStop=/opt/zookeeper/bin/zkServer.sh stop /opt/zookeeper/conf/zoo.cfg
       ExecReload=/opt/zookeeper/bin/zkServer.sh restart /opt/zookeeper/conf/zoo.cfg
       TimeoutSec=30
       Restart=on-failure
       
       [Install]
       WantedBy=default.target
```   
   
   Save the file and exit the editor.
   
   Now that your systemd configuration is in place, you can start the service:
   
```bash   
    sudo systemctl start zk
```
   
   Once you’ve confirmed that your systemd file can successfully start the service, you will enable the service to start on boot.
   
```bash   
    sudo systemctl enable zk
```   

   Check the status of the ZooKeeper service using:
   
```bash
    sudo systemctl status zk
```   
   
   Stop the ZooKeeper service using systemctl.
   
```bash   
    sudo systemctl stop zk
```
   restart the daemon using systemctl
   
```bash
    sudo systemctl restart zk
```
   
### STEP 7 -  Repeat  )   
   
   Repeat the last 7 steps on your other nodes then proceed to step 8 to configure ZooKeeper as 3 nodes cluster
   
### STEP 8 -  Configuring a Multi-Node ZooKeeper Cluster  )   
   
   While the standalone ZooKeeper server is useful for development and testing, every production environment should have a replicated multi-node cluster.
   
   Nodes in the ZooKeeper cluster that work together as an application form a quorum. 
   
   - what is a quorum?
   
   `Quorum refers to the minimum number of nodes that need to agree on a transaction before it’s committed.`
   
   How many nodes do you need for quorum?
   
   `A quorum needs an odd number of nodes so that it can establish a majority. An even number of nodes may result in a tie, which would mean the nodes would not reach a majority or consensus.`
   
   In a production environment, you should run each ZooKeeper node on a separate host. This prevents service disruption due to host hardware failure or reboots. This is an important and necessary architectural consideration for building a resilient and highly available distributed system.
   
   open `zoo.cfg` in the editor on each node.
   
    sudo nano /opt/zookeeper/conf/zoo.cfg
   
   All nodes in a quorum will need the same configuration file. In your zoo.cfg file on each of the three nodes, make the following changes
   
```text
       tickTime=2000
       dataDir=/data/zookeeper
       clientPort=2181
       maxClientCnxns=60
       initLimit=10
       syncLimit=5
       server.1=zookeeper_node_1_ip:2888:3888
       server.2=zookeeper_node_2_ip:2888:3888
       server.3=zookeeper_node_3_ip:2888:3888
       4lw.commands.whitelist=mntr,conf,ruok
```
   
   - `initLimit` specifies the time that the initial synchronization phase can take. 
   
   - `syncLimit` specifies the time that can pass between sending a request and receiving an acknowledgment
   
   - ZooKeeper nodes use a pair of ports, `:2888` and `:3888`, for follower nodes to connect to the leader node and for leader election, respectively.
   
   Save and exit config file after you are done with edits.
   
   On `zookeeper_node_1_ip`, create the `myid` file that will specify the node ID:
   
    sudo nano /data/zookeeper/myid
   
   - `zookeeper_node_1_ip` is identified as server.1, you will enter 1 to define the node ID. 
   - `zookeeper_node_2_ip` is identified as server.2, you will enter 2 to define the node ID. 
   - `zookeeper_node_3_ip` is identified as server.3, you will enter 3 to define the node ID. 
   
   After this step proceed to installing Solr
      
   
# Installing Solr   
   
```console

ulimit -n
1024


cat /proc/sys/fs/file-max
810574
```


then:

```bash

cd /opt

```

then download and extrac the install script

```bash
sudo wget http://apache.mirrors.lucidnetworks.net/lucene/solr/8.9.0/solr-8.9.0.tgz
sudo tar xzf solr-8.9.0.tgz solr-8.9.0/bin/install_solr_service.sh --strip-components=2
```
  

Run the `install_solr_service.sh`:

```bash   
sudo bash ./install_solr_service.sh  solr-8.9.0.tgz -u zk -n
sudo chown -R zk /opt/solr /opt/solr-8.9.0
echo ZK_HOST=$HOST1,$HOST2,$HOST3 | sudo tee -a  /etc/default/solr.in.sh
```

Then run solr cloud
   
```bash
sudo service solr start
```

## Test

```bash
curl localhost:8983/solr/#
```

You should get a response.

You can also open up this in a browser

`http://YOURIPADDRESS:8983/solr/#`


