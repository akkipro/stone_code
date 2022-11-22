package com.titan.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import javax.persistence.EntityManager;
//import javax.persistence.ParameterMode;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import javax.persistence.StoredProcedureQuery;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
//import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.titan.StoneCodeValidationApplication;

//import com.titan.ProcedureCallApplication;
//import com.titan.model.VendorQuery;

@org.springframework.stereotype.Repository
public class Repository {

//	@Autowired
//	private EntityManager entityManager;
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(StoneCodeValidationApplication.class);
	Map<String,String> nmap= new HashMap<>();
	

	
	
	
	
	
	
	
	
	
//	@Transactional
//	public <T> T  storeEncodeedFile(String errorFile, String fixedFile) {
//		try {
//		
//		entityManager.createNativeQuery("insert into validation_sheet_table( error_sheet, fixed_sheet)values("+errorFile+","+fixedFile+");").getResultList(); 
//		}
//		catch(Exception e)
//		{
//			System.out.println(e.getMessage());
//		}
//
//	    return (T) null;
//	}









//
//	@Transactional
//	public void storeEncodeedFile(Blob errorFile, Blob fixedFile) throws SQLException {
//		
//		String queryStr="insert into validation_sheet_table( error_sheet, fixed_sheet)values(?,?)";
//		
//		System.out.println("errorFile.length()  "+errorFile.length());
//		try {
//			Query query = entityManager.createNativeQuery(queryStr);
//	        query.setParameter(1, errorFile);
//	        query.setParameter(2, errorFile);
//	        query.executeUpdate();
////			entityManager.createNativeQuery("insert into validation_sheet_table( error_sheet, fixed_sheet)values("+errorFile+","+fixedFile+");").executeUpdate(); 
//			}
//			catch(Exception e)
//			{
//				System.out.println(e.getMessage());
//			}
//		
//	}
//
//
//	@Transactional
//	public Blob fetchEncodeedFile() throws SerialException, SQLException {
//		
//		List<Object[]> lst = new ArrayList<>();
//		
//		String queryStr="select * from validation_sheet_table where id=?";
//		try {
//			Query query = entityManager.createNativeQuery(queryStr);
//	        query.setParameter(1, 1);
//	       
//	       lst= query.getResultList();
//	      
////			entityManager.createNativeQuery("insert into validation_sheet_table( error_sheet, fixed_sheet)values("+errorFile+","+fixedFile+");").executeUpdate(); 
//			}
//			catch(Exception e)
//			{
//				System.out.println(e.getMessage());
//			}
//	
//		Blob b=(Blob)lst.get(0)[0];
//		return b;
//	}




//	@Transactional
	public int storeBlob(Blob errorFile, Blob fixedFile, String file_name) throws SerialException, SQLException {


	
		
		
	
		
		  int id=0;

//		  String jdbcURL = "jdbc:mysql://localhost:3306/mydb";
//	        String username = "root";
//	        String password = "root";
	 
	        String jdbcURL = "jdbc:sqlserver://ptapps.titan.in:49172;databaseName=Support;encrypt=true;trustServerCertificate=true";
//	        String username = "mtovq";
//	        String password = "vQ!2021";
	        String username = "pbpm";
	        String password = "Product!23";
	       
	 
	        Connection connection = null;
	 
	        try {
	            long start = System.currentTimeMillis();
	             
	          
	            connection = DriverManager.getConnection(jdbcURL, username, password);
	            connection.setAutoCommit(false);
	        
	            String sql =  "insert into validation_sheet_table( error_sheet, fixed_sheet, file_Name)values(?,?,?)";
	            PreparedStatement statement = connection.prepareStatement(sql);    
	             
	         
	             
//	            statement.setLong(1, 1);
	            
	            statement.setBlob(1, errorFile);
	            statement.setBlob(2, fixedFile);
	            statement.setString(3, file_name);
//	            statement.executeQuery(sql);
	             
	           statement.execute();
	           
//	           String sql1 =  "select * from validation_sheet_table ORDER BY id DESC LIMIT 1";
//	           String sql1 =  "select * from validation_sheet_table ORDER BY id DESC OFFSET 1 ROWS";
//	           SELECT TOP 1 * FROM table_Name ORDER BY unique_column DESC 
	           String sql1 =  "SELECT TOP 1 * FROM validation_sheet_table ORDER BY id DESC";
	          
	           statement = connection.prepareStatement(sql1);    
	             
	         
	             
//	            statement.setLong(1, 1);
	            
//	            statement.setInt(1, 1);
	              
//	            statement.executeQuery(sql);
	             
	            ResultSet rs=  statement.executeQuery();
	            if(rs.next()){
	            id=rs.getInt("id");
	            	
	            }
	           
	           
	            //Retrieving the data
//	            ResultSet rs = stmt.executeQuery("select * from validation_sheet_table where id=?");
//	            int i = 1;
	          
//	             blob = rs.getBlob(2);
	            
	          
	            rs.close();
	            statement.close();
	            connection.commit();
	            connection.close();
	            
	    
	        } catch (SQLException ex2) {
	            System.out.println("Database error");
	            ex2.printStackTrace();
	        }
	 
		
		
		
		
		
		
		
		
		
		
		
		
//	 //Registering the Driver
//    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//    //Getting the connection
//    String mysqlUrl = "jdbc:mysql://localhost:3306/mydb";
//    Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
//    System.out.println("Connection established......");
//    //Creating a table
//    Statement stmt = con.createStatement();
////    stmt.execute("CREATE TABLE SampleTable( Name VARCHAR(255), Image BLOB)");
////    System.out.println("Table Created");
////    //Inserting values
//    String query = "select * from validation_sheet_table where id=?";
//    PreparedStatement pstmt = con.prepareStatement(query);
//    pstmt.setInt(1, 1);
////    FileInputStream fin = new FileInputStream("E:\images\cat.jpg");
////    pstmt.setBlob(2, fin);
//    ResultSet rs=pstmt.executeQuery(query);
//    //Retrieving the data
////    ResultSet rs = stmt.executeQuery("select * from validation_sheet_table where id=?");
////    int i = 1;
//    
//    Blob blob = rs.getBlob("error_sheet");
////    System.out.println("Contents of the table are: ");
////    while(rs.next()) {
////       System.out.println(rs.getString("Name"));
////       Blob blob = rs.getBlob("Image");
////       byte byteArray[] = blob.getBytes(1,(int)blob.length());
////       FileOutputStream outPutStream = new
////       FileOutputStream("E:\images\blob_output"+i+".jpg");
////       outPutStream.write(byteArray);
////       System.out.println("E:\images\blob_output"+i+".jpg");
////       System.out.println();
////       i++;
	return id;
	        
    }
	
	
	

	
	
	
	


//	@Transactional
	public List fetchEncod(int reqNumber) throws SerialException, SQLException {


	
		
		
		
		
		
		Blob blob = null;
		Blob blob2 = null;
		String fileName=null;
		
//		  String jdbcURL = "jdbc:mysql://localhost:3306/mydb";
//	        String username = "root";
//	        String password = "root";
	        String jdbcURL = "jdbc:sqlserver://ptapps.titan.in:49172;databaseName=Support;encrypt=true;trustServerCertificate=true";
//	        String username = "mtovq";
//	        String password = "vQ!2021";
	        String username = "pbpm";
	        String password = "Product!23";
	 
	        
	 
	        Connection connection = null;
	 
	        try {
	            long start = System.currentTimeMillis();
	             
	          
	            connection = DriverManager.getConnection(jdbcURL, username, password);
	            connection.setAutoCommit(false);
	  
	            String sql =  "select * from validation_sheet_table where id=?";
	            PreparedStatement statement = connection.prepareStatement(sql);    
	             
	         
	             
//	            statement.setLong(1, 1);
	            
	            statement.setInt(1, reqNumber);
	              
//	            statement.executeQuery(sql);
	             
	            ResultSet rs=  statement.executeQuery();
	            //Retrieving the data
//	            ResultSet rs = stmt.executeQuery("select * from validation_sheet_table where id=?");
//	            int i = 1;
	            if(rs.next()){

	            	blob = rs.getBlob("error_sheet");
	            	blob2 = rs.getBlob("fixed_sheet");
	            	fileName = rs.getString("file_Name");
	            }
//	             blob = rs.getBlob(2);
	            
	           
	            rs.close();
	            statement.close();
	            connection.commit();
	            connection.close();
	            
	    
	        } catch (SQLException ex2) {
	            System.out.println("Database error");
	            ex2.printStackTrace();
	        }
	 
		
		
		
		
		
		
		
		
		
		
		
		
//	 //Registering the Driver
//    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//    //Getting the connection
//    String mysqlUrl = "jdbc:mysql://localhost:3306/mydb";
//    Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
//    System.out.println("Connection established......");
//    //Creating a table
//    Statement stmt = con.createStatement();
////    stmt.execute("CREATE TABLE SampleTable( Name VARCHAR(255), Image BLOB)");
////    System.out.println("Table Created");
////    //Inserting values
//    String query = "select * from validation_sheet_table where id=?";
//    PreparedStatement pstmt = con.prepareStatement(query);
//    pstmt.setInt(1, 1);
////    FileInputStream fin = new FileInputStream("E:\images\cat.jpg");
////    pstmt.setBlob(2, fin);
//    ResultSet rs=pstmt.executeQuery(query);
//    //Retrieving the data
////    ResultSet rs = stmt.executeQuery("select * from validation_sheet_table where id=?");
////    int i = 1;
//    
//    Blob blob = rs.getBlob("error_sheet");
////    System.out.println("Contents of the table are: ");
////    while(rs.next()) {
////       System.out.println(rs.getString("Name"));
////       Blob blob = rs.getBlob("Image");
////       byte byteArray[] = blob.getBytes(1,(int)blob.length());
////       FileOutputStream outPutStream = new
////       FileOutputStream("E:\images\blob_output"+i+".jpg");
////       outPutStream.write(byteArray);
////       System.out.println("E:\images\blob_output"+i+".jpg");
////       System.out.println();
////       i++;
	        List result= new ArrayList<>();
	        Blob[] Blob_result = new Blob[2];
	        Blob_result[0] = blob;
	        Blob_result[1] = blob2;
	        result.add(blob);
	        result.add(blob2);
	        result.add(fileName);
	return result;
    }
	
	


//	@Transactional
	public Map<String,String> fetchMasterData() throws SerialException, SQLException {


	
		
		Map<String,String> master_data_map= new HashMap<>();
		
		
		
		
		
		  String jdbcURL = "jdbc:mysql://localhost:3306/mydb";
	        String username = "root";
	        String password = "root";
	 
	        
	 
	        
	 
	        Connection connection = null;
	 
	        try {
	            long start = System.currentTimeMillis();
	             
	          
	            connection = DriverManager.getConnection(jdbcURL, username, password);
	            connection.setAutoCommit(false);
	  
	            String sql =  "select * from masterdata";
	            PreparedStatement statement = connection.prepareStatement(sql);    
	             
	         
	             
//	            statement.setLong(1, 1);
	            
	           
	              
//	            statement.executeQuery(sql);
	             
	            ResultSet rs=  statement.executeQuery();
	            //Retrieving the data
//	            ResultSet rs = stmt.executeQuery("select * from validation_sheet_table where id=?");
//	            int i = 1;
	            if(rs.next()){

	            	master_data_map.put(rs.getString(1), rs.getString(2));
	            	
	            }
//	             blob = rs.getBlob(2);
	            
	           
	            rs.close();
	            statement.close();
	            connection.commit();
	            connection.close();
	            
	    
	        } catch (SQLException ex2) {
	            System.out.println("Database error");
	            ex2.printStackTrace();
	        }

	        
	return master_data_map;
    }

	
	
	
	}

	
	
	
	
	
	
	
	
	
	
	

