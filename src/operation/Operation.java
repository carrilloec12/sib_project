package operation;

public abstract class Operation {
	
	String sibPath;
	String folderPath;
	
	public Operation() {
		// TODO Auto-generated constructor stub
	}
	
	Operation(String line){
		
	}
	
	abstract String compress();
	
	abstract void decompress();

}
