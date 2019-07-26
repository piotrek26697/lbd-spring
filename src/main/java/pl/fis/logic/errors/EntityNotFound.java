package pl.fis.logic.errors;

public class EntityNotFound extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EntityNotFound()
	{
		super("Entity not found");
	}
	public EntityNotFound(String message)
	{
		super(message);
	}
}
