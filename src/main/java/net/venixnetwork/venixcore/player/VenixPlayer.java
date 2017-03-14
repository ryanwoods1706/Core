package net.venixnetwork.venixcore.player;

import net.venixnetwork.venixcore.Core;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Ryan on 13/03/2017.
 */
public class VenixPlayer {

    private Core core;
    private UUID uuid;
    private InetAddress IP;
    private String group;

    public VenixPlayer(UUID uuid, InetAddress IP, Core core){
        this.uuid = uuid;
        this.IP = IP;
        this.core = core;
    }


    public void getInformation(){
        new BukkitRunnable(){
            public void run(){
                Connection connection = core.getSql().getConnection();
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                PreparedStatement statement1 = null;
                try{
                    statement = connection.prepareStatement("SELECT * FROM `user_data` WHERE `uuid` = ?;");
                    statement.setString(1, uuid.toString());
                    statement.executeQuery();
                    resultSet = statement.getResultSet();
                    if (resultSet.next()){
                        group = resultSet.getString("group");
                    }else{
                        statement1 = connection.prepareStatement("INSERT INTO `user_data` (uuid, ip) VALUES (?, ?);");
                        statement1.setString(1, uuid.toString());
                        statement1.setString(2, IP.toString());
                        statement1.executeUpdate();
                        statement1.close();
                        group = "default";
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }finally {
                    try {
                        if (statement != null) {
                            statement.close();
                        }
                        if (statement1 != null){
                            statement1.close();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }

            }
        }.runTaskAsynchronously(this.core);
    }

    public synchronized void saveData(){
        try{
            Connection connection = this.core.getSql().getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE `user_data` SET `ip` = ?, `group` = ? WHERE `uuid` = ?");
            statement.setString(1, this.IP.toString());
            statement.setString(2, this.group);
            statement.setString(3, this.uuid.toString());
            statement.executeUpdate();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public String getGroup(){ return this.group;}
    public void setGroup(String group){
        this.group = group;
    }
    public InetAddress getIP(){ return this.IP;}
}
