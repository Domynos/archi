package archi.serveur;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Utils {

	static void sendBytes(byte[] myByteArray, Socket socket) throws IOException {
	    sendBytes(myByteArray, 0, myByteArray.length, socket);
	}

	static void sendBytes(byte[] myByteArray, int start, int len, Socket socket) throws IOException {
	    if (len < 0)
	        throw new IllegalArgumentException("Negative length not allowed");
	    if (start < 0 || start >= myByteArray.length)
	        throw new IndexOutOfBoundsException("Out of bounds: " + start);

	    OutputStream out = socket.getOutputStream(); 
	    DataOutputStream dos = new DataOutputStream(out);
	    
	    //dos.writeInt(len);
	    //dos.writeLong(len);
	    if (len > 0) {
	        dos.write(myByteArray, start, len);
	    }
	    dos.flush();
	}

}
