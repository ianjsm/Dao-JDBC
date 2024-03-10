package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)");
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			ps.setDouble(4, seller.getBaseSalary());
			ps.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
				System.out.println("Insert was completed");
			}
			else {
				throw new DbException("Error!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));

				Seller sel = new Seller();
				sel.setId(rs.getInt("id"));
				sel.setName(rs.getString("name"));
				sel.setEmail(rs.getString("email"));
				sel.setBirthDate(rs.getDate("birthDate"));
				sel.setBaseSalary(rs.getDouble("baseSalary"));
				sel.setDepartment(dep);
				return sel;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "ORDER BY Name");
			rs = ps.executeQuery();

			List<Seller> sellers = new ArrayList<>();
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));

				Seller sel = new Seller();
				sel.setId(rs.getInt("id"));
				sel.setName(rs.getString("name"));
				sel.setEmail(rs.getString("email"));
				sel.setBirthDate(rs.getDate("birthDate"));
				sel.setBaseSalary(rs.getDouble("baseSalary"));
				sel.setDepartment(dep);
				sellers.add(sel);
				return sellers;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();

			List<Seller> sellers = new ArrayList<>();
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));

				Seller sel = new Seller();
				sel.setId(rs.getInt("id"));
				sel.setName(rs.getString("name"));
				sel.setEmail(rs.getString("email"));
				sel.setBirthDate(rs.getDate("birthDate"));
				sel.setBaseSalary(rs.getDouble("baseSalary"));
				sel.setDepartment(dep);
				sellers.add(sel);
				return sellers;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}
}