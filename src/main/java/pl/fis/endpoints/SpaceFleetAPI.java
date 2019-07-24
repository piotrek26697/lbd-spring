package pl.fis.endpoints;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.fis.data.SpaceFleet;
import pl.fis.data.Spaceship;
import pl.fis.logic.SpaceFleetHandler;

@RestController
@RequestMapping("/api/space-fleet")
public class SpaceFleetAPI
{
	@Autowired
	SpaceFleetHandler spacefleetHandler;

	@RequestMapping(path = "/{spaceshipName}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Spaceship getSpaceship(@PathVariable("spaceshipName") String name)
	{
		return spacefleetHandler.getSpaceship(name);
	}

	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public SpaceFleet getSpaceFleet()
	{
		return spacefleetHandler.getSpaceFleet();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addSpaceship(@RequestBody @Valid Spaceship ship)
	{
		spacefleetHandler.addSpaceship(ship);
	}
}
