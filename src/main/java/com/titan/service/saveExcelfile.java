package com.titan.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class saveExcelfile {

	
	
	public void savefile(MultipartFile f,String str) throws IOException {
		MultipartFile file2=f;
//		String path= System. getProperty("java. io. tmpdir");
//		path=path+"\\str";
//		File path = new File(System.getProperty("java.io.tmpdir")+"\\"+str);
		 if (!f.getOriginalFilename().equals("")) {
             f.transferTo(new File(System.getProperty("java.io.tmpdir")+"\\"+ f.getOriginalFilename()));
             
           
//             File f1 = new File(System.getProperty("java.io.tmpdir")+"\\fixed_file\\");  
//             boolean bool = f1.mkdir(); 
//             f.transferTo(new File(System.getProperty("java.io.tmpdir")+"\\fixed_file\\"+ f.getOriginalFilename()));

//             File create_folder = new File(System.getProperty("java.io.tmpdir")+"\\fixed_file\\");
             File create_folder = new File(System.getProperty("java.io.tmpdir")+"\\fixed_file"); 
             try{
            	 if(create_folder.mkdir()) { 
            		 System.out.println("fixed_file Directory Created");
//            		 f.transferTo(new File(System.getProperty("java.io.tmpdir")+"\\fixed_file"+"\\"+ f.getOriginalFilename()));
            		 
//            		 FileUtils.copyFile(System.getProperty("java.io.tmpdir")+"\\"+ str, System.getProperty("java.io.tmpdir")+"\\fixed_file\\"+str);
            		 FileUtils.copyFileToDirectory(new File(System.getProperty("java.io.tmpdir")+"\\"+str), new File (System.getProperty("java.io.tmpdir")+"\\fixed_file"));
//            		 file2.transferTo(new File(System.getProperty("java.io.tmpdir")+"\\"+ file2.getOriginalFilename()));
            	 } else {
            		 System.out.println("fixed_file Directory is not created");
            	 }
             } catch(Exception e){
            	 e.printStackTrace();
             } 
             
             
//             f.transferTo(new File(System.getProperty("java.io.tmpdir")+"\\fixed_file\\"+ f.getOriginalFilename()));
             
         }
		 
//		 if (!f.getOriginalFilename().equals("")) {
//			 String path=System.getProperty("java.io.tmpdir");
//			 path=path+"D:\\fixed_file\\";
//			 f.transferTo(new File(path+f.getOriginalFilename()));
//		 }
		 System.out.println("4534534");
		 
////		 File file = new File("D:\\Bom_errors.xlsx");
//		    try {
//		    	System.out.println("fgfgdgdf");
//		      // create a new file with name specified
//		      // by the file object
//		      boolean value = file.createNewFile();
//		      System.out.println("done");
//		      if (value) {
//		        System.out.println("New Java File is created.");
//		      }
//		      else {
//		        System.out.println("The file already exists.");
//		      }
//		      
//		    }
//		    catch(Exception e) {
//		      e.getStackTrace();
//		    }
////		    
	        try {
	        	File filename = new File(System.getProperty("java.io.tmpdir")+"\\"+"Bom_errors.xlsx");
	            Workbook workbook = new  XSSFWorkbook();
	             workbook.createSheet("Sheet1");  

	           

	            FileOutputStream fileOut = new FileOutputStream(filename);
	            workbook.write(fileOut);
	            fileOut.close();
	            workbook.close();
	            
	            System.out.println("Your excel file has been generated!");

	        } catch ( Exception ex ) {
	            System.out.println(ex);
	        }
	        
	       
//
//	        try {
//	        	File filename = new File(System.getProperty("java.io.tmpdir")+"\\fixed_file\\"+f.getOriginalFilename());
//	            Workbook workbook = new  XSSFWorkbook();
//	             workbook.createSheet("Sheet1");  
//
//	           
//
//	            FileOutputStream fileOut = new FileOutputStream(filename);
//	            workbook.write(fileOut);
//	            fileOut.close();
//	            workbook.close();
//	            System.out.println(f.getOriginalFilename()+ " excel file has been generated!");
//
//	        } catch ( Exception ex ) {
//	            System.out.println(ex);
//	        }

		    
		    
//		File file = new File(path);
//		System.out.println("path"+  path);
//		OutputStream out = new FileOutputStream(path);
		// Write your data
//		out.close();
	}
	
	
	
}
