import java.io.File;
 public class CurrentWorkingDirectory {
	 
	 
 public String path() {
 File directory = new File (".");

 try{
 
	String out = directory.getCanonicalPath(); 
    out.replace('\\', '/');
    return out; 
 
 }catch(Exception e) {
 System.out.println("Exceptione is ="+e.getMessage());
  }
return "Error";
 }
}