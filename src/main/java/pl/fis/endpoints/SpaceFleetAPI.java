package pl.fis.endpoints;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.fis.data.SpaceFleet;
import pl.fis.data.Spaceship;
import pl.fis.logic.SpaceFleetHandler;
import pl.fis.logic.errors.EntityNotFound;

@Api(value = "Space-fleet endpoint. Provides funcionality to operate space fleet")
@RestController
@RequestMapping("/api/space-fleet")
public class SpaceFleetAPI
{
	@Autowired
	SpaceFleetHandler spacefleetHandler;

	@ApiOperation(value = "Retrive information about specific ship", notes = "Returns Json format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrived information about specific spaceship", response = Spaceship.class),
			@ApiResponse(code = 404, message = "Spaceship not found", response = EntityNotFound.class) })
	@RequestMapping(path = "/{spaceshipName}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public Spaceship getSpaceship(@PathVariable("spaceshipName") String name)
	{
		return spacefleetHandler.getSpaceship(name);
	}

	@ApiOperation(value = "Retrive available ships", notes = "Returns Json format")
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public SpaceFleet getSpaceFleet()
	{
		return spacefleetHandler.getSpaceFleet();
	}

	@ApiOperation(value = "Add ship to the fleet", notes = "Accepts Json format")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully added ship"),
			@ApiResponse(code = 400, message = "Spaceship invalid", response = MethodArgumentNotValidException.class) })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addSpaceship(@RequestBody @Valid Spaceship ship)
	{
		spacefleetHandler.addSpaceship(ship);
	}
}
