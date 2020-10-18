package frc5124.robot2020;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;



/**
 * Network Table for Simulation
 */
public class SimTable extends Thread {
    NetworkTableInstance simTable;
    RobotContainer robotContainer;
    Thread simSocket;
    SimTable (RobotContainer robotContainer) {
        this.robotContainer = robotContainer;
    }

      public void run() {
      
        NetworkTableInstance simTable = NetworkTableInstance.create();
        NetworkTable table = simTable.getTable("SimTable");
        NetworkTableEntry entryRightLeaderPower = table.getEntry("RightLeaderPower");
        NetworkTableEntry entryLeftLeaderPower = table.getEntry("LeftLeaderPower");
        simTable.startClientTeam(5124);  // where TEAM=190, 294, etc, or use inst.startClient("hostname") or similar
        simTable.startDSClient();  // recommended if running on DS computer; this gets the robot IP from the DS
        simSocket = new SimSocket(simTable);
        simSocket.start();

        while (true) {
          try {
            Thread.sleep(10);
          } catch (InterruptedException ex) {
            System.out.println("interrupted");
            return;
          }

          testEntry.setDouble(robotContainer.driveTrain.rightLeader.get());
          
          
        }
      }
}

/**
 * Socket to Simulator
 */
class SimSocket extends Thread 
{
    
    NetworkTableInstance simTable;
    SimSocket(NetworkTableInstance simTable) {
        this.simTable = simTable;
    }

    public void run() 
    {
        ServerSocket serverSocket;
        Socket socket;
        InputStream inputStream;
        OutputStream outputStream;
        try {
            serverSocket = new ServerSocket(4343, 10);
            System.out.print("\n\n\n WAITING FOR CLIENT \n\n\n" );
            socket = serverSocket.accept();
            System.out.print("\n\n\n CLIENT ACCEPTED \n\n\n" );
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            return;
        }

        NetworkTable table = simTable.getTable("SimTable");
        NetworkTableEntry testEntry = table.getEntry("x");

        Runtime.getRuntime().addShutdownHook(new Thread() { public void run(){ try { 
            socket.close();
            serverSocket.close(); 
        } catch (IOException e) {
               return;
            }
        }});
    

        while (true) {
            try {
              Thread.sleep(10);
            } catch (InterruptedException ex) {
              System.out.println("interrupted");
              return;
            }
        

            try{

            //In
            byte[] lenBytes = new byte[4];
            inputStream.read(lenBytes, 0, 4);
            int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
                      ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
            byte[] receivedBytes = new byte[len];
            inputStream.read(receivedBytes, 0, len);
            String received = new String(receivedBytes, 0, len);
    
            System.out.print("\n\n RECIEVED " + received + "\n\n");
            

            // Out
            String toSend = "Echo: NO   " + received;
            byte[] toSendBytes = toSend.getBytes();
            int toSendLen = toSendBytes.length;
            byte[] toSendLenBytes = new byte[4];
            toSendLenBytes[0] = (byte)(toSendLen & 0xff);
            toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
            toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
            toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
            outputStream.write(toSendLenBytes);
            outputStream.write(toSendBytes);
            } catch (IOException e) {
                return;
            }
           
      }
    }
}

