package net.venixnetwork.venixcore.sql;

import net.venixnetwork.venixcore.Core;
import net.venixnetwork.venixcore.permissions.Group;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.UUID;

/**
 * Created by Ryan on 13/03/2017.
 */
public class SQL {

    private Core core;
    private Connection connection;
    private String ip;
    private String database;
    private String username;
    private String password;

    public SQL(Core core) {
        this.core = core;
        this.ip = this.core.getConfig().getString("SQL.ip");
        this.database = this.core.getConfig().getString("SQL.database");
        this.username = this.core.getConfig().getString("SQL.user");
        this.password = this.core.getConfig().getString("SQL.password");
    }


    public Connection getConnection() {
        try {
            if (this.connection.isClosed() || this.connection == null) {
                Bukkit.getLogger().info("[SQL] Attempting to re-open the connection with the database!");
                openConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("[SQL] A fatal error occured whilst checking connection");
            return null;
        }
        return this.connection;
    }

    public void openConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database, username, password);
            generateTables();
            Bukkit.getLogger().info("[SQL] Connected");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("[SQL] Failed to connect!");
            Bukkit.getLogger().info("[SQL] This plugin is SQL dependant!");
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateTables() {
        try {
            Statement statement = this.connection.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS `user_data`(`uuid` VARCHAR(60),`group` VARCHAR(60) NOT NULL DEFAULT 'default', `ip` VARCHAR(15));");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doesUserExist(UUID uuid) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM `user_data` WHERE `uuid` = ?;");
            statement.setString(1, uuid.toString());
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public synchronized void updateOfflinerPlayerRank(UUID uuid, String group) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("UPDATE `user_data` SET" +
                    " `group` = " + group + " WHERE `uuid` = ?;");
            statement.setString(1, group);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Group getUserGroup(final UUID uuid) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM `user_data` WHERE `uuid` = ?;");
            statement.setString(1, uuid.toString());
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Group group = core.getGroupManager().getPlayerGroupCla(resultSet.getString("group"));
                return group;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getTotalGroupMembers(String group){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            statement = connection.prepareStatement("SELECT COUNT(*) AS groupTotal FROM user_data WHERE group = ?");
            statement.setString(1, group);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getLong("groupTotal");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

}
