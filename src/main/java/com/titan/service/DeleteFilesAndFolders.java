package com.titan.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.stereotype.Service;

//import net.bytebuddy.implementation.bind.MethodDelegationBinder.MethodInvoker.Virtual;

@Service
public class DeleteFilesAndFolders {


	public  void deleteAllFiles(String bom_sheet) {
		
		
		
		
		File fixed_file_folder = new File(System.getProperty("java.io.tmpdir")+"\\fixed_file");
		File validation_excel_file = new File(System.getProperty("java.io.tmpdir")+"\\"+bom_sheet);
		File Bom_errors_file = new File(System.getProperty("java.io.tmpdir")+"\\Bom_errors.xlsx");
		boolean Bom_errors_delete=false;
		boolean validation_excel_delete=false;
		 
		
		 while(validation_excel_delete==true) {
			 
			 validation_excel_delete=validation_excel_file.canWrite();
		 }
		 while(Bom_errors_delete==true) {
			 
			 Bom_errors_delete=Bom_errors_file.canWrite();
		 }
		 
		
        try
        {
        	
        	if(fixed_file_folder.exists()) {
       		 System.out.println("fixed_file_folder Directory is exist ");
       		if(fixed_file_folder.isDirectory()) {
       			System.out.println("fixed_file_folder Directory deleted ");
       			FileUtils.cleanDirectory(fixed_file_folder);
       			System.out.println("fixed_file_folder Directory deleted " + fixed_file_folder.delete());
           	}
       	}
        	if(validation_excel_file.exists()) {
        		
        		 System.out.println("validation_excel_file  is exist ");
        		 
//        		 Files.delete( validation_excel_file.toPath() );
//        		 FileUtils.forceDelete(validation_excel_file);
        	if (validation_excel_file.delete()) {
        		
                System.out.println("validation_excel_file deleted successfully");
            }
            else {
                System.out.println("Failed to delete the validation_excel_file ");
            }
        	}else {
        		System.out.println("validation_excel_file Directory is not exist ");
        		
        	}
        	
        	
        	if(Bom_errors_file.exists()) {
       		 System.out.println("Bom_errors_file  is exist ");
        	if (Bom_errors_file.delete()) {
                System.out.println("Bom_errors_file deleted successfully");
            }
            else {
                System.out.println("Failed to delete the Bom_errors_file ");
            }
        	
       	
        } else {
    		System.out.println("Bom_errors_file  is not exist ");
    		
    	}}
        	
        	
        	catch(Exception e){
       	 e.printStackTrace();
        } 
        
		
		
		
		
		
	}
	
	
}
