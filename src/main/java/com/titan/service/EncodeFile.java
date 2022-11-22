package com.titan.service;

import java.io.File;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.titan.repository.Repository;

@Service
public class EncodeFile {

	
	
	@Autowired
	public Repository repository ;
	
	public int encodeFile(String file_name)   {
    int id=0;
try {
		 byte[] Fixed_file_bytes = FileUtils.readFileToByteArray(new File(System.getProperty("java.io.tmpdir")+"\\fixed_file\\"+ file_name));
		
		 String encodedString = Base64.getEncoder().encodeToString(Fixed_file_bytes);
//		
		 System.out.println(Fixed_file_bytes.length);
//		 repository.storeEncodeedFile(encodedString, encodedString);
		 
		 
		 Blob fixed_file_blob = new SerialBlob(Fixed_file_bytes );
		 System.out.println("blob1.length()    "+fixed_file_blob.length());
//		 repository.storeEncodeedFile(blob1, blob1);
		
//		 repository.storeBlob(blob1, blob1);
		 
		 
		 byte[] error_file_bytes = FileUtils.readFileToByteArray(new File(System.getProperty("java.io.tmpdir")+"\\"+"Bom_errors.xlsx"));
		
//		
		 System.out.println(error_file_bytes.length);
//		 repository.storeEncodeedFile(encodedString, encodedString);
		 
		 
		 Blob error_file_blob = new SerialBlob(error_file_bytes );
		 System.out.println("blob1.length()    "+error_file_blob.length());
//		 repository.storeEncodeedFile(blob1, blob1);
		
		 id=repository.storeBlob(error_file_blob,fixed_file_blob,file_name);
//		 Blob blob = blob1;
//
//		 int blobLength = (int) blob.length();  
//		 
//		 
//		 System.out.println("blobLength   "+blobLength);
//		 byte[] blobAsBytes = blob.getBytes(1, blobLength);
//
//		 String encodedString1 = Base64.getEncoder().encodeToString(blobAsBytes);
//		 //release the blob and free up memory. (since JDBC 4.0)
//		 byte[] decodedBytes = Base64.getDecoder().decode(encodedString1);
//		 FileUtils.writeByteArrayToFile(new File(System.getProperty("java.io.tmpdir")+"\\"+"mynew.xlsx"), decodedBytes);
//		 
		 
		 fixed_file_blob.free();
		 error_file_blob.free();
		 
		 
////		System.out.println(encodedString.length());
//		
		
}
catch(Exception e) {
	
	System.out.println(e.getMessage());
}
	 return id;
	}
	
	
	public String dencodeFile(int reqNumber) throws SQLException   {
//		Blob encodedblob=repository.fetchEncodeedFile();
	
		
		
		
		File create_folder = new File(System.getProperty("java.io.tmpdir")+"\\downloadExcelFile");
        try{
        	if(create_folder.exists()) {
        		 System.out.println("downloadExcelFile Directory is exist ");
        		if(create_folder.isDirectory()) {
        			System.out.println("downloadExcelFile Directory deleted ");
        			FileUtils.cleanDirectory(create_folder);
        			System.out.println("downloadExcelFile Directory deleted " + create_folder.delete());
            	}
        	}
        	
        	
       	 if(create_folder.mkdir()) { 
       		 System.out.println("downloadExcelFile Directory Created");
       	 } else {
       		 System.out.println("downloadExcelFile Directory is not created");
       	 }
        } catch(Exception e){
       	 e.printStackTrace();
        } 
        
		
		List  encodedblob=repository.fetchEncod(reqNumber);
		
//		System.out.println("encodedString  "+encodedblob[0].length());
try {
	 
//	
//		
//		 byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//		 FileUtils.writeByteArrayToFile(new File(System.getProperty("java.io.tmpdir")+"\\"+"mynew.xlsx"), decodedBytes);
for(int i=0;i<=1;i++) {
	
	 Blob blob = (Blob) encodedblob.get(i);

	 int blobLength = (int) blob.length();  
	 
	 
	 System.out.println("blobLength   "+blobLength);
	 byte[] blobAsBytes = blob.getBytes(1, blobLength);

	 String encodedString1 = Base64.getEncoder().encodeToString(blobAsBytes);
	 //release the blob and free up memory. (since JDBC 4.0)
	 byte[] decodedBytes = Base64.getDecoder().decode(encodedString1);
	 
	 if(i==1) {
	 FileUtils.writeByteArrayToFile(new File(System.getProperty("java.io.tmpdir")+"\\downloadExcelFile\\"+encodedblob.get(2)), decodedBytes);
	 }if(i==0) {
		 FileUtils.writeByteArrayToFile(new File(System.getProperty("java.io.tmpdir")+"\\downloadExcelFile\\"+"bom_error.xlsx"), decodedBytes);
	 }
	 
	 blob.free();
	
}
	
	
	
//	 
	
	
	



}

catch(Exception e) {
	
	System.out.println(e.getMessage());
}
	 
return (String) encodedblob.get(2);

	}
	
	

	
}
