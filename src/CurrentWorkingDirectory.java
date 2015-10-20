import java.io.File;
 public class CurrentWorkingDirectory {
	 
	 
 public String path() {
 File directory = new File ("./");

 try{
	String out = directory.getCanonicalPath(); 
	System.out.println(System.getProperty("os.name"));
    //if(System.getProperty("os.name").substring(0, 2) == "Win")
	out.replace('\\', '/');
    return out; 
 
 }catch(Exception e) {
 System.out.println("Exceptione is ="+e.getMessage());
  }
return "Error";
 }
}