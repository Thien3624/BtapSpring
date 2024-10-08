package vn.iotstar.DAO.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vn.iotstar.DAO.IUserDAO;
import vn.iotstar.configs.DBconnect;
import vn.iotstar.models.UserModel;

public class UserDaoImpl implements IUserDAO{
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	@Override
	public UserModel get(String username) {
		return findByUserName(username);
	}
	@Override
	public UserModel findByUserName(String username) {
		String sql = "SELECT * FROM [User] WHERE username = ?" ;
		try {
			conn = new DBconnect().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) 
			{
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(username);
				user.setFullname(rs.getString("fullname"));
				user.setPassword(rs.getString("password"));
				user.setImages(rs.getString("avatar"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setPhone(rs.getString("phone"));
				user.setCreatedDate(rs.getDate("createdDate"));
				return user;
			}
			} catch (Exception e) 
			{
				e.printStackTrace();	
			}
		return null;
	}
	@Override
	public void insert(UserModel user) {
		String sql = "INSERT INTO [User](email, username, fullname, password, avatar, roleid,phone, createddate) VALUES (?,?,?,?,?,?,?,?)";
		try {
				conn = new DBconnect().getConnection();
				ps = conn.prepareStatement(sql);
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getUsername());
				ps.setString(3, user.getFullname());
				ps.setString(4, user.getPassword());
				ps.setString(5, user.getImages());
				ps.setInt(6,user.getRoleid());
				ps.setString(7,user.getPhone());
				ps.setDate(8, (Date) user.getCreatedDate());
				ps.executeUpdate();
				}catch (Exception e) 
				{
					e.printStackTrace();
				}	
	}
	@Override
	public boolean checkExistEmail(String email) {
		boolean duplicate = false;
		String query = "select * from [user] where email = ?" ;
		try {
			conn = new DBconnect().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1,email);
			rs = ps.executeQuery();
			if(rs.next())
			{
				duplicate = true;
			}
			ps.close();
			conn.close();
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return duplicate;
	}
	@Override
	public boolean checkExistPhone(String phone) {
		boolean duplicate = false;
		String query = "select * from [user] where phone = ?";
		try {
			conn = new DBconnect().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1,phone);
			rs = ps.executeQuery();
			if(rs.next())
			{
				duplicate = true;
			}
			ps.close();
			conn.close();
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return duplicate;
	}
	@Override
	public boolean checkExistUsername(String username) {
		boolean duplicate = false;
		String query = "select * from [user] where username = ?";
		try {
			conn = new DBconnect().getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1,username);
			rs = ps.executeQuery();
			if(rs.next())
			{
				duplicate = true;
			}
			ps.close();
			conn.close();
		}catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return duplicate;
	}
}
