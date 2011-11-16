package com.foo.bug;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.mapred.MiniMRCluster;
import org.junit.Test;

public class BugTest {

  @Test
  public void hangForever() throws Exception {
    String baseDir = "target/bug";
    File f = new File(baseDir);
    f.mkdirs();
    if (!f.exists() || !f.isDirectory()) {
      throw new Exception("unable to create temporary dir for test: " + f.getAbsolutePath());
    }
 
    System.setProperty("hadoop.log.dir", baseDir+"/hadoop_log"); 
    System.setProperty("hadoop.tmp.dir",baseDir+"/hadoop_tmp");
    System.setProperty("mapred.local.dir", baseDir+"/hadoop_tasks");
    System.setProperty("test.build.data", baseDir+"/build_data");
    
    Configuration cfg = new Configuration(true);
    
    MiniDFSCluster dfsCluster = new MiniDFSCluster(cfg, 2, true, null);
    dfsCluster.waitActive();
    MiniMRCluster mrCluster = new MiniMRCluster(2, dfsCluster.getFileSystem().getUri().toString(), 1);  

  }

}
