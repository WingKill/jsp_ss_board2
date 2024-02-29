package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.BDto;

public class BDao {
	private DataSource dataSource = null;
	private Context context = null;
	
	public BDao() {
		try {
			context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:/comp/env/jdbc/oracle");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public List<BDto> list(){
		List<BDto> dtos = new ArrayList<>();
		String sql = "select * from mvc_board order by bid desc, bstep asc";
		try(Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();) {
			while(rs.next()) {
				int bid = rs.getInt("bid");     
				String bname = rs.getString("bname");   
				String btitle = rs.getString("btitle");             
				String bcontent = rs.getString("bcontent");          
				String bdate = rs.getString("bdate");                      
				int bhit = rs.getInt("bhit");             
				int bgroup = rs.getInt("bgroup");                
				int bstep = rs.getInt("bstep");                
				int bindent = rs.getInt("bindent");
				
				BDto dto = new BDto(bid, bname, btitle, bcontent, bdate, bhit, bgroup, bstep, bindent);
				dtos.add(dto);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dtos;
	}

	public void insert(BDto dto) {
		String sql = "insert into mvc_board(BID, BNAME, BTITLE, BCONTENT, BDATE, BHIT, BGROUP, BSTEP, BINDENT) " 
					+"values (MVC_BOARD_SEQ.nextval, ?, ?, ?, sysdate, 0, MVC_BOARD_SEQ.currval, 0, 0)";		
		try(Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, dto.getBname());
			pstmt.setString(2, dto.getBtitle());
			pstmt.setString(3, dto.getBcontent());
			int result = pstmt.executeUpdate();
			
			System.out.println("insert된 개수 : " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BDto contentView(String bId) {
		BDto dto = new BDto();
		ResultSet rs = null;
		String sql = "select * from mvc_board where bid = ?";
		try(Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, Integer.valueOf(bId));
			rs = pstmt.executeQuery();						
			while(rs.next()) {
				int bid = rs.getInt("bid");     
				String bname = rs.getString("bname");   
				String btitle = rs.getString("btitle");             
				String bcontent = rs.getString("bcontent");          
				String bdate = rs.getString("bdate");                      
				int bhit = rs.getInt("bhit");             
				int bgroup = rs.getInt("bgroup");                
				int bstep = rs.getInt("bstep");                
				int bindent = rs.getInt("bindent");
				
				dto = new BDto(bid, bname, btitle, bcontent, bdate, bhit, bgroup, bstep, bindent);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}	
		return dto;
	}
	
	public void update(BDto dto) {
		String sql = "update mvc_board set bname = ?, btitle = ?, bcontent = ? where bid = ?";		
		try(Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, dto.getBname());
			pstmt.setString(2, dto.getBtitle());
			pstmt.setString(3, dto.getBcontent());
			pstmt.setInt(4, dto.getBid());
			int result = pstmt.executeUpdate();
			
			System.out.println("insert된 개수 : " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	
}
