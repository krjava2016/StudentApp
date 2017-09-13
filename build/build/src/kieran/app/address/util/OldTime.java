package kieran.app.address.util;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;

public class OldTime {

	
		
	
		public long timeInMs;
		public String path = System.getProperty("user.dir")+"Time.txt";
	
		public void setTimeTxt(){
			try {
				
		
		String sendTime;
			
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		sendTime = Long.toString(timestamp.getTime());
		
		Files.write(Paths.get(path),sendTime.getBytes());
	
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		
		
			}
		
		public long getOldTime() throws IOException{
			
			try {
				
			java.util.List<String>lines = Files.readAllLines(Paths.get(path),Charset.defaultCharset());
			for(String line : lines){
				
				String string = line;
				
				timeInMs = Long.parseLong(string);
			}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return timeInMs;
				

			
		}
		
	}
	


