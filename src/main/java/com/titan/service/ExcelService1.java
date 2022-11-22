package com.titan.service;




import java.lang.reflect.Field;
import java.sql.SQLException;
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.sql.rowset.serial.SerialException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;  
import com.titan.repository.Repository;

@Service
public class ExcelService1  
{  
	 static int rowid = 1;
	 static int q=1;
	
	 static int ii=0;
	
	
	
	

	
	public static void check_fGlength(ArrayList<String> fG_code,Set<String> not_for_upload_fg_code) throws IOException {
		int c=0;
		
		for(String fg: fG_code) {
			
				if(fg.length()!=14){
				System.out.println("length is not 12  "+ fg);
				write_error(fg,"Length of FG code is not 14");
				not_for_upload_fg_code.add(fg);
				c+=1;
			}			
		}
				System.out.println("Everything is ok  " + c);
	}

	
	
	
	public static void write_error(String name, String error) throws IOException {
		
		try {
//			System.getProperty("java.io.tmpdir")+"\\"+"bom_errors.xlsx"
			File xlsxFile = new File(System.getProperty("java.io.tmpdir")+"\\"+"Bom_errors.xlsx");
//			File xlsxFile = new File("C:\\Users\\rokon\\AppData\\Local\\Temp\\bom_errors.xlsx");
//			File xlsxFile = new File("D:\\Bom_errors.xlsx");
//			File xlsxFile = new File("D:\\Bom_errors.xlsx");
	        FileInputStream inputStream = new FileInputStream(xlsxFile);
	        
//		 XSSFWorkbook workbook = new XSSFWorkbook();
	        
	        
	        Workbook workbook = WorkbookFactory.create(inputStream);
	        
	        
	      
	        
            //Reading first sheet of excel file
//            Sheet spreadsheet = workbook.getSheetAt(0);
            Sheet spreadsheet = workbook.getSheet("Sheet1");
	        
	        
	  
	        // creating a row object
	        XSSFRow row;
	        
	        
	        
	        
	       
	
	  
	  
//	  XSSFRow roww = spreadsheet.getRow(num);
//	  roww.getCell(1).setCellValue("asdf");
//	  roww.getCell(2).setCellValue("jghjghj");
	  Cell cell;
	  row = (XSSFRow) spreadsheet.createRow(0);
	  cell =row.createCell(0);
	  cell.setCellValue("Name");
      cell = row.createCell(1);
      cell.setCellValue("Description");
      System.out.println("last number "+spreadsheet.getLastRowNum());
       int t= spreadsheet.getLastRowNum();
     
	  XSSFRow  rowr = (XSSFRow) spreadsheet.createRow(++t);
	 
	  System.out.println("last number active "+t);
	  

     
          cell = rowr.createCell(0);
          cell.setCellValue(name);
          cell = rowr.createCell(1);
          cell.setCellValue(error);
          
          
//	  
//          inputStream.close();
//          System.getProperty("java.io.tmpdir")+"\\"+"bom_errors.xlsx"
          FileOutputStream out = new FileOutputStream(xlsxFile);
//          FileOutputStream out = new FileOutputStream(new File("C:\\Users\\rokon\\AppData\\Local\\Temp\\bom_errors.xlsx"));
          
//          FileOutputStream out = new FileOutputStream(new File("D:\\Bom_errors.xlsx"));
	        workbook.write(out);
	        workbook.close();
	        out.close();
	        inputStream.close();
		}
		
		catch(Exception e) {}
		
	}
	
	
	
	
	public static void delete_rows(Set<String> not_for_upload_fg_code, Map<String, Integer> requiredHeaders, Set<String> not_for_upload_total_code,String bom_sheet) throws IOException {
		
		try {
//			System.getProperty("java.io.tmpdir")+"\\"+"bom_errors.xlsx"
//			File xlsxFile = new File(System.getProperty("java.io.tmpdir")+"\\"+"Bom_errors.xlsx");
//			File xlsxFile = new File(System.getProperty("java.io.tmpdir")+"fixed_file\\test_error.xlsx");
			File xlsxFile = new File(System.getProperty("java.io.tmpdir")+"fixed_file\\"+bom_sheet);
//			File xlsxFile = new File("C:\\Users\\rokon\\AppData\\Local\\Temp\\bom_errors.xlsx");
//			File xlsxFile = new File("D:\\Bom_errors.xlsx");
//			File xlsxFile = new File("D:\\Bom_errors.xlsx");
	        FileInputStream inputStream = new FileInputStream(xlsxFile);
	        
//		 XSSFWorkbook workbook = new XSSFWorkbook();
	        
	        
	        Workbook workbook = WorkbookFactory.create(inputStream);
	        
	       
	      
	        
            //Reading first sheet of excel file
//            Sheet spreadsheet = workbook.getSheetAt(0);
            Sheet spreadsheet = workbook.getSheetAt(0);
	        
	        
	  
	        // creating a row object
	        XSSFRow row;
	        
	        
	        
	      
	        
	        for(String fg_code : not_for_upload_fg_code) {
	        	
	        	 for(int i = 0; i < spreadsheet.getLastRowNum(); i++)
	 	        {
	 	        	XSSFRow roww = (XSSFRow) spreadsheet.getRow(i);
//	 	        	System.out.println("roww.getCell(2).getStringCellValue()  "+roww);
	 	        	 if(roww != null) {
	 	  	            	if(roww.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue().equals(fg_code)) {
	 	  	            		System.out.println("roww.getCell(2).getStringCellValue()  "+roww.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue());
	 	  	            		spreadsheet.removeRow(roww);
	 		  	            	}
	 	  	            	}
	 	  	            	
	 	  	            }
	 	        }
	  	        
	        for(String fg_code : not_for_upload_total_code) {
	        	
	        	 for(int i = 0; i < spreadsheet.getLastRowNum(); i++)
	 	        {
	 	        	XSSFRow roww = (XSSFRow) spreadsheet.getRow(i);
//	 	        	System.out.println("roww.getCell(2).getStringCellValue()  "+roww);
	 	        	 if(roww != null) {
	 	  	            	if(roww.getCell(requiredHeaders.get("QTY")).getAddress().toString().equals(fg_code)) {
	 	  	            		System.out.println("roww.getCell(2).getStringCellValue()  "+roww.getCell(requiredHeaders.get("QTY")).getAddress().toString());
	 	  	            		spreadsheet.removeRow(roww);
	 		  	            	}
	 	  	            	}
	 	  	            	
	 	  	            }
	 	        }
	  	        
	      
	        
	  
	  
//	  XSSFRow roww = spreadsheet.getRow(num);
//	  roww.getCell(1).setCellValue("asdf");
//	
          
          
          
//	  
//          inputStream.close();
//          System.getProperty("java.io.tmpdir")+"\\"+"bom_errors.xlsx"
          FileOutputStream out = new FileOutputStream(xlsxFile);
//          FileOutputStream out = new FileOutputStream(new File("C:\\Users\\rokon\\AppData\\Local\\Temp\\bom_errors.xlsx"));
          
//          FileOutputStream out = new FileOutputStream(new File("D:\\Bom_errors.xlsx"));
	        workbook.write(out);
	        workbook.close();
	        out.close();
	        inputStream.close();
	       
		}
		
		catch(Exception e) {}
		
	}
	
	
	
public static void check_stone_code_length(Map <String, List<String>> fg_code_details,Set<String> not_for_upload_fg_code) throws IOException {
	
	List<String> except_prolif_list= Arrays.asList("P1","P2","P3","P4","00");
	
	for(String key:fg_code_details.keySet()) {
		List<String> temp = fg_code_details.get(key);
		String prolif = key.substring(Math.max(key.length() - 2, 0));
		if(!except_prolif_list.contains(prolif)) {
			for(String temp_key:temp) {
				
				if(temp_key.length()!=12) {
					not_for_upload_fg_code.add(key);
					System.out.println("length of stone code is not 12  "+ temp_key);
					write_error(key,"For this fg code Length of this "+temp_key+" stone code is not 12");
					
				}
				
				
			}
			
		}
	
		
	}
	
	
	
	
	
	
}
	
	
	
public static void check_unique_stoneCode(Map <String, List<String>> fgCode_and_address,Set<String> not_for_upload_fg_code) throws IOException {
	
	
	
	System.out.println("HHHHHHHHHHHHHHHHHHEEEEEEEEEEEEEEEEEEEEEYYYYYYYYYYYYYYYYYYY");
	
//	Map <String, List<String>> fgCode_and_stone_address_value = get_fgCode_and_stone_address_value(fgCode_and_address);
	Map <String, List<String>> fgCode_and_stone_address_value =fgCode_and_address;
	ArrayList <String> stone_unique_or_not = new ArrayList<>();
	
	
	for(String key:fgCode_and_stone_address_value.keySet()) 
	{
		
		List<String> Klist = fgCode_and_stone_address_value.get(key);
		List<String> new_Klist = new ArrayList<>();
		
		for(String str:Klist) {
			
			if(str!="") {
				new_Klist.add(str);
			}
		}
		
		
		Set <String> tps = new HashSet<>(new_Klist);
		
		System.out.println("Klist "+Klist);
		System.out.println("tps "+tps);
		if(new_Klist.size()!=tps.size()) {
			stone_unique_or_not.add(key);
			not_for_upload_fg_code.add(key);
			write_error(key, "Duplicate stone code is present for this FG number");
		}
		
}

System.out.println("This fg code not have unique stone codes "+stone_unique_or_not);
System.out.println("This fg code not have unique stone codes "+stone_unique_or_not.size());
}
	
	
	
	
	

	
	
	public static Map<String, List<String>> get_fgCode_and_stone_address_value(Map <String, List<String>> fgCode_and_address, ArrayList<String> stone_id,String bom_sheet) {
		Map <String, List<String>> fgCode_and_stone_address_value = new TreeMap<>();
				
		
		try {
		    Map<String, Integer> requiredHeaders = new HashMap<>();
//		    System.getProperty("java.io.tmpdir")+"\\"+"bom_sheet.xlsx"
		    
		    FileInputStream fileInputStream = new FileInputStream(new File(System.getProperty("java.io.tmpdir")+"\\"+bom_sheet));
//		    FileInputStream file = new FileInputStream(new File("C:\\Users\\rokon\\Downloads\\BOM UPDATION_TRD 2nd aug.xlsx"));
		    Workbook workbook = new XSSFWorkbook(fileInputStream);
		    DataFormatter formatter = new DataFormatter();
		    Sheet sheet = workbook.getSheetAt(0);
		    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		    
		
		
		Map <String, List<String>> fgCode_and_stone_address = new TreeMap<>();
		

			for(String key:fgCode_and_address.keySet()) 
			{
				ArrayList <String> tp= new ArrayList<>();
				List<String> Klist = fgCode_and_address.get(key);
				for(int j =0; j<= Klist.size()-1;j++) {
					Map <String, List<String>> fgCode_and_stoneAddress= new HashMap<>();
					StringBuilder myName = new StringBuilder(Klist.get(j));
//					myName.setCharAt(0, 'I');
					myName.setCharAt(0, stone_id.get(0).charAt(0));
					tp.add(myName.toString());
					
			}
				fgCode_and_stone_address.put(key, tp);
		}
		
		System.out.println("fgCode_and_stone_address  "+fgCode_and_stone_address);
	
		
		
		
		
		
		for(String key:fgCode_and_stone_address.keySet()) 
		{
			
			ArrayList <String> tp= new ArrayList<>();
			List<String> Klist = fgCode_and_stone_address.get(key);
			
			System.out.println("inside    "+Klist);
			
			for(int j =0; j<= Klist.size()-1;j++) {
				Map <String, List<String>> fgCode_and_stoneAddress= new HashMap<>();
//				StringBuilder myName = new StringBuilder(Klist.get(j));
//				myName.setCharAt(0, 'I');
				
				CellReference cellReference = new CellReference(Klist.get(j)); 
		      	 Row roww = sheet.getRow(cellReference.getRow()); 
		      	 Cell celll = roww.getCell(cellReference.getCol()); 
		      	 CellValue cellValue = evaluator.evaluate(celll); 
		      	 if( key.charAt(12)=='P'|| key.charAt(12)=='0' && cellValue == null) {
		      		if( key.charAt(13)=='1'||key.charAt(13)=='2'||key.charAt(13)=='3'|| key.charAt(13)=='4'|| key.charAt(13)=='0') {
		      			tp.add("");
		      		}
		      		 
		      	 }
		      	 else {tp.add(cellValue.getStringValue());
		      	System.out.println(" key and value cellValue.getStringValue()  54354365464565346756756754  "+ key + "  "+cellValue.getStringValue());
		      	}
				
					
				
				
//				tp.add(cellValue.getStringValue());
				
		}
			fgCode_and_stone_address_value.put(key, tp);
	}
	
	System.out.println("fgCode_and_stone_address_value  "+fgCode_and_stone_address_value);
		
		
		
		
		
		
//	ArrayList <String> stone_unique_or_not = new ArrayList<>();
//	
//	
//	for(String key:fgCode_and_stone_address_value.keySet()) 
//	{
//		
//		List<String> Klist = fgCode_and_stone_address_value.get(key);
//		Set <String> tps = new HashSet<>(Klist);
//		
//		System.out.println("Klist "+Klist);
//		System.out.println("tps "+tps);
//		if(Klist.size()!=tps.size()) {
//			stone_unique_or_not.add(key);
//			write_error(key, "This fg code does not have unique number");
//		}
//		
//}
//
//System.out.println("This fg code not have unique stone codes "+stone_unique_or_not);
//System.out.println("This fg code not have unique stone codes "+stone_unique_or_not.size());
	
	
	
	
		
//		for(String key:fgCode_and_stone_address.keySet()) {
//			List<String> Klist = fgCode_and_address.get(key);
//			for(int j =0; j<= Klist.size()-1;j++) {
//	      	 CellReference cellReference = new CellReference(Klist.get(j)); 
//	      	 Row roww = sheet.getRow(cellReference.getRow());
//	      	 Cell celll = roww.getCell(cellReference.getCol()); 
//	      	 CellValue cellValue = evaluator.evaluate(celll);
//
//	      	  System.out.println("for total cell address "+ cellValue.getStringValue());
//	      	  
//	      	  
//	      	  
//			}
////	      	  Double data=cellValue.getNumberValue();
////	      	  int value = data.intValue();
////	      	  System.out.println(value);
//	      	    }
//		
		workbook.close();
		
		fileInputStream.close();
		
		
	}
	
	
	    
	
	catch(Exception e) {}
		System.out.println("fgCode_and_stone_address_value  @@@@@@@@@@@@@@@@@@@@@@@"+fgCode_and_stone_address_value);
		return fgCode_and_stone_address_value;
}
	
	
	
	
	
	
	public static void get_fgCodeAddress(HashSet<String> unique_fgCode, ArrayList<String> fG_code,ArrayList<String> allAddress)
	{
		
		ArrayList<String> uFgCode =new ArrayList<>(unique_fgCode);
		
		
		
		
		
		
		
//		if (cell.getStringCellValue().equals(fG_code)) {
//			allAddress.add(cell.getAddress().toString(););
//       	 
//		}
		
		
		for(int j=0; j <= uFgCode.size()-1 ; j++) {
			
			
			
			for (int i = 1; i <= fG_code.size()-1 ; i++) {
		    	
			       
				        
		        if (uFgCode.get(j).equals(fG_code.get(i))) {
		        	System.out.println("true is true");
		        	allAddress.add(((Cell) fG_code).getAddress().toString());
		          }
		        
		      
		        }
			
			
			
		}
		
		
		
		        
	        
	        
	        
	    }
	    
		
	
	public static void check_prolif_color_quality_validations(Map <String, List<String>> fgCode_and_address ,Set<String> not_for_upload_fg_code) throws IOException {
		Map<String, Map<Map<String, String>,Map<String, String>>>quality_color_map_details;
		Map <String, List<String>> fg_code_details;
		String[] prolif_10= {"01","02","03","04","05","06","07","08","09","10"};
		String[] prolif_11_20_em= {"11","14","17"};
		String[] prolif_11_20_ru= {"12","15","18","20"};
		String[] prolif_11_20_sa= {"13","16","19"};
		
		String quality = null;
		String color = null;
		String[] prolif_21_36= {"21","28","29","30","31","32","33","34","35","36"};
		
		String[] prolif_47_52= {"47","52"};
		
		List<String> prolif_22= Arrays.asList("22");
		List<String> prolif_47_52_list= Arrays.asList(prolif_47_52);
		List<String> prolif_21_36_dcs= Arrays.asList(prolif_21_36);
		List<String> prolif11_20_sa= Arrays.asList(prolif_11_20_sa);
		List<String> prolif11_20_ru= Arrays.asList(prolif_11_20_ru);
		List<String> prolif11_20_em= Arrays.asList(prolif_11_20_em);
		List<String> prolif1_10= Arrays.asList(prolif_10);
		
		
		
		
		quality_color_map_details= qcMap();
		
		System.out.println(" qcMap()   insideeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee//////////////////////   ");
		System.out.println(" qcMap()   "+quality_color_map_details);

		
		fg_code_details=fgCode_and_address;
		
//		fg_code_details=get_fgCode_and_stone_address_value(fgCode_and_address);
//		if(prolif.equals("01"))
		
		
		for(String key:fg_code_details.keySet()) {
			
			String prolif = key.substring(Math.max(key.length() - 2, 0));
			
			
			
			
			if(prolif_22.contains(prolif)  ) {
//				System.out.println(" prolif  --------------->>>>>>>>>>>      "+prolif);
				List<String> temp = fg_code_details.get(key);  
				System.out.println("dimond fg code prolif "+prolif+  "  "+key);
				for(String temp_key:temp) {
					for (Map<String,String> r:quality_color_map_details.get(prolif).keySet()) {
//						System.out.println(" quality  --------------->>>>>>>>>>>      "+r.get("quality"));
						quality=r.get("quality");
					}
					for (Map<String,String> r:quality_color_map_details.get(prolif).values()) {
						System.out.println(r.get("color"));
						color=r.get("color");
					}
					
					if(temp_key.charAt(2)=='D' && temp_key.charAt(3)=='I' ) {
					if(temp_key.charAt(5)==quality.charAt(0) && temp_key.charAt(6)==color.charAt(0) ) {
						
						System.out.println("color quality is correct "+temp_key);
						
					}else {
						not_for_upload_fg_code.add(key);
						write_error(temp_key, " color quality is not correct in this stone code for prolif "+prolif+"  and FG code is "+key+"  ");
						System.out.println("color quality not present ");
					}
					
					}
			
			}
			
			}
			
			
			
			
			
			
			
			
			
			
			
			
		if(prolif1_10.contains(prolif)  ) {
//			System.out.println(" prolif  --------------->>>>>>>>>>>      "+prolif);
			List<String> temp = fg_code_details.get(key);  
			System.out.println("dimond fg code prolif "+prolif+  "  "+key);
			for(String temp_key:temp) {
				for (Map<String,String> r:quality_color_map_details.get(prolif).keySet()) {
//					System.out.println(" quality  --------------->>>>>>>>>>>      "+r.get("quality"));
					quality=r.get("quality");
				}
				for (Map<String,String> r:quality_color_map_details.get(prolif).values()) {
					System.out.println(r.get("color"));
					color=r.get("color");
				}
				
				if(temp_key.charAt(2)=='D' && temp_key.charAt(3)=='I' ) {
				if(temp_key.charAt(5)==quality.charAt(0) && temp_key.charAt(6)==color.charAt(0) ) {
					
					System.out.println("color quality is correct "+temp_key);
					
				}else {
					not_for_upload_fg_code.add(key);
					write_error(temp_key, " color quality is not correct in this stone code for prolif "+prolif+"  and FG code is "+key+"  ");
					System.out.println("color quality not present ");
				}
				
				}
		
		}
		
		}
		
		
				if(prolif11_20_em.contains(prolif)) {
			
					List<String> temp = fg_code_details.get(key);
					System.out.println("dimond fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						for (Map<String,String> r:quality_color_map_details.get(prolif).keySet()) {
//							System.out.println(" quality  --------------->>>>>>>>>>>      "+r.get("quality"));
							quality=r.get("quality");
						}
						for (Map<String,String> r:quality_color_map_details.get(prolif).values()) {
							System.out.println(r.get("color"));
							color=r.get("color");
						}
				if(temp_key.charAt(2)=='E' && temp_key.charAt(3)=='M' ) {
					if(temp_key.charAt(5)==quality.charAt(0) && temp_key.charAt(6)==color.charAt(0) ) {
						
						System.out.println("color quality is correct "+temp_key);
						
					}else {
						not_for_upload_fg_code.add(key);
						write_error(temp_key, " color quality is not correct in this stone code for prolif "+prolif+"");
						System.out.println("color quality not present ");
					}
					
					}
		
		
		}
		
		}
		
				
				if(prolif11_20_ru.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					System.out.println("ruby fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						for (Map<String,String> r:quality_color_map_details.get(prolif).keySet()) {
//							System.out.println(" quality  --------------->>>>>>>>>>>      "+r.get("quality"));
							quality=r.get("quality");
						}
						for (Map<String,String> r:quality_color_map_details.get(prolif).values()) {
							System.out.println(r.get("color"));
							color=r.get("color");
						}
				if(temp_key.charAt(2)=='R' && temp_key.charAt(3)=='U' ) {
					if(temp_key.charAt(5)==quality.charAt(0) && temp_key.charAt(6)==color.charAt(0) ) {
						
						System.out.println("color quality is correct "+temp_key);
						
					}else {
						not_for_upload_fg_code.add(key);
						write_error(temp_key, " color quality is not correct in this stone code for prolif "+prolif+"");
						System.out.println("color quality not present ");
					}
					
					}
				
		
		
		}
		
		}		
				
			
				if(prolif11_20_sa.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						for (Map<String,String> r:quality_color_map_details.get(prolif).keySet()) {
//							System.out.println(" quality  --------------->>>>>>>>>>>      "+r.get("quality"));
							quality=r.get("quality");
						}
						for (Map<String,String> r:quality_color_map_details.get(prolif).values()) {
							System.out.println(r.get("color"));
							color=r.get("color");
						}
				if(temp_key.charAt(2)=='S' && temp_key.charAt(3)=='A' ) {
					if(temp_key.charAt(5)==quality.charAt(0) && temp_key.charAt(6)==color.charAt(0) ) {
						
						System.out.println("color quality is correct "+temp_key);
						
					}else {
						not_for_upload_fg_code.add(key);
						write_error(temp_key, " color quality is not correct in this stone code for prolif "+prolif+"");
						System.out.println("color quality not present ");
					}
					
					}
				
		
		
		}
		
		}			
				
			
				
//		if(prolif_21_36_dcs.contains(prolif)) {
//					
//					List<String> temp = fg_code_details.get(key);
//					List<String> check_di_cs = new ArrayList<>();
//					check_di_cs.add("DI");check_di_cs.add("SA");check_di_cs.add("RU");check_di_cs.add("EM");
//					
//				
//					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
//					for(String temp_key:temp) {
//				
//						String di_or_cs= String.format("%c%c", temp_key.charAt(2), temp_key.charAt(3));
//						System.out.println("String.format(\"%c%c\", temp_key.charAt(2), temp_key.charAt(3));  "+di_or_cs);
//						for (Map<String,String> r:quality_color_map_details.get(prolif).keySet()) {
////							System.out.println(" quality  --------------->>>>>>>>>>>      "+r.get("quality"));
//							quality=r.get("quality");
//						}
//						for (Map<String,String> r:quality_color_map_details.get(prolif).values()) {
//							System.out.println(r.get("color"));
//							color=r.get("color");
//						}
//						
//				
//						if(check_di_cs.contains(di_or_cs) ) {
//							if(temp_key.charAt(5)==quality.charAt(0) && temp_key.charAt(6)==color.charAt(0) ) {
//								
//								System.out.println("color quality is correct "+temp_key);
//								
//							}else {
//								not_for_upload_fg_code.add(key);
//								write_error(temp_key, " color quality is not correct in this stone code for prolif "+prolif+"");
//								System.out.println("color quality not present ");
//							}
//							
//							}
//						
						
					if(prolif_21_36_dcs.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					List<String> check_di_cs = new ArrayList<>();
					check_di_cs.add("DI");
					
				
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						String di_or_cs= String.format("%c%c", temp_key.charAt(2), temp_key.charAt(3));
						System.out.println("String.format(\"%c%c\", temp_key.charAt(2), temp_key.charAt(3));  "+di_or_cs);
						for (Map<String,String> r:quality_color_map_details.get(prolif).keySet()) {
//							System.out.println(" quality  --------------->>>>>>>>>>>      "+r.get("quality"));
							quality=r.get("quality");
						}
						for (Map<String,String> r:quality_color_map_details.get(prolif).values()) {
							System.out.println(r.get("color"));
							color=r.get("color");
						}
						
				
						if(check_di_cs.contains(di_or_cs) ) {
							if(temp_key.charAt(5)==quality.charAt(0) && temp_key.charAt(6)==color.charAt(0) ) {
								
								System.out.println("color quality is correct "+temp_key);
								
							}else {
								not_for_upload_fg_code.add(key);
								write_error(temp_key, " color quality is not correct in this stone code for prolif "+prolif+"");
								System.out.println("color quality not present ");
							}
							
							}
						
						
		
		
		}
		
		}			
			
				
				
				
				
					if(prolif_47_52_list.contains(prolif)) {
						
						List<String> temp = fg_code_details.get(key);
						List<String> check_di_cs = new ArrayList<>();
						check_di_cs.add("DI");
						
					
						System.out.println("saphire fg code prolif "+prolif+  "  "+key);
						for(String temp_key:temp) {
					
							String di_or_cs= String.format("%c%c", temp_key.charAt(2), temp_key.charAt(3));
							System.out.println("String.format(\"%c%c\", temp_key.charAt(2), temp_key.charAt(3));  "+di_or_cs);
							for (Map<String,String> r:quality_color_map_details.get(prolif).keySet()) {
//								System.out.println(" quality  --------------->>>>>>>>>>>      "+r.get("quality"));
								quality=r.get("quality");
							}
							for (Map<String,String> r:quality_color_map_details.get(prolif).values()) {
								System.out.println(r.get("color"));
								color=r.get("color");
							}
							
					
							if(check_di_cs.contains(di_or_cs) ) {
								if(temp_key.charAt(5)==quality.charAt(0) && temp_key.charAt(6)==color.charAt(0) ) {
									
									System.out.println("color quality is correct "+temp_key);
									
								}else {
									not_for_upload_fg_code.add(key);
									write_error(temp_key, " color quality is not correct in this stone code for prolif "+prolif+"");
									System.out.println("color quality not present ");
								}
								
								}
							
							
			
			
			}
			
			}			
				
					
				
				
				
				
				
				
		
		}
	
		

		
		
		
		
		
		
		
		
	}
	
	
	
	public static Map<String, Map<Map<String, String>,Map<String, String>>> qcMap() {
		
        
//
//		String  prolif[]= {"1","2","3","4","5","6","7","8","9","10","D2","11","12","13","14",
//				"15","16","17","18","19","20","21","22","28","29","30","31","32","33","34","35","36",
//				"40","88","44","47","52","C2","DT","DC","DR","CZ","P1","P2","P3","P4","P5","P6","PL","00"};
		
		
		
		String  prolif[]= {"01","02","03","04","05","06","07","08","09","10","D2","11","12","13","14",
				"15","16","17","18","19","20","21","22","28","29","30","31","32","33","34","35","36","47","52","C2"};
		
		String quality[]= {"V","R","S","C","C","R","M","U","J","Y","R","D","D","D","B",
			"B","B","E","E","E","G","Y","R","U","J","U","V","R","S","C","C","R","M","R","R"};
		
		
		
		String color[]= {"A","A","A","A","V","N","A","V","A","3","X","G","R","E","G",
			"R","E","G","R","E","R","3","T","V","A","C","A","A","A","A","V","N","A","T","X"};

			System.out.println("prolif"+prolif.length);
			System.out.println("q"+quality.length);
			System.out.println("c"+color.length);
			Map<String, Map<Map<String, String>,Map<String, String>>>quality_color_map = new HashMap<>();
			Map<String, String>map3 = new HashMap<>();

			map3.put("col", "blk");
			map3.put("qty", "new");
			System.out.println(map3);
//			for(int f=0; f<=7;f++) {
//				System.out.println(f);
//			}
//			
			
			for ( ii=0 ;ii<=prolif.length-1;ii++) {
//		
//		Map<String, Map<Map<String, String>,Map<String, String>>>map2 = new HashMap<>();
//		
//		map2.put("2", new HashMap(){{put(new HashMap(){{put("colour","black");}},new HashMap(){{put("quality","new");}});}});
//		
				
			
				
				quality_color_map.put(prolif[ii], new HashMap(){{put(new HashMap(){{put("quality",quality[ii]);}},new HashMap(){{put("color",color[ii]);}});}});
				
				
		
			}
			return quality_color_map;
		
		
	}
	
		
	public static void check_prolif_validations(Map <String, List<String>> fgCode_and_address,Set<String> not_for_upload_fg_code) throws IOException, SerialException, SQLException {
		Map <String, List<String>> fg_code_details=fgCode_and_address;
//		Map<String,String> solitaire_precious_37_99_map =new HashMap<>();
//		solitaire_precious_37_99_map.put("50E2D2FEMLAA37", "A9");
//		solitaire_precious_37_99_map.put("50E2D2FEMLAA38", "77");
//		solitaire_precious_37_99_map.put("50E2D2FEMLAA39", "78");
		
		Repository repository = new Repository();
		Map<String,String> solitaire_precious_37_99_map =repository.fetchMasterData();
		
		String[] prolif_10= {"01","02","03","04","05","06","07","08","09","10","22"};
		String[] prolif_11_20_em= {"11","14","17"};
		String[] prolif_11_20_ru= {"12","15","18","20"};
		String[] prolif_11_20_sa= {"13","16","19"};
		String[] prolif_21_36= {"21","28","29","30","31","32","33","34","35","36"};
		String[] prolif_47_52= {"47","52"};
		String[] prolif_cz_cl= {"88"};
		String[] prolif_cz= {"CZ"};
		String[] prolif_44= {"EM","RU"};
		String[] prolif_p5_p6= {"DI"};
		String[] prolif_PL= {"PL"};
		String[] prolif_p1_p4= {"P1","P2","P3","P4","00"};
		String[] prolif_solitaire= {"77","A9","B9"};
		String[] prolif_precious_stone= {"78","A3","B3"};
		List<String>  prolif_37_99_list= new ArrayList<>();
		
		for(int i=37;i<=99;i++) 
		{
			prolif_37_99_list.add(String.valueOf(i));
		}
		
		
		List<String> except_prolif_list= Arrays.asList("40","88","44","47","52");
		
		
	
		List<String> prolif_solitaire_list= Arrays.asList(prolif_solitaire);
		List<String> prolif_precious_stone_list= Arrays.asList(prolif_precious_stone);
		List<String> prolif_p1_p4_list= Arrays.asList(prolif_p1_p4);
		List<String> prolif_40_list= Arrays.asList("40");
		List<String> prolif_PLlist= Arrays.asList(prolif_PL);
		List<String> prolif_p5_p6_list= Arrays.asList(prolif_p5_p6);
		List<String> prolif_44_list= Arrays.asList(prolif_44);
		List<String> prolif_cz_list= Arrays.asList(prolif_cz);
		List<String> prolif_cz_cl_list= Arrays.asList(prolif_cz_cl);
		List<String> prolif_47_52_list= Arrays.asList(prolif_47_52);
		List<String> prolif_21_36_dcs= Arrays.asList(prolif_21_36);
		List<String> prolif11_20_sa= Arrays.asList(prolif_11_20_sa);
		List<String> prolif11_20_ru= Arrays.asList(prolif_11_20_ru);
		List<String> prolif11_20_em= Arrays.asList(prolif_11_20_em);
		List<String> prolif1_10= Arrays.asList(prolif_10);
		
		
		
		
		
		
		
//		fg_code_details=get_fgCode_and_stone_address_value(fgCode_and_address);
//		if(prolif.equals("01"))
		
		
		for(String key:fg_code_details.keySet()) {
			
			String prolif = key.substring(Math.max(key.length() - 2, 0));
			
		if(prolif1_10.contains(prolif)) {
			int count=0;
			int other=0;
			List<String> temp = fg_code_details.get(key);
			System.out.println("dimond fg code prolif "+prolif+  "  "+key);
			for(String temp_key:temp) {
				
				if(temp_key.charAt(2)=='D' && temp_key.charAt(3)=='I' ) {
					count+=1;
					System.out.println("dimond present "+temp_key);
					
				}else {
					other+=1;
//					not_for_upload_fg_code.add(key);
//					write_error(temp_key, " DI is not present in this stone code for prolif "+prolif+"");
//					System.out.println("dimond not present ");
				}
				
		
		
		}
			
			if(count<=0) {
				not_for_upload_fg_code.add(key);
				write_error(key, " 0 DI is present in this fg code for prolif "+prolif+"");
			}
			if(other>=1) {
				not_for_upload_fg_code.add(key);
				write_error(key, other+" other stone code is present in this fg code for prolif "+prolif+"");
			}
		
		}
		
		
				if(prolif11_20_em.contains(prolif)) {
					int count=0;
					int other=0;
					List<String> temp = fg_code_details.get(key);
					System.out.println("dimond fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
				if(temp_key.charAt(2)=='E' && temp_key.charAt(3)=='M' ) {
					count+=1;
					System.out.println("Emrald present "+temp_key);
					
				}else {
					other+=1;
//					not_for_upload_fg_code.add(key);
//					write_error(temp_key, " EM is not present in this stone code for prolif "+prolif+"");
//					System.out.println("Emrald not present ");
				}
				
		
		
		}
					
					if(count<=0) {
						not_for_upload_fg_code.add(key);
						write_error(key, " 0 EM is present in this fg code for prolif "+prolif+"");
					}
					if(other>=1) {
						not_for_upload_fg_code.add(key);
						write_error(key, other+" other stone code is present in this fg code for prolif "+prolif+"");
					}	
					
		
		}
		
				
				if(prolif11_20_ru.contains(prolif)) {
					int count=0;
					int other=0;
					List<String> temp = fg_code_details.get(key);
					System.out.println("ruby fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
				if(temp_key.charAt(2)=='R' && temp_key.charAt(3)=='U' ) {
					count+=1;
					System.out.println("ruby present "+temp_key);
				
				}else {
					other+=1;
//					not_for_upload_fg_code.add(key);
//					write_error(temp_key, " RU is not present in this stone code for prolif "+prolif+"");
//					System.out.println("ru not present ");
				}
				
		
		
		}
					
					if(count<=0) {
						not_for_upload_fg_code.add(key);
						write_error(key, " 0 RU is present in this fg code for prolif "+prolif+"");
					}
					if(other>=1) {
						not_for_upload_fg_code.add(key);
						write_error(key, other+" other stone code is present in this fg code for prolif "+prolif+"");
					}		
					
		
		}		
				
			
				if(prolif11_20_sa.contains(prolif)) {
					int count=0;
					int other=0;
					List<String> temp = fg_code_details.get(key);
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
				if(temp_key.charAt(2)=='S' && temp_key.charAt(3)=='A' ) {
					count+=1;
					System.out.println("saphire present "+temp_key);
				
				}else {
					other+=1;
//					not_for_upload_fg_code.add(key);
//					write_error(temp_key, " SA is not present in this stone code for prolif "+prolif+"");
//					System.out.println("ru not present ");
				}
				
		
		
		}
					
					if(count<=0) {
						not_for_upload_fg_code.add(key);
						write_error(key, " 0 SA is present in this fg code for prolif "+prolif+"");
					}
					if(other>=1) {
						not_for_upload_fg_code.add(key);
						write_error(key, other+" other stone code is present in this fg code for prolif "+prolif+"");
					}	
		
		}			
				
		
				if(prolif_21_36_dcs.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					List<String> check_di = new ArrayList<>();
					List<String> check_cs = new ArrayList<>();
					int dimond_count=0;
					int cs_count=0;
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						
						
						
						if(temp_key.charAt(2)=='D' && temp_key.charAt(3)=='I' ) {
							
							System.out.println("dimond "+temp_key);
							dimond_count+=1;
						
						}else {
							cs_count+=1;
						}
						
					}	
						if(dimond_count>=1 && cs_count>=1 ) {
							System.out.println(" both dimond and cs are present ");
							
						}else { 
							
							not_for_upload_fg_code.add(key);
							write_error(key, dimond_count+" Dimond and "+cs_count+" colour stones are present for this prolif "+prolif+"");
							System.out.println("DI not present ");
						}
						
						
//				if(temp_key.charAt(2)=='D' && temp_key.charAt(3)=='I' ) {
//					
//					System.out.println("dimond "+temp_key);
//					dimond_count+=1;
//				
//				}else {
//					not_for_upload_fg_code.add(key);
//					write_error(temp_key, " DI is not present in this stone code for prolif "+prolif+"");
//					System.out.println("DI not present ");
//				}
				
		
		
//		}
		
		}			
				
				
			
				
				if(prolif_47_52_list.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					List<String> check_di = new ArrayList<>();
					List<String> check_cs = new ArrayList<>();
					int dimond_count=0;
					int cs_count=0;
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						
						
						
						if(temp_key.charAt(2)=='D' && temp_key.charAt(3)=='I' ) {
							
							System.out.println("dimond "+temp_key);
							dimond_count+=1;
						
						}else {
							cs_count+=1;
						}
						
					}	
						if(dimond_count>=1 && cs_count>=1 ) {
							System.out.println(" both dimond and cs are present ");
							
						}else { 
							
							not_for_upload_fg_code.add(key);
							write_error(key, dimond_count+" Dimond and "+cs_count+" colour stones are present for this prolif "+prolif+"");
							System.out.println("DI not present ");
						}
						

		
		}			
				
	
				
			if(prolif_cz_cl_list.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					List<String> check_di = new ArrayList<>();
					List<String> check_cs = new ArrayList<>();
					int cz_count=0;
					int cl_count=0;
					int other=0;
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						
						
						
						if(temp_key.charAt(2)=='C' && temp_key.charAt(3)=='Z' ) {
							
							System.out.println("dimond "+temp_key);
							cz_count+=1;
						
						}
						else if(temp_key.charAt(2)=='C' && temp_key.charAt(3)=='L' ) {
							
							System.out.println("dimond "+temp_key);
							cl_count+=1;
						
						}else {
							other+=1;
						}
						
					}	
						if(cz_count>=1 && cl_count>=1 && other==0) {
							System.out.println(" both cz and cl are present ");
							
						}else { 
							
							not_for_upload_fg_code.add(key);
							write_error(key, cz_count+" CZ and "+cl_count+" CL colour stones are present for this prolif "+prolif+"");
							System.out.println("DI not present ");
						}
						if(cz_count>=1 && cl_count>=1 && other>=1) {
							System.out.println(" both cz and cl are present and other is also present ");
							not_for_upload_fg_code.add(key);
							write_error(key, other+" other  colour stones are present for this prolif "+prolif+"");
						}
//							else { 
//							
//							not_for_upload_fg_code.add(key);
//							write_error(key, other+" other  colour stones are present for this prolif"+prolif+"");
////							System.out.println("DI not present ");
//						}
						

		
		}			
				
	
			
			
			if(prolif_44_list.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					List<String> check_di = new ArrayList<>();
					List<String> check_cs = new ArrayList<>();
					int ru_count=0;
					int em_count=0;
					int other=0;
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						
						
						
						if(temp_key.charAt(2)=='E' && temp_key.charAt(3)=='M' ) {
							
							System.out.println("dimond "+temp_key);
							em_count+=1;
						
						}
						else if(temp_key.charAt(2)=='R' && temp_key.charAt(3)=='U' ) {
							
							System.out.println("dimond "+temp_key);
							ru_count+=1;
						
						}else {
							other+=1;
						}
						
					}	
						if(em_count>=1 && ru_count>=1 && other==0) {
							System.out.println(" both cz and cl are present ");
							
						}else { 
							
							not_for_upload_fg_code.add(key);
							write_error(key, em_count+" EM and "+ru_count+" RU colour stones are present for this prolif"+prolif+"");
							System.out.println("DI not present ");
						}
						if(em_count>=1 && ru_count>=1 && other>=1) {
							System.out.println(" both cz and cl are present and other is also present ");
							not_for_upload_fg_code.add(key);
							write_error(key, other+" other  colour stones are present for this prolif"+prolif+"");
						}
//						else { 
//							
//							not_for_upload_fg_code.add(key);
//							write_error(key, other+" other  colour stones are present for this prolif"+prolif+"");
////							System.out.println("DI not present ");
//						}
						

		
		}			
				
			
			
			
			if(prolif_40_list.contains(prolif)) {
				
				List<String> temp = fg_code_details.get(key);
				
				int ru_count=0;
				int em_count=0;
				int sa_count=0;
				int other=0;
				System.out.println("saphire fg code prolif "+prolif+  "  "+key);
				for(String temp_key:temp) {
			
					
					
					
					if(temp_key.charAt(2)=='S' && temp_key.charAt(3)=='A' ) {
						
						System.out.println("sa "+temp_key);
						sa_count+=1;
					
					}
					if(temp_key.charAt(2)=='E' && temp_key.charAt(3)=='M' ) {
						
						System.out.println("em "+temp_key);
						em_count+=1;
					
					}
					if(temp_key.charAt(2)=='R' && temp_key.charAt(3)=='U' ) {
						
						System.out.println("ru "+temp_key);
						ru_count+=1;
					
					}
					
					if(!(temp_key.charAt(2)=='R' && temp_key.charAt(3)=='U') && !(temp_key.charAt(2)=='E' && temp_key.charAt(3)=='M') && !(temp_key.charAt(2)=='S' && temp_key.charAt(3)=='A')) {
						other+=1;
					}
					
				}	
				if(em_count>=1 && ru_count>=1 && other==0 ||em_count>=1 && sa_count>=1 && other==0|| ru_count>=1 && sa_count>=1 && other==0 || em_count>=1 && ru_count>=1 && sa_count>=1 && other==0 ) {
					
					System.out.println("no error for prolif 40");
					
				}else { 
					
					not_for_upload_fg_code.add(key);
					write_error(key, other+" other colour stones "+ em_count+" EM and "+ru_count+" RU  and "+sa_count+" SA colour stones are present for this prolif"+prolif+"");
					
				}
				
					
//					if(other>=1 ) {
//						not_for_upload_fg_code.add(key);
//						write_error(key, other+" other  colour stones are present for this prolif"+prolif+"");
//					}

	
	}	
			
			
			
			
			
			
			
			
			
	
			
			if(prolif_p5_p6_list.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					List<String> check_di = new ArrayList<>();
					List<String> check_cs = new ArrayList<>();
					int dimond_count=0;
					
					int other=0;
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						
						
						
						if(temp_key.charAt(2)=='D' && temp_key.charAt(3)=='I' ) {
							
							System.out.println("dimond "+temp_key);
							dimond_count+=1;
						
						}
						else {
							other+=1;
						}
						
					}	
						if(dimond_count>=1  && other==0) {
							System.out.println(" both cz and cl are present ");
							
						}else { 
							
							not_for_upload_fg_code.add(key);
							write_error(key, dimond_count+" DI stones are present for this prolif"+prolif+"");
							System.out.println("DI not present ");
						}
						if(dimond_count>=1 && other>=1) {
							
							not_for_upload_fg_code.add(key);
							write_error(key, other+" other  colour stones are present for this prolif "+prolif+"");
						}
//						else { 
//							
//							not_for_upload_fg_code.add(key);
//							write_error(key, other+" other  colour stones are present for this prolif"+prolif+"");
////							System.out.println("DI not present ");
//						}
						

		
		}			
		
				
			
			
			
				if(prolif_PLlist.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					List<String> check_di = new ArrayList<>();
					List<String> check_cs = new ArrayList<>();
					int pl_count=0;
					
					int other=0;
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						
						
						
						if(temp_key.charAt(2)=='P' && temp_key.charAt(3)=='L' ) {
							
							System.out.println("dimond "+temp_key);
							pl_count+=1;
						
						}
						else {
							other+=1;
						}
						
					}	
						if(pl_count>=1  && other==0) {
							System.out.println(" PL greater than 1 are present ");
							
						}else { 
							
							not_for_upload_fg_code.add(key);
							write_error(key, pl_count+" PL stones are present for this prolif"+prolif+"");
							System.out.println("DI not present ");
						}
						if(pl_count>=1 && other>=1) {
							
							not_for_upload_fg_code.add(key);
							write_error(key, other+" other  colour stones are present for this prolif "+prolif+"");
							
						}
//						else { 
//							
//							not_for_upload_fg_code.add(key);
//							write_error(key, other+" other  colour stones are present for this prolif"+prolif+"");
////							System.out.println("DI not present ");
//						}
						

		
		}			
		
			
			
			
				
				
				if(prolif_cz_list.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					List<String> check_di = new ArrayList<>();
					List<String> check_cs = new ArrayList<>();
					int cz_count=0;
					
					int other=0;
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						
						
						
						if(temp_key.charAt(2)=='C' && temp_key.charAt(3)=='Z' ) {
							
							System.out.println("dimond "+temp_key);
							cz_count+=1;
						
						}
						else {
							other+=1;
						}
						
					}	
						if(cz_count>=1  && other==0) {
							System.out.println(" both cz and cl are present ");
							
						}else { 
							
							not_for_upload_fg_code.add(key);
							write_error(key, cz_count+" CZ stones are present for this prolif"+prolif+"");
							
						}
						if(cz_count>=1 && other>=1) {
							not_for_upload_fg_code.add(key);
							write_error(key, other+" other  colour stones are present for this prolif"+prolif+"");
							
						}
//						else { 
//							
//							not_for_upload_fg_code.add(key);
//							write_error(key, other+" other  colour stones are present for this prolif"+prolif+"");
////							System.out.println("DI not present ");
//						}
						

		
		}			
		
					if(prolif_p1_p4_list.contains(prolif)) {
					
					List<String> temp = fg_code_details.get(key);
					List<String> check_di = new ArrayList<>();
					List<String> check_cs = new ArrayList<>();
					
					
					int other=0;
					System.out.println("saphire fg code prolif "+prolif+  "  "+key);
					for(String temp_key:temp) {
				
						
						
						
						if(temp_key.isEmpty()||temp_key.equals("")) {
							
							
						}
						else {
							other+=1;
							
						}
						
					}	
						
						if( other>=1) {
							System.out.println(" other is also present ");
							not_for_upload_fg_code.add(key);
							write_error(key, other+" other colour stones are present for this prolif"+prolif+"");
						}
						

		
		}			
					
					
							
//					if(Integer.parseInt(prolif) >= 37 && Integer.parseInt(prolif)<=99) {
					if(prolif_37_99_list.contains(prolif)) {
						List<String> temp = fg_code_details.get(key);
						int corret_solataire = 0;
						int corret_ps = 0;
						int other =0;
						if(!except_prolif_list.contains(prolif)) {
							if(solitaire_precious_37_99_map.containsKey(key)) {
								if(prolif_solitaire_list.contains(solitaire_precious_37_99_map.get(key)))
								{    
									for(String temp_key:temp) {
										
										
										List <Character> solitaire_color=Arrays.asList('C', 'D', 'E', 'U', 'V','W', 'T', 'Q', 'R');
										
										if(temp_key.charAt(2)=='D' && temp_key.charAt(3)=='I' ) {
											if(solitaire_color.contains(temp_key.charAt(5)) ) {
												
												corret_solataire+=1;
											}
											
										}else {
											other+=1;
										}
										
										
									}	
									
									
									if(corret_solataire>=1) {
										
										
									}else {
										not_for_upload_fg_code.add(key);
										
										write_error(key," For this fg code there is no dimond present which has color at 6th character from this list ->  ['C', 'D', 'E', 'U', 'V','W', 'T', 'Q', 'R'] ");
									}
									
									
									
								}
								if(prolif_precious_stone_list.contains(solitaire_precious_37_99_map.get(key)))
								{    
										for(String temp_key:temp) {
										
										if(temp_key.charAt(2)=='D' && temp_key.charAt(3)=='I' ) {
											corret_ps+=1;
											
										}else {
											other+=1;
										}
										
										
									}	
									
									
									if(corret_ps>=1) {
										not_for_upload_fg_code.add(key);
										write_error(key," For this fg code there is "+corret_ps+" dimond present  ");

										
									}else {	}
									
								}	
									
								write_error(key," This fg code is not present in Master Data ");
								
								
								}
								
								
							
							

							}
							
						}
						
							
						
					
					
					
					
		}
	
	
		

		
		
		
		
		
	
	}
	
	
	
public static void validation(String bom_sheet , String filter) throws IOException, SerialException, SQLException  { 
//File file = new File("C:\\Users\\rokon\\Downloads\\BOM UPDATION_TRD 2nd aug.xlsx"); 
ArrayList<String> fG_code = new ArrayList<>();
ArrayList<String>fgg = new ArrayList<>();
ArrayList<String> ars = new ArrayList<>();
ArrayList<String> nar = new ArrayList<>();
ArrayList<String> temp = new ArrayList<>();
ArrayList<String> temp_total = new ArrayList<>();
ArrayList<String> stone_id = new ArrayList<>();
ArrayList<String> qty = new ArrayList<>();
Set<String> s= new HashSet<>();
ArrayList<String> total_columns = new ArrayList<>();
//HashSet<String> unique_fgCode= new HashSet<>(fG_code);
ArrayList<String> allAddress = new ArrayList<>();
Map <String, List<String>> fgCode_and_address = new TreeMap<>();
Map <String, String> fg_cast_colour = new TreeMap<>();
ArrayList<String> total_columns_getaddress_list = new ArrayList<>();
String[] required_for_total_headers= {"PAVE","PRONG","CHANNEL","BEZEL","INVISIBLE","FLUSH","RIVET","STRING","GLUE","PRESSURE","KNOT","BACK","STAR","TENSION","FREE",	"LINK",	"NICK",	"PIN",	"CLUSTER"};

Map <String, List<String>> cast_code_and_address = new TreeMap<>();

Set<String> not_for_upload_fg_code= new HashSet<>();






total_columns.add("L");
total_columns.add("M");
total_columns.add("N");
total_columns.add("O");
total_columns.add("P");
total_columns.add("Q");
total_columns.add("R");
total_columns.add("S");
total_columns.add("T");
total_columns.add("U");
total_columns.add("V");
total_columns.add("W");
total_columns.add("X");
total_columns.add("Y");
total_columns.add("Z");
total_columns.add("AA");
total_columns.add("AB");
total_columns.add("AC");
total_columns.add("AD");

Map<String, Integer> requiredHeaders = new HashMap<>();
ArrayList <String> araytemp=new ArrayList<>();
ArrayList <String> updated_qty=new ArrayList<>();
ArrayList <String> temp_updated_qty=new ArrayList<>();
Set<String> not_for_upload_total_code= new HashSet<>();






try {
//    Map<String, Integer> requiredHeaders = new HashMap<>();System.getProperty("java.io.tmpdir")+"\\"+"bom_sheet.xlsx"
//	FileInputStream file = new FileInputStream(new File(System.getProperty("java.io.tmpdir")+"\\"+bom_sheet));
	FileInputStream file = new FileInputStream(new File(System.getProperty("java.io.tmpdir")+"\\"+bom_sheet));
//    FileInputStream file = new FileInputStream(new File("C:\\Users\\rokon\\Downloads\\BOM UPDATION_TRD 2nd aug.xlsx"));
    Workbook workbook = new XSSFWorkbook(file);
    DataFormatter formatter = new DataFormatter();
    Sheet sheet = workbook.getSheetAt(0);
//    Sheet sheet = workbook.getSheet("BOM FORMAT");
    
    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
    
   System.out.println("sheet namr =="+workbook.getSheetAt(0).getSheetName());
    System.out.println("last row number== "+sheet.getLastRowNum());
//    for (Cell cell : sheet.getRow(0)) {
    for (Cell cell : sheet.getRow(0)) {
    	
            if (cell.getCellType() != CellType.BLANK || cell!=null) {
            	 requiredHeaders.put(cell.getStringCellValue(), cell.getColumnIndex());
            	 if(cell.getStringCellValue().equals("QTY")) {
            		 qty.add(cell.getAddress().toString());
            	 }
            	 
            	 for(int i=0;i<=required_for_total_headers.length-1;i++) 
            	 { if (cell.getStringCellValue().equals(required_for_total_headers[i])) {
                	 
            		 
            		 total_columns_getaddress_list.add(cell.getAddress().toString());
            		 
                	 
                }}
            	 
            }
            if (cell.getStringCellValue().equals("STONE CODE")) {
            	
            	 
           	 System.out.println("stone address  "+cell.getAddress().toString());
           	 stone_id.add(cell.getAddress().toString());
           	
           }
           
//        requiredHeaders.put(cell.getStringCellValue(), cell.getColumnIndex());
        
    }
    System.out.println("last row number "+sheet.getLastRowNum());
    System.out.println("last row number physical  "+sheet.getPhysicalNumberOfRows());
    System.out.println("last row number   "+sheet.getRow(0).getRowNum());
    
    
    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
    	
        Row row = sheet.getRow(i);
        
//        System.out.println("ITEM CODE = " + formatter.formatCellValue(row.getCell(requiredHeaders.get("ITEM CODE"))));
//        System.out.println("STONE CODE = " + formatter.formatCellValue(row.getCell(requiredHeaders.get("STONE CODE"))));
        if((row.getCell(requiredHeaders.get("ITEM CODE")).getCellType()!= CellType.BLANK)){
        	System.out.println(" type 123"+row.getCell(requiredHeaders.get("ITEM CODE")).getCellType());
        fG_code.add(row.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue());
        fgg.add(row.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue());
       
        System.out.println("address  "+row.getCell(requiredHeaders.get("ITEM CODE")).getAddress());
        s.add(row.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue());
        
        if (row.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue().equals("50C2FFSAXABA29")) {
        	System.out.println("*****address  "+row.getCell(requiredHeaders.get("ITEM CODE")).getAddress());
        	ars.add(row.getCell(requiredHeaders.get("ITEM CODE")).getAddress().toString());
          }
                            
        }
        
        
     
       
//        sr.add(row.getCell(requiredHeaders.get("ITEM CODE")));f (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//        for (cell.getCellType() == CellType.STRING) {
//            if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
//                return row.getRowNum();  
//            }
       
        
    }
    
    
    
    
    
    updated_qty.add(qty.get(0).substring(0, qty.get(0).length() - 1));
  Double total= 0.0;
  for(String rl:total_columns_getaddress_list) {
  	
  	araytemp.add(rl.substring(0, rl.length() - 1)); 
  }
    for (int i = 2; i <= sheet.getLastRowNum(); i++) {
    	
        Row row = sheet.getRow(i);
        
//        System.out.println("ITEM CODE = " + formatter.formatCellValue(row.getCell(requiredHeaders.get("ITEM CODE"))));
//        System.out.println("STONE CODE = " + formatter.formatCellValue(row.getCell(requiredHeaders.get("STONE CODE"))));
        temp_updated_qty.add(updated_qty.get(0)+i);
        if((row.getCell(requiredHeaders.get("STONE CODE")).getCellType()!= CellType.BLANK)){
        	
for(int j=0;j<=araytemp.size()-1;j++) {
        		
        		temp_total.add(araytemp.get(j)+i);
        		
        		
  		
        	}
        	
				Double opt=0.0;
        	for(int k=0 ; k<=temp_total.size()-1;k++) {
		      	 CellReference cellReference = new CellReference(temp_total.get(k));
		      	 System.out.println(temp_total.get(k));
		      	 Row roww = sheet.getRow(cellReference.getRow());
		      	 Cell celll = roww.getCell(cellReference.getCol()); 
		      	 CellValue cellValue = evaluator.evaluate(celll);
//		      	if(cellValue.getCellType()!= CellType.BLANK) {
		      	if(cellValue== null) {
		      		total=total+opt;
		      		
		      	}else {
		      		System.out.println("for total cell address "+ cellValue.getNumberValue());
		      		total=total+cellValue.getNumberValue();
		      	}
		      	  
		      	
		      	  
		      	  
		      	
//		      	  Double data=cellValue.getNumberValue();
//		      	  int value = data.intValue();
//		      	  System.out.println(value);
		      	    }
        	  
	      	 CellReference cellReference1 = new CellReference(temp_updated_qty.get(0)); 
	      	 Row roww1 = sheet.getRow(cellReference1.getRow());
	      	 Cell celll1 = roww1.getCell(cellReference1.getCol()); 
	      	 CellValue cellValue1 = evaluator.evaluate(celll1);

	      	 
	      	if(cellValue1== null ) {
	      		write_error(temp_updated_qty.get(0), "Total quantity is not present at this address");
	      		not_for_upload_total_code.add(temp_updated_qty.get(0));
        }else{

		      	  System.out.println("for total cell address "+ cellValue1.getNumberValue());
		      	 System.out.println("total value ******************>>>>>>>>> "+ total);
		      	if(total==cellValue1.getNumberValue()) {
	        		System.out.println(" temp_total  correct total"+ total);
	        	}else {
	        		
	        		
	        		write_error(temp_updated_qty.get(0), "total value at this address is wrong");
	        		not_for_upload_total_code.add(temp_updated_qty.get(0));
	        	}
	      	}
	      	 
	      	 
        	
//        	PAVE	PRONG	CHANNEL	BEZEL	INVISIBLE	FLUSH	RIVET	STRING	GLUE	PRESSURE	KNOT	BACK	STAR	TENSION	FREE	LINK	NICK	PIN	CLUSTER

        	
//        	System.out.println(" rows having value"+ rwv );
//      
//        	  System.out.println("temp_total   "+temp_total);
        	  
        	  
//        	  System.out.println(" temp_total "+ temp_total);
    
        	 System.out.println("temp_updated_qty "+ temp_updated_qty);
             System.out.println("temp_total "+ temp_total);
             temp_updated_qty.clear();
             temp_total.clear();
             total=0.0;
                            
        }
       
//        sr.add(row.getCell(requiredHeaders.get("ITEM CODE")));f (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//        for (cell.getCellType() == CellType.STRING) {
//            if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
//                return row.getRowNum();  
//            }
       
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    updated_qty.add(qty.get(0).substring(0, qty.get(0).length() - 1));
//    Double total= 0.0;
//    for(String rl:total_columns_getaddress_list) {
//    	
//    	araytemp.add(rl.substring(0, rl.length() - 1)); 
//    	
//    }
//    for (int i = 2; i <= sheet.getLastRowNum(); i++) {
//    	
//        Row row = sheet.getRow(i);
//        
////        System.out.println("ITEM CODE = " + formatter.formatCellValue(row.getCell(requiredHeaders.get("ITEM CODE"))));
////        System.out.println("STONE CODE = " + formatter.formatCellValue(row.getCell(requiredHeaders.get("STONE CODE"))));
//        temp_updated_qty.add(updated_qty.get(0)+i);
//        if((row.getCell(requiredHeaders.get("STONE CODE")).getCellType()!= CellType.BLANK)){
//        	for(int j=0;j<=araytemp.size()-1;j++) {
//        		
//        		temp_total.add(araytemp.get(j)+i);
//        		
//        		
//  		
//        	}
//        	
//
//        	for(int k=0 ; k<=temp_total.size()-1;k++) {
//		      	 CellReference cellReference = new CellReference(temp_total.get(k)); 
//		      	 Row roww = sheet.getRow(cellReference.getRow());
//		      	 Cell celll = roww.getCell(cellReference.getCol()); 
//		      	 CellValue cellValue = evaluator.evaluate(celll);
//
//		      	  System.out.println("for total cell address "+ cellValue.getNumberValue());
//		      	  
//		      	  
//		      	  total=total+cellValue.getNumberValue();
//		      	
////		      	  Double data=cellValue.getNumberValue();
////		      	  int value = data.intValue();
////		      	  System.out.println(value);
//		      	    }
//        	  
//	      	 CellReference cellReference1 = new CellReference(temp_updated_qty.get(0)); 
//	      	 Row roww1 = sheet.getRow(cellReference1.getRow());
//	      	 Cell celll1 = roww1.getCell(cellReference1.getCol()); 
//	      	 CellValue cellValue1 = evaluator.evaluate(celll1);
//
//	      	  System.out.println("for total cell address "+ cellValue1.getNumberValue());
//        	if(total==cellValue1.getNumberValue()) {
//        		System.out.println(" temp_total  correct total"+ temp_total);
//        	}else {
//        		
//        		
////        		write_error(temp_updated_qty.get(0), "total value at this address is wrong");
//        	}
////        	PAVE	PRONG	CHANNEL	BEZEL	INVISIBLE	FLUSH	RIVET	STRING	GLUE	PRESSURE	KNOT	BACK	STAR	TENSION	FREE	LINK	NICK	PIN	CLUSTER
//
////        	rwv+=1;
////        	System.out.println(" rows having value"+ rwv );
////      
////        	  System.out.println("temp_total   "+temp_total);
//        	  
//        	  
////        	  System.out.println(" temp_total "+ temp_total);
//    
//        	 System.out.println("temp_updated_qty "+ temp_updated_qty);
//             System.out.println("temp_total "+ temp_total);
//             temp_updated_qty.clear();
//             temp_total.clear();
//      
//        }
//       
////        sr.add(row.getCell(requiredHeaders.get("ITEM CODE")));f (cell.getCellType() == Cell.CELL_TYPE_STRING) {
////        for (cell.getCellType() == CellType.STRING) {
////            if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
////                return row.getRowNum();  
////            }
//       
//        
//    }
//    
 
    
    System.out.println("hiiiiiiiii");
    
    HashSet<String> uns =new HashSet<>(fG_code);
    ArrayList<String> un =new ArrayList<>(uns);
    for(int i = 1; i <=  fG_code.size()-1; i++) 
    {
    	
    	System.out.println("hiiiiiiiii");
    	 
    	List<String> arr = new ArrayList<>();
      for (int j = 0; j <= sheet.getLastRowNum(); j++) {
        Row row = sheet.getRow(j);
//        	
        if (row.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue().equals(fG_code.get(i))) {
        	arr.add(row.getCell(requiredHeaders.get("ITEM CODE")).getAddress().toString());
        	System.out.println(" "+row.getCell(requiredHeaders.get("ITEM CODE")).getAddress().toString());
//        
        }
    	  fgCode_and_address.put(fgg.get(i), arr);
    	 
    }
    
    }
    System.out.println(" fgCode_and_address.put(fgg.get(i), arr);  "+fgCode_and_address);
    int rwv=0;
    
	
    
   if(filter.equals("IHO") || filter.equals("JWK") ) { 
//***************************     for  fg code and cast code value   ************************
    
    
    for(int i = 1; i <=  fG_code.size()-1; i++) 
    {
    	
    	System.out.println("hiiiiiiiii");
    	 
    	List<String> arr = new ArrayList<>();
      for (int j = 0; j <= sheet.getLastRowNum(); j++) {
        Row row = sheet.getRow(j);
//        	 Cell cell = row.getCell(j); 
        if (row.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue().equals(fG_code.get(i)) && row.getCell(requiredHeaders.get("CAST CODE"))!= null) {
        	
        	
        	System.out.println(" not blank"+ row.getCell(requiredHeaders.get("CAST CODE")).getStringCellValue() );
        	arr.add(row.getCell(requiredHeaders.get("CAST CODE")).getStringCellValue());
        	System.out.println(" "+row.getCell(requiredHeaders.get("CAST CODE")).getAddress().toString());
        
        }
//    	  fgCode_and_address.put(fgg.get(i), arr);
    	  cast_code_and_address.put(fgg.get(i), arr) ;
    	 
    }
    
    }
    System.out.println(" cast_code_and_address.put(fgg.get(i), arr);  "+cast_code_and_address);
    
//  ***********************************************************************************************************  
    
    
    
    for(int i = 1; i <=  fG_code.size()-1; i++) 
    {
    	
    	System.out.println(" fg code and cast colour");
    	 
//    	List<String> arr = new ArrayList<>();
      for (int j = 0; j <= sheet.getLastRowNum(); j++) {
        Row row = sheet.getRow(j);
//        	 Cell cell = row.getCell(j); 
        if (row.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue().equals(fG_code.get(i)) && row.getCell(requiredHeaders.get("COLOR"))!= null) {
        	
        	
        	System.out.println(" not blank"+ row.getCell(requiredHeaders.get("COLOR")).getStringCellValue() );
//        	arr.add(row.getCell(requiredHeaders.get("COLOR")).getStringCellValue());
        	 fg_cast_colour.put(fG_code.get(i),row.getCell(requiredHeaders.get("COLOR")).getStringCellValue());
        	System.out.println(" "+row.getCell(requiredHeaders.get("COLOR")).getAddress().toString());
        
        }
//    	  fgCode_and_address.put(fgg.get(i), arr);
//        fg_cast_colour.put(fgg.get(i), arr) ;
    	 
    }
    
    }
    System.out.println(" fg_cast_colour.put(fgg.get(i), arr) ;  "+fg_cast_colour);
    
    
    
    
  //***************************** new code  for mount codes ****************************************************************    
    

//    Map<String,String> fg_cast_colour = new HashMap<>();
  Map<String,Character>karat_22_map=new HashMap<>();
  karat_22_map.put("50", 'L');
  karat_22_map.put("51", 'P');
  karat_22_map.put("52", 'R');
  karat_22_map.put("53", 'F');
  karat_22_map.put("54", 'G');
  karat_22_map.put("55", 'H');
  karat_22_map.put("57", 'N');
  karat_22_map.put("SL", 'L');
  karat_22_map.put("EH", 'H');
  karat_22_map.put("EL", 'L');
  karat_22_map.put("EP", 'P');
  karat_22_map.put("UH", 'H');
  karat_22_map.put("UL", 'L');
  karat_22_map.put("UP", 'P');
    List<String> metal_pt_codes= Arrays.asList("74", "70", "ST" ,"ET" ,"UT" ,"ZT");
    List<String> roll_codes= Arrays.asList("23", "24", "27" ,"41" ,"25" ,"11");
    List<String> mount_codes_fg= Arrays.asList("50", "51", "55");	
    List<String> mount_codes_cast= Arrays.asList("30", "31", "35");
    
    Map<String,Character>mount_code_color=new HashMap<>();
    mount_code_color.put("YEL", 'Y');
    mount_code_color.put("WHI", 'W');
    mount_code_color.put("ROS", 'R');
    mount_code_color.put("ROS-WHI", '3');
    mount_code_color.put("YEL-ROS", '2');
    mount_code_color.put("YEL-WHI", '1');
    mount_code_color.put("YEL-WHI-ROS", '4');
    mount_code_color.put("WHI-ROS", '3');
    mount_code_color.put("ROS-YEL", '2');
    mount_code_color.put("WHI-YEL", '1');
    
    
    
  
  Map<String,String>cast_color=new HashMap<>();
  cast_color.put("YEL", "Y");
  cast_color.put("WHI", "W");
  cast_color.put("ROS", "R");
  cast_color.put("ROS-WHI", "RW");
  cast_color.put("YEL-ROS", "YR");
  cast_color.put("YEL-WHI", "YW");
  cast_color.put("WHI-ROS", "RW");
  cast_color.put("ROS-YEL", "YR");
  cast_color.put("WHI-YEL", "YW");
  
  
  for(String key: cast_code_and_address.keySet()) {
  	int roll_color_first=0;
    int first=0;
	int roll_color_second=0;
  	int second=0;
  	int other=0; boolean color_size_greater_than_3=false; boolean fg_code_not_3=false;
  	
  	List<String> klist=cast_code_and_address.get(key);
  	
  	for(String castCode : klist) {
  		
  		if(castCode.substring(0,2).equals("22")||castCode.substring(0,2).equals("2R")) {

   if(castCode.length()==11 || castCode.length()==12 || castCode.length()==13 || castCode.length()==14  ) {
  	  
    
  			
  			if(karat_22_map.containsKey(key.substring(0,2))) {

    if(castCode.charAt(castCode.length()-5)=='G' && castCode.charAt(castCode.length()-4)=='O') {
  				if(castCode.charAt(castCode.length()-3)==karat_22_map.get(key.substring(0,2))) {
//  					karat is correct
  				}else{
//    			show error massagenot_for_upload_fg_code.add(key);
  					not_for_upload_fg_code.add(key); 
					write_error(key, "for this fg code karat are different in fg code and cast code "+castCode+"");
    }
  				if(castCode.charAt(castCode.length()-1)== key.charAt(key.length()-5) ) {
//  					show size match
  				}
    			else if(castCode.charAt(castCode.length()-1)=='A' ) {
//					show  massage  A is present no need to check
    				
			}else{
//    				show error massage size mismatch
				not_for_upload_fg_code.add(key); 
				write_error(key, "for this fg code size are different in fg code and cast code "+castCode+"");
    }
  				if(cast_color.containsKey(fg_cast_colour.get(key))) {
  					
  					String fgcast_colour=fg_cast_colour.get(key);
  					if(fgcast_colour.length()>3) {
  color_size_greater_than_3=true;  fg_code_not_3=true;
  						if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
  							
  							first+=1;
  							
  						}if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(1)) {
  							
  							
  							second+=1;
  						}
  						
  					}
  					if(fgcast_colour.length()==3) {
  						
  						if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
  							
  							
  						}else {
//  							show error massage  colour is  different/
  							not_for_upload_fg_code.add(key); 
  							write_error(key, "for this fg code colours are different in fg code and cast code "+castCode+"");
  						}
  						
  					}
  					
  				}
}else{
//    show error massage  GO is not present
	not_for_upload_fg_code.add(key); 
	write_error(key, "for this fg code GO is not present in this  cast code "+castCode+"");
    }
  			}
  			
    
    
		
		if(metal_pt_codes.contains(key.substring(0,2))) {
if(castCode.charAt(castCode.length()-5)=='P' && castCode.charAt(castCode.length()-4)=='T') {

			if(castCode.charAt(castCode.length()-1)== key.charAt(key.length()-5) ) {
//				show size match
			}
		else if(castCode.charAt(castCode.length()-1)=='A' ) {
//			show  massage  A is present no need to check
	}else{
//			show error massage size mismatch
		not_for_upload_fg_code.add(key); 
		write_error(key, "for this fg code size are different in fg code and this cast code "+castCode+"");
}
			if(cast_color.containsKey(fg_cast_colour.get(key))) {
				
				String fgcast_colour=fg_cast_colour.get(key);
				if(fgcast_colour.length()>3) {
  color_size_greater_than_3=true;  fg_code_not_3=true;
					if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
						
						first+=1;
						
					}if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(1)) {
						
						
						second+=1;
					}if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
						
						other+=1;
					}
					
				}
				if(fgcast_colour.length()==3) {
					
					if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
						
						
					}else {
						not_for_upload_fg_code.add(key); 
						write_error(key, "for this fg code colours are different in fg code and this cast code "+castCode+"");
//						show error massage  colour is  different
					}
					
				}
				
			}
}	else{
//	show error massage  PT is not present
	not_for_upload_fg_code.add(key); 
	write_error(key, "for this fg code PT is not present in this  cast code "+castCode+"");
	}
		}
    
    
  		}else {
  			
  			not_for_upload_fg_code.add(key); 
  			write_error(key, "for this fg code length of this cast code "+castCode+" is not 11 ,12,13,14 ");
  		}
 
   
  	}
    
    
    if(roll_codes.contains(castCode.substring(0,2))) {
        if(castCode.length()==11 || castCode.length()==12 || castCode.length()==13 || castCode.length()==14  ) {
  color_size_greater_than_3=true;  fg_code_not_3=true;
  	  
        
			
			if(karat_22_map.containsKey(key.substring(0,2))) {
if(castCode.charAt(castCode.length()-5)=='G' && castCode.charAt(castCode.length()-4)=='O') {
				if(castCode.charAt(castCode.length()-3)==karat_22_map.get(key.substring(0,2))) {
//					karat is correct
				}else{
//			show error massage
					not_for_upload_fg_code.add(key); 
					write_error(key, "for roll code for this fg code karat are different in fg code and this cast code "+castCode+"");
}

				if(cast_color.containsKey(fg_cast_colour.get(key))) {
					
					String fgcast_colour=fg_cast_colour.get(key);
					if(fgcast_colour.length()>3) {
						color_size_greater_than_3=true;  fg_code_not_3=true;
						if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
							
							first+=1;
							
						}if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(1)) {
							
							
							second+=1;
						}if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
							
							other+=1;
						}
						
					}
					if(fgcast_colour.length()==3) {
						
						if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
							
							
						}else {
//							show error massage  colour is  different
							not_for_upload_fg_code.add(key); 
							write_error(key, "for roll code for this fg code colours are different in fg code and this cast code "+castCode+"");
						}
						
					}
					
				}
}else{
//show error massage  GO is not present
	not_for_upload_fg_code.add(key); 
	write_error(key, "for roll code for this fg code PT is not present in this  cast code "+castCode+"");
}
			}
			


	
	if(metal_pt_codes.contains(key.substring(0,2))) {
if(castCode.charAt(castCode.length()-5)=='P' && castCode.charAt(castCode.length()-4)=='T') {


		if(cast_color.containsKey(fg_cast_colour.get(key))) {
			
			String fgcast_colour=fg_cast_colour.get(key);
			if(fgcast_colour.length()>3) {
  color_size_greater_than_3=true;  fg_code_not_3=true;
				if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
					
					first+=1;
					
				}if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(1)) {
					 
					
					second+=1;
				}if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
					
					other+=1;
				}
				
			}
			if(fgcast_colour.length()==3) {
				
				if(castCode.charAt(castCode.length()-2)==cast_color.get(fg_cast_colour.get(key)).charAt(0)) {
					
					
				}else {
//					show error massage  colour is  different
					not_for_upload_fg_code.add(key); 
					write_error(key, "for roll code for this fg code colours are different in fg code and this cast code "+castCode+"");
					
				}
				
			}
			
		}
}	else{
//show error massage  PT is not present
	not_for_upload_fg_code.add(key); 
	write_error(key, "for roll code for this fg code PT is not present in this  cast code "+castCode+"");
}
	}


		}else {
  			
  			not_for_upload_fg_code.add(key); 
  			write_error(key, "for this fg code length of this cast code "+castCode+" is not 11 ,12,13,14 ");
  		}
  	}

    
    
    
    if(castCode.charAt(0)=='3') {
    	if(castCode.length()==15) {
  	  
  	  if(castCode.substring(0,2).equals(mount_codes_cast.get(0)) && key.substring(0,2).equals(mount_codes_fg.get(0))) {
  		
  	  }
  	  else if(castCode.substring(0,2).equals(mount_codes_cast.get(1))  && key.substring(0,2).equals(mount_codes_fg.get(1))) {
  		  
  	  }
  	  else if(castCode.substring(0,2).equals(mount_codes_cast.get(2))  && key.substring(0,2).equals(mount_codes_fg.get(2))) {
  		  
  	  }else {
  		not_for_upload_fg_code.add(key); 
  		write_error(key, "for mount code for this fg code first 2 character in fg code are "+castCode.substring(0,2)+" and first 2 character in cast code are "+key.substring(0,2)+" ");
  	  }
  	  
  	  if(castCode.charAt(castCode.length()-1) == key.charAt(key.length()-5)) {
  		  
  	  }else {
  		  
//  		  show error size mismatch
  		not_for_upload_fg_code.add(key); 
		write_error(key, "for mount code for this fg code size are different in fg code and this cast code "+castCode+"");
  	  }
  	  
  	  if(mount_code_color.containsKey(fg_cast_colour.get(key))) {
				
				
					if(castCode.charAt(castCode.length()-4)==mount_code_color.get(fg_cast_colour.get(key))) {
						
						
						
					}else{
//  		  show error not math colour
						not_for_upload_fg_code.add(key); 
						write_error(key, "for mount code for this fg code colours are different in fg code and this cast code "+castCode+"");
  	  }
  		  
		  

  	  
		}
    
    
    if(castCode.charAt(castCode.length()-5)==key.charAt(key.length()-4)) {
  	  
    }else {
  	  
//  	  show error not match size
    	not_for_upload_fg_code.add(key); 
		write_error(key, "for this fg code size are different in fg code and this cast code "+castCode+"");
    }
    
    
    
    
    
    
}else {
		
		not_for_upload_fg_code.add(key); 
		write_error(key, "for this fg code length of this cast code "+castCode+" is not 15 ");
	}
    
  	}   
    
    

  	}
  	
    
    if(color_size_greater_than_3==true) {
  	  if(first<=0 || second<=0) {
      	  
//      show error both colour is not present
  		not_for_upload_fg_code.add(key); 
		write_error(key, "for this fg code in dual colours cast code  first color is "+first+" is present and second color is "+second+"  present");
        } 
  	  
    }
    

  	
  }
  
}
   
//***************************** new code  for mount codes ****************************************************************   
    
    
    
    
    
    
    
    
    
//    updated_qty.add(qty.get(0).substring(0, qty.get(0).length() - 1));
//    Double total= 0.0;
//    for(String rl:total_columns_getaddress_list) {
//    	
//    	araytemp.add(rl.substring(0, rl.length() - 1)); 
//    	
//    }
//    for (int i = 2; i <= sheet.getLastRowNum(); i++) {
//    	
//        Row row = sheet.getRow(i);
//        
////        System.out.println("ITEM CODE = " + formatter.formatCellValue(row.getCell(requiredHeaders.get("ITEM CODE"))));
////        System.out.println("STONE CODE = " + formatter.formatCellValue(row.getCell(requiredHeaders.get("STONE CODE"))));
//        temp_updated_qty.add(updated_qty.get(0)+i);
//        if((row.getCell(requiredHeaders.get("STONE CODE")).getCellType()!= CellType.BLANK)){
//        	for(int j=0;j<=araytemp.size()-1;j++) {
//        		
//        		temp_total.add(araytemp.get(j)+i);
//        		
//        		
//  		
//        	}
//        	
//
//        	for(int k=0 ; k<=temp_total.size()-1;k++) {
//		      	 CellReference cellReference = new CellReference(temp_total.get(k)); 
//		      	 Row roww = sheet.getRow(cellReference.getRow());
//		      	 Cell celll = roww.getCell(cellReference.getCol()); 
//		      	 CellValue cellValue = evaluator.evaluate(celll);
//
//		      	  System.out.println("for total cell address "+ cellValue.getNumberValue());
//		      	  
//		      	  
//		      	  total=total+cellValue.getNumberValue();
//		      	
////		      	  Double data=cellValue.getNumberValue();
////		      	  int value = data.intValue();
////		      	  System.out.println(value);
//		      	    }
//        	  
//	      	 CellReference cellReference1 = new CellReference(temp_updated_qty.get(0)); 
//	      	 Row roww1 = sheet.getRow(cellReference1.getRow());
//	      	 Cell celll1 = roww1.getCell(cellReference1.getCol()); 
//	      	 CellValue cellValue1 = evaluator.evaluate(celll1);
//
//	      	  System.out.println("for total cell address "+ cellValue1.getNumberValue());
//        	if(total==cellValue1.getNumberValue()) {
//        		System.out.println(" temp_total  correct total"+ temp_total);
//        	}else {
//        		
//        		
////        		write_error(temp_updated_qty.get(0), "total value at this address is wrong");
//        	}
////        	PAVE	PRONG	CHANNEL	BEZEL	INVISIBLE	FLUSH	RIVET	STRING	GLUE	PRESSURE	KNOT	BACK	STAR	TENSION	FREE	LINK	NICK	PIN	CLUSTER
//
//        	rwv+=1;
////        	System.out.println(" rows having value"+ rwv );
////      
////        	  System.out.println("temp_total   "+temp_total);
//        	  
//        	  
////        	  System.out.println(" temp_total "+ temp_total);
//    
//        	 System.out.println("temp_updated_qty "+ temp_updated_qty);
//             System.out.println("temp_total "+ temp_total);
//             temp_updated_qty.clear();
//             temp_total.clear();
//      
//        }
//       
////        sr.add(row.getCell(requiredHeaders.get("ITEM CODE")));f (cell.getCellType() == Cell.CELL_TYPE_STRING) {
////        for (cell.getCellType() == CellType.STRING) {
////            if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
////                return row.getRowNum();  
////            }
//       
//        
//    }
//    
    
  
    
    
//    ars having addresscode if only one fgcode multiple entries
//    for(int i=0;i<=ars.size()-1;i++) {
//    	
//    	 stone_id.get(0).charAt(0);
//    	 StringBuilder myName = new StringBuilder(ars.get(i));
//    	    myName.setCharAt(0, 'I');
//    	    temp.add(myName.toString());
//    	
//    }
    
    
    
    
//    Double total= 0.0;
//    for(int i=0 ; i<=total_columns.size()-1;i++) {
//      	 CellReference cellReference = new CellReference(total_columns.get(i)); 
//      	 Row roww = sheet.getRow(cellReference.getRow());
//      	 Cell celll = roww.getCell(cellReference.getCol()); 
//      	 CellValue cellValue = evaluator.evaluate(celll);
//      	
//      	  System.out.println("for total cell address "+ cellValue.getNumberValue());
//      	  
//      	  
//      	  total=total+cellValue.getNumberValue();
//      	  
////      	  Double data=cellValue.getNumberValue();
////      	  int value = data.intValue();
////      	  System.out.println(value);
//      	    }
//    
    
    CellReference cellReference1 = new CellReference(qty.get(0)); 
 	 Row roww1 = sheet.getRow(cellReference1.getRow());
 	 Cell celll1 = roww1.getCell(cellReference1.getCol()); 
 	 CellValue cellValue1 = evaluator.evaluate(celll1);

 	  System.out.println("for total cell address "+ cellValue1.getNumberValue());
 	  
 	  if(cellValue1.getNumberValue()==total) {
 		  System.out.println("values are same " + total);
 		  
 	  }
 	 
    
    
    
    for(int i=0 ; i<=temp.size()-1;i++) {
   	 CellReference cellReference = new CellReference(temp.get(i)); 
   	 Row roww = sheet.getRow(cellReference.getRow());
   	 Cell celll = roww.getCell(cellReference.getCol()); 
   	 CellValue cellValue = evaluator.evaluate(celll);

   	  System.out.println("usint cell address "+ cellValue.getStringValue());
//   	  Double data=cellValue.getNumberValue();
//   	  int value = data.intValue();
//   	  System.out.println(value);
   	    }
//    Sheet ss = workbook.getSheetAt(0);
//    Row r = ss.getRow(0);
//    int patchColumn = -1;
//    for (int cn=0; cn<r.getLastCellNum(); cn++) {
//       Cell c = r.getCell(cn);
//       if (c == null || c.getCellType() == CellType.BLANK) {
//           // Can't be this cell - it's empty
//           continue;
//       }
//       if (c.getCellType() == CellType.STRING) {
//          String text = c.getStringCellValue();
//          if ("Patch".equals(text)) {
//             patchColumn = cn;
//             break;
//          }
//       }
//    }
//    
    System.out.println("hiiiiiiiii");
    
  
//    
//    String arr[];
//    arr =new String[50];
// for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//    	
//        
//       
//        
//        
//        for (int j = 0; j <= fG_code.size()-1; j++) {
//        
//        	
//        if (row.getCell(requiredHeaders.get("ITEM CODE")).getStringCellValue().equals(fG_code.get(i))) {
//        	arr[j]=row.getCell(requiredHeaders.get("ITEM CODE")).getAddress().toString();
//        
//        }
//        
//        fgCode_and_address.put(fG_code.get(i), arr);
// }
//   
    
    
    
    
    
    
    
    
   
    

    workbook.close();
    
    file.close();
}
catch(Exception e) {
	
	System.out.println(e.getMessage());
	System.out.println("  error is here");
	System.out.println(e.getStackTrace().toString());
}

System.out.println(fG_code);
System.out.println(fG_code.size());

int t=0;
ArrayList r = new ArrayList<>();

for(int i=0;i<=fG_code.size()-1;i++) {
	
	if(fG_code.get(i)=="") {
		t+=1;
	}
	if(fG_code.get(i)!="") {
		r.add(fG_code.get(i));
		
	}
	
	if(fG_code.get(i).toString().length()>0) {
//		ar.set(i, "space");
		r.add(fG_code.get(i));
		
		
	}
}

int re=0;
Map<String,Integer> mp = new HashMap<>();
for(int i =0;i<=fG_code.size()-1;i++) {

if(mp.containsKey(fG_code.get(i))) {
	
mp.put( fG_code.get(i), mp.get(fG_code.get(i))+1);
}else {
	
	mp.put( fG_code.get(i), 0);
}

}

String str="50C2FFSAXABA29";


if(mp.containsKey(str)) {
	
	System.out.println("true");
}else {
	
	System.out.println("false");
}




System.out.println("map"+ mp);
System.out.println("mp 50C2FFSAXABA29    "+mp.get("50C2FFSAXABA29"));
System.out.println("map"+mp.size());

System.out.println("t"+t);
System.out.println("r"+r);
System.out.println("fG_code"+fG_code.size());
System.out.println("r"+r.size());
System.out.println("fG_code"+fG_code);
//System.out.println("ar 3       "+ar.get(300).toString().length());

System.out.println("s"+s);
System.out.println("s"+s.size());

System.out.println("ars"+ars);
System.out.println("ars"+ars.size());
Set<String> unique_fgCode= new HashSet<>(fG_code);
System.out.println("unique_fgCode"+unique_fgCode);
System.out.println("keyset"+ mp.keySet());
System.out.println("map"+ mp.size());
System.out.println("unique_fgCode size"+unique_fgCode.size());

//System.out.println(temp);





//exdemo.get_fgCodeAddress(unique_fgCode, fG_code, nar);

System.out.println("nar "+nar);
System.out.println("allAddress "+allAddress);
System.out.println("allAddress "+fgCode_and_address);
ExcelService1.check_fGlength(fG_code,not_for_upload_fg_code);
Map <String, List<String>> fg_code_details =ExcelService1.get_fgCode_and_stone_address_value(fgCode_and_address,stone_id,bom_sheet);

ExcelService1.check_prolif_validations(fg_code_details,not_for_upload_fg_code);

ExcelService1.check_unique_stoneCode(fg_code_details,not_for_upload_fg_code);
ExcelService1.check_prolif_color_quality_validations(fg_code_details,not_for_upload_fg_code);
System.out.println(" temp_total "+ temp_total);
ExcelService1.check_stone_code_length(fg_code_details, not_for_upload_fg_code);
System.out.println(" requiredHeaders "+ requiredHeaders);
//exdemo.check_total_quantity(fg_code_details);

System.out.println(" total_columns_getaddress_list "+ total_columns_getaddress_list);

System.out.println(" araytemp "+ araytemp);
System.out.println(" not_for_upload_fg_code  "+ not_for_upload_fg_code);
System.out.println(" not_for_upload_fg_code size "+ not_for_upload_fg_code.size());

System.out.println("unique_fgCode"+unique_fgCode.contains("50E2PTFJFLAAP1"));
System.out.println("fgCode"+fG_code.contains("50E2PTFJFLAAP1"));
if(unique_fgCode.contains("50E2PTFJFLAAP1")) {System.out.println("unique_fgCode.contains50E2PTFJFLAAP1");}
if(fG_code.contains("50E2PTFJFLAAP1")) {System.out.println("fgCodefG_code.contains50E2PTFJFLAAP1");}
if(fg_code_details.containsKey("50E2PTFJFLAAP1")) {System.out.println("fg_code_details");}
System.out.println("fg_code_details"+ fg_code_details);
ExcelService1.delete_rows(not_for_upload_fg_code,requiredHeaders,not_for_upload_total_code,bom_sheet);


}}  