package pl.fis.logic;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.fis.data.DataBase;
import pl.fis.data.SpaceFleet;
import pl.fis.data.Spaceship;
import pl.fis.logic.errors.EntityNotFound;

@Service
public class SpaceFleetHandler
{
	@Autowired
	private DataBase db;

	public SpaceFleetHandler()
	{
	}

	public SpaceFleet getSpaceFleet()
	{
		return db.getFleet();
	}

	public void addSpaceship(Spaceship spaceship)
	{
		db.getFleet().getSpaceFleetShips().add(spaceship);
	}

	public Spaceship getSpaceship(String name)
	{
		for (Iterator<Spaceship> iter = db.getFleet().getSpaceFleetShips().iterator(); iter.hasNext();)
		{
			Spaceship ship = iter.next();
			if (ship.getName().equalsIgnoreCase(name))
				return ship;
		}
		throw new EntityNotFound(name);
//		return null;
	}
}
