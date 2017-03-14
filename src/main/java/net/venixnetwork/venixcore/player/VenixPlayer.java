package net.venixnetwork.venixcore.player;

import net.venixnetwork.venixcore.Core;
import org.bukkit.scheduler.BukkitRunnable;

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
    private String group;

    public VenixPlayer(Core core){
        this.core = core;
    }

    public VenixPlayer(UUID uuid){
        this.uuid = uuid;
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
                        group = resultSet.getString("grouo");
                    }else{
                        statement1 = connection.prepareStatement("INSERT INTO `user_data` (uuid) VALUES (?);");
                        statement.setString(1, uuid.toString());
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


    public String getGroup(){ return this.group;}
}
