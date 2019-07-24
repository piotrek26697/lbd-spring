package pl.fis.logic;

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
