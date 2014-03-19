package operation;

public abstract class Operation {
	
	String sibPath;
	String folderPath;
	
	abstract String compress();
	
	abstract void decompress();

}
