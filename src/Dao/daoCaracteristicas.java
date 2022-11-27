package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Conexion.conexion;
import Modelo.Caracteristicas;

public class daoCaracteristicas {
	conexion cx;

	public daoCaracteristicas() {
		cx = new conexion();
	}

	public boolean insertarCaracteristica(Caracteristicas carac) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement("INSERT INTO caracteristicas VALUES(null,?,?,?,?)");
			ps.setString(1, carac.getMarca());
			ps.setString(2, carac.getModelo());
			ps.setDouble(3, carac.getPrecio());
			ps.setString(4, carac.getImg());
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}

	}

	public ArrayList<Caracteristicas> consultaCaracteristicas() {
		ArrayList<Caracteristicas> lista = new ArrayList<Caracteristicas>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = cx.conectar().prepareStatement("SELECT * FROM caracteristicas");
			rs = ps.executeQuery();
			while (rs.next()) {
				Caracteristicas caracteristicas = new Caracteristicas();
				caracteristicas.setId(rs.getInt("id"));
				caracteristicas.setMarca(rs.getString("marca"));
				caracteristicas.setModelo(rs.getString("modelo"));
				caracteristicas.setPrecio(rs.getDouble("precio"));
				caracteristicas.setImg(rs.getString("img"));
				lista.add(caracteristicas);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;

	}

	public boolean eliminarCaracteristica(int id) {
		PreparedStatement ps = null;
		try {
			ps = cx.conectar().prepareStatement("DELETE FROM caracteristicas WHERE id =?");
			ps.setInt(1, id);
			ps.executeUpdate();
			cx.desconectar();
			return true;
		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}

	}

public boolean editarCaracteristica(Caracteristicas carac) {
	PreparedStatement ps = null;
	try {
		ps = cx.conectar().prepareStatement("UPDATE caracteristicas  SET marca=?,modelo=?,precio=?,img=? WHERE id=?");
		ps.setString(1, carac.getMarca());
		ps.setString(2, carac.getModelo());
		ps.setDouble(3, carac.getPrecio());
		ps.setString(4, carac.getImg());
		ps.setInt(4, carac.getId());
		ps.executeUpdate();
		cx.desconectar();
		return true;
	} catch (SQLException e) {

		e.printStackTrace();

		return false;
	}

}
}
