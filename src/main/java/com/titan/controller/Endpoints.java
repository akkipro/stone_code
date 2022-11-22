

package com.titan.controller;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.titan.service.DeleteFilesAndFolders;
import com.titan.service.EncodeFile;
import com.titan.service.ExcelService1;

import com.titan.service.saveExcelfile;


@RestController
@RestControllerAdvice
public class Endpoints {
	
	
	
	@Autowired
	ExcelService1 excelService;
	
	@Autowired
	saveExcelfile ssaveExcelfile;
	
	@Autowired
	EncodeFile encodeFile;
	
	@Autowired
	DeleteFilesAndFolders deleteFilesAndFolders;
	
	
	String bom_sheet;
	String bom_error_sheet;
	

	
	 @PostMapping("/upload")
	  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
	    String message = "";
	    
	    
//	    if (ExcelHelper.hasExcelFormat(file)) {
	      try {
//	        fileService.save(file);
	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        bom_sheet=file.getOriginalFilename();
	        ssaveExcelfile.savefile(file,file.getOriginalFilename());
	        return ResponseEntity.status(HttpStatus.OK).body(message);
	      }
//	        catch(MultipartException e) {System.out.println(e.getMessage());
//		  System.out.println("uyt"+ e.getStackTrace());
//		  message = "please upload file size less the 2GB ";
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
////	        ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
//		  }
	      catch(Exception e) {System.out.println("hgfhgfhgf"+ e.getMessage());
	      
//	      if (e instanceof MaxUploadSizeExceededException) {
//	          
////	          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file:  File size exceeds limit!");
//	    	  message="Could not upload the file:  File size exceeds limit!";
//	      }
//	      message="Could not upload the file:  File size exceeds limit!";
//		  System.out.println("uyt"+ e.getStackTrace());
		  message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		  }
//		return null;
//	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		
//	      } catch (Exception e) {
//	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//	      }
//	    }
//	    message = "Please upload an excel file!";
//	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	  }
	 
	 
	 @ExceptionHandler(MaxUploadSizeExceededException.class)
	 public String handlefilerror() {
		 return "file size exceeded !!!  you could not upload the file bigger than 1.8 GB ";
	 }
	 
	 
	 
	 
	 @GetMapping(value = "/downloadFixedFile/{reqNumber}",  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	  public ResponseEntity<?> downloadFile(@RequestParam(value="filename") String filename) {
		 public ResponseEntity<?> downloadFixedFile(@PathVariable int reqNumber) {
		 String fileName = null;
		 try {
//			 if(new File(System.getProperty("java.io.tmpdir")+"\\downloadExcelFile\\").exists()) {
//					
//					new File(System.getProperty("java.io.tmpdir")+"\\downloadExcelFile\\").delete();
//				}
			 fileName=encodeFile.dencodeFile(reqNumber);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		 
		
		 
	    String dirPath = System.getProperty("java.io.tmpdir")+"\\downloadExcelFile\\";
//	     fileName="Bom_errors - Copy.xlsx";
	    byte[] fileBytes = null;
	    try {
	      fileBytes = Files.readAllBytes(Paths.get(dirPath + fileName));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return ResponseEntity.ok()
	        .contentType(MediaType.APPLICATION_OCTET_STREAM)
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
	        .body(fileBytes);
	 
	   
	 }
	
	 @GetMapping(value = "/downloadErrorFile/{reqNumber}",  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	  public ResponseEntity<?> downloadFile(@RequestParam(value="filename") String filename) {
		 public ResponseEntity<?> downloadFile(@PathVariable int reqNumber) {
		 try {
//			 if(new File(System.getProperty("java.io.tmpdir")+"\\downloadExcelFile\\").exists()) {
//					
//					new File(System.getProperty("java.io.tmpdir")+"\\downloadExcelFile\\").delete();
//				}
			encodeFile.dencodeFile(reqNumber);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
	    String dirPath = System.getProperty("java.io.tmpdir")+"\\downloadExcelFile\\";
	    String fileName="bom_error.xlsx";
	    byte[] fileBytes = null;
	    try {
	      fileBytes = Files.readAllBytes(Paths.get(dirPath + fileName));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return ResponseEntity.ok()
	        .contentType(MediaType.APPLICATION_OCTET_STREAM)
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
	        .body(fileBytes);
	 
	   
	 }
	 
	 
	@GetMapping(value = "/excelValidation/{filter}")
	public ResponseEntity<String> callexcelValidation(@PathVariable String filter) throws IOException, SerialException, SQLException {
		excelService.validation(bom_sheet,filter);
		int id=encodeFile.encodeFile(bom_sheet);
		deleteFilesAndFolders.deleteAllFiles(bom_sheet);
	    return ResponseEntity.ok("validation done "+ '\n' + " Request No: "+id );
		
	}
	 
	 
		@GetMapping(value = "/store")
		public ResponseEntity<String> std() throws IOException, SQLException {
			
//			encodeFile.encodeFile(bom_sheet);
			encodeFile.dencodeFile(1);
		    return ResponseEntity.ok("storage done");
			
		}
	 
	 
	    @GetMapping(value = "/call")
		public ResponseEntity<String> call() {
		    return ResponseEntity.ok("hello");  
	    
		    
	}

}
