package day19_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBClass {
	private String url;
	private String id;
	private String pwd;
	private Connection con;
	public DBClass() {
	//오라클의 기능을 자바에서 사용하기 위한, 무조건 처음 실행 시켜 준다
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@localhost:1521:xe";
			id = "java"; 
			pwd = "1234";
			con = DriverManager.getConnection(url,id,pwd);
			System.out.println(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/*
 1.드라이브 로드(오라클 기능 사용)
 2. 연결된 객체를 얻어온다
 3. 연결된 객체를 이용해서 명령어(쿼리문)을 전송할 수 있는 전송 객체를 얻어온다
 4. 전송 객체를 이용해서 데이터베이스에 전송후 결과를 얻어온다.
 5. 얻어온 결과는 int 또는 ResultSet으로 받는다
 */
	public ArrayList<StudentDTO> getUsers(){
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		String sql = "select * from newst";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setStNum( rs.getString("id") );
				dto.setName( rs.getString("name") );
				dto.setAge( rs.getInt("age") );
				list.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	public int saveData(String stNum, String name, int age) {
		// insert into newst values('111','홍길동',20);
		String sql =
		"insert into newst values('"+stNum+"','"+name+"',"+age+")";
		int result = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			//ResultSet rs = ps.executeQuery();
			// 저장 성공시 1을 반환, 실패시 catch이동이나 0을 반환
			result = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int saveData02(String stNum, String name, int age) {
		String sql = "insert into newst values(?, ?, ?)";
		int result = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, stNum);
			ps.setString(2, name);
			ps.setInt(3, age);
			result = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int delete(String userNum) {
		int result = 0;
		//delete from newst where id = 'userNum';
		String sql = "delete from newst where id = '"+userNum+"'";
		//String sql = "delete from newst where id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int modify(String stNum, String name, int age) {
		int result = 0;
		//update newst set name='홍길동', age=20 where id='test';
		String sql = "update newst set name=?, age=? where id = ? ";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, stNum);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
}




